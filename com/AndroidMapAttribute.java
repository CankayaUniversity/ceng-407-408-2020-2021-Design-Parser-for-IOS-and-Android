package com;

import java.util.HashMap;
import java.util.Map;

public class AndroidMapAttribute{

    private static class KeywordAdapter{
        private static final HashMap<String, String> gravityKeys;
        static {
        	gravityKeys = new HashMap<>();
        	gravityKeys.put("left", "LEFT");
        	gravityKeys.put("right", "RIGHT");
        	gravityKeys.put("top", "TOP");
        	gravityKeys.put("bottom", "BOTTOM");
        	gravityKeys.put("leftTop", "LEFT | GRAVITY.TOP");
        	gravityKeys.put("leftBottom", "LEFT | GRAVITY.BOTTOM");
        	gravityKeys.put("rightTop", "RIGHT | GRAVITY.TOP");
        	gravityKeys.put("rightBottom", "RIGHT | GRAVITY.BOTTOM");
        	gravityKeys.put("horizontal", "HORIZONTAL");
        	gravityKeys.put("vertical", "VERTICAL");
        }
        private String str;
        private int bracketCount;
        private boolean quotes;
        private boolean gravity;
        public KeywordAdapter(String str, int bracketCount){
        	quotes = true;
        	gravity = false;
            this.str = str;
            this.bracketCount = bracketCount;
        }
        public KeywordAdapter(boolean quotes, String str, int bracketCount){
        	this.quotes = quotes;
        	gravity = false;
            this.str = str;
            this.bracketCount = bracketCount;
        }
        public KeywordAdapter(boolean quotes, boolean gravity, String str, int bracketCount){
        	this.quotes = quotes;
        	this.gravity = gravity;
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
        	if(gravity) 
        		value = GetGravity(value);
        	if(quotes)
        		string += "\"" + value + "\"";
        	else
        		string += value;
        	string += RepeatBrackets() + ";\n";
        	return string;
        }
		private String GetGravity(String value) {
			char [] charString = value.toCharArray();
			String holder = "", ret = "";
			for(int i = 0; i < charString.length; i++) {
				holder += charString[i];
				if(gravityKeys.containsKey(holder))
					ret = holder;
			}
			return gravityKeys.get(ret);
		}
    }

    private static final HashMap<String, KeywordAdapter> keywords;
    static {
        keywords = new HashMap<>();
        //VIEW
        keywords.put("VIEW_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("VIEW_gravity" , new KeywordAdapter(false, true, "setGravity(GRAVITY.", 1));
        keywords.put("VIEW_width" , new KeywordAdapter(false, "getLayoutParams().width = ", 0));
        keywords.put("VIEW_height" , new KeywordAdapter(false, "getLayoutParams().height = ", 0));
        keywords.put("VIEW_orientation" , new KeywordAdapter(false, true, "setOrientation(LinearLayout.", 1));
        
        //TEXT
        keywords.put("TEXT_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("TEXT_text" , new KeywordAdapter("setText(", 1));
        keywords.put("TEXT_textsize" , new KeywordAdapter(false, "setTextSize(", 1));
        keywords.put("TEXT_text-color" , new KeywordAdapter("setTextColor(Color.parseColor(", 2));
        keywords.put("TEXT_gravity" , new KeywordAdapter(false, true, "setGravity(GRAVITY.", 1));
        keywords.put("TEXT_width" , new KeywordAdapter(false, "getLayoutParams().width = ", 0));
        keywords.put("TEXT_height" , new KeywordAdapter(false, "getLayoutParams().height = ", 0));
        
        //BUTTON
        keywords.put("BUTTON_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("BUTTON_text" , new KeywordAdapter("setText(", 1));
        keywords.put("BUTTON_textsize" , new KeywordAdapter(false, "setTextSize(", 1));
        keywords.put("BUTTON_text-color" , new KeywordAdapter("setTextColor(Color.parseColor(", 2));
        keywords.put("BUTTON_gravity" , new KeywordAdapter(false, true, "setGravity(GRAVITY.", 1));
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
