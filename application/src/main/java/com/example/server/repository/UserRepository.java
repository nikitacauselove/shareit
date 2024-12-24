package com.example.server.repository;

import com.example.server.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для взаимодействия с пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * .
     * @param id идентификатор пользователя
     * @param email электронная почта пользователя
     */
    boolean existsByIdNotAndEmail(Long id, String email);
}
