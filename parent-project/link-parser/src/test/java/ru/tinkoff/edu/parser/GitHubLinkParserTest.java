package ru.tinkoff.edu.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.record.GitHubRecord;

public class GitHubLinkParserTest {
    private final GitHubLinkParser gitHubLinkParser = new GitHubLinkParser(null);
    private final String username = "MarinaBiryukova";
    private final String repo = "tinkoff-java";

    @Test
    public void validLinkParse() {
        String link = "https://github.com/" + username + "/" + repo + "/";

        Record record = gitHubLinkParser.parseLink(link);

        Assertions.assertNotNull(record);
        Assertions.assertTrue(record instanceof GitHubRecord);
        Assertions.assertEquals(username, ((GitHubRecord)record).username());
        Assertions.assertEquals(repo, ((GitHubRecord)record).repo());
    }

    @Test
    public void invalidLinkParse() {
        String link1 = "https://github.com/" + username;
        String link2 = "https://github.com/" + username + "/" + repo + "/" + "pulls";

        Record record1 = gitHubLinkParser.parseLink(link1);
        Record record2 = gitHubLinkParser.parseLink(link2);

        Assertions.assertNull(record1);
        Assertions.assertNull(record2);
    }
}
