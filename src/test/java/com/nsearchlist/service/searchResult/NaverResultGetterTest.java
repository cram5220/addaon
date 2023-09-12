package com.nsearchlist.service.searchResult;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NaverResultGetterTest {
//    @Test
//    public void getSearchCountTest() {
//        NaverResultGetter result = new NaverResultGetter("쭈꾸미+볶음");
//        result.getSearchCount();
//
//        System.out.println(result.getPcView());
//        System.out.println(result.getMobileView());
//
//    }

//    @Test
//    public void getSectorSortTest() {
//
//        NaverResultGetter result = new NaverResultGetter("갯벌");
//        List<String> sectors = result.getSectorSort();
//
//        //then
//        for(String sector: sectors){
//            System.out.println(sector);
//        }
//    }

//    @Test
//    public void getSectionListTest() {
//        
//        NaverResultGetter result = new NaverResultGetter("미스트");
//        List<String> sectors = result.getSectionList();
//
//        //then
//        for(String sector: sectors){
//            System.out.println(sector);
//        }
//    }

//    //이거 다시 테스트
//    @Test
//    public void getSectionTitleTest() {
//        NaverResultGetter result = new NaverResultGetter("미스트");
//        String org = "<h2 class=\"api_title\"> <i class=\"spnew api_ico_shoplogo\"></i>네이버쇼핑<div class=\"api_title_inner\"> <a role=\"button\" href=\"#\" class=\"api_link_help _trigger\" aria-pressed=\"false\" title=\"이 정보가 표시된 이유\" onclick=\"return tCR('a=shp_gui.imark&amp;r=&amp;i=&amp;u=javascript');\"><i class=\"spnew api_ico_alert\">이 정보가 표시된 이유</i></a> <div class=\"ly_api_info _content\" style=\"display: none;\"> <p class=\"dsc\">네이버가 운영하는 쇼핑 서비스입니다.</p> <button type=\"button\" class=\"btn_close _trigger\" title=\"안내 레이어 닫기\" onclick=\"return tCR('a=shp_gui.guideclose&amp;r=&amp;i=&amp;u=javascript');\"><i class=\"spnew ico_close\">정보확인 레이어 닫기</i></button> </div> </div> </h2>";
//        String expected = result.getSectionTitle(org);
//        System.out.println(expected);
//        //assertThat(expected).isEqualTo("네이버쇼핑");
//    }
//
//    @Test
//    public void getPostListTest() {
//
//        NaverResultGetter result = new NaverResultGetter("메이플");
//        List<String> sectors = result.getSectionList();
//
//        String sectionTitle;
//        //then
//        for(String sector: sectors){
//
//            sector = sector.replaceAll("<ul\\sclass=[\"\'](list_item|list_rel|lst_related_srch)[\"\'](.+?)</ul>","");
//            sector = sector.replaceAll("<div\\sid=[\"\']sub_pack[\"\'](.+?)</script>","");
//
//            String listPattern;
//            sectionTitle = result.getSectionTitle(sector);
//            // 인기주제 둘러보기이면
//            if(sectionTitle.contains("인기 주제 둘러보기")){
//                listPattern = "<div\\sclass=[\"\']flick_bx[\"\']([\\s\\S]+?)<\\/div>";
//            }else if(sectionTitle.contains("뉴스")){ //뉴스면
//                listPattern = "(<div\\sclass=\"news_area\">.+?<ul|<li\\sclass=\"sub_bx\">.+?</li>)";
//            }else{// 그 외면
//                listPattern = "<li\\s([\\s\\S]+?)<\\/li>";
//            }
//
//            System.out.println(sectionTitle); //
//            //li (인텐트블록 외 나머지) or div.flick_bx (인텐트 블록)
//
//            Pattern ElementPattern = Pattern.compile(listPattern);
//            Matcher match = ElementPattern.matcher(sector);
//
//            Pattern unimpl = Pattern.compile("(<li class=\"item\"|<li class=\"list_more\">)");
//            Matcher finder;
//
//            List<String> elementList = new ArrayList<String>();
//            String elementString;
//
//            while(match.find()) {
//                elementString = match.group();
//                finder = unimpl.matcher(elementString);
//                if(!finder.find()){
//                    elementList.add(elementString);
//                    System.out.println("count : "+Integer.toString(elementList.size())+"===================================");
//                    System.out.println(elementString);
//                    System.out.println("===================================");
//                }
//            }
//        }
//
//    }
//
//


    @Test
    public void getPostTitleTest() {
        NaverResultGetter result = new NaverResultGetter("메이플");

        //섹션 목록 가져오기
        List<String> sectionList = result.getSectionList();

        String sectionTitle;
        List<String> postList;
        String postTitle;
        String postDate;
        String postLink;
        for (String section : sectionList) {
            sectionTitle = result.getSectionTitle(section);
            System.out.println("=============================================================");
            System.out.println("=============================================================");
            System.out.println(sectionTitle);
            postList = result.getPostList(section,sectionTitle);
            for(String post : postList) {
                postTitle = result.getPostTitle(post);
                System.out.println(postTitle);
                postDate = result.getPostDate(post);
                System.out.println(postDate);
                postLink = result.getPostLink(post);
                System.out.println(postLink);
            }
        }

    }


}
