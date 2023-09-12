package com.nsearchlist.service.searchResult;

import com.nsearchlist.common.HttpGetResponse;
import com.nsearchlist.common.Signatures;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class NaverResultGetter {

    private String responseText;
    private String keyword;
    private Long pcView;
    private Long mobileView;
    private String sectionTitles;
    private String postsJson;

    public NaverResultGetter(String keyword){
        this.keyword = keyword;
        getSearchCount();
        setNaverResultText();
        setNaverResultJson();
    }

    private void setNaverResultJson(){
        List<String> sections = getSectionList();

        String sectionTtile;
        List<String> sectionTitleList = new ArrayList<String>();
        List<String> rawPostList = new ArrayList<String>();
        List<String> posts = new ArrayList<String>();
        List<String> postJsonList = new ArrayList<String>();
        for (String section : sections) {
            // 제목 넣기
            sectionTtile = getSectionTitle(section);
            sectionTitleList.add(sectionTtile);
            rawPostList = getPostList(section,sectionTtile);
            for (String rawPost : rawPostList){
                posts.add(
                    "{"+
                    "\"title\":\""+getPostTitle(rawPost)+"\","+
                    "\"link\":\""+getPostLink(rawPost)+"\","+
                    "\"date\":\""+getPostDate(rawPost)+"\""
                    +"}"
                );
            }
            if(posts.size()>0){
                postJsonList.add("\""+sectionTtile+"\":["+String.join(",",posts)+"]");
                posts.clear();
            }
        }

        // 제목 목록을 한 줄로 만들어 저장
        this.sectionTitles = String.join("$sprt$",sectionTitleList);
        // 포스트 목록을 jsonString 으로 저장
        this.postsJson = "{"+String.join(",",postJsonList)+"}";
    }

    private void setNaverResultText(){
        String keywordText;
        try {
            keywordText = URLEncoder.encode(this.keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }
        String url = "https://search.naver.com/search.naver?query="+keywordText;
        HttpGetResponse httpget = new HttpGetResponse(url);
        this.responseText = httpget.getResponseText();
    }

    // 검색량 가져오기
    private void getSearchCount() {

        //리퀘스트 헤더 설정
        String encodedKeyword = this.keyword;
        try {
            encodedKeyword = URLEncoder.encode(this.keyword.replaceAll("\\s",""),"UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
        String keywordsToolUrl = "https://api.naver.com/keywordstool?hintKeywords="+encodedKeyword;
        String customerId = "2478734";
        String secretKey = "AQAAAAAcv+y0pQ3We14k31JyQdqcMCU7gKS5lgT2nU5ngb/k0w==";
        String apiKey = "01000000001cbfecb4a50dd67b5e24df527241da9c96c5d82671156d37d2110b7420b51388";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = "";
        try {
            sign = Signatures.of(timestamp, "GET", "/keywordstool", secretKey);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
        Map<String,String> headers = new HashMap<String,String>();

        headers.put("User-Agent", "API module");
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("X-API-KEY", apiKey);
        headers.put("X-Customer", customerId);
        headers.put("X-Timestamp", timestamp);
        headers.put("X-Signature", sign);

        // httpget 객체 생성
        HttpGetResponse httpget = new HttpGetResponse(keywordsToolUrl);
        httpget.setRequestHeaders(headers);

        if(((Map<String,Object>)httpget.getResponseJson()).containsKey("keywordList")){
            Map<String,Object> resultMap = ((List<Map<String,Object>>)httpget.getResponseJson().get("keywordList")).get(0);
            if(resultMap.get("monthlyPcQcCnt").getClass() == String.class){
                this.pcView = Long.valueOf(((String)resultMap.get("monthlyPcQcCnt")).replaceAll("[^0-9]",""));
            }else{
                this.pcView = Long.valueOf((Integer)resultMap.get("monthlyPcQcCnt"));
            }
            if(resultMap.get("monthlyMobileQcCnt").getClass() == String.class){
                this.mobileView = Long.valueOf(((String)resultMap.get("monthlyMobileQcCnt")).replaceAll("[^0-9]",""));
            }else{
                this.mobileView = Long.valueOf((Integer)resultMap.get("monthlyMobileQcCnt"));
            }
        }else{
            this.pcView = 0L;
            this.mobileView = 0L;
        }

    }

    // 검색결과에서 섹터 순서 가져오기
//    private List<String> getSectorSort(){
//        List<String> sectorList = new ArrayList<String>();
//        // 섹터 제목에 해당하는 것 뽑아내기
//        Pattern pattern = Pattern.compile("(\\<(h2|strong)\\sclass=\\\"(api_|intent_|)title\\\"\\>([\\S\\s]+?)\\<\\/(h2|strong)\\>)|(검색결과\\s더보기)");
//        Matcher match = pattern.matcher(this.responseText);
//
//        String title = "";
//        while(match.find()){ //찾은 블록마다
//            title = match.group();
//            title = title.replaceAll("\\<([\\S\\s]+?)>","");
//            sectorList.add(title);
//        }
//
//        return sectorList;
//    }

    // 검색결과를 섹션단위로 추출
    List<String> getSectionList() {
        List<String> sectionList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<section([\\S\\s]+?)<\\/section>");
        Matcher match = pattern.matcher(this.responseText);

        String title = "";
        Pattern exceptions=Pattern.compile("(_cs_newgame|_au_people_content_wrap)");
        Matcher finder;
        String section;
        while(match.find()){ //찾은 블록마다
            section = match.group();
            finder = exceptions.matcher(section);
            if(!finder.find()){
                sectionList.add(section);
            }
        }
        return sectionList;
    }

    // 섹션에서 제목 추출
    String getSectionTitle(String section) {
        Pattern pattern = Pattern.compile("(<(h2|strong)\\sclass=\"(api_|intent_|)title[\"\'\\s].+?</(h2|strong)>)|(검색결과\\s더보기)|(_prs_sit_|_nsite_)");
        Matcher match = pattern.matcher(section);
        String title = "-";
        if(match.find()){
            title = match.group();
            title = title.replaceAll("<.+?>","");
            title = title.replaceAll("[a-z]+?=[\"'][^'\"]+?[\"']","");
            if(title.contains("_prs_sit_") || title.contains("_nsite_")){
                title = "사이트";
            }else if(title.contains("이 정보가 표시된 이유")){
                title = title.substring(0,title.indexOf("이 정보가 표시된 이유"));
            }else if(title.contains("네이버가 운영하는 쇼핑 서비스입니다") || title.contains("네이버쇼핑")){
                title = "네이버쇼핑";
            }else if(title.contains("카페 중고거래")){
                title = "카페 중고거래";
            }else if(title.contains("트랜드 키워드")){
                title = "트렌드 키워드";
            }else if(title.contains("인플루언서")){
                title = "인플루언서";
            }else if(title.contains("플레이스")){
                title = "플레이스";
            }else if(title.contains("맛집")){
                title = "맛집";
            }else if(title.contains("많이 본 지식백과")){
                title = "-";
            }else if(title.contains("연관 검색어")){
                title = "-";
            }

            title = title.strip();

        }
        return title;
    }

    // 섹션에서 포스트 목록 추출
    List<String> getPostList(String section, String sectionTitle) {
        // 불필요하게 검색에 걸리는 내용 삭제
        section = section.replaceAll("<ul\\sclass=[\"\'](list_item|list_rel|lst_related_srch)[\"\'](.+?)</ul>","");
        section = section.replaceAll("<div\\sid=[\"\']sub_pack[\"\'](.+?)</script>","");

        // 포스트 목록 뽑아낼 기준 선정
        String listPattern;
        //String sectionTitle = getSectionTitle(section);
        // 인기주제 둘러보기이면
        if(sectionTitle.contains("인기 주제 둘러보기")){
            listPattern = "<div\\sclass=[\"\']flick_bx[\"\']([\\s\\S]+?)<\\/a>";
        }else if(sectionTitle.contains("뉴스")){ //뉴스면
            listPattern = "(<div\\sclass=\"news_area\">.+?<ul|<li\\sclass=\"sub_bx\">.+?</li>)";
        }else{// 그 외면
            listPattern = "<li\\s([\\s\\S]+?)<\\/li>";
        }

        Pattern ElementPattern = Pattern.compile(listPattern);
        Matcher match = ElementPattern.matcher(section);

        // li 태그의 경우 li > ul > li 구조면 잘못 걸릴 수 있어서 제외할 것 따로
        Pattern unimpl = Pattern.compile("(<li class=\"item\"|<li class=\"list_more\">)");
        Matcher finder;

        List<String> postList = new ArrayList<String>();
        String elementString;

        while(match.find()) {
            elementString = match.group();
            finder = unimpl.matcher(elementString);
            if(!finder.find()){
                postList.add(elementString);
            }
        }
        return postList;
    }
    
    // 포스트에서 제목 추출
    String getPostTitle(String post){
        // 웹검색, view, 지식인, 뉴스 = a.~_tit
        // 인텐트블록 div.dsc
        // 인텐트블록 연관, 인플루언서 div.title_area
        // 쇼핑 div.product_info
        String titlePattern = "class=[\"']([^\"']+?_tit|title_area|product_info|title|question_text|dsc_area)[\"\'\\s].+?</a>";
        Pattern pattern = Pattern.compile(titlePattern);
        Matcher matcher = pattern.matcher(post);
        String title = "";
        if(matcher.find()){
            title = matcher.group().replaceAll("<.+?>","").replaceAll("^.+?>","").strip();
        }
        title = title.replaceAll("([\"])","\\\\$1");
        return title;
    }
    
    // 포스트에서 날짜 추출
    String getPostDate(String post){
        // 인텐트블록은 날짜 안나옴. 당연함 키워드임
        // 사이트, 어학사전, 웹결과 안 나옴
        // 인텐트블록 연관은 div.info
        // 인플루언서 div.info (그런데 날짜 외 다른 정보도 있음)
        // 뉴스 span.info (a.info 로 링크 있음)
        // 뷰 span.sub_time

        String datePattern = "<(div|span)\\sclass=[\"\'](info|sub_time)[\"\']>([.0-9시간분일전\\s]+?)</(span|div)>";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(post);
        String date = "none";
        if(matcher.find()){
            date = matcher.group().replaceAll("(<.+?>|::(Before|After))","").strip();
        }
        return date;
    }
    
    // 포스트에서 링크 추출
    String getPostLink(String post){
        String tagPattern = "(<a\\s(target=\"[^\"\']+?[\"\']\\s|)href=[\"\']http[^\"\']+?\"\\s[^>]*?class=\"[^\"\']*?(popular_block_wrap|name_link|title|(link|news|sub|total)_tit)[\\s\"\'].+?)>";
        Pattern pattern = Pattern.compile(tagPattern);
        Matcher matcher = pattern.matcher(post);
        String link = "none";
        String linkAttr = "";
        if(matcher.find()){
            linkAttr = matcher.group(1).replaceAll("<.+>","");
            Matcher mat = Pattern.compile("href=[\"\'](.+?)[\"\']").matcher(linkAttr);
            if(mat.find()){
                link = mat.group(1);
            }
        }
        return link;
    }
    
}
