package com.epam.training.restapp;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.dto.UserInfo;
import com.epam.training.restapp.entity.Post;
import com.epam.training.restapp.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Arrays;

@UtilityClass
public class TestDataProvider {
    public static UserInfo getUserInfoTestData() {
        return UserInfo.builder()
                .email("Some email")
                .fullName("Some fullname")
                .id(1)
                .password("Some encrypted password")
                .postInfos(Arrays.asList(null, null, null))
                .build();
    }

    public static UserInfo getUserInfoPassedData() {
        return UserInfo.builder()
                .email("email")
                .fullName("fullname")
                .id(2)
                .password("encrypted password")
                .postInfos(Arrays.asList(null, null))
                .build();
    }

    public static User getUserTestData() {
        return User.builder()
                .email("Some email")
                .fullName("Some fullname")
                .id(1)
                .password("Some encrypted password")
                .posts(Arrays.asList(null, null, null))
                .build();
    }

    public static User getUserPassedData() {
        return User.builder()
                .email("email")
                .fullName("fullname")
                .id(2)
                .password("encrypted password")
                .posts(Arrays.asList(null, null, null))
                .build();
    }

    public static PostInfo getPostInfoTestData() {
        return PostInfo.builder()
                .content("Some text")
                .createdTime(LocalDateTime.of(2019, 5, 13, 14, 0, 0))
                .description("Some description")
                .id(1)
                .modifiedTime(LocalDateTime.of(2020, 5, 13, 14, 0, 0))
                .title("Some title")

                .build();
    }

    public static PostInfo getPostInfoPassedData() {
        return PostInfo.builder()
                .content("text")
                .createdTime(LocalDateTime.of(2018, 5, 13, 14, 0, 0))
                .description("description")
                .id(2)
                .modifiedTime(LocalDateTime.of(2019, 5, 12, 14, 0, 0))
                .title("title")
                .build();
    }

    public static Post getPostTestData() {
        return Post.builder()
                .content("Some text")
                .createdTime(LocalDateTime.of(2019, 5, 13, 14, 0, 0))
                .description("Some description")
                .id(1)
                .modifiedTime(LocalDateTime.of(2020, 5, 13, 14, 0, 0))
                .title("Some title")
                .build();
    }

    public static Post getPostPassedData() {
        return Post.builder()
                .content("text")
                .createdTime(LocalDateTime.of(2018, 5, 13, 14, 0, 0))
                .description("description")
                .id(2)
                .modifiedTime(LocalDateTime.of(2019, 5, 12, 14, 0, 0))
                .title("title")
                .build();
    }
}
