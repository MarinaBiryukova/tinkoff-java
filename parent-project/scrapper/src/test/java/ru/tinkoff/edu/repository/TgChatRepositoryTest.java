package ru.tinkoff.edu.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.IntegrationEnvironment;
import ru.tinkoff.edu.repository.dto.TgChat;
import ru.tinkoff.edu.repository.mapper.TgChatMapper;

import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
public class TgChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private TgChatRepository tgChatRepository;
    @Autowired
    private TgChatMapper tgChatMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final List<Long> tgChatIds = List.of(1234L, 123456L);

    @BeforeEach
    public void setup() {
        for (Long tgChatId: tgChatIds) {
            jdbcTemplate.update("insert into chat (tg_chat_id) values (?)", tgChatId);
        }
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        List<TgChat> all = tgChatRepository.findAll();
        Assertions.assertEquals(tgChatIds.size(), all.size());
        for (int i =0; i < tgChatIds.size(); i++) {
            Assertions.assertEquals(tgChatIds.get(i), all.get(i).getTgChatId());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        tgChatRepository.remove(tgChatIds.get(0));

        Assertions.assertEquals(tgChatIds.size() - 1, jdbcTemplate.queryForObject("select count(*) from chat", Integer.class));
        Assertions.assertEquals(tgChatIds.get(1), jdbcTemplate.queryForObject("select tg_chat_id from chat", Long.class));
    }

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        Long newId = 1234567L;
        tgChatRepository.add(newId);

        List<TgChat> all = jdbcTemplate.query("select * from chat", tgChatMapper);
        Assertions.assertEquals(tgChatIds.size() + 1, all.size());
        Assertions.assertEquals(newId, all.get(all.size() - 1).getTgChatId());
    }

    @TestConfiguration
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(POSTGRE_SQL_CONTAINER.getJdbcUrl());
            config.setUsername(POSTGRE_SQL_CONTAINER.getUsername());
            config.setPassword(POSTGRE_SQL_CONTAINER.getPassword());
            return new HikariDataSource(config);
        }
    }
}
