package ru.tinkoff.edu.model;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import java.util.Arrays;
import java.util.List;
import io.micrometer.core.instrument.MeterRegistry;
import ru.tinkoff.edu.command.Command;
import ru.tinkoff.edu.metric.ProcessedMessagesMetric;

public class Bot implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final MessageProcessor processor;
    private final ProcessedMessagesMetric metric;

    public Bot(String token, MeterRegistry registry, Command... commands) {
        telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(this);
        telegramBot.execute(new SetMyCommands(Arrays.stream(commands).map(Command::toBotCommand)
            .toArray(BotCommand[]::new)));
        processor = new MessageProcessor(commands);
        metric = new ProcessedMessagesMetric(registry);
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            telegramBot.execute(processor.processCommand(update));
            metric.incrementMetric();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void sendMessages(String description, List<Long> tgChatIds) {
        for (Long tgChatId : tgChatIds) {
            telegramBot.execute(new SendMessage(tgChatId, description));
        }
    }
}
