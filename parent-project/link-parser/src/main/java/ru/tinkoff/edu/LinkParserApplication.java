package ru.tinkoff.edu;

import ru.tinkoff.edu.parser.GitHubLinkParser;
import ru.tinkoff.edu.parser.StackOverflowLinkParser;

public class LinkParserApplication
{
    public static void main( String[] args )
    {
        StackOverflowLinkParser stackOverflow = new StackOverflowLinkParser(null);
        GitHubLinkParser gitHub = new GitHubLinkParser(stackOverflow);
        System.out.println(gitHub.parseLink("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        System.out.println(gitHub.parseLink("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
        System.out.println(gitHub.parseLink("https://stackoverflow.com/search?q=unsupported%20link"));
    }
}
