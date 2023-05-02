package com.excel.download.domain.member.controller;

import com.excel.download.domain.member.dao.MemberDao;
import com.excel.download.domain.member.dto.MemberCreateRequest;
import com.excel.download.domain.member.dto.MemberExcelDownload;
import com.excel.download.domain.member.service.MemberService;
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
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private static final int MAX_ROW = 5000;

    private final ExcelUtilsV1 excelUtilsV1;
    private final ExcelUtilsV2 excelUtilsV2;
    private final MemberDao memberDao;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid MemberCreateRequest request) {
        Long result = memberService.create(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/excel/downloadV1")
    public void excelDownloadV1(HttpServletResponse response) {
        List<MemberExcelDownload> result = memberDao.excelDownload();
        excelUtilsV1.download(MemberExcelDownload.class, result, "download", response);
    }

    @GetMapping(value = "/excel/downloadV2")
    public void excelDownloadV2(HttpServletResponse response) {
        int index = 0;
        excelUtilsV2.connect(response);
        Long totalElementCount = memberDao.totalCount();
        List<MemberExcelDownload> result = new ArrayList<>();
        for (int i = 0; i < totalElementCount; i += MAX_ROW) {
            result = memberDao.excelDownloadByPaging((index * MAX_ROW), MAX_ROW);
            excelUtilsV2.draw(index, MemberExcelDownload.class, result);
            index++;
            result.clear();
        }
        excelUtilsV2.download("download");
    }
}
