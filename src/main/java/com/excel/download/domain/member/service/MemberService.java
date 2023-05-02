package com.excel.download.domain.member.service;

import com.excel.download.domain.member.repository.MemberRepository;
import com.excel.download.domain.member.dao.MemberDao;
import com.excel.download.domain.member.domain.Address;
import com.excel.download.domain.member.domain.Member;
import com.excel.download.domain.member.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;
    private final MemberRepository memberRepository;

    public Long create(MemberCreateRequest request) {

        Address address = Address.builder()
                .zipCode(request.getZipCode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .build();

        Member member = Member.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .gender(request.getGender())
                .address(address)
                .build();

        return memberRepository.save(member).getMemberIdx();
    }
}
