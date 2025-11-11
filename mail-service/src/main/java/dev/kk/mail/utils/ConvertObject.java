package dev.kk.mail.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ConvertObject {

    public static Map<String, Object> ObjToMap(Object clazz) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        for (Field f : clazz.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            map.put(f.getName(), f.get(clazz));
        }
        return map;
    }
}
