package com;

import java.util.HashMap;

import static com.TokenType.*;


public class AndroidMapToken {

    private static final HashMap<TokenType, String> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put(VIEW , "LinearLayout ");
        keywords.put(TEXT , "TextView ");
    }

    public String ToString(Node node, String parentID, int indexOfChildhood){
        String str = "";
        str += keywords.get(node.getToken().getTokenType()) + parentID + "_" + indexOfChildhood + " = new " + keywords.get(node.getToken().getTokenType()) + "(this); \n";
        node.setId(parentID + "_" + indexOfChildhood);
        return str;
    }
}
