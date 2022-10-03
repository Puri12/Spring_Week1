package com.puri12.spring1.controller;

import com.puri12.spring1.dto.PostRequestDto;
import com.puri12.spring1.dto.BasicResponse;
import com.puri12.spring1.entity.Post;
import com.puri12.spring1.repository.PostRepository;
import com.puri12.spring1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/api/post")
    public ResponseEntity<BasicResponse> getPosts() {
        return postService.response(Collections.singletonList(postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))));
    }


    @PostMapping("/api/post")
    public ResponseEntity<BasicResponse> createPost(@RequestBody PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        return postService.response(Collections.singletonList(postRepository.save(post)));
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity<BasicResponse> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @GetMapping ("/api/post/{id}")
    public ResponseEntity<BasicResponse> getPostId(@PathVariable Long id) {
        return postService.response(Collections.singletonList(postRepository.findById(id).get()));
    }

    @PostMapping ("/api/post/{id}")
    public ResponseEntity<BasicResponse> getPostPasswd(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.response(Collections.singletonList(postService.compare(id, requestDto)));
    }

    @DeleteMapping ("/api/post/{id}")
    public ResponseEntity<BasicResponse> deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return postService.response(Collections.singletonList(HttpStatus.OK.is2xxSuccessful()));
    }

}
