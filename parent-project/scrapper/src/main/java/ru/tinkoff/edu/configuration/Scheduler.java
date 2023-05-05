package ru.tinkoff.edu.configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.convert.DurationUnit;

public record Scheduler(@DurationUnit(ChronoUnit.MILLIS) Duration interval) { }
