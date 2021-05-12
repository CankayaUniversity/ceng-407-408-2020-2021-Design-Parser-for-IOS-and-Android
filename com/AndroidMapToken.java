package com;

import java.util.HashMap;

import static com.TokenType.*;


public class AndroidMapToken {

    private static final HashMap<TokenType, String> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put(VIEW , "LinearLayout ");
        keywords.put(VIEW_H , "LinearLayout ");
        keywords.put(TEXT , "TextView ");
    }
   
    public String ToString(Node node, String parentID, int indexOfChildhood){
        String str = "";
        str += keywords.get(node.getToken().getTokenType()) + parentID + "_" + indexOfChildhood + " = new " + keywords.get(node.getToken().getTokenType()) + "(this); \n";
        node.setId(parentID + "_" + indexOfChildhood);
        
        str += parentID + ".addView(" + node.getId() + "); \n";
        
        AndroidMapAttribute ama = new AndroidMapAttribute();
        str += ama.ToString(node);
        return str;
    }
}
