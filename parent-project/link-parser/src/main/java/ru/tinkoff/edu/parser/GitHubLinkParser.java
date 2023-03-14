package ru.tinkoff.edu.parser;

import ru.tinkoff.edu.record.GitHubRecord;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public final class GitHubLinkParser extends LinkParser {
    private final Pattern PATTERN = Pattern.compile("^https://github.com/[\\w.-]+/[\\w.-]+/");

    public GitHubLinkParser(LinkParser nextParser) {
        super(nextParser);
    }

    @Override
    public Record parseLink(String link) {
        if (PATTERN.matcher(link).matches()) {
            try {
                URL url = new URL(link);
                String path = url.getPath().substring(1);
                return new GitHubRecord(path.split("/")[0], path.split("/")[1]);

            } catch (MalformedURLException e) {
                return null;
            }
        }
        if (nextParser != null) {
            return nextParser.parseLink(link);
        }
        return null;
    }
}
