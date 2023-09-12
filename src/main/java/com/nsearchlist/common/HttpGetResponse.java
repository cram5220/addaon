package com.nsearchlist.common;

import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@NoArgsConstructor
public class HttpGetResponse {

    private String url;
    private Map<String, String> requestHeaders;

    public HttpGetResponse(String url){
        this.url = url;
    }

    public void encodeUrl() {
        try {
            this.url = URLEncoder.encode(this.url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패",e);
        }
    }

    public String getSpecificResponseText(String patternText){
        String responseText = getResponseText();
        Pattern pattern = Pattern.compile(patternText);
        Matcher match = pattern.matcher(responseText);
        if(match.find()){
            return match.group();
        }else{
            return "";
        }
    }

    public void putReqestHeader(String headerName, String headerValue){
        this.requestHeaders.put(headerName, headerValue);
    }

    public String getResponseText() {

        if (this.requestHeaders==null){
            this.requestHeaders = new HashMap<String,String>();
        }
        this.requestHeaders.put("Content-Type", "charset=UTF-8");
        String responseBody = get(this.url,this.requestHeaders);

        return responseBody;
    }

    public Map<String,Object> getResponseJson() {
        String responseText = getResponseText();
        Map<String, Object> mapData = new HashMap<String,Object>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(responseText);
            mapData.putAll(json);
        } catch (ParseException e) {
            throw new RuntimeException("json parse 에 실패했습니다.");
        }


        return mapData;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");

            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        if(body == null){
            return "";
        }
        InputStreamReader streamReader = null;
        try {
            streamReader = new InputStreamReader(body, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("응답을 인코딩하는 데 실패했습니다.", e);
        }


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("응답을 읽는데 실패했습니다.", e);
        }
    }
}
