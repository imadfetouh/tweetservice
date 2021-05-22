package com.imadelfetouh.tweetservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.db.TweetDalDB;
import com.imadelfetouh.tweetservice.dalinterface.TweetDal;
import com.imadelfetouh.tweetservice.model.dto.NewTweetDTO;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.jwt.UserData;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TweetRabbitTests {

    String getJWT() {
        Gson gson = new Gson();
        UserData userData = new UserData("u123", "imad", "USER");;
        Map<String, String> claims = new HashMap<>();
        claims.put("userdata", gson.toJson(userData));
        return CreateJWTToken.getInstance().create(claims);
    }

    @BeforeAll
    static void setupDatabase() {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        executer.execute(new SetupTestDatabase());
    }

    @Test
    @Order(1)
    void getTweetEmpty() {
        TweetDal tweetDal = new TweetDalDB();

        ResponseModel<List<TweetDTO>> responseModel = tweetDal.getTweets("u123");

        Assertions.assertEquals(ResponseType.EMPTY, responseModel.getResponseType());
    }

    @Test
    @Order(2)
    void addTweetEmpty() throws InterruptedException {
        Gson gson = new Gson();
        TweetDal tweetDal = new TweetDalDB();

        NewTweetDTO newTweetDTO = new NewTweetDTO("t123", "hello");
        ResponseModel<Void> responseModel = tweetDal.addTweet(newTweetDTO);

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());

        Thread.sleep(2000);

        String jwtToken = getJWT();
        String url = "http://localhost:8089/profile/u123";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "jwt-token="+jwtToken);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals("hello", jsonObject.getAsJsonArray("tweets").get(0).getAsJsonObject().get("content").getAsString());
    }


}
