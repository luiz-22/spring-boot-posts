package com.example.springbootposts.service.impl;

import com.example.springbootposts.dto.AuthorDTO;
import com.example.springbootposts.entity.Author;
import com.example.springbootposts.exception.BadRequestException;
import com.example.springbootposts.repository.IAuthorRepository;
import com.example.springbootposts.service.IAuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {

    private static final Logger logger = Logger.getLogger(AuthorService.class.getName());

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void createAuthor(AuthorDTO authorDTO) throws BadRequestException {
        logger.info(authorDTO.getUsername());

        if (authorDTO.getUsername() == null || authorDTO.getUsername().isEmpty()) {
            throw new BadRequestException("Author name cannot be blank");
        }

        Optional<Author> existingAuthor = authorRepository.findByUsername(authorDTO.getUsername());
        if (existingAuthor.isPresent()) {
            throw new BadRequestException("Author already exists");
        }

        Author newAuthor = mapper.convertValue(authorDTO, Author.class);
        authorRepository.save(newAuthor);
    }

    @Override
    public Set<AuthorDTO> getAuthors() {
        List<Author> authors = authorRepository.findAll();
        Set<AuthorDTO> authorsDTO = new HashSet<>();

        for (Author author : authors) {
            authorsDTO.add(mapper.convertValue(author, AuthorDTO.class));
        }

        return authorsDTO;
    }

    @Override
    public AuthorDTO getAuthor(Long id) throws BadRequestException {
        Optional<Author> found = authorRepository.findById(id);
        if(found.isPresent())
            return mapper.convertValue(found, AuthorDTO.class);
        else
            throw new BadRequestException("Author Not Exist");
    }

    @Override
    public void updateAuthor(AuthorDTO authorDTO) throws BadRequestException {
        Author newAuthor =mapper.convertValue(authorDTO, Author.class);
        authorRepository.save(newAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}