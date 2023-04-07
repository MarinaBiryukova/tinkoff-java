package ru.tinkoff.edu.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.client.BotClient;
import ru.tinkoff.edu.client.GitHubClient;
import ru.tinkoff.edu.client.StackOverflowClient;
import ru.tinkoff.edu.parser.GitHubLinkParser;
import ru.tinkoff.edu.parser.StackOverflowLinkParser;
import ru.tinkoff.edu.record.GitHubRecord;
import ru.tinkoff.edu.record.StackOverflowRecord;
import ru.tinkoff.edu.repository.ChatLinkRepository;
import ru.tinkoff.edu.repository.LinkRepository;
import ru.tinkoff.edu.repository.dto.Link;
import ru.tinkoff.edu.repository.dto.TgChat;
import ru.tinkoff.edu.request.LinkUpdate;
import ru.tinkoff.edu.response.QuestionResponse;
import ru.tinkoff.edu.response.RepositoryResponse;
import ru.tinkoff.edu.service.LinkUpdater;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@AllArgsConstructor
@Service
public class JdbcLinkUpdater implements LinkUpdater {
    private final ChatLinkRepository chatLinkRepository;
    private final LinkRepository linkRepository;
    private final BotClient botClient;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final GitHubLinkParser gitHubLinkParser = new GitHubLinkParser(null);
    private final StackOverflowLinkParser stackOverflowLinkParser = new StackOverflowLinkParser(gitHubLinkParser);

    @Override
    public void update(Link link) {
        Record record = stackOverflowLinkParser.parseLink(link.getLink().toString());
        if (record instanceof StackOverflowRecord) {
            QuestionResponse response = stackOverflowClient.getQuestionInfo(((StackOverflowRecord) record).questionId());
            link.setLastUpdate(OffsetDateTime.now(ZoneId.systemDefault()));
            if (!response.answer_count().equals(link.getAnswerCount())) {
                link.setLastActivity(response.last_activity_date());
                link.setAnswerCount(response.answer_count());
                List<Long> chatIds = chatLinkRepository.getChatsForLink(link).stream().map(TgChat::getId).toList();
                sendUpdate(link, "In question '" + response.question_id() + "' new answers have been added!", chatIds);
            } else if (response.last_activity_date().isAfter(link.getLastActivity())) {
                link.setLastActivity(response.last_activity_date());
                List<Long> chatIds = chatLinkRepository.getChatsForLink(link).stream().map(TgChat::getId).toList();
                sendUpdate(link, "Question '" + response.question_id() + "' has updates!", chatIds);
            }
            linkRepository.update(link);
        } else if (record instanceof GitHubRecord) {
            RepositoryResponse response = gitHubClient.getRepoInfo(((GitHubRecord) record).username(), ((GitHubRecord) record).repo());
            link.setLastUpdate(OffsetDateTime.now());
            if (!response.open_issues_count().equals(link.getOpenIssuesCount())) {
                link.setLastActivity(response.updated_at());
                link.setOpenIssuesCount(response.open_issues_count());
                List<Long> chatIds = chatLinkRepository.getChatsForLink(link).stream().map(TgChat::getId).toList();
                sendUpdate(link, "In repository '" + response.full_name() + "' new open issues have been added!", chatIds);
            } else if (response.updated_at().isAfter(link.getLastActivity())) {
                link.setLastActivity(response.updated_at());
                List<Long> chatIds = chatLinkRepository.getChatsForLink(link).stream().map(TgChat::getId).toList();
                sendUpdate(link, "Repository '" + response.full_name() + "' has updates!", chatIds);
            }
            linkRepository.update(link);
        }
    }

    private void sendUpdate(Link link, String description, List<Long> tgChatIds) {
        LinkUpdate request = new LinkUpdate();
        request.setId(link.getId());
        request.setUrl(link.getLink());
        request.setDescription(description);
        request.setTgChatIds(tgChatIds);
        botClient.sendUpdate(request);
    }
}
