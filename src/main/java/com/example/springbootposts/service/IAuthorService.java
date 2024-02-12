package com.example.springbootposts.service;

import com.example.springbootposts.dto.AuthorDTO;
import com.example.springbootposts.exception.BadRequestException;

import java.util.Set;

public interface IAuthorService {

    void createAuthor(AuthorDTO authorDTO) throws BadRequestException;
    Set<AuthorDTO> getAuthors();
    AuthorDTO getAuthor(Long id) throws BadRequestException;
    void updateAuthor(AuthorDTO authorDTO) throws BadRequestException;
    void deleteAuthor(Long id);
}
