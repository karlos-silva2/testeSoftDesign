package com.example.teste.util;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static Integer geradorNumeroRandow(){
        return ThreadLocalRandom.current().nextInt(100, 9999 + 1);
    }
}
