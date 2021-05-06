package com.imadelfetouh.tweetservice.endpoint;

import com.google.gson.Gson;
import com.imadelfetouh.tweetservice.dalinterface.LikeDal;
import com.imadelfetouh.tweetservice.model.jwt.UserData;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("like")
public class LikeResource {

    @Autowired
    private LikeDal likeDal;

    @PutMapping("/{tweetId}")
    public ResponseEntity<Void> likeTweet(@RequestAttribute("userdata") String userDataString, @PathVariable("tweetId") String tweetId) {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(userDataString, UserData.class);

        ResponseModel<Void> responseModel = likeDal.likeTweet(userData.getUserId(), tweetId);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.ALREADYLIKED)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(500).build();
    }
}
