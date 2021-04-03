package com.imadelfetouh.tweetservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trend")
public class TrendResource {

    @GetMapping
    public ResponseEntity<String> getTrends() {
        return ResponseEntity.ok().body("trends");
    }
}
