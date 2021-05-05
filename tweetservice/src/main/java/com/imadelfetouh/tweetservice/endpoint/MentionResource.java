package com.imadelfetouh.tweetservice.endpoint;

import com.google.gson.Gson;
import com.imadelfetouh.tweetservice.dalinterface.MentionDal;
import com.imadelfetouh.tweetservice.model.dto.TweetDTO;
import com.imadelfetouh.tweetservice.model.jwt.UserData;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mention")
public class MentionResource {

    @Autowired
    private MentionDal mentionDal;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TweetDTO>> getTweetMentions(@RequestAttribute("userdata") String userDataString) {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(userDataString, UserData.class);

        ResponseModel<List<TweetDTO>> responseModel = mentionDal.getTweetMentions(userData.getUserId());

        if(responseModel.getResponseType().equals(ResponseType.EMPTY)) {
            return ResponseEntity.noContent().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().body(responseModel.getData());
        }

        return ResponseEntity.status(500).build();

    }
}
