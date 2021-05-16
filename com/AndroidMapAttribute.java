package com;

import java.util.HashMap;
import java.util.Map;

public class AndroidMapAttribute{

    private static class KeywordAdapter{
        private String str;
        private int bracketCount;
        private boolean quotes;
        public KeywordAdapter(String str, int bracketCount){
        	quotes = true;
            this.str = str;
            this.bracketCount = bracketCount;
        }
        public KeywordAdapter(boolean quotes, String str, int bracketCount){
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

    private static final HashMap<String, KeywordAdapter> keywords;
    static {
        keywords = new HashMap<>();
        //VIEW
        keywords.put("VIEW_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("VIEW_gravity" , new KeywordAdapter("setGravity(GRAVITY.", 1));//Add multiple attribute feature.
        keywords.put("VIEW_width" , new KeywordAdapter(false, "getLayoutParams().width = ", 0));
        keywords.put("VIEW_height" , new KeywordAdapter(false, "getLayoutParams().height = ", 0));
        keywords.put("VIEW_orientation" , new KeywordAdapter("setOrientation(LinearLayout.", 1));
        
        //TEXT
        keywords.put("TEXT_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("TEXT_text" , new KeywordAdapter("setText(", 1));
        keywords.put("TEXT_textsize" , new KeywordAdapter(false, "setTextSize(", 1));
        keywords.put("TEXT_text-color" , new KeywordAdapter("setTextColor(Color.parseColor(", 2));
        keywords.put("TEXT_gravity" , new KeywordAdapter("setGravity(GRAVITY.", 1));
        keywords.put("TEXT_width" , new KeywordAdapter(false, "getLayoutParams().width = ", 0));
        keywords.put("TEXT_height" , new KeywordAdapter(false, "getLayoutParams().height = ", 0));
        
        //BUTTON
        keywords.put("BUTTON_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("BUTTON_text" , new KeywordAdapter("setText(", 1));
        keywords.put("BUTTON_textsize" , new KeywordAdapter(false, "setTextSize(", 1));
        keywords.put("BUTTON_text-color" , new KeywordAdapter("setTextColor(Color.parseColor(", 2));
        keywords.put("BUTTON_gravity" , new KeywordAdapter("setGravity(GRAVITY.", 1));
        keywords.put("BUTTON_width" , new KeywordAdapter(false, "getLayoutParams().width = ", 0));
        keywords.put("BUTTON_height" , new KeywordAdapter(false, "getLayoutParams().height = ", 0));
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
