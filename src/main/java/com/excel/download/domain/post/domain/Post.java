package com.excel.download.domain.post.domain;

import com.excel.download.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @Comment(value = "post PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postIdx;

    @Comment(value = "제목")
    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Comment(value = "내용")
    @Column(name = "contents")
    private String contents;

    @Embedded
    private Tags tags;

    @Comment(value = "작성일")
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", foreignKey = @ForeignKey(name = "fk_post_member"))
    private Member member;

    @Builder
    public Post(Member member, String title, String contents, Tags tags) {
        this.member = member;
        this.title = title;
        this.contents = contents;
        this.tags = tags;
        this.createdDate = LocalDateTime.now();
    }
}
