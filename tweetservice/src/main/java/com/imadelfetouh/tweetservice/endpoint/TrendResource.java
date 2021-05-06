package com.imadelfetouh.tweetservice.endpoint;

import com.google.gson.Gson;
import com.imadelfetouh.tweetservice.dalinterface.TrendDal;
import com.imadelfetouh.tweetservice.model.dto.TrendDTO;
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
@RequestMapping("/trend")
public class TrendResource {

    @Autowired
    private TrendDal trendDal;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrendDTO>> getTrends() {
        ResponseModel<List<TrendDTO>> responseModel = trendDal.getTrends();

        if(responseModel.getResponseType().equals(ResponseType.EMPTY)) {
            return ResponseEntity.noContent().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().body(responseModel.getData());
        }

        return ResponseEntity.status(500).build();
    }

    @GetMapping(value = "/{trend}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TweetDTO>> getTweetTrends(@RequestAttribute("userdata") String userDataString, @PathVariable("trend") String trend) {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(userDataString, UserData.class);

        String hashtag = "#" + trend;
        ResponseModel<List<TweetDTO>> responseModel = trendDal.getTweetTrends(userData.getUserId(), hashtag);

        if(responseModel.getResponseType().equals(ResponseType.EMPTY)) {
            return ResponseEntity.noContent().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().body(responseModel.getData());
        }

        return ResponseEntity.status(500).build();
    }
}
