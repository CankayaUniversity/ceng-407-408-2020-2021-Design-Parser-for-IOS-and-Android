package com;

import java.util.HashMap;
import java.util.Map;

public class AndroidMapAttribute{

    private static class BracketCounter{
        private String str;
        private int bracketCount;
        public BracketCounter(String str, int bracketCount){
            this.str = str;
            this.bracketCount = bracketCount;
        }

        public String RepeatBrackets(){
            String brackets = "";
            for(int i = 0; i < bracketCount; i++)
                brackets += ")";
            return brackets;
        }
    }

    private static final HashMap<String, BracketCounter> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put("background-color" , new BracketCounter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("text" , new BracketCounter("setText(", 1));
        keywords.put("text-color" , new BracketCounter("setTextColor(Color.parseColor(", 2));
        keywords.put("gravity" , new BracketCounter("setGravity(GRAVITY.", 1));
        keywords.put("width" , new BracketCounter("setWidth(", 1));
        keywords.put("height" , new BracketCounter("setWidth(", 1));
        keywords.put("orientation" , new BracketCounter("setOrientation(", 1));
    }
   
    public String ToString(Node node){
        String str = "";
        for(Map.Entry<String, String> pair : node.getAttribute().getKeywords().entrySet()){
            str += node.getId() + "." + keywords.get(pair.getKey()).str + "\"" + pair.getValue() + "\"";
            str += keywords.get(pair.getKey()).RepeatBrackets();
            str += "\n";
        }

        return str;
    }
}
