package com;

import java.util.HashMap;
import java.util.Map;

public class Attribute {

    private String atrbt_source;
    private Map<String, String> keywords;

    public Attribute() {
        this.keywords = new HashMap<>();
    }

    public void addAttribute(String key, String value) {
        this.keywords.put(key, value);
    }

    public void setAtrribute(String source) {
        this.atrbt_source = source;

        String s = this.atrbt_source.split("\"")[1];

        for (String m : s.split(";")) {
            System.out.println(m);
            String[] x = m.split(":");
            keywords.put(x[0], x[1]);
        }
    }

    public Map<String, String> getKeywords() {
        return this.keywords;
    }

    public String geta() {
        return this.atrbt_source;
    }

}
