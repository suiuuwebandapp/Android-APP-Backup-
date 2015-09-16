package com.minglang.suiuu.utils;

import com.minglang.suiuu.entity.CountryAssistData;

import java.util.Comparator;

/**
 * @author Mr.Z
 */
public class CountryNameComparator implements Comparator<CountryAssistData> {

    public int compare(CountryAssistData o1, CountryAssistData o2) {

        String str1 = o1.getFirstLetter();
        String str2 = o2.getFirstLetter();

        if (str1.equals("@") || str2.equals("#")) {
            return -1;
        } else if (str1.equals("#") || str2.equals("@")) {
            return 1;
        } else {
            return str1.compareTo(str2);
        }

    }

}