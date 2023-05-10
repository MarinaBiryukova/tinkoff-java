package ru.tinkoff.edu.service.updater;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.record.GitHubRecord;
import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.response.RepositoryResponse;
import ru.tinkoff.edu.service.LinkManipulator;
import ru.tinkoff.edu.service.LinkService;
import ru.tinkoff.edu.service.sender.LinkUpdateSender;

@AllArgsConstructor
@Service
public class GitHubLinkUpdater implements LinkUpdater {
    private final LinkService linkService;
    private final LinkManipulator linkManipulator;
    private final LinkUpdateSender linkUpdateSender;

    @Override
    public void update(Link link) {
        RepositoryResponse response = linkManipulator.getResponse((GitHubRecord) linkManipulator.getRecord(link));
        link.setLastUpdate(OffsetDateTime.now());
        if (!response.open_issues_count().equals(link.getOpenIssuesCount())) {
            String desc = (response.open_issues_count() > link.getOpenIssuesCount())
                ? "В репозитории '" + response.full_name() + "' были открыты новые issues!"
                : "В репозитории '" + response.full_name() + "' были закрыты issues!";
            link.setLastActivity(response.updated_at());
            link.setOpenIssuesCount(response.open_issues_count());
            linkUpdateSender.sendUpdate(link, desc);
        } else if (response.updated_at().isAfter(link.getLastActivity())) {
            link.setLastActivity(response.updated_at());
            linkUpdateSender.sendUpdate(link, "В репозитории '" + response.full_name() + "' есть обновления!");
        }
        linkService.updateLink(link);
    }
}
