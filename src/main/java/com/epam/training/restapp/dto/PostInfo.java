package com.epam.training.restapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class PostInfo {
    private Integer id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private String content;

    @PastOrPresent
    private LocalDateTime createdTime;

    @PastOrPresent
    private LocalDateTime modifiedTime;

}
