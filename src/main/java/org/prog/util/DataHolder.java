package org.prog.util;

import java.util.HashMap;

public class DataHolder {
    private static final DataHolder instance = new DataHolder();
        private final HashMap<String, Object> data;
    private DataHolder() {
        data = new HashMap<>();
    }
    public static DataHolder getInstance() {
        return instance;
    }
    public void put(String key, Object value) {
        data.put(key, value);
    }
    public Object get(String key) {
        return data.get(key);
    }
}
