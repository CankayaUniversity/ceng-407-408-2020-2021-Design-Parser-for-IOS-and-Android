package com;

import java.util.HashMap;
import java.util.Map;

import static com.TokenType.*;

public class IosMapToken {
	private static final HashMap<TokenType, String> keywords;
	private static final HashMap<String, String> alignment;
	static  {
		//this for tokens
		keywords = new HashMap<>();
		keywords.put(VIEW, "VStack");
		keywords.put(VIEW_H, "HStack");
		keywords.put(TEXT, "Text");
		keywords.put(IMAGE, "Image");
		
		//this for constructor properties
		alignment = new HashMap<>();
		alignment.put("gravity", "alignment: ");
	}

	public String toString(Node node, String parentID, int indexOfChildhood){
		/*
		 * Ex VStack Layout
		 * 
		 * VStack{ ... }
		 * VStack(constructor properties){ ... }
		 */
		String str = "";
		
		if(node.getToken().getTokenType() == VIEW) {
			Map<String, String> nodeAttr = node.getAttribute().getKeywords();

			//orientation can be vertical, horizontal default value is vertical
			String orientation = "vertical";
 			if(nodeAttr.get("orientation") != null)
				orientation = nodeAttr.get("orientation");
			 
 			//set orientation token. In ios VStack, HStack and ZStack orientation is different.
			if(orientation.equals("vertical"))
				str += "\n\t"+keywords.get(VIEW);
			else if(orientation.equals("horizontal"))
				str += "\n\t"+keywords.get(VIEW_H);

			//for look if there is constructor properties like gravity this is like accumulator.
			int i=0;
			String temp_str = "";
			for(Object attr : alignment.keySet().toArray()) {
				if(nodeAttr.containsKey(attr)) {
					//this for dont add , to first item.
					if(i != 0)
						temp_str += ", ";
					temp_str += alignment.get(attr) + "."+  nodeAttr.get(attr);
					//str += "\n"+ keywords.get(attr) +  nodeAttr.get(attr) + ")\n";
					System.out.println("pair 2 : " + keywords.get(attr) +  nodeAttr.get(attr) + ")") ;
					i++;
				}
			}
			//if there is constructor property add it.
			if(temp_str.length() > 0)
				str += "("+ temp_str + ")";
			
			//Stacks should start their body with '{'
			str += "{\n";	
		}
		//Ex text layout
		//Text("value").foregroundColor(...)...
		else if(node.getToken().getTokenType() == TEXT) {
			str += "\n\t"+keywords.get(TEXT);
			str +=  "(\""+node.getAttribute().getKeywords().get("text")+"\") ";
		}
		else
			//other tokens should start '('
			str += keywords.get(node.getToken().getTokenType())+"(";
	       //this for ios text beacuse it should be like that Text("text inside") and its attributes.
       
        	
		
		node.setId(parentID + "_" + indexOfChildhood) ;
				
        return str;
	}
}
