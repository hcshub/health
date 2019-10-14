package com.golday.jobs;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class Myjob {
    public void jobOne(){
        System.out.println("success...");
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-11);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(year);
        Date date = new Date();
        long time = date.getTime();
        System.out.println(time);

    }
}
