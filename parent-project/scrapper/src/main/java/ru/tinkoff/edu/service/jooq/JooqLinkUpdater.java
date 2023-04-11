package ru.tinkoff.edu.service.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.client.BotClient;
import ru.tinkoff.edu.client.GitHubClient;
import ru.tinkoff.edu.client.StackOverflowClient;
import ru.tinkoff.edu.domain.jooq.Tables;
import ru.tinkoff.edu.parser.GitHubLinkParser;
import ru.tinkoff.edu.parser.StackOverflowLinkParser;
import ru.tinkoff.edu.record.GitHubRecord;
import ru.tinkoff.edu.record.StackOverflowRecord;
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
public class JooqLinkUpdater implements LinkUpdater {
    private final BotClient botClient;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final GitHubLinkParser gitHubLinkParser = new GitHubLinkParser(null);
    private final StackOverflowLinkParser stackOverflowLinkParser = new StackOverflowLinkParser(gitHubLinkParser);
    private DSLContext context;

    @Override
    public void update(Link link) {
        Record record = stackOverflowLinkParser.parseLink(link.getLink().toString());
        if (record instanceof StackOverflowRecord) {
            QuestionResponse response = stackOverflowClient.getQuestionInfo(((StackOverflowRecord) record).questionId());
            link.setLastUpdate(OffsetDateTime.now(ZoneId.systemDefault()));
            if (!response.answer_count().equals(link.getAnswerCount())) {
                link.setLastActivity(response.last_activity_date());
                link.setAnswerCount(response.answer_count());
                List<Long> chatIds = getChatsForLink(link).stream().map(TgChat::getTgChatId).toList();
                sendUpdate(link, "In question '" + response.question_id() + "' new answers have been added!", chatIds);
            } else if (response.last_activity_date().isAfter(link.getLastActivity())) {
                link.setLastActivity(response.last_activity_date());
                List<Long> chatIds = getChatsForLink(link).stream().map(TgChat::getTgChatId).toList();
                sendUpdate(link, "Question '" + response.question_id() + "' has updates!", chatIds);
            }
            updateLink(link);
        } else if (record instanceof GitHubRecord) {
            RepositoryResponse response = gitHubClient.getRepoInfo(((GitHubRecord) record).username(), ((GitHubRecord) record).repo());
            link.setLastUpdate(OffsetDateTime.now());
            if (!response.open_issues_count().equals(link.getOpenIssuesCount())) {
                link.setLastActivity(response.updated_at());
                link.setOpenIssuesCount(response.open_issues_count());
                List<Long> chatIds = getChatsForLink(link).stream().map(TgChat::getTgChatId).toList();
                String desc = (response.open_issues_count() > link.getOpenIssuesCount()) ?
                        "In repository '" + response.full_name() + "' new open issues have been added!" :
                        "In repository '" + response.full_name() + "' issues have been closed!";
                sendUpdate(link, desc, chatIds);
            } else if (response.updated_at().isAfter(link.getLastActivity())) {
                link.setLastActivity(response.updated_at());
                List<Long> chatIds = getChatsForLink(link).stream().map(TgChat::getTgChatId).toList();
                sendUpdate(link, "Repository '" + response.full_name() + "' has updates!", chatIds);
            }
            updateLink(link);
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

    private List<TgChat> getChatsForLink(Link link) {
        return context.select(Tables.CHAT.fields()).from(Tables.CHAT_LINK).join(Tables.CHAT).on(Tables.CHAT.ID.eq(Tables.CHAT_LINK.CHAT_ID))
                .where(Tables.CHAT_LINK.LINK_ID.eq(link.getId())).fetchInto(TgChat.class);
    }

    private void updateLink(Link link) {
        context.update(Tables.LINK).set(Tables.LINK.LAST_UPDATE, link.getLastUpdate().toLocalDateTime())
                .set(Tables.LINK.LAST_ACTIVITY, link.getLastActivity().toLocalDateTime())
                .set(Tables.LINK.ANSWER_COUNT, link.getAnswerCount())
                .set(Tables.LINK.OPEN_ISSUES_COUNT, link.getOpenIssuesCount())
                .where(Tables.LINK.ID.eq(link.getId())).execute();
    }
}
