package ru.tinkoff.edu.model;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.command.Command;

import java.util.Arrays;
import java.util.List;

public class MessageProcessor {
    private final List<? extends Command> commands;

    public MessageProcessor(Command... commands) {
        this.commands = Arrays.stream(commands).toList();
    }

    public SendMessage processCommand(Update update) {
        String command = update.message().text().substring(1);
        Command processor = commands.stream().filter(el -> el.command().equals(command))
                .findAny().orElse(null);
        if (processor == null) {
            return invalidCommandMessage(update);
        }
        else {
            return processor.process(update);
        }
    }

    private SendMessage invalidCommandMessage(Update update) {
        return new SendMessage(update.message().chat().id(), "Неизвестная команда");
    }
}
