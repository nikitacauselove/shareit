package com.example.server.repository;

import com.example.server.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для взаимодействия с отзывами.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Получение списка всех отзывов на определенный предмет.
     * @param itemId идентификатор предмета
     */
    List<Comment> findAllByItemId(Long itemId);

    /**
     * Получение владельцем списка всех отзывов на его предметы.
     * @param ownerId идентификатор пользователя
     */
    @Query(FIND_ALL_BY_OWNER_ID)
    List<Comment> findAllByOwnerId(@Param("ownerId") Long ownerId);

    String FIND_ALL_BY_OWNER_ID = """
            SELECT comment
            FROM Comment AS comment
            JOIN FETCH comment.item AS item
            JOIN FETCH item.owner AS user
            WHERE user.id = :ownerId
            """;
}
