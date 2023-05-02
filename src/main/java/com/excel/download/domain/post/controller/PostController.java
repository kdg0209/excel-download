package com.excel.download.domain.post.controller;

import com.excel.download.domain.post.dao.PostDao;
import com.excel.download.domain.post.dto.PostCreateRequest;
import com.excel.download.domain.post.dto.PostExcelDownload;
import com.excel.download.domain.post.service.PostService;
import com.excel.download.global.common.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final ExcelUtils excelUtils;
    private final PostDao postDao;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid PostCreateRequest request) {
        Long result = postService.create(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/excel/download")
    public void excelDownload(HttpServletResponse response) {
        List<PostExcelDownload> result = postDao.excelDownload();
        excelUtils.download(PostExcelDownload.class, result, "download", response);
    }
}
