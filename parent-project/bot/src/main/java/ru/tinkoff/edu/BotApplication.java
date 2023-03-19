package ru.tinkoff.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.configuration.ApplicationConfig;

@SpringBootApplication
@EnableScheduling
public class BotApplication
{

    public static void main( String[] args )
    {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);
    }

    @Bean("applicationConfig")
    @ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
    public ApplicationConfig applicationConfig() {
        return new ApplicationConfig();
    }
}
