package com.dauphine.blogger.repositories;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByCategory(Category category);
    List<Post> findAllPostByTitleOrContent(@Param("title") String title, @Param("content") String content);
    boolean existsById(UUID id);
}
