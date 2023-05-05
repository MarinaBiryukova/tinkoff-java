package ru.tinkoff.edu.repository.jdbc.dto;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Long id;
    private URI link;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastActivity;
    private Integer openIssuesCount;
    private Integer answerCount;
}
