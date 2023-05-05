package ru.tinkoff.edu.parser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract sealed class AbstractLinkParser permits GitHubLinkParser, StackOverflowLinkParser {
    protected AbstractLinkParser nextParser;

    abstract Record parseLink(String link);
}
