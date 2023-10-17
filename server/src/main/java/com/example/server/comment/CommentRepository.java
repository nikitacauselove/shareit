package com.example.server.comment;

import com.example.server.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByItemId(long itemId);

    @Query("select comment from Comment as comment join fetch comment.item as item join fetch item.owner as user where user.id = :ownerId")
    List<Comment> findAllByOwnerId(@Param("ownerId") long ownerId);
}
