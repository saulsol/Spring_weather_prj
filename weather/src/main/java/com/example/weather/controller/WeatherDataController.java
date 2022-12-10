package com.example.weather.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class WeatherDataController {
    String apiUrl = "http://apis.data.go.kr/1360000/TourStnInfoService/getTourStnVilageFcst";
    String serviceKey = "서비스 키";
    String pageNo = "1";	//페이지 번호
    String numOfRows = "10";	//한 페이지 결과 수
    String dataType = "JSON";	//데이터 타입
    String CURRENT_DATE = "20220329";	//조회하고싶은 날짜
    String HOUR = "24";	//조회하고 싶은 날짜의 시간 날짜
    String COURSE_ID = "1";	//관광 코스ID


    @GetMapping("/wea")
    public String weather() throws IOException {


        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", StandardCharsets.UTF_8) + "="+serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(pageNo, StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(numOfRows, StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("dataType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(dataType, StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("CURRENT_DATE", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(CURRENT_DATE, StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("HOUR", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(HOUR, StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("COURSE_ID", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(COURSE_ID, StandardCharsets.UTF_8));

        /*
         * GET방식으로 전송해서 파라미터 받아오기
         */
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result= sb.toString();
        System.out.println(result);

        return result;

    }


}
