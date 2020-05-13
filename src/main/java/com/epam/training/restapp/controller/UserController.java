package com.epam.training.restapp.controller;

import com.epam.training.restapp.dto.UserInfo;
import com.epam.training.restapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService service;

    @GetMapping("/users")
    public Page<UserInfo> getAllPosts(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PostMapping("/users")
    public UserInfo createPost(@Valid @RequestBody UserInfo user) {
        return service.create(user);
    }

    @PutMapping("/users/{userId}")
    public UserInfo updatePost(@PathVariable Integer userId, @Valid @RequestBody UserInfo userRequest) {
        return service.update(userId, userRequest);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer userId) {
        service.delete(userId);
        return ResponseEntity.ok().build();
    }
}
