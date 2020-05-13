package com.epam.training.restapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@Entity(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private final String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private final String password;

    @Column(name = "full_name", nullable = false)
    private final String fullName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Post> posts;
}
