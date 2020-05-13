package com.epam.training.restapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonIgnoreProperties(
        value = {"createdTime", "modifiedTime"},
        allowGetters = true
)
public class PostInfo {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @JsonIgnore
    private UserInfo userInfo;
}
