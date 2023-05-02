package com.excel.download.domain.member.dto;

import com.excel.download.domain.member.domain.Gender;
import com.excel.download.global.common.ExcelColumnName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberExcelDownload {

    @ExcelColumnName(name = "이름")
    private String name;

    @ExcelColumnName(name = "연락처")
    private String phone;

    @ExcelColumnName(name = "성별")
    private String gender;

    @ExcelColumnName(name = "우편주소")
    private String zipCode;

    @ExcelColumnName(name = "주소")
    private String address;

    @ExcelColumnName(name = "상세 주소")
    private String addressDetail;

    @QueryProjection
    public MemberExcelDownload(String name, String phone, Gender gender, String zipCode, String address, String addressDetail) {
        this.name = name;
        this.phone = phone;
        this.gender = gender.getValue();
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
