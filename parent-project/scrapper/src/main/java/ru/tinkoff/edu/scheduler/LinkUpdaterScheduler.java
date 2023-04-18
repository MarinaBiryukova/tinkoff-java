package ru.tinkoff.edu.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.record.GitHubRecord;
import ru.tinkoff.edu.record.StackOverflowRecord;
import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.service.*;

import java.util.List;

@Component
@AllArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkService linkService;
    private final LinkManipulator linkManipulator;
    private final GitHubLinkUpdater gitHubLinkUpdater;
    private final StackOverflowLinkUpdater stackOverflowLinkUpdater;

    @Scheduled(fixedDelayString = "#{@applicationConfig.scheduler.interval()}")
    public void update() {
        System.out.println("Data update started");

        List<Link> links = linkService.findLinksForUpdate();
        for (Link link: links) {
            Record record = linkManipulator.getRecord(link);
            if (record instanceof GitHubRecord) {
                gitHubLinkUpdater.update(link);
            } else if (record instanceof StackOverflowRecord) {
                stackOverflowLinkUpdater.update(link);
            }
        }

        System.out.println("Data update finished");
    }
}
