var keyword = {
    init : function () {
        var _this = this;

        _this.getVisitorCnt();

        //키워드 등록 버튼 클릭 시
        $("#saveKeywordBtn").bind("click",_this.save);

        //키워드에서 엔터 클릭 시
        $("#keywordInput").keyup(function(e){
            if(e.keyCode==13){
                _this.save();
            }
        });

        // 키워드에 마우스 오버 시 섹터 목록
        $("td.keywordCol > .keywordText").hover(function(){
            $("#tooltip").css("top",$(this).offset().top+20);
            $("#tooltip").css("display","inline-block");
            $("#tooltip").html($(this).siblings(".tooltipText").html());
        },function(){
            $("#tooltip").css("display","none");
        });

        //삭제 클릭 시
        $(".deleteKeywordCol").click(function(){
            var idx = $(this).closest("tr").attr("idx");
            _this.delete(idx);
        });

        //keyword 별로 검색 결과 호출
        _this.setSearchResults();
    },
    save: function() {
        var data = {"keywordText":$("#keywordInput").val()};
        $.ajax({
            url:"/keyword/save",
            method:"post",
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            async:false,
            success:function(){
                console.log("저장 성공");
            },
            failure:function(data){
                console.log("실패");
            },
            complete:function(data){
                location.reload();
            }
        });
    },
    delete: function(idx){
        $.ajax({
            url:"/keyword/delete/"+idx,
            method:"delete",
            async:false,
            complete:function(data){
                location.reload();
            }
        });
    },
    setSearchResults: function(){
        var _this = this;
        var keywordSet = new Set();
        $(".keywordMainRow").each(function(){
            keywordSet.add($(this).attr("keyword"));
        });
        for(let keyword of keywordSet.values()){
            _this.retrieveKeywordResult(keyword);
        }
    },
    retrieveKeywordResult: function(keyword){
        var _this = this;

        $.ajax({
            url:"/api/searchResult/"+keyword,
            method:"get",
            async:false,
            complete:function(data){
                var result = data.responseJSON;
                //아래는 함수로 구성
                _this.setKeywordRowFromResult(keyword,result);
            }
        });
    },
    setKeywordRowFromResult:function(keyword,result){
        var _this = this;
        //월 검색량
        if(result["pcView"]){
            $(".keywordMainRow[keyword='"+keyword+"'] .searchCntCol").append(result["pcView"].toLocaleString("en-US")+"<br>(PC)<br><br>");
        }
        if(result["mview"]){
            $(".keywordMainRow[keyword='"+keyword+"'] .searchCntCol").append(result["mview"].toLocaleString("en-US")+"<br>(Mobile)");
        }


        var sectionList = result["sections"].split("$sprt$");
        console.log(result["posts"]);
        var posts = JSON.parse(result["posts"]);
        console.log(posts);

        $(".keywordMainRow[keyword='"+keyword+"'] td.keywordCol > .tooltipText").html("("+sectionList.join(", ").replaceAll(", -","")+")");
        var rmvSections = ["어학사전","지식백과","카페 중고거래","이미지","연관 검색어도움말",keyword+" 국가정보","플레이스","관련 정보","함께 찾는 콘텐츠","동영상"
        ,"FAQ","공모주","몸에 좋은 제철음식","중국어사전&middot;웹수집","비슷한 연령대가 많이 찾아본 쇼핑","영어사전"
        ,keyword+"(주)","(주)"+keyword,"학술정보","앱정보"
        ,"다른 ‘"+keyword+"’을 찾으세요?"
        ,"다른 ‘"+keyword+"’를 찾으세요?","트렌드 키워드","많이 본 지식백과","-","네이버 책"];
        var rmvSecIdx;
        for(var i=0; i<rmvSections.length; i++){
            rmvSecIdx = sectionList.indexOf(rmvSections[i]);
            if(rmvSecIdx > -1){
                sectionList.splice(rmvSecIdx,1);
            }
        }
        var rmvSections2 = ["많이 찾아 본","-"];
        for(var i=sectionList.length-1; i>=0; i--){
            for(var j=0; j<rmvSections2.length; j++){
                if(sectionList[i].indexOf(rmvSections2[j]) > -1){
                    sectionList.splice(i,1);
                }
            }
        }

        var postList;
        var postType="기타";
        var postedDate="";
        var visitCnt="";
        for(var i=0; i<5 && i<sectionList.length; i++){
            //섹션 5개 넣기
            $("tr[keyword='"+keyword+"'] .section"+(i+1)).html(sectionList[i]);
            //포스트 넣기
            postList = posts[sectionList[i]];
            for(var j=0; j<$(postList).length; j++){
                postType="기타";
                postedDate="";
                visitCnt="";
                if(postList[j]["link"].indexOf("blog.naver.com")>-1){
                    postType = postList[j]["title"]+" (블로그)";
                    var cnt = _this.getBlogIdFromLink(postList[j]["link"]);
                    visitCnt = _this.getVisitorCnt(cnt);
                }else if(postList[j]["link"].indexOf("cafe.naver.com")>-1){
                    postType = postList[j]["title"]+" (카페)";
                }else if(postList[j]["link"].indexOf("post.naver.com")>-1){
                    postType = postList[j]["title"]+" (포스트)";
                }else if(postList[j]["link"].indexOf("in.naver.com")>-1){
                    postType = postList[j]["title"]+" (인플루언서 블로그)";
                    visitCnt = _this.getVisitorCnt(_this.getBlogIdFromLink(postList[j]["link"]));
                }else if(postList[j]["link"].indexOf("/intentblock/search.naver")>-1){
                    postType = postList[j]["title"];
                }else if(sectionList[i].indexOf("네이버쇼핑") > -1 && j>7){
                    continue;
                }else if(sectionList[i].indexOf("네이버쇼핑") > -1){
                    postType = postList[j]["title"];
                }

                if(postList[j]["date"].indexOf("none") < 0){
                    postedDate=" ("+postList[j]["date"]+")";
                }
                $("tr[keyword='"+keyword+"'] .posts"+(i+1)).append(
                    $("<a></a>").attr("href",postList[j]["link"]).html(postType+visitCnt+postedDate)
                );
                $("tr[keyword='"+keyword+"'] .posts"+(i+1)).append(" / ");
            }

        }

//        $("tr[keyword='쭈꾸미 요리법'] td[class^=section]:NOT(.section1)").each(function(){
//            if($(this).html()==""){
//                $(this).closest("tr").remove();
//            }
//        });
    },
    getBlogIdFromLink:function(link){
        var blogId = "";
        if(link.indexOf("blogId=")>-1){//http://blog.naver.com/PostList.nhn?blogId=sfef
            blogId = link.substring(link.indexOf("blogId="),link.length);
        }else{//http://m.blog.naver.com/wfadf/32424
            link = link.replace("http://","");
            link = link.replace("https://","");
            link = link.replace("m.","");
            link = link.replace("in.","");
            link = link.replace("blog.","");
            link = link.replace("naver.com/","");
            blogId = link.substring(0,link.indexOf("/"));
        }
        return blogId;

    },
    getVisitorCnt:function(blogId){
        var responseText="";
        var visitCnt = "";

        if(blogId){
            $.ajax({
                url:"/cmn/blogVisitCnt/"+blogId,
                method:"get",
                async:false,
                complete:function(data){
                    responseText = data.responseJSON["responseText"].toString();
                    if(typeof(responseText)=="string"){
                        var startIdx = responseText.indexOf("<div class=\"count__LOiMv\">");
                        var visitCntString = responseText.substring(startIdx,startIdx+100);
                        var today = visitCntString.substring(visitCntString.indexOf("오늘")+2,visitCntString.indexOf("<i class=\"dot__q1HEN\">"));
                        var total = visitCntString.substring(visitCntString.indexOf("전체")+2,visitCntString.indexOf("</div><h2 class"));
                        visitCnt = "("+today+"/"+total+")";
                    }
                }
            });
        }
        return visitCnt;
    }
};

keyword.init();