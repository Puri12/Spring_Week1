package com.puri12.spring1.dto;

import com.puri12.spring1.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {

    private Boolean success;
    private List<Object> data;
    private String error;
}
