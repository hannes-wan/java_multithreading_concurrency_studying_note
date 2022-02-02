package com.hannes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sleep_02{

    public static void main(String[] args) {
        Date date = new Date(System.currentTimeMillis());

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(date));
                date = new Date(System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
