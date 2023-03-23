package ru.tinkoff.edu.model;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import ru.tinkoff.edu.command.Command;

import java.util.Arrays;
import java.util.List;

public class Bot implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final MessageProcessor processor;

    public Bot(String token, Command... commands) {
        telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(this);
        telegramBot.execute(new SetMyCommands(Arrays.stream(commands).map(Command::toBotCommand).toArray(BotCommand[]::new)));
        processor = new MessageProcessor(commands);
    }

    @Override
    public int process(List<Update> list) {
        for (Update update: list) {
            telegramBot.execute(processor.processCommand(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
