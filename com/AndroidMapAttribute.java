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
        //View
        keywords.put("View_background-color" , new BracketCounter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("View_gravity" , new BracketCounter("setGravity(GRAVITY.", 1));
        keywords.put("View_width" , new BracketCounter("getLayoutParams().height = ", 0));
        keywords.put("View_height" , new BracketCounter("getLayoutParams().width = ", 0));
        keywords.put("View_orientation" , new BracketCounter("setOrientation(LinearLayout.", 1));
        
        //Text
        keywords.put("Text_background-color" , new BracketCounter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("Text_gravity" , new BracketCounter("setGravity(GRAVITY.", 1));
        keywords.put("Text_width" , new BracketCounter("setWidth(", 1));
        keywords.put("Text_height" , new BracketCounter("setWidth(", 1));
        keywords.put("Text_text" , new BracketCounter("setText(", 1));
        keywords.put("Text_text-color" , new BracketCounter("setTextColor(Color.parseColor(", 2));
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
