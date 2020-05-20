package com.epam.training.restapp.controller;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.service.PostService;
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
@RequestMapping("/posts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostController {

    private final PostService service;

    @GetMapping
    public Page<PostInfo> getAllPosts(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity<PostInfo> createPost(@Valid @RequestBody PostInfo post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(post));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostInfo> updatePost(@PathVariable Integer postId, @Valid @RequestBody PostInfo postRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(postId, postRequest));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
        service.delete(postId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
