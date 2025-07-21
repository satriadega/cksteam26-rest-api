package com.juaracoding.cksteam26.util;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 21/07/25 20.13
@Last Modified 21/07/25 20.13
Version 1.0
*/

import com.juaracoding.cksteam26.config.OtherConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingFile {
    private static StringBuilder sBuild = new StringBuilder();

    private static Logger logger = LogManager.getLogger(LoggingFile.class);

    public static void logException(String strClass, String strMethod, Exception e) {
        if (OtherConfig.getEnableLogFile().equals("y")) {
            sBuild.setLength(0);
            logger.error(sBuild.append(System.getProperty("line.separator")).
                    append("ERROR IN CLASS ==> ").append(strClass).append("\n").
                    append("METHOD ==> ").append(strMethod).append("\n").
                    append("ERROR IS ==> ").append(e.getMessage() + "===" + e.getClass().getName()).append("\n"));
        }
    }
}
