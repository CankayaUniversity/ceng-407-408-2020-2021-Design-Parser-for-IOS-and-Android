package com;

import java.util.HashMap;
import static com.TokenType.*;

public class IosMapToken {
	private static final HashMap<TokenType, String> keywords;
	static  {
		keywords = new HashMap<>();
		keywords.put(VIEW, "VStack");
		keywords.put(VIEW_H, "HStack");
		keywords.put(TEXT, "Text");
		keywords.put(IMAGE, "Image");
	}

	public String toString(Node node, String parentID, int indexOfChildhood){
		String str = "";
		
		if(node.getToken().getTokenType() == VIEW)
			str += "\n\t"+keywords.get(node.getToken().getTokenType())+"{\n";
		else
			str += keywords.get(node.getToken().getTokenType())+"(";
		
		node.setId(parentID + "_" + indexOfChildhood);
				
        return str;
	}
}
