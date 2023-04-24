package ru.tinkoff.edu.service.updater;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.record.GitHubRecord;
import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.response.RepositoryResponse;
import ru.tinkoff.edu.service.LinkManipulator;
import ru.tinkoff.edu.service.LinkService;
import ru.tinkoff.edu.service.sender.LinkUpdateSender;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class GitHubLinkUpdater implements LinkUpdater {
    private final LinkService linkService;
    private final LinkManipulator linkManipulator;
    private final LinkUpdateSender httpLinkUpdateSender;

    @Override
    public void update(Link link) {
        RepositoryResponse response = linkManipulator.getResponse((GitHubRecord) linkManipulator.getRecord(link));
        link.setLastUpdate(OffsetDateTime.now());
        if (!response.open_issues_count().equals(link.getOpenIssuesCount())) {
            String desc = (response.open_issues_count() > link.getOpenIssuesCount()) ?
                    "In repository '" + response.full_name() + "' new open issues have been added!" :
                    "In repository '" + response.full_name() + "' issues have been closed!";
            link.setLastActivity(response.updated_at());
            link.setOpenIssuesCount(response.open_issues_count());
            httpLinkUpdateSender.sendUpdate(link, desc);
        } else if (response.updated_at().isAfter(link.getLastActivity())) {
            link.setLastActivity(response.updated_at());
            httpLinkUpdateSender.sendUpdate(link, "Repository '" + response.full_name() + "' has updates!");
        }
        linkService.updateLink(link);
    }
}
