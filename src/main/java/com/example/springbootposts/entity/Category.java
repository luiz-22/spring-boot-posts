package com.example.springbootposts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Category {

    @Id
    @SequenceGenerator(name = "category_generator", allocationSize = 1, sequenceName = "category_generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Post> posts;
}
