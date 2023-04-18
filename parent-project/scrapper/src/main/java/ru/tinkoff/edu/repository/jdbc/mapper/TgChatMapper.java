package ru.tinkoff.edu.repository.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.repository.jdbc.dto.TgChat;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TgChatMapper implements RowMapper<TgChat> {
    @Override
    public TgChat mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TgChat(rs.getLong("id"), rs.getLong("tg_chat_id"));
    }
}
