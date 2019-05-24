package com.us.masterpass.referenceApp.utils;

import com.us.masterpass.merchantapp.BuildConfig;
import java.util.Random;
import java.util.UUID;

/**
 * Created by e058798 on 1/11/18.
 */

public class RandomGenerator {
    public static String randomPhoneNumber(){
        Random random = new Random();
        int max = 999999999;
        int min = 100000000;
        Integer integer = random.nextInt(max - min +1) + min;
        return "9"+integer.toString();
    }

    public static String randomPhoneNumberInvalid(){
        Random random = new Random();
        int max = 999999999;
        int min = 100000000;
        Integer integer = random.nextInt(max - min +1) + min;
        return "1472"+integer.toString();
    }
    public static String randomEmailAddress(){
        String uuid = UUID.randomUUID().toString().substring(0, 8);
         return uuid + "@example.com";
    }
}
