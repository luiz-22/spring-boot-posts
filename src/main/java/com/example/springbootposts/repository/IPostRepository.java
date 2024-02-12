package com.example.springbootposts.repository;

import com.example.springbootposts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {
}
