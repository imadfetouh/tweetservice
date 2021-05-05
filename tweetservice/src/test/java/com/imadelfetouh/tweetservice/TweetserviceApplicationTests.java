package com.imadelfetouh.tweetservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class TweetserviceApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Test
//    void testRequest() {
//        String url = "http://localhost:8082/tweet";
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyZGF0YSI6IntcInVzZXJJZFwiOlwidTEyM1wiLFwidXNlcm5hbWVcIjpcImltYWRcIixcInJvbGVcIjpcIlVTRVJcIn0iLCJpc3MiOiJLd2V0dGVyaW1hZCIsImlhdCI6MTYyMDIyODgxNiwiZXhwIjoxNjIwMjMyNDE2fQ.M9ZhYiJ-mocYV0TZPW_FpAn-9lY_5fB55CWpj_QEgNg";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cookie","jwt-token="+token+"; Domain=localhost; Path=/; HttpOnly;");
//        HttpEntity<?> httpEntity = new HttpEntity(headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
//
//        Assertions.assertEquals(204, responseEntity.getStatusCode().value());
//    }

}
