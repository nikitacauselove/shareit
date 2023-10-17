package com.example.server.item;

import com.example.server.item.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwnerId(long ownerId, Pageable pageable);

    @Query("select item from Item as item where item.available = true and (lower(item.name) like %:text% or lower(item.description) like %:text%)")
    List<Item> search(@Param("text") String text, Pageable pageable);

    List<Item> findAllByRequestId(long requestId);

    List<Item> findAllByRequestIdNotNull();
}
