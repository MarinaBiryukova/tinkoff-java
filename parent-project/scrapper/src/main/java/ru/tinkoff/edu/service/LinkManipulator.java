package ru.tinkoff.edu.service;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.client.GitHubClient;
import ru.tinkoff.edu.client.StackOverflowClient;
import ru.tinkoff.edu.parser.GitHubLinkParser;
import ru.tinkoff.edu.parser.StackOverflowLinkParser;
import ru.tinkoff.edu.record.GitHubRecord;
import ru.tinkoff.edu.record.StackOverflowRecord;
import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.repository.jpa.entity.LinkEntity;
import ru.tinkoff.edu.response.QuestionResponse;
import ru.tinkoff.edu.response.RepositoryResponse;

@AllArgsConstructor
@Service
public class LinkManipulator {
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final GitHubLinkParser gitHubLinkParser = new GitHubLinkParser(null);
    private final StackOverflowLinkParser stackOverflowLinkParser = new StackOverflowLinkParser(gitHubLinkParser);

    public Link createLink(URI url) {
        Record apiRecord = stackOverflowLinkParser.parseLink(url.toString());
        if (apiRecord == null) {
            throw new RuntimeException("Invalid link '" + url + "'");
        }
        Link link = new Link();
        link.setLink(url);
        link.setLastUpdate(OffsetDateTime.now());
        if (apiRecord instanceof GitHubRecord) {
            RepositoryResponse response = gitHubClient.getRepoInfo(
                ((GitHubRecord) apiRecord).username(),
                ((GitHubRecord) apiRecord).repo()
            );
            link.setLastActivity(response.updated_at());
            link.setOpenIssuesCount(response.open_issues_count());
        }
        if (apiRecord instanceof StackOverflowRecord) {
            QuestionResponse response =
                stackOverflowClient.getQuestionInfo(((StackOverflowRecord) apiRecord).questionId());
            link.setLastActivity(response.last_activity_date());
            link.setAnswerCount(response.answer_count());
        }
        return link;
    }

    public LinkEntity createLinkEntity(URI url) {
        Record apiRecord = stackOverflowLinkParser.parseLink(url.toString());
        if (apiRecord == null) {
            throw new RuntimeException("Invalid link '" + url + "'");
        }
        LinkEntity link = new LinkEntity();
        link.setLink(url.toString());
        link.setLastUpdate(OffsetDateTime.now());
        if (apiRecord instanceof GitHubRecord) {
            RepositoryResponse response = gitHubClient.getRepoInfo(
                ((GitHubRecord) apiRecord).username(),
                ((GitHubRecord) apiRecord).repo()
            );
            link.setLastActivity(response.updated_at());
            link.setOpenIssuesCount(response.open_issues_count());
        }
        if (apiRecord instanceof StackOverflowRecord) {
            QuestionResponse response =
                stackOverflowClient.getQuestionInfo(((StackOverflowRecord) apiRecord).questionId());
            link.setLastActivity(response.last_activity_date());
            link.setAnswerCount(response.answer_count());
        }
        return link;
    }

    public Record getRecord(Link link) {
        return stackOverflowLinkParser.parseLink(link.getLink().toString());
    }

    public RepositoryResponse getResponse(GitHubRecord gitHubRecord) {
        return gitHubClient.getRepoInfo(gitHubRecord.username(), gitHubRecord.repo());
    }

    public QuestionResponse getResponse(StackOverflowRecord stackOverflowRecord) {
        return stackOverflowClient.getQuestionInfo(stackOverflowRecord.questionId());
    }
}
