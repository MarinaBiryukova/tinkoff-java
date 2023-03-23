package ru.tinkoff.edu.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import ru.tinkoff.edu.client.ScrapperClient;

@AllArgsConstructor
public class TrackCommand implements Command {
    private final ScrapperClient client;

    @Override
    public String command() {
        return "track";
    }

    @Override
    public String description() {
        return "Начать отслеживание ссылки";
    }

    @Override
    public SendMessage process(Update update) {
        boolean result = client.addTrackedLink(update.message().chat().id(), null);
        return null;
    }
}
