package ru.tinkoff.edu.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.client.ScrapperClient;
import ru.tinkoff.edu.command.HelpCommand;
import ru.tinkoff.edu.command.ListCommand;
import ru.tinkoff.edu.command.StartCommand;
import ru.tinkoff.edu.command.TrackCommand;
import ru.tinkoff.edu.command.UntrackCommand;
import ru.tinkoff.edu.model.Bot;

@Configuration
public class BotConfig {
    @Bean
    public Bot bot(
            ApplicationConfig config,
            ScrapperClient client
    ) {
        return new Bot(
                config.token(),
                new StartCommand(client),
                new ListCommand(client),
                new TrackCommand(client),
                new UntrackCommand(client),
                new HelpCommand());
    }
}
