package com.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Value("${application.name}")
    private String client;
    @Value("${application.secret}")
    private String secret;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*
        Authorisation Server needs the details of Client id, Client secret & details of user.
        Whenever anyone clicks on login with google then authorization server of google asks the details
         for client as well as user
        Here we configure about the client (app) that who is accessing the Authorization Server.
         We also need to tell the Authorization Server about the details of our app.
        Here we are configuring our Client(app)
        */
        clients.inMemory().withClient(client).secret(secret).scopes("read").authorizedGrantTypes("password");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //here we are configuring user qwerty with  client
        endpoints.authenticationManager(authenticationManager);
    }
}
