package com.excel.download.domain.post.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags {

    @Comment(value = "태크")
    @Column(name = "tags")
    private String tags;

    public Tags(Set<String> tags) {
        this.tags = tags.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
