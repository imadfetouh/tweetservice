package com.imadelfetouh.tweetservice.endpoint;

import com.google.gson.Gson;
import com.imadelfetouh.tweetservice.dalinterface.TweetDal;
import com.imadelfetouh.tweetservice.model.dto.NewTweetDTO;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.jwt.UserData;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetResource {

    @Autowired
    private TweetDal tweetDal;

    @Autowired
    private TweetCheck tweetCheck;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TweetDTO>> getTweets(@RequestAttribute("userdata") String userDataString) {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(userDataString, UserData.class);

        ResponseModel<List<TweetDTO>> responseModel = tweetDal.getTweets(userData.getUserId());

        if(responseModel.getResponseType().equals(ResponseType.EMPTY)) {
            return ResponseEntity.noContent().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().body(responseModel.getData());
        }

        return ResponseEntity.status(500).build();

    }

    @PostMapping
    public ResponseEntity<String> addTweet(@RequestAttribute("userdata") String userDataString, @RequestParam("content") String content) {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(userDataString, UserData.class);

        if(content == null || content.trim().isEmpty() || content.trim().length() > 140) {
            return ResponseEntity.badRequest().build();
        }

        int tweetCheckStatusCode = tweetCheck.check(content);
        if(tweetCheckStatusCode != 200) {
            return ResponseEntity.status(tweetCheckStatusCode).build();
        }

        NewTweetDTO newTweetDTO = new NewTweetDTO(userData.getUserId(), content);

        ResponseModel<Void> responseModel = tweetDal.addTweet(newTweetDTO);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(500).build();

    }
}
