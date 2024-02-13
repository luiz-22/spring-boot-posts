package com.example.springbootposts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class Author {

    @Id
    @SequenceGenerator(name = "author_generator", allocationSize = 1, sequenceName = "author_generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String username;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Post> posts;
}