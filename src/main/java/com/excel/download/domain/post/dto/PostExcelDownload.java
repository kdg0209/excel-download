package com.excel.download.domain.post.dto;

import com.excel.download.global.common.ExcelColumnName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PostExcelDownload {

    @ExcelColumnName(name = "제목")
    private String title;

    @ExcelColumnName(name = "내용")
    private String contents;

    @ExcelColumnName(name = "태그")
    private String tags;

    @ExcelColumnName(name = "작성자")
    private String writer;

    @ExcelColumnName(name = "작성일")
    private LocalDate createdDate;

    @QueryProjection
    public PostExcelDownload(String title, String contents, String tags, String writer, LocalDateTime createdDate) {
        this.title = title;
        this.contents = contents;
        this.tags = tags;
        this.writer = writer;
        this.createdDate = createdDate.toLocalDate();
    }
}
