package com.example.springbootposts.service.impl;

import com.example.springbootposts.dto.PostDTO;
import com.example.springbootposts.dto.PostResponseDTO;
import com.example.springbootposts.entity.Author;
import com.example.springbootposts.entity.Category;
import com.example.springbootposts.entity.Post;
import com.example.springbootposts.exception.BadRequestException;
import com.example.springbootposts.repository.IAuthorRepository;
import com.example.springbootposts.repository.ICategoryRepository;
import com.example.springbootposts.repository.IPostRepository;
import com.example.springbootposts.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService {

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void createPost(PostDTO postDTO) throws BadRequestException {
        Optional<Author> authorOptional = authorRepository.findById(postDTO.getAuthorId());
        if (authorOptional.isEmpty()) {
            throw new BadRequestException("Author not found");
        }

        Set<Category> categories = new HashSet<>();
        for (Long categoryId : postDTO.getCategoryIds()) {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            categoryOptional.ifPresent(categories::add);
        }

        Post newPost = mapper.convertValue(postDTO, Post.class);
        newPost.setAuthor(authorOptional.get());
        newPost.setCategories(categories);

        postRepository.save(newPost);
    }

    @Override
    public Set<PostResponseDTO> getPosts() {
        List<Post> posts = postRepository.findAll();
        Set<PostResponseDTO> postsDTO = new HashSet<>();

        for (Post post : posts) {
            PostResponseDTO postDTOMapper = mapper.convertValue(post, PostResponseDTO.class);

            postDTOMapper.setAuthorName(post.getAuthor().getUsername());
            postDTOMapper.setCategoryNames(
                    post.getCategories().stream().map(Category::getName).collect(Collectors.toSet())
            );

            postsDTO.add(postDTOMapper);
        }

        return postsDTO;
    }

    @Override
    public PostResponseDTO getPost(Long id) throws BadRequestException {
        Optional<Post> found = postRepository.findById(id);

        if (found.isPresent()) {
            Post post = found.get();

            PostResponseDTO postMapperDTO = mapper.convertValue(post, PostResponseDTO.class);

            postMapperDTO.setAuthorName(post.getAuthor().getUsername());
            postMapperDTO.setCategoryNames(
                    post.getCategories().stream().map(Category::getName).collect(Collectors.toSet())
            );

            return postMapperDTO;
        } else {
            throw new BadRequestException("Post Not Exist");
        }
    }

    @Override
    public void updatePost(PostDTO postDTO) throws BadRequestException {
        Optional<Author> authorOptional = authorRepository.findById(postDTO.getAuthorId());
        if (authorOptional.isEmpty()) {
            throw new BadRequestException("Author not found");
        }

        Set<Category> categories = new HashSet<>();
        for (Long categoryId : postDTO.getCategoryIds()) {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            categoryOptional.ifPresent(categories::add);
        }

        Optional<Post> existingPostOptional = postRepository.findById(postDTO.getId());
        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();

            Post updatedArticle = mapper.convertValue(postDTO, Post.class);
            updatedArticle.setAuthor(authorOptional.get());
            updatedArticle.setCategories(categories);
            updatedArticle.setId(existingPost.getId());

            postRepository.save(updatedArticle);
        } else {
            throw new BadRequestException("Article not found");
        }
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
