package com.excel.download.domain.post.service;

import com.excel.download.domain.post.domain.Post;
import com.excel.download.domain.post.domain.Tags;
import com.excel.download.domain.post.dto.PostCreateRequest;
import com.excel.download.domain.post.repository.PostRepository;
import com.excel.download.domain.member.dao.MemberDao;
import com.excel.download.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final MemberDao memberDao;
    private final PostRepository postRepository;

    public Long create(PostCreateRequest request) {
        Member member = memberDao.findByIdx(request.getMemberIdx());
        Post post = Post.builder()
                .member(member)
                .title(request.getTitle())
                .contents(request.getContents())
                .tags(new Tags(request.getTags()))
                .build();
        return postRepository.save(post).getPostIdx();
    }
}
