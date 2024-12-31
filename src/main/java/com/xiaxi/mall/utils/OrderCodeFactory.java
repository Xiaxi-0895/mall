package com.xiaxi.mall.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderCodeFactory {
    public static String getDateTime(){
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public static int getRandom(Long n){
        Random r = new Random();
        return (int)(r.nextDouble()*(90000))+10000;
    }

    public static String getOrderCode(Long userId){
        return getDateTime()+getRandom(userId);
    }
}
