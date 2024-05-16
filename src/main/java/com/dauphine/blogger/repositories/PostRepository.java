package com.dauphine.blogger.repositories;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByCategory(Category category);
    @Query("""
    SELECT post
    FROM Post post
    WHERE UPPER(post.title) LIKE UPPER(CONCAT('%' ,: title,'%'))
        OR UPPER(post.content) LIKE UPPER(CONCAT('%' ,: content,'%'))
    """)
    List<Post> findAllPostByTitleOrContent(@Param("title") String title, @Param("content") String content);
}
