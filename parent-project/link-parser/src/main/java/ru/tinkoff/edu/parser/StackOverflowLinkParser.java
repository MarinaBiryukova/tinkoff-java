package ru.tinkoff.edu.parser;

import ru.tinkoff.edu.record.StackOverflowRecord;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public final class StackOverflowLinkParser extends LinkParser {
    private final Pattern PATTERN =
            Pattern.compile("^https://stackoverflow.com/questions/[\\d]+/[a-z-\\d]+");

    public StackOverflowLinkParser(LinkParser nextLink) {
        super(nextLink);
    }

    @Override
    public Record parseLink(String link) {
        if (PATTERN.matcher(link).matches()) {
            try {
                URL url = new URL(link);
                return new StackOverflowRecord(Long.parseLong(url.getPath().substring(1).split("/")[1]));

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
