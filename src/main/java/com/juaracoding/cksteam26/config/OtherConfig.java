package com.juaracoding.cksteam26.config;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 18/07/25 11.21
@Last Modified 18/07/25 11.21
Version 1.0
*/

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:other.properties")
public class OtherConfig {

    private static String enablePrint;

    public static String getEnablePrint() {
        return enablePrint;
    }

    @Value("${enable.print}")
    private void setEnablePrint(String enablePrint) {
        OtherConfig.enablePrint = enablePrint;
    }
}
