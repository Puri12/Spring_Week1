package com.puri12.spring1.dto;


import com.puri12.spring1.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PostRequestDto {
    private String username;
    private String title;
    private String contents;
    private String passwd;
    private int category;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public PostRequestDto(String username, String title, String contents, int category, String passwd) {
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.category = category;
        this.passwd = passwd;
    }
}
