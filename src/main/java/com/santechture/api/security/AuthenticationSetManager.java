package com.santechture.api.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticationSetManager {
    private static Map<String,String> authenticationMap = new ConcurrentHashMap<>() ;

    private AuthenticationSetManager() {

    }

    public static void addNewAuthentication(String user,String newAuthentication) {
        user=user.toLowerCase();
        authenticationMap.put(user ,newAuthentication);
    }

    public static void removeAuthentication(String user) {
        if(user ==null)
            return;
        user=user.toLowerCase();
        authenticationMap.remove(user);
    }

    public static boolean isHasAuthentication(String user , String authToken) {
        if(user ==null || authToken==null)
            return false;
        user=user.toLowerCase();
        return authenticationMap.get(user)!=null && authenticationMap.get(user).equalsIgnoreCase(authToken);
    }
}
