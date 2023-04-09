package ru.tinkoff.edu.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.client.GitHubClient;
import ru.tinkoff.edu.client.StackOverflowClient;
import ru.tinkoff.edu.parser.GitHubLinkParser;
import ru.tinkoff.edu.parser.StackOverflowLinkParser;
import ru.tinkoff.edu.record.GitHubRecord;
import ru.tinkoff.edu.record.StackOverflowRecord;
import ru.tinkoff.edu.repository.dto.Link;
import ru.tinkoff.edu.response.QuestionResponse;
import ru.tinkoff.edu.response.RepositoryResponse;

import java.net.URI;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class LinkCreator {
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    public Link createLink(URI url) {
        GitHubLinkParser gitHubLinkParser = new GitHubLinkParser(null);
        StackOverflowLinkParser stackOverflowLinkParser = new StackOverflowLinkParser(gitHubLinkParser);
        Record record = stackOverflowLinkParser.parseLink(url.toString());
        if (record == null) {
            throw new RuntimeException("Invalid link '" + url + "'");
        }
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
