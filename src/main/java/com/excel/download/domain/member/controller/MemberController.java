package com.excel.download.domain.member.controller;

import com.excel.download.domain.member.dao.MemberDao;
import com.excel.download.domain.member.dto.MemberCreateRequest;
import com.excel.download.domain.member.dto.MemberExcelDownload;
import com.excel.download.domain.member.service.MemberService;
import com.excel.download.global.common.ExcelUtilsV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final ExcelUtilsV1 excelUtils;
    private final MemberDao memberDao;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid MemberCreateRequest request) {
        Long result = memberService.create(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/excel/download")
    public void excelDownload(HttpServletResponse response) {
        List<MemberExcelDownload> result = memberDao.excelDownload();
        excelUtils.download(MemberExcelDownload.class, result, "download", response);
    }
}
