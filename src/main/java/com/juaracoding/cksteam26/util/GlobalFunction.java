package com.juaracoding.cksteam26.util;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 30/07/25 20.57
@Last Modified 30/07/25 20.57
Version 1.0
*/

import com.juaracoding.cksteam26.config.OtherConfig;

import java.util.regex.Pattern;

public class GlobalFunction {

    public static void print(Object object) {
        if (OtherConfig.getEnablePrintConsole().equals("y")) {
            System.out.println(object);
        }
    }

    public static Boolean matchingPattern(String value, String regex
    ) {
        Boolean isValid = Pattern.compile(regex).matcher(value).find();
        if (!isValid) {
            return false;
        }
        return true;
    }
}
