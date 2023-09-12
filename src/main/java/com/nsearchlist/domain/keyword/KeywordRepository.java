package com.nsearchlist.domain.keyword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    @Query("SELECT k FROM Keyword k WHERE k.memberIdx = ?1 ORDER BY k.sort DESC")
    List<Keyword> getListByMemberIdx(@Param("memberIdx") Long memberIdx);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM KEYWORD WHERE MEMBER_IDX= ?1")
    int getKeywordCountByMemberIdx(@Param("memberIdx") Long memberIdx);

    @Transactional //이거 테스트에서만 적용인지 실제 필요한지 확인
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE KEYWORD SET SORT=SORT-1 WHERE MEMBER_IDX=:memberIdx AND SORT>:sort")
    void updateSort(@Param("memberIdx") Long memberIdx,@Param("sort") int sort);
}
