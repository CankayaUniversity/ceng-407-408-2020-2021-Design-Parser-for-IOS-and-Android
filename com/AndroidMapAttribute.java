package com;

import java.util.HashMap;
import java.util.Map;

public class AndroidMapAttribute{

    private static class BracketCounter{
        private String str;
        private int bracketCount;
        private boolean quotes;
        public BracketCounter(String str, int bracketCount){
        	quotes = true;
            this.str = str;
            this.bracketCount = bracketCount;
        }
        public BracketCounter(boolean quotes, String str, int bracketCount){
        	this.quotes = quotes;
            this.str = str;
            this.bracketCount = bracketCount;
        }

        public String RepeatBrackets(){
            String brackets = "";
            for(int i = 0; i < bracketCount; i++)
                brackets += ")";
            return brackets;
        }

        public String GetString(String value){
        	String string = "";
        	string = str;
        	if(quotes)
        		string += "\"" + value + "\"";
        	else
        		string += value;
        	string += RepeatBrackets() + ";\n";
        	return string;
        }
    }

    private static final HashMap<String, BracketCounter> keywords;
    static {
        keywords = new HashMap<>();
        //VIEW
        keywords.put("VIEW_background-color" , new BracketCounter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("VIEW_gravity" , new BracketCounter("setGravity(GRAVITY.", 1));//Add multiple attribute feature.
        keywords.put("VIEW_width" , new BracketCounter(false, "getLayoutParams().width = ", 1));
        keywords.put("VIEW_height" , new BracketCounter(false, "getLayoutParams().height = ", 1));
        keywords.put("VIEW_orientation" , new BracketCounter("setOrientation(LinearLayout.", 1));//Remove quotes
        
        //TEXT
        keywords.put("TEXT_background-color" , new BracketCounter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("TEXT_text" , new BracketCounter("setText(", 1));
        keywords.put("TEXT_textsize" , new BracketCounter(false, "setTextSize(", 1));//Remove quotes
        keywords.put("TEXT_text-color" , new BracketCounter("setTextColor(Color.parseColor(", 2));
        keywords.put("TEXT_gravity" , new BracketCounter("setGravity(GRAVITY.", 1));
        keywords.put("TEXT_width" , new BracketCounter(false, "getLayoutParams().width = ", 2));
        keywords.put("TEXT_height" , new BracketCounter(false, "getLayoutParams().height = ", 2));
        
        //BUTTON
        keywords.put("BUTTON_background-color" , new BracketCounter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("BUTTON_text" , new BracketCounter("setText(", 1));
        keywords.put("BUTTON_textsize" , new BracketCounter(false, "setTextSize(", 1));
        keywords.put("BUTTON_text-color" , new BracketCounter("setTextColor(Color.parseColor(", 2));
        keywords.put("BUTTON_gravity" , new BracketCounter("setGravity(GRAVITY.", 1));
        keywords.put("BUTTON_width" , new BracketCounter(false, "getLayoutParams().width = ", 2));
        keywords.put("BUTTON_height" , new BracketCounter(false, "getLayoutParams().height = ", 2));
    }
   
    public String ToString(Node node){
        String str = "";
        for(Map.Entry<String, String> pair : node.getAttribute().getKeywords().entrySet()){
        	if(keywords.get(node.getToken().Type() + "_" + pair.getKey()) != null) {
                str += node.getId() + "." + keywords.get(node.getToken().Type() + "_" + pair.getKey()).GetString(pair.getValue());
        	}
        }

        return str;
    }
}
