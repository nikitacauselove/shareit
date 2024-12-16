package com.example.server.repository;

import com.example.server.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для взаимодействия с пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Получение информации о пользователе.
     * @param email электронная почта пользователя
     */
    Optional<User> findByEmail(String email);
}
