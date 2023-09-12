package com.nsearchlist.domain.searchResult;

import com.nsearchlist.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class SearchResult extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50, nullable = false)
    private String keyword;

    @Column(nullable = false)
    private Long retCnt = 0L;

    @Column(nullable = true)
    private Long pcView;

    @Column(nullable = true)
    private Long mView;

    @Column(length = 500,nullable = true)
    private String sections;

    @Column(columnDefinition = "TEXT",nullable = true)
    private String posts;

    @Builder
    public SearchResult(String keyword, Long pcView, Long mView, String sections, String posts){
        this.keyword = keyword;
        this.pcView = pcView;
        this.mView = mView;
        this.sections = sections;
        this.posts = posts;
    }

    public void incRetCnt() {
        this.retCnt = this.retCnt+1;
    }

    public void update(Long pcView, Long mView, String sections, String posts) {
        this.pcView = pcView;
        this.mView = mView;
        this.sections = sections;
        this.posts = posts;
    }

}
