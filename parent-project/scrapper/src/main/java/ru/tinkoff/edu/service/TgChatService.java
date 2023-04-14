package ru.tinkoff.edu.service;

public interface TgChatService {
    void register(Long tgChatId);
    void unregister(Long tgChatId);
}
