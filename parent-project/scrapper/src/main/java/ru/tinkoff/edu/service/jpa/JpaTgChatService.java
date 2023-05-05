package ru.tinkoff.edu.service.jpa;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.exception.ResourceNotFoundException;
import ru.tinkoff.edu.repository.jpa.ChatLinkEntityRepository;
import ru.tinkoff.edu.repository.jpa.LinkEntityRepository;
import ru.tinkoff.edu.repository.jpa.TgChatEntityRepository;
import ru.tinkoff.edu.repository.jpa.entity.LinkEntity;
import ru.tinkoff.edu.repository.jpa.entity.TgChatEntity;
import ru.tinkoff.edu.service.TgChatService;

@AllArgsConstructor
public class JpaTgChatService implements TgChatService {
    private final TgChatEntityRepository tgChatEntityRepository;
    private final LinkEntityRepository linkEntityRepository;
    private final ChatLinkEntityRepository chatLinkEntityRepository;

    @Transactional
    @Override
    public void register(Long tgChatId) {
        tgChatEntityRepository.insertTgChat(tgChatId);
    }

    @Transactional
    @Override
    public void unregister(Long tgChatId) {
        TgChatEntity tgChatEntity = tgChatEntityRepository.findByTgChatId(tgChatId)
                .orElseThrow(() -> new ResourceNotFoundException("Tg chat '" + tgChatId + "' was not found"));
        List<LinkEntity> trackedLinks = chatLinkEntityRepository.getLinksByTgChatId(tgChatId);
        for (LinkEntity link: trackedLinks) {
            untrackLink(link, tgChatEntity);
        }
        tgChatEntityRepository.deleteById(tgChatEntity.getId());
    }

    private void untrackLink(LinkEntity link, TgChatEntity tgChat) {
        chatLinkEntityRepository.deleteByTgChatAndLink(tgChat, link);
        if (chatLinkEntityRepository.getTgChatsByLinkId(link.getId()).size() == 0) {
            linkEntityRepository.deleteById(link.getId());
        }
    }
}
