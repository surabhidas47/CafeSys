package com.inn.cafe.utils;

import com.inn.cafe.constants.CafeConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//util methods that we will be reusing a lot
public class CafeUtils {
    private CafeUtils(){

    }

    //static so can use class to call the method
    public static ResponseEntity<String> getResponseEntity(String responseMsg, HttpStatus httpStatus){
        // "\"}": This part of the string represents the closing curly brace of the JSON object.
        return new ResponseEntity<String>("{\"message\":\""+responseMsg+"\"}",httpStatus);
    }
}
