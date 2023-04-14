package ru.tinkoff.edu.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.repository.jpa.entity.TgChatEntity;

import java.util.Optional;

public interface TgChatEntityRepository extends JpaRepository<TgChatEntity, Long> {
    Optional<TgChatEntity> findByTgChatId(Long tgChatId);
}
