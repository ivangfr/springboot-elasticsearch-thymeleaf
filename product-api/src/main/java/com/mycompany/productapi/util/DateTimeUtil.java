package com.mycompany.productapi.util;

import java.time.Instant;

public class DateTimeUtil {

    public static String createCurrentDateAsString(){
        return Instant.ofEpochSecond(Instant.now().getEpochSecond()).toString();
    }

}
