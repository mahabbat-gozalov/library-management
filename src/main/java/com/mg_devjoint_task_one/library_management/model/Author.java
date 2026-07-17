package com.mg_devjoint_task_one.library_management.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "AUTHORS")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "summary")
    private String summary;

    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
