package com.oauth2.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /*
     * Grant_type = password
     * following endpoint with Basic Authorisation with Client & secret will give the access token
     * http://localhost:8080/oauth/token?grant_type=password&scope=read&username=qwerty&password=qwerty  (POST)
     */


    /*
     * Grant_type : authorization-code
     * to get the auth_code for substituting the user's credential
     * http://localhost:8080/oauth/authorize?response_type=code&client_id=client_2&scope=read     (GET)
     *
     *http://localhost:8080/oauth/token?grant_type=authorization_code&scope=read&code=v4ZXC-  (POST)
     * with Basic Authorization with Clientid & secret
     * */

    /*
     *   Grant_type : Client_credentials
     *   http://localhost:8080/oauth/token?grant_type=client_credentials&scope=XYZ__ABC          (POST)
     *   with Basic Authorization with Client_id & secret
     */

    @GetMapping("/data")
    public String getStrig() {
        return "Security String";
    }
}
