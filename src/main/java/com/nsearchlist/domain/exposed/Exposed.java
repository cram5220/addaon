package com.nsearchlist.domain.exposed;

import com.nsearchlist.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Exposed extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long memberIdx;

    @Column(nullable = true)
    private int sort;

    @Column(length = 50, nullable = false)
    private String keyword;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String url;


    @Builder
    public Exposed(Long memberIdx, String keyword, String title, String url, int sort) {
        this.memberIdx = memberIdx;
        this.keyword = keyword;
        this.title = title;
        this.url = url;
        this.sort = sort;
    }

    public void update(int sort) {
        this.sort = sort;
    }

}
