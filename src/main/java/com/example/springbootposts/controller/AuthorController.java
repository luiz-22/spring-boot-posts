package com.example.springbootposts.controller;

import com.example.springbootposts.dto.AuthorDTO;
import com.example.springbootposts.exception.BadRequestException;
import com.example.springbootposts.service.IAuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private IAuthorService authorService;

    @PostMapping
    public ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        try {
            if (authorDTO.getUsername() == null || authorDTO.getUsername().isEmpty()) {
                throw new BadRequestException("Author name cannot be blank");
            }
            authorService.createAuthor(authorDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public Set<AuthorDTO> getAuthors() {
        return authorService.getAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable Long id) {
        try {
            AuthorDTO authorDTO = authorService.getAuthor(id);
            return ResponseEntity.ok(authorDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        try {
            if (authorDTO.getUsername() == null || authorDTO.getUsername().isEmpty()) {
                throw new BadRequestException("Author name cannot be blank");
            }
            authorService.updateAuthor(authorDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}