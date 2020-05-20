package com.epam.training.restapp.controller;

import com.epam.training.restapp.dto.UserInfo;
import com.epam.training.restapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService service;

    @GetMapping
    public Page<UserInfo> getAllUsers(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity<UserInfo> createUser(@Valid @RequestBody UserInfo user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserInfo> updateUser(@PathVariable Integer userId,
                                               @Valid @RequestBody UserInfo userRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(userId, userRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        service.delete(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }
}
