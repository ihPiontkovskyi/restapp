package com.epam.training.restapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class PostInfo {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private Integer userId;
}
