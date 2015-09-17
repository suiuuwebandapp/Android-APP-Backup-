package com.minglang.suiuu.utils.comparator;

import com.minglang.suiuu.entity.HaveAssistCity;

import java.util.Comparator;

public class HaveCityNameComparator  implements Comparator<HaveAssistCity> {

    @Override
    public int compare(HaveAssistCity lhs, HaveAssistCity rhs) {
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