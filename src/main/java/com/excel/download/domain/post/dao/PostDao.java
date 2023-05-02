package com.excel.download.domain.post.dao;


import com.excel.download.domain.post.dto.PostExcelDownload;
import com.excel.download.domain.post.dto.QPostExcelDownload;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.excel.download.domain.member.domain.QMember.member;
import static com.excel.download.domain.post.domain.QPost.post;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostDao {

    private final JPAQueryFactory queryFactory;

    public List<PostExcelDownload> excelDownload() {
        return queryFactory
                .select(new QPostExcelDownload(
                        post.title,
                        post.contents,
                        post.tags.tags,
                        member.name,
                        post.createdDate
                ))
                .from(post)
                .innerJoin(member)
                .on(member.memberIdx.eq(post.member.memberIdx))
                .fetch();
    }
}
