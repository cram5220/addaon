package com.nsearchlist.domain.keyword;

import com.nsearchlist.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Keyword extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long memberIdx;

    @Column(nullable = true)
    private int sort;

    @Column(length = 50, nullable = false)
    private String keywordText;

    @Builder
    public Keyword(Long memberIdx, String keywordText, int sort) {
        this.memberIdx = memberIdx;
        this.keywordText = keywordText;
        this.sort = sort;
    }

    public void update(int sort) {
        this.sort = sort;
    }

}
