package ru.tinkoff.edu.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.repository.LinkRepository;
import ru.tinkoff.edu.repository.dto.Link;
import ru.tinkoff.edu.service.LinkUpdater;

import java.util.List;

@Component
@AllArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdater;
    private final LinkRepository linkRepository;

    @Scheduled(fixedDelayString = "#{@applicationConfig.scheduler.interval()}")
    public void update() {
        System.out.println("Data update started");

        List<Link> links = linkRepository.findAllForUpdate();
        for (Link link: links) {
            System.out.println("Updating link" + link.getLink());
            linkUpdater.update(link);
        }

        System.out.println("Data update finished");
    }
}
