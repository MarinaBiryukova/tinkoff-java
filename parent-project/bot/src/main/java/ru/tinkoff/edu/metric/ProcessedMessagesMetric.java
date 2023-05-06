package ru.tinkoff.edu.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class ProcessedMessagesMetric {
    private final Counter counter;

    public ProcessedMessagesMetric(MeterRegistry registry) {
        counter = Counter.builder("processed_messages")
            .description("Number of processed messages from users")
            .register(registry);
    }

    public void incrementMetric() {
        counter.increment();
    }
}
