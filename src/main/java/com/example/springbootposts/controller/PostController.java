package com.example.springbootposts.controller;

import com.example.springbootposts.dto.PostDTO;
import com.example.springbootposts.dto.PostResponseDTO;
import com.example.springbootposts.exception.BadRequestException;
import com.example.springbootposts.service.IPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private IPostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDTO postDTO) {
        try {
            postService.createPost(postDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public Set<PostResponseDTO> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        try {
            PostResponseDTO postDTO = postService.getPost(id);
            return ResponseEntity.ok(postDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@Valid @RequestBody PostDTO postDTO) {
        try {
            postService.updatePost(postDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
