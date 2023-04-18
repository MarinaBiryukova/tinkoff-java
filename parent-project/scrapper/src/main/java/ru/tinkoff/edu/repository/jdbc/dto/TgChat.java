package ru.tinkoff.edu.repository.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TgChat {
    private Long id;
    private Long tgChatId;
}
