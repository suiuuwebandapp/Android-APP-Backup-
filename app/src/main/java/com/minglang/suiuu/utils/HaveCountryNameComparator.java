package com.minglang.suiuu.utils;


import com.minglang.suiuu.entity.HaveAssistData;

import java.util.Comparator;

public class HaveCountryNameComparator implements Comparator<HaveAssistData> {

    @Override
    public int compare(HaveAssistData lhs, HaveAssistData rhs) {
        String str1 = lhs.getFirstLetter();
        String str2 = rhs.getFirstLetter();

        if (str1.equals("@") || str2.equals("#")) {
            return -1;
        } else if (str1.equals("#") || str2.equals("@")) {
            return 1;
        } else {
            return str1.compareTo(str2);
        }
    }
}
