package com.epam.training.restapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserInfo {
    private Integer id;
    private String email;
    private String password;
    private String fullName;
    private List<PostInfo> postInfos;
}
