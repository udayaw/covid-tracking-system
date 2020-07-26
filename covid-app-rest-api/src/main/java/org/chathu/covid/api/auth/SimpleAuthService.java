package org.chathu.covid.api.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.apache.commons.codec.digest.DigestUtils;
import org.chathu.covid.api.controller.AppUserController;
import org.chathu.covid.api.model.documents.User;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;

public class SimpleAuthService {

    public AuthToken authenticateRequest(JSONObject requestBody){
        if(requestBody.has("authToken")){
            String providedAuthToken = requestBody.getString("authToken");
            try {
                AuthToken authToken = new AuthToken(providedAuthToken);
                return authToken;
            } catch (JWTDecodeException |UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public AuthToken authenticateUser(String userName, String password){


        AppUserController controller = new AppUserController();
        try {
            User userDoc = controller.getUser(userName);
            if(userDoc == null){
                return null;
            }

            String providedPassHash = getPassHash(password);
            String validPassHash = userDoc.getPassHash();


            if(validPassHash.equals(providedPassHash)){

                boolean isPrivilege = userDoc.getUserType() == User.UserType.PRIVILEGE ? true : false;
                AuthToken token = new AuthToken(userName,isPrivilege);
                return token;
            }

        } catch (UnsupportedEncodingException e ) {
            e.printStackTrace();
        }

        return null;
    }

    public String getPassHash(String password){
        if(password == null){
            throw new InvalidParameterException("password cannot be null");
        }
        String passHash = DigestUtils.md5Hex(password);
        return passHash;
    }


}
