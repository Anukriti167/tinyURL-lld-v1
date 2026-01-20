package com.anukriti.tinyURL.model;

import org.springframework.stereotype.Component;

@Component
public class Base62IdGenerator implements IdGenerator {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String generate(long number) {
        StringBuilder str = new StringBuilder();

        if(number == 0) return "0";

        while(number > 0) {
            long remainder = number % 62;
            number = number / 62;
            str.append(BASE62.charAt((int)remainder));
        }
        String res = str.reverse().toString();
        return res;
    }
}
