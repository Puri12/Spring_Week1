package com.puri12.spring1.service;

import com.puri12.spring1.dto.PostRequestDto;
import com.puri12.spring1.dto.BasicResponse;
import com.puri12.spring1.model.Post;
import com.puri12.spring1.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postrepository;
    private BasicResponse basicResponse;

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
                    .error(e.toString())
                    .build();
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }
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
        return post.getPasswd().equals(requestDto.getPasswd());
    }
}
