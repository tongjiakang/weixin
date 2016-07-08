package com.vanke.mhj.dao.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static final String BLANK = "";
    public static final String ZERO = "0";

    public static String[] getArrayInString(String str) {
        String[] strings = str.split(" ");
        List<String> output = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++) {
            if (!strings[i].equals(BLANK)) {
                output.add(strings[i]);
            }
        }
        String[] stringArray = new String[output.size()];
        return output.toArray(stringArray);
    }

    public static String nullToZero(String str) {
        if (str == null) {
            return ZERO;
        } else {
            return str;
        }
    }
}
