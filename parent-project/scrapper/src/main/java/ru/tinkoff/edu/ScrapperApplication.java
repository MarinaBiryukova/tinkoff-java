package ru.tinkoff.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.client.GitHubClient;
import ru.tinkoff.edu.client.StackOverflowClient;
import ru.tinkoff.edu.configuration.ApplicationConfig;

@SpringBootApplication
@EnableScheduling
public class ScrapperApplication
{

    public static void main( String[] args )
    {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);
        StackOverflowClient stackOverflowClient = ctx.getBean(StackOverflowClient.class);
        System.out.println(stackOverflowClient.getQuestionInfo(75771004L));
        GitHubClient gitHubClient = ctx.getBean(GitHubClient.class);
        System.out.println(gitHubClient.getRepoInfo("MarinaBiryukova", "tinkoff-java"));
    }

    @Bean("applicationConfig")
    @ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
    public ApplicationConfig applicationConfig() {
        return new ApplicationConfig();
    }
}
