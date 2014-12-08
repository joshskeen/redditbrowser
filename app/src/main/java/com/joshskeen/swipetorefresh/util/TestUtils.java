package com.joshskeen.swipetorefresh.util;

import android.content.Context;

import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtils {

    public static String getRandomStringOfLength(int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static Picasso picassoInstance(Context context){
        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(true);
        return picasso;
    }

    public static boolean isImage(String url) {
        String regex = "http(s?)://([\\w-]+\\.)+[\\w-]+(/[\\w./]*)+\\.(?:[gG][iI][fF]|[jJ][pP][gG]|[jJ][pP][eE][gG]|[pP][nN][gG]|[bB][mM][pP])";
        Matcher m = Pattern.compile(regex).matcher(url);
        return m.find();
    }

}
