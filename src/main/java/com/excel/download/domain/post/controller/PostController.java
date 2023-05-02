package com.excel.download.domain.post.controller;

import com.excel.download.domain.member.dto.MemberExcelDownload;
import com.excel.download.domain.post.dao.PostDao;
import com.excel.download.domain.post.dto.PostCreateRequest;
import com.excel.download.domain.post.dto.PostExcelDownload;
import com.excel.download.domain.post.service.PostService;
import com.excel.download.global.common.ExcelUtilsV1;
import com.excel.download.global.common.ExcelUtilsV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private static final int MAX_ROW = 5000;

    private final ExcelUtilsV1 excelUtilsV1;
    private final ExcelUtilsV2 excelUtilsV2;
    private final PostDao postDao;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid PostCreateRequest request) {
        Long result = postService.create(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/excel/downloadV1")
    public void excelDownload(HttpServletResponse response) {
        List<PostExcelDownload> result = postDao.excelDownload();
        excelUtilsV1.download(PostExcelDownload.class, result, "download", response);
    }

    @GetMapping(value = "/excel/downloadV2")
    public void excelDownloadV2(HttpServletResponse response) {
        int index = 0;
        excelUtilsV2.connect(response);
        Long totalElementCount = postDao.totalCount();
        List<PostExcelDownload> result = new ArrayList<>();
        for (int i = 0; i < totalElementCount; i += MAX_ROW) {
            result = postDao.excelDownloadByPaging((index * MAX_ROW), MAX_ROW);
            excelUtilsV2.draw(index, PostExcelDownload.class, result);
            index++;
            result.clear();
        }
        excelUtilsV2.download("download");
    }
}
