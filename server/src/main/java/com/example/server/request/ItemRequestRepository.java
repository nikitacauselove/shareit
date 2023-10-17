package com.example.server.request;

import com.example.server.request.model.ItemRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByRequesterId(long requesterId, Sort sort);

    List<ItemRequest> findAllByRequesterIdNot(long requesterId, Pageable pageable);
}
