package com.puri12.spring1.service;

import com.puri12.spring1.dto.PostRequestDto;
import com.puri12.spring1.dto.BasicResponse;
import com.puri12.spring1.entity.Post;
import com.puri12.spring1.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postrepository;
    private BasicResponse basicResponse;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<BasicResponse> response(List<Object> postList) {
        try {
            basicResponse = BasicResponse.builder()
                    .success(HttpStatus.OK.is2xxSuccessful())
                    .data(postList)
                    .build();
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } catch (Exception e) {
            basicResponse = BasicResponse.builder()
                    .success(HttpStatus.OK.is2xxSuccessful())
                    .build();
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<BasicResponse> create(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        post.setPasswd(passwordEncoder.encode(post.getPasswd()));
        return response(Collections.singletonList(postrepository.save(post)));
    }

    @Transactional
    public ResponseEntity<BasicResponse> update(Long id, PostRequestDto requestDto) {
        Post post = postrepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        post.update(requestDto);
        return response(Collections.singletonList(postrepository.findById(id).get()));
    }

    @Transactional
    public Boolean compare(Long id, PostRequestDto requestDto) {
        Post post = postrepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return passwordEncoder.matches(requestDto.getPasswd(), post.getPasswd());
    }

    @Transactional
    public Boolean delete(Long id, String passwd) {
        Post post = postrepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (passwordEncoder.matches(passwd, post.getPasswd())) {
            postrepository.deleteById(id);
            return HttpStatus.OK.is2xxSuccessful();
        }
        return false;
    }
}
