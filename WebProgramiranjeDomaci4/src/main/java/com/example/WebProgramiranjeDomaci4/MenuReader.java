package com.example.WebProgramiranjeDomaci4;

import java.io.File;
import java.util.HashMap;

public class MenuReader {

    private HashMap<String, Integer> mondayCount;
    private HashMap<String, Integer> tuesdayCount;
    private HashMap<String, Integer> wednesdayCount;
    private HashMap<String, Integer> thursdayCount;
    private HashMap<String, Integer> fridayCount;

    private static MenuReader instance = null;

    public static MenuReader getInstance() {
        if (instance == null) {
            synchronized (MenuReader.class) {
                if (instance == null)
                    instance = new MenuReader();
            }
        }
        return instance;
    }

    private MenuReader() {
        mondayCount = new HashMap<>();
        tuesdayCount = new HashMap<>();
        wednesdayCount = new HashMap<>();
        thursdayCount = new HashMap<>();
        fridayCount = new HashMap<>();
    }

    public HashMap<String, Integer> getMondayCount() {
        return mondayCount;
    }

    public HashMap<String, Integer> getTuesdayCount() {
        return tuesdayCount;
    }

    public HashMap<String, Integer> getWednesdayCount() {
        return wednesdayCount;
    }

    public HashMap<String, Integer> getThursdayCount() {
        return thursdayCount;
    }

    public HashMap<String, Integer> getFridayCount() {
        return fridayCount;
    }
}
