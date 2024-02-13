package com.example.springbootposts.repository;

import com.example.springbootposts.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByUsername(String username);
}
