package com.nsearchlist.domain.exposed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExposedRepository extends JpaRepository<Exposed, Long> {
    @Query("SELECT k FROM Exposed k WHERE k.memberIdx = ?1 ORDER BY k.sort ASC")
    List<Exposed> getListByMemberIdx(@Param("memberIdx") Long memberIdx);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM EXPOSED WHERE MEMBER_IDX= ?1")
    int getCountByMemberIdx(@Param("memberIdx") Long memberIdx);

    //@Transactional //이거 테스트에서만 적용인지 실제 필요한지 확인
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE EXPOSED SET SORT=SORT-1 WHERE MEMBER_IDX=:memberIdx AND SORT>:sort")
    void updateSort(@Param("memberIdx") Long memberIdx,@Param("sort") int sort);
}
