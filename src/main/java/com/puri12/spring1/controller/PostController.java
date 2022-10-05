package com.puri12.spring1.controller;

import com.puri12.spring1.dto.PostRequestDto;
import com.puri12.spring1.dto.BasicResponse;
import com.puri12.spring1.entity.Category;
import com.puri12.spring1.entity.Post;
import com.puri12.spring1.repository.PostRepository;
import com.puri12.spring1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostController {

    @Enumerated(EnumType.STRING)
    private Category category;

    private final PostRepository postRepository;
    private final PostService postService;

//    @GetMapping(value = {"/api/post", "/api/post/{nid:[0-9]*$}", "/api/post/{name:[a-zA-Z]*$}"})
//    public ResponseEntity<BasicResponse> getPosts(@RequestParam(value = "id", required = false, defaultValue = "0") int id,
//                                                  @PathVariable(required = false) Integer nid,
//                                                  @PathVariable(required = false) String  name) {
//        if (nid == null)
//            nid = 0;
//        System.out.println(id);
//        System.out.println(nid);
//        System.out.println(name);
////        return postService.response(Collections.singletonList(postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))));
//        return postService.response(postRepository.findAllByOrderByCreatedAtDesc());
//    }

    @GetMapping("/api/post")
    public ResponseEntity<BasicResponse> getPosts() {
//        return postService.response(Collections.singletonList(postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))));
        return postService.response(Collections.singletonList(postRepository.findAllByOrderByCreatedAtDesc()));
    }

    @GetMapping("/api/list")
    public ResponseEntity<BasicResponse> getList(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.response(Collections.singletonList(postRepository.findAll(pageable)));
    }


    @GetMapping("/api/find")
    public ResponseEntity<BasicResponse> fildCategory(@RequestParam(value = "category") String category) {
        return postService.response(Collections.singletonList(postRepository.findAllByCategory(category)));
    }

    @PostMapping("/api/post")
    public ResponseEntity<BasicResponse> createPost(@RequestBody PostRequestDto requestDto) {
        return postService.create(requestDto);
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity<BasicResponse> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @GetMapping ("/api/post/{id:[0-9]*$}")
    public ResponseEntity<BasicResponse> getPostId(@PathVariable Long id) {
        return postService.response(Collections.singletonList(postRepository.findById(id).get()));
    }

    @PostMapping ("/api/post/{id}")
    public ResponseEntity<BasicResponse> getPostPasswd(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.response(Collections.singletonList(postService.compare(id, requestDto)));
    }

    @DeleteMapping ("/api/post/{id}")
    public ResponseEntity<BasicResponse> deletePost(
            @PathVariable Long id,
            @RequestBody PostRequestDto requestDto
    ) {
        System.out.println(requestDto.getPasswd());
        return postService.response(Collections.singletonList(postService.delete(id, requestDto.getPasswd())));
    }

}
