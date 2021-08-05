package com.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Value("${application.name}")
    private String client;
    @Value("${application.secret}")
    private String secret;
    private final String SECRET = "TEST_SECRET";
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
        clients.inMemory().withClient(client).secret(secret).scopes("read").authorizedGrantTypes("password")
            .and()

            /*
            Grant_type : Authorization Code
            in grant_type = password, to generate access_token, we were providing user's credential (qwerty,qwerty) on the client side itself
            while when anybody uses signin with google, a new screen is opened by google's authorization server to provide credentials so that client app can not access
            user's credential. From that screen authorization server issues auth_code(used as substitute of user's credential) which is used to generate the access_token.
             */
            .withClient("client_2").secret("secret2").scopes("read").authorizedGrantTypes("authorization_code", "refresh_token")
            .accessTokenValiditySeconds(8000).redirectUris("https://localhost:8081")
            .and()
            /*
             *  Grant_type : Client Credential
             *  Here only three actors are present: Client, Auth_Server, Resource_Server, User's Details are not used here
             * scopes can be anything here
             *
             */
            .withClient("client_3").secret("secret3").scopes("XYZ__ABC").authorizedGrantTypes("client_credentials")
            .accessTokenValiditySeconds(8000);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //here we are configuring user qwerty with  client
        endpoints.authenticationManager(authenticationManager)
            .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //This encoder is used for encoding the client_secret
        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
        // /oauth/check_token doesn't allow every one to access the same so we have to enable it
        security.checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SECRET);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
}
