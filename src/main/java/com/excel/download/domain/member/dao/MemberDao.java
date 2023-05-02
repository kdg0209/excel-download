package com.excel.download.domain.member.dao;

import com.excel.download.domain.member.dto.MemberExcelDownload;
import com.excel.download.domain.member.dto.QMemberExcelDownload;
import com.excel.download.domain.member.repository.MemberRepository;
import com.excel.download.domain.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.excel.download.domain.member.domain.QMember.member;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDao {

    private final JPAQueryFactory queryFactory;
    private final MemberRepository memberRepository;

    public Member findByIdx(Long memberIdx) {
        return memberRepository.findById(memberIdx)
                .orElseThrow(() -> new IllegalArgumentException("not found member"));
    }

    public List<MemberExcelDownload> excelDownload() {
        return queryFactory
                .select(new QMemberExcelDownload(
                        member.name,
                        member.phone,
                        member.gender,
                        member.address.zipCode,
                        member.address.address,
                        member.address.addressDetail
                ))
                .from(member)
                .fetch();
    }

    public List<MemberExcelDownload> excelDownloadByPaging(int offset, int limit) {
        return queryFactory
                .select(new QMemberExcelDownload(
                        member.name,
                        member.phone,
                        member.gender,
                        member.address.zipCode,
                        member.address.address,
                        member.address.addressDetail
                ))
                .from(member)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public Long totalCount() {
        return queryFactory
                .select(member.count())
                .from(member)
                .fetchFirst();
    }
}
