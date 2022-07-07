package com.example.demosp;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class pictures {

    @GetMapping("/pictures/{sol}/largest")
    public ResponseEntity getLargestImage(@PathVariable int sol) {
        String url = String.format("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=%d&api_key=DEMO_KEY", sol);
        RestTemplate restTemplate = new RestTemplate();
        JsonNode forEntity = restTemplate.getForObject(url, JsonNode.class);
        long imageLength = 0;
        String largestUrl = "";
        for (var photo : forEntity.get("photos")) {
            String img_src = photo.get("img_src").asText();
            long contentLength = restTemplate.headForHeaders(restTemplate.headForHeaders(img_src).getLocation()).getContentLength();
            if (contentLength > imageLength) {
                imageLength = contentLength;
                largestUrl = img_src;
            }
        }
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(URI.create(largestUrl)).build();
    }
}
