package com.krish.app.playo.utils;

import java.util.List;

public class Utilities {

    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }
}
