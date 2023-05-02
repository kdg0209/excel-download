package com.excel.download.domain.member.domain;

import com.excel.download.domain.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Comment(value = "member PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    @Comment(value = "이름")
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Comment(value = "연락처")
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Comment(value = "성별")
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 1, nullable = false)
    private Gender gender;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Member(String name, String phone, Gender gender, Address address) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
    }
}
