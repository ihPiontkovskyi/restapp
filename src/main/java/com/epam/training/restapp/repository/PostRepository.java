package com.epam.training.restapp.repository;

import com.epam.training.restapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByUserId(Integer postId, Pageable pageable);

    Optional<Post> findByUserIdAndId(Integer id, Integer postId);
}
