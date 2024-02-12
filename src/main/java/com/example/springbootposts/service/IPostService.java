package com.example.springbootposts.service;

import com.example.springbootposts.dto.PostDTO;
import com.example.springbootposts.dto.PostResponseDTO;
import com.example.springbootposts.exception.BadRequestException;

import java.util.Set;

public interface IPostService {

    void createPost(PostDTO postDTO) throws BadRequestException;
    Set<PostResponseDTO> getPosts();
    PostResponseDTO getPost(Long id) throws BadRequestException;
    void updatePost(PostDTO postDTO) throws BadRequestException;
    void deletePost(Long id);
}
