package com.imadelfetouh.tweetservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imadelfetouh.tweetservice.dal.configuration.Executer;
import com.imadelfetouh.tweetservice.dal.configuration.SessionType;
import com.imadelfetouh.tweetservice.dal.db.LikeDalDB;
import com.imadelfetouh.tweetservice.dal.db.MentionDalDB;
import com.imadelfetouh.tweetservice.dal.db.TrendDalDB;
import com.imadelfetouh.tweetservice.dal.db.TweetDalDB;
import com.imadelfetouh.tweetservice.dalinterface.LikeDal;
import com.imadelfetouh.tweetservice.dalinterface.MentionDal;
import com.imadelfetouh.tweetservice.dalinterface.TrendDal;
import com.imadelfetouh.tweetservice.dalinterface.TweetDal;
import com.imadelfetouh.tweetservice.model.dto.NewTweetDTO;
import com.imadelfetouh.tweetservice.model.dto.TrendDTO;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.jwt.UserData;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import com.imadelfetouh.tweetservice.rabbit.RabbitConfiguration;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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

        Channel channel = RabbitConfiguration.getInstance().getChannel();
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().correlationId("testcorr").build();

        try {
            String user1 = new JSONObject()
                    .put("userId", "u123").put("username", "imad").put("password", "imad").put("role", "USER").put("photo", "imad.jpg")
                    .put("profile", new JSONObject().put("profileId", "p123").put("bio", "Hello").put("location", "Helmond").put("website", "imad.nl")).toString();

            String user2 = new JSONObject()
                    .put("userId", "u1234").put("username", "peter").put("password", "peter").put("role", "USER").put("photo", "peter.jpg")
                    .put("profile", new JSONObject().put("profileId", "p1234").put("bio", "Hello").put("location", "Helmond").put("website", "peter.nl")).toString();

            String following1 = new JSONObject()
                    .put("userId", "u123").put("followingId", "u1234").toString();

            channel.basicPublish("adduserexchange", "", properties, user1.getBytes());
            channel.basicPublish("adduserexchange", "", properties, user2.getBytes());
            channel.basicPublish("addfollowingexchange", "", properties, following1.getBytes());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
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
    void addTweetCorrect() throws InterruptedException {
        Gson gson = new Gson();
        TweetDal tweetDal = new TweetDalDB();

        NewTweetDTO newTweetDTO = new NewTweetDTO("u123", "hello");
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

    @Test
    @Order(3)
    void addAndGetTrend() {
        TweetDal tweetDal = new TweetDalDB();
        NewTweetDTO newTweetDTO = new NewTweetDTO("u123", "hello, my #name is Imad. @peter is my friend");

        ResponseModel<Void> responseModel = tweetDal.addTweet(newTweetDTO);

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());

        TrendDal trendDal = new TrendDalDB();
        ResponseModel<List<TrendDTO>> responseModelTrends = trendDal.getTrends();

        Assertions.assertEquals(ResponseType.CORRECT, responseModelTrends.getResponseType());
        Assertions.assertEquals(1, responseModelTrends.getData().size());
        Assertions.assertEquals("#name", responseModelTrends.getData().get(0).getTrend());

    }

    @Test
    @Order(4)
    void getTweetTrends() {
        TrendDal trendDal = new TrendDalDB();
        ResponseModel<List<TweetDTO>> responseModel = trendDal.getTweetTrends("u123", "#name");

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());
        Assertions.assertEquals(1, responseModel.getData().size());
        Assertions.assertEquals("u123", responseModel.getData().get(0).getUser().getUserId());

    }

    @Test
    @Order(5)
    void getTweetMention() {
        MentionDal mentionDal = new MentionDalDB();
        ResponseModel<List<TweetDTO>> responseModel = mentionDal.getTweetMentions("u1234");

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());
        Assertions.assertEquals(1, responseModel.getData().size());
        Assertions.assertEquals("hello, my #name is Imad. @peter is my friend", responseModel.getData().get(0).getContent());

    }

    @Test
    @Order(6)
    void addLike() throws InterruptedException {
        Gson gson = new Gson();
        LikeDal likeDal = new LikeDalDB();
        ResponseModel<Void> responseModel = likeDal.likeTweet("u1234", "t123");

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
        Assertions.assertEquals(1, jsonObject.getAsJsonArray("tweets").get(0).getAsJsonObject().get("likes").getAsInt());
    }
}
