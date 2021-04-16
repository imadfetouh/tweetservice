package com.imadelfetouh.tweetservice.endpoint;

import com.imadelfetouh.tweetservice.dalinterface.LikeDal;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("like")
public class LikeResource {

    @Autowired
    private LikeDal likeDal;

    @PutMapping("/{tweetId}")
    public ResponseEntity<Void> likeTweet(@PathVariable("tweetId") String tweetId) {
        ResponseModel<Void> responseModel = likeDal.likeTweet(tweetId);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(500).build();
    }
}
