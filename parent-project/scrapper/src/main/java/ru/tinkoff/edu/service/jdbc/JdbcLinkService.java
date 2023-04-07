package ru.tinkoff.edu.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.client.GitHubClient;
import ru.tinkoff.edu.client.StackOverflowClient;
import ru.tinkoff.edu.converter.Converter;
import ru.tinkoff.edu.parser.GitHubLinkParser;
import ru.tinkoff.edu.parser.StackOverflowLinkParser;
import ru.tinkoff.edu.record.GitHubRecord;
import ru.tinkoff.edu.record.StackOverflowRecord;
import ru.tinkoff.edu.repository.ChatLinkRepository;
import ru.tinkoff.edu.repository.dto.Link;
import ru.tinkoff.edu.response.LinkResponse;
import ru.tinkoff.edu.response.ListLinksResponse;
import ru.tinkoff.edu.response.QuestionResponse;
import ru.tinkoff.edu.response.RepositoryResponse;
import ru.tinkoff.edu.service.LinkService;

import java.net.URI;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class JdbcLinkService implements LinkService {
    private final ChatLinkRepository repository;
    private final Converter converter;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Override
    public LinkResponse add(Long tgChatId, URI url) {
        Record record = getRecord(url);
        if (record == null) {
            throw new RuntimeException("Invalid link '" + url + "'");
        }
        return converter.linkToLinkResponse(repository.trackLink(tgChatId, createLink(url, record)));
    }

    @Override
    public LinkResponse remove(Long tgChatId, URI url) {
        return converter.linkToLinkResponse(repository.untrackLink(tgChatId, url));
    }

    @Override
    public ListLinksResponse listAll(Long tgChatId) {
        return converter.linksToListLinksResponse(repository.getAllLinks(tgChatId));
    }

    private Record getRecord(URI url) {
        GitHubLinkParser gitHubLinkParser = new GitHubLinkParser(null);
        StackOverflowLinkParser stackOverflowLinkParser = new StackOverflowLinkParser(gitHubLinkParser);
        return stackOverflowLinkParser.parseLink(url.toString());
    }

    private Link createLink(URI url, Record record) {
        Link link = new Link();
        link.setLink(url);
        link.setLastUpdate(OffsetDateTime.now());
        if (record instanceof GitHubRecord) {
            RepositoryResponse response = gitHubClient.getRepoInfo(((GitHubRecord) record).username(),
                    ((GitHubRecord) record).repo());
            link.setLastActivity(response.updated_at());
            link.setOpenIssuesCount(response.open_issues_count());
        }
        if (record instanceof StackOverflowRecord) {
            QuestionResponse response = stackOverflowClient.getQuestionInfo(((StackOverflowRecord) record).questionId());
            link.setLastActivity(response.last_activity_date());
            link.setAnswerCount(response.answer_count());
        }
        return link;
    }
}
