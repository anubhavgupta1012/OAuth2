package com.oauth2.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /*
    following endpoint with Basic Authorisation with Client & secret will give the access token
    * http://localhost:8080/oauth/token?grant_type=password&scope=read&username=qwerty&password=qwerty  (POST)
    */

    @GetMapping("/data")
    public String getStrig() {
        return "Security String";
    }
}
