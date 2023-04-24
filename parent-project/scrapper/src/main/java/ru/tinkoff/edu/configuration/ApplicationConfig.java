package ru.tinkoff.edu.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.configuration.access.AccessType;

@Getter
@Setter
@ToString
@Validated
public class ApplicationConfig {
    @NotNull
    String test;

    @NotNull
    Scheduler scheduler;

    @NotNull
    AccessType databaseAccessType;

    @NotNull
    boolean useQueue;

    String queueName;

    String exchangeName;
}

