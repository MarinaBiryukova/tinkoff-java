package ru.tinkoff.edu.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.repository.ChatLinkRepository;
import ru.tinkoff.edu.service.TgChatService;

@AllArgsConstructor
@Service
public class JdbcTgChatService implements TgChatService {
    private final ChatLinkRepository repository;

    @Override
    public void register(Long tgChatId) {
        repository.registerChat(tgChatId);
    }

    @Override
    public void unregister(Long tgChatId) {
        repository.unregisterChat(tgChatId);
    }
}
