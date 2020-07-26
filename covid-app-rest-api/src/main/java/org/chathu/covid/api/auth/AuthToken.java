package org.chathu.covid.api.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

public class AuthToken {

    private final String authSecret="2wjhIOJWy9LxVWRsUYjbhgNNTL6KI1vs";
    private final String iss = "http://covid-app-rest-api.azurewebsites.net";

    private String userName;
    private boolean isPrivilege;
    private String userToken;

    public AuthToken(String userName, boolean isPrivilege, String userToken){
        this.userName = userName;
        this.isPrivilege = isPrivilege;
        this.userToken = userToken;
    }

    public AuthToken(String userName, boolean isPrivilege) throws UnsupportedEncodingException {

        Algorithm algorithm = Algorithm.HMAC256(authSecret);
        String token = JWT.create()
                .withIssuer(iss)
                .withClaim("userName",userName)
                .withClaim("isPrivilege",isPrivilege)
                .sign(algorithm);

        this.userName = userName;
        this.isPrivilege = isPrivilege;
        this.userToken = token;
    }

    public AuthToken(String userToken) throws UnsupportedEncodingException {

        Algorithm algorithm = Algorithm.HMAC256(authSecret);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(iss)
                .build();
        DecodedJWT jwt = verifier.verify(userToken);
        this.userName = jwt.getClaim("userName").asString();
        this.isPrivilege = jwt.getClaim("isPrivilege").asBoolean();
        this.userToken = userToken;
    }


    public String getUserName() {
        return userName;
    }

    public boolean isPrivilege() {
        return isPrivilege;
    }

    public String getUserToken() {
        return userToken;
    }
}
