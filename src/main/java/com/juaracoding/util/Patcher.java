package com.juaracoding.util;

import com.juaracoding.model.User;
import com.juaracoding.security.Crypto;
import com.juaracoding.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class Patcher {

    public static void userPatcher(User existingUser, User incompleteUser) throws IllegalAccessException {
        Class<?> userClass= User.class;
        Field[] userFields=userClass.getDeclaredFields();
        for(Field field : userFields){
            field.setAccessible(true);
            Object value=field.get(incompleteUser);
            if(value!=null){
                field.set(existingUser,value);
            }
            field.setAccessible(false);
        }
    }
}