package ru.tinkoff.edu.scheduler;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.record.GitHubRecord;
import ru.tinkoff.edu.record.StackOverflowRecord;
import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.service.LinkManipulator;
import ru.tinkoff.edu.service.LinkService;
import ru.tinkoff.edu.service.updater.GitHubLinkUpdater;
import ru.tinkoff.edu.service.updater.StackOverflowLinkUpdater;

@Component
@AllArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkService linkService;
    private final LinkManipulator linkManipulator;
    private final GitHubLinkUpdater gitHubLinkUpdater;
    private final StackOverflowLinkUpdater stackOverflowLinkUpdater;

    @Scheduled(fixedDelayString = "#{@applicationConfig.scheduler.interval()}")
    public void update() {
        List<Link> links = linkService.findLinksForUpdate();
        for (Link link: links) {
            Record apiRecord = linkManipulator.getRecord(link);
            if (apiRecord instanceof GitHubRecord) {
                gitHubLinkUpdater.update(link);
            } else if (apiRecord instanceof StackOverflowRecord) {
                stackOverflowLinkUpdater.update(link);
            }
        }
    }
}
