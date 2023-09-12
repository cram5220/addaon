package com.nsearchlist.domain.searchResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SearchResultRepository extends JpaRepository<SearchResult, Long> {
    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM SEARCH_RESULT WHERE KEYWORD=':keyword'") //AND MODIFIED_DATE > (sysdate - interval '1' hour)
    SearchResult findByKeyword(@Param("keyword") String keyword);

}
