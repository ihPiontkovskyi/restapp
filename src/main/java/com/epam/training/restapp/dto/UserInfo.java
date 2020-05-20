package com.epam.training.restapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@Builder
public class UserInfo {
    private Integer id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String fullName;

    private List<PostInfo> postInfos;
}
