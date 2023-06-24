package com.akgames.biriba3;

import com.badlogic.gdx.utils.StringBuilder;

import java.util.List;

public class Utils {

    public static String listToString(List<?> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i).toString());
            if (i != list.size()-1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }
}
