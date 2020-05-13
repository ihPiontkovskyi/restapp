package com.epam.training.restapp.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity(name = "posts")
public class Post {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Integer id;

    @Column(name = "title", nullable = false)
    private final String title;

    @Column(name = "description", nullable = false)
    private final String description;

    @Column(name = "content")
    private final String content;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private final LocalDateTime createdTime;

    @Column(name = "modified_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private final LocalDateTime modifiedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private final User user;
}
