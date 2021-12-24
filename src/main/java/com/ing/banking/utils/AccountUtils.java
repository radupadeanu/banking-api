package com.ing.banking.utils;

import java.util.Random;

public class AccountUtils {
    public static String generateIban(){
        {
            Random rand = new Random();
            String card = "RO";
            for (int i = 0; i < 14; i++)
            {
                int n = rand.nextInt(10);
                card += Integer.toString(n);
            }
            return card;
        }
    }
}
