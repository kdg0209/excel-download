package com.excel.download.domain.post.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Getter
public class PostCreateRequest {

    @NotNull
    @Positive
    private Long memberIdx;

    @NotBlank
    private String title;

    private String contents;

    private Set<String> tags;
}
