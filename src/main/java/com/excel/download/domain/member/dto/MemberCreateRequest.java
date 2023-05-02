package com.excel.download.domain.member.dto;

import com.excel.download.domain.member.domain.Gender;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class MemberCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotNull
    private Gender gender;
    private String zipCode;
    private String address;
    private String addressDetail;
}
