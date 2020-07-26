package com.chathu.covidapp.util;

import com.chathu.covidapp.api.RestAPIClient;
import com.chathu.covidapp.model.LoggedInUser;

public class SessionManager {
    private static SessionManager manager;
    private LoggedInUser loggedInUser;
    private SessionManager(){

    }


    public static SessionManager getInstance(){
        if(manager == null){
            manager = new SessionManager();
        }
        return manager;
    }

    public void setLoggedInUser(LoggedInUser loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    public LoggedInUser getLoggedInUser(){
        return loggedInUser;
    }
}
