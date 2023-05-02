package com.excel.download.domain.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Comment(value = "우편주소")
    @Column(name = "zipcode", length = 20)
    private String zipCode;

    @Comment(value = "주소")
    @Column(name = "address", length = 20)
    private String address;

    @Comment(value = "상세 주소")
    @Column(name = "address_detail", length = 50)
    private String addressDetail;

    @Builder
    public Address(String zipCode, String address, String addressDetail) {
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
