package ru.tinkoff.edu.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.exception.ResourceNotFoundException;
import ru.tinkoff.edu.repository.dto.Link;
import ru.tinkoff.edu.repository.dto.TgChat;
import ru.tinkoff.edu.repository.mapper.LinkMapper;
import ru.tinkoff.edu.repository.mapper.TgChatMapper;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@Repository
public class ChatLinkRepository {
    private final LinkRepository linkRepository;
    private final TgChatRepository tgChatRepository;
    private final JdbcTemplate jdbcTemplate;
    private final TgChatMapper tgChatMapper;
    private final LinkMapper linkMapper;

    public void registerChat(Long tgChatId) {
        tgChatRepository.add(tgChatId);
    }

    @Transactional
    public void unregisterChat(Long tgChatId) {
        List<Link> trackedLinks = getAllLinks(tgChatId);
        for (Link link: trackedLinks) {
            untrackLink(tgChatId, link.getLink());
        }
        tgChatRepository.remove(tgChatId);
    }

    @Transactional
    public List<Link> getAllLinks(Long tgChatId) {
        TgChat tgChat = tgChatRepository.get(tgChatId);
        if (tgChat == null) {
            throw new ResourceNotFoundException("Tg chat '" + tgChatId + "' was not found");
        }
        return jdbcTemplate.query("select * from link where id in (select link_id from chat_link cl " +
                "join chat c on cl.chat_id=c.id where c.id=?)", linkMapper, tgChat.getId());
    }

    @Transactional
    public Link trackLink(Long tgChatId, Link url) {
        Link link = linkRepository.get(url.getLink());
        if (link == null) {
            link = linkRepository.add(url);
        }
        TgChat tgChat = tgChatRepository.get(tgChatId);
        if (tgChat == null) {
            throw new ResourceNotFoundException("Tg chat '" + tgChatId + "' was not found");
        }
        int rowCount = jdbcTemplate.update("insert into chat_link (chat_id, link_id) values (?, ?)", tgChat.getId(), link.getId());
        if (rowCount == 0) {
            throw new RuntimeException("Error while adding tracked link '" + url + "' for tg chat '" + tgChatId + "'");
        }
        return link;
    }

    @Transactional
    public Link untrackLink(Long tgChatId, URI url) {
        Link link = linkRepository.get(url);
        if (link == null) {
            throw new ResourceNotFoundException("Link '" + url + "' was not found");
        }
        TgChat tgChat = tgChatRepository.get(tgChatId);
        if (tgChat == null) {
            throw new ResourceNotFoundException("Tg chat '" + tgChatId + "' was not found");
        }
        int rowCount = jdbcTemplate.update("delete from chat_link where chat_id=? and link_id=?", tgChat.getId(), link.getId());
        if (rowCount == 0) {
            throw new RuntimeException("Error while untracking link '" + url + "' for tg chat '" + tgChatId + "'");
        }
        if (getLinkCount(link.getId()).equals(0)) {
            linkRepository.remove(link.getLink());
        }
        return link;
    }

    public List<TgChat> getChatsForLink(Link link) {
        return jdbcTemplate.query("select * from chat where id in (select chat_id from chat_link where link_id=?)", tgChatMapper);
    }

    private Integer getLinkCount(Long linkId) {
        return jdbcTemplate.queryForObject("select count(*) from chat_link where link_id=?", Integer.class, linkId);
    }
}
