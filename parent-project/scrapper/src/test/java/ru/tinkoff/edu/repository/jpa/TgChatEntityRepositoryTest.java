package ru.tinkoff.edu.repository.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.IntegrationEnvironment;

import java.util.List;

@SpringBootTest
public class TgChatEntityRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private TgChatEntityRepository tgChatEntityRepository;
    private final List<Long> tgChatIds = List.of(123456L, 1234567L);

    @Test
    @Transactional
    @Rollback
    public void insertTgChat() {
        for (Long tgChatId : tgChatIds) {
            for (int j = 0; j < 3; j++) {
                tgChatEntityRepository.insertTgChat(tgChatId);
            }
        }

        Assertions.assertEquals(tgChatIds.size(), tgChatEntityRepository.findAll().size());
        Assertions.assertEquals(tgChatIds.get(0), tgChatEntityRepository.findAll().get(0).getTgChatId());
        Assertions.assertEquals(tgChatIds.get(1), tgChatEntityRepository.findAll().get(1).getTgChatId());
    }
}
