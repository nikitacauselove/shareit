package com.example.server.repository;

import com.example.server.repository.entity.ItemRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для взаимодействия с запросами на добавление предмета.
 */
@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    /**
     * Получение списка всех запросов, добавленных пользователем.
     * @param requesterId идентификатор пользователя
     */
    List<ItemRequest> findAllByRequesterId(Long requesterId, Sort sort);

    /**
     * Получение списка всех запросов, добавленных другими пользователем.
     * @param requesterId идентификатор пользователя
     */
    List<ItemRequest> findAllByRequesterIdNot(Long requesterId, Pageable pageable);
}
