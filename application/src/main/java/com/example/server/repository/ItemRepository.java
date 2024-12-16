package com.example.server.repository;

import com.example.server.repository.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для взаимодействия с предметами.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Получение владельцем списка всех его предметов.
     * @param ownerId идентификатор пользователя
     */
    List<Item> findAllByOwnerId(Long ownerId, Pageable pageable);

    /**
     * Поиск предметов.
     * @param text текст для поиска
     */
    @Query(SEARCH)
    List<Item> search(@Param("text") String text, Pageable pageable);

    /**
     * Получение владельцем списка всех его предметов.
     * @param requestId идентификатор пользователя
     */
    List<Item> findAllByRequestId(Long requestId);

    List<Item> findAllByRequestIdNotNull();

    String SEARCH = """
            SELECT item
            FROM Item AS item
            WHERE item.available = true
            AND (item.name iLIKE %:text% OR item.description iLIKE %:text%)
            """;
}
