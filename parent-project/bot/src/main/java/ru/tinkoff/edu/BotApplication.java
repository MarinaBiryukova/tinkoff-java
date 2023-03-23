package ru.tinkoff.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.command.HelpCommand;
import ru.tinkoff.edu.command.ListCommand;
import ru.tinkoff.edu.command.StartCommand;
import ru.tinkoff.edu.client.ScrapperClient;
import ru.tinkoff.edu.configuration.ApplicationConfig;
import ru.tinkoff.edu.model.*;

@SpringBootApplication
@EnableScheduling
public class BotApplication
{

    public static void main( String[] args )
    {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        ScrapperClient client = ctx.getBean(ScrapperClient.class);
        Bot bot = new Bot(
                config.getToken(),
                new StartCommand(client),
                new ListCommand(client),
                new HelpCommand());
    }

    @Bean("applicationConfig")
    @ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
    public ApplicationConfig applicationConfig() {
        return new ApplicationConfig();
    }

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient();
    }
}
