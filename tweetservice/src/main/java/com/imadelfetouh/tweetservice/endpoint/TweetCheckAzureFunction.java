package com.imadelfetouh.tweetservice.endpoint;

import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Repository
public class TweetCheckAzureFunction implements TweetCheck {

    @Override
    public int check(String content) {
        String url = "https://faasimadquarkus.azurewebsites.net/api/tweet";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("content", content);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        try{
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            return responseEntity.getStatusCode().value();
        }
        catch (HttpClientErrorException e) {
            return e.getStatusCode().value();
        }
    }
}
