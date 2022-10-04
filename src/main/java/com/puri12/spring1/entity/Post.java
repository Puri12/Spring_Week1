package com.puri12.spring1.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.puri12.spring1.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Post extends Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @JsonIgnore
    @Column(nullable = false)
    private String passwd;

    public Post(String usernamename, String title, String contents, String passwd) {
        this.username = usernamename;
        this.title = title;
        this.contents = contents;
        this.passwd = passwd;
    }
    public Post(PostRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.passwd = requestDto.getPasswd();
        this.setCreatedAt(requestDto.getCreatedAt());
        this.setModifiedAt(requestDto.getModifiedAt());
    }

    public void update(PostRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.passwd = requestDto.getPasswd();
    }
}
