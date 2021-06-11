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
        	gravityKeys.put("center", "CENTER");
        	gravityKeys.put("leftTop", "LEFT | GRAVITY.TOP");
        	gravityKeys.put("leftBottom", "LEFT | GRAVITY.BOTTOM");
        	gravityKeys.put("rightTop", "RIGHT | GRAVITY.TOP");
        	gravityKeys.put("rightBottom", "RIGHT | GRAVITY.BOTTOM");
        	gravityKeys.put("horizontal", "HORIZONTAL");
        	gravityKeys.put("vertical", "VERTICAL");
        	gravityKeys.put("match_parent", "300");
        	gravityKeys.put(".png", "");
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
			if(ret.length() > 1)
				return gravityKeys.get(ret);
			else
				return value;
		}
    }

    private static final HashMap<String, KeywordAdapter> keywords;
    static {
        keywords = new HashMap<>();
        //VIEW
        keywords.put("VIEW_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("VIEW_alignment" , new KeywordAdapter(false, true, "setGravity(Gravity.", 1));
        keywords.put("VIEW_width" , new KeywordAdapter(false, true, "getLayoutParams().width = ", 0));
        keywords.put("VIEW_height" , new KeywordAdapter(false, true, "getLayoutParams().height = ", 0));
        keywords.put("VIEW_minWidth" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("VIEW_minHeight" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("VIEW_orientation" , new KeywordAdapter(false, true, "setOrientation(LinearLayout.", 1));
        keywords.put("VIEW_padding" , new KeywordAdapter(false, true, "setPadding(", 1));
        
        //SCROLLVIEW
        keywords.put("SCROLLVIEW_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("SCROLLVIEW_alignment" , new KeywordAdapter(false, true, "setGravity(Gravity.", 1));
        keywords.put("SCROLLVIEW_width" , new KeywordAdapter(false, true, "getLayoutParams().width = ", 0));
        keywords.put("SCROLLVIEW_height" , new KeywordAdapter(false, true, "getLayoutParams().height = ", 0));
        keywords.put("SCROLLVIEW_minWidth" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("SCROLLVIEW_minHeight" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("SCROLLVIEW_padding" , new KeywordAdapter(false, true, "setPadding(", 1));
        
        //TEXT
        keywords.put("TEXT_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("TEXT_text" , new KeywordAdapter("setText(", 1));
        keywords.put("TEXT_textsize" , new KeywordAdapter(false, "setTextSize(", 1));
        keywords.put("TEXT_text-color" , new KeywordAdapter("setTextColor(Color.parseColor(", 2));
        keywords.put("TEXT_alignment" , new KeywordAdapter(false, true, "setGravity(Gravity.", 1));
        keywords.put("TEXT_width" , new KeywordAdapter(false, true, "getLayoutParams().width = ", 0));
        keywords.put("TEXT_height" , new KeywordAdapter(false, true, "getLayoutParams().height = ", 0));
        keywords.put("TEXT_minWidth" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("TEXT_minHeight" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("TEXT_padding" , new KeywordAdapter(false, true, "setPadding(", 1));
        
        //BUTTON
        keywords.put("BUTTON_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("BUTTON_text" , new KeywordAdapter("setText(", 1));
        keywords.put("BUTTON_textsize" , new KeywordAdapter(false, "setTextSize(", 1));
        keywords.put("BUTTON_text-color" , new KeywordAdapter("setTextColor(Color.parseColor(", 2));
        keywords.put("BUTTON_alignment" , new KeywordAdapter(false, true, "setGravity(Gravity.", 1));
        keywords.put("BUTTON_width" , new KeywordAdapter(false, true, "getLayoutParams().width = ", 0));
        keywords.put("BUTTON_height" , new KeywordAdapter(false, true, "getLayoutParams().height = ", 0));
        keywords.put("BUTTON_minWidth" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("BUTTON_minHeight" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("BUTTON_padding" , new KeywordAdapter(false, true, "setPadding(", 1));
        
        //IMAGE
        keywords.put("IMAGE_background-color" , new KeywordAdapter("setBackgroundColor(Color.parseColor(", 2));
        keywords.put("IMAGE_src" , new KeywordAdapter(false, true, "setImageResource(R.drawable.", 1));
        keywords.put("IMAGE_text-color" , new KeywordAdapter("setTextColor(Color.parseColor(", 2));
        keywords.put("IMAGE_alignment" , new KeywordAdapter(false, true, "setGravity(Gravity.", 1));
        keywords.put("IMAGE_width" , new KeywordAdapter(false, true, "getLayoutParams().width = ", 0));
        keywords.put("IMAGE_height" , new KeywordAdapter(false, true, "getLayoutParams().height = ", 0));
        keywords.put("IMAGE_minWidth" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("IMAGE_minHeight" , new KeywordAdapter(false, "setMinimumWidth(", 1));
        keywords.put("IMAGE_padding" , new KeywordAdapter(false, true, "setPadding(", 1));
    }
   
    public String ToString(Node node){
        String str = "";
        for(Map.Entry<String, String> pair : node.getAttribute().getKeywords().entrySet()){
        	if(keywords.get(node.getToken().Type() + "_" + pair.getKey()) != null) {
                str += node.getId() + "." + keywords.get(node.getToken().Type() + "_" + pair.getKey()).GetString(pair.getValue().trim());
        	}
        }
        return str;
    }
}
