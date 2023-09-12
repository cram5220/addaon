var exposed = {
    init : function () {
        var _this = this;
        //키워드 등록 버튼 클릭 시
        $("#saveExposedBtn").bind("click",_this.save);

        //키워드 삭제 클릭 시
        $(".deleteKeywordCol").click(function(){
            var idx = $(this).closest("tr").attr("idx");
            _this.delete(idx);
        });

        //keyword 별로 검색 결과 호출
        _this.setSearchResults();
    },
    save: function() {
        var data = {
            "keyword":$("#keywordInput").val(),
            "title":$("#postNameInput").val(),
            "url":$("#postUrlInput").val()
            };
        $.ajax({
            url:"/exposed/save",
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
            success:function(data){
                console.log(data);
            },
            failure:function(data){
                console.log(data);
            },
            complete:function(data){
                console.log(data);
            }
        });
    },
    setSearchResults: function(){
        var _this = this;
        var keywordSet = new Set();
        $(".exposedRow").each(function(){
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
                console.log(data.responseJSON);
                var result = data.responseJSON;

                var sectionList = result["sections"].split("$sprt$");
                var posts = JSON.parse(result["posts"]);
                //아래는 함수로 구성
                $("#exposedListArea tr.exposedRow").each(function(){
                    if($(this).attr("keyword")==keyword){
                        _this.findPostUrl($(this),sectionList,posts);
                    }
                });
            }
        });
    },
    findPostUrl:function(row, sectionList, posts){
        var url = $(row).attr("posturl");
        for(var i=0; i<sectionList.length; i++){
            postList = posts[sectionList[i]];
            console.log("sectionList[i] = "+sectionList[i]);
            console.log("posts[sectionList[i]]");
            console.log(postList);
            if(postList){
                for(var j=0; j<postList.length; j++){
                    if(postList[j]["link"]==url){
                        $(row).find(".exposedInfoCol").append((i+1)+" "
                        +sectionList[i]+" "+(j+1)+"번 째");
                        break;
                    }
                }
            }
        }
    }
};

exposed.init();