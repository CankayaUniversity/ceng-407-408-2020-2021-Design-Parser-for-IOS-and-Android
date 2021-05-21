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
		keywords.put(BUTTON, "Button");
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
		
		if(node.getToken().Type() == VIEW) {
			Map<String, String> nodeAttr = node.getAttribute().getKeywords();
			
			//orientation can be vertical, horizontal default value is vertical
			String orientation = "";
 			if(nodeAttr.get("orientation") != null)
				orientation = nodeAttr.get("orientation");
			 
 			if(node.getChildren().size() > 1 && orientation.equals("")) {
 				str += "\n\t"+keywords.get(VIEW_H);
 			}else {
 				orientation = "vertical";
 			}
 				
 			//set orientation token. In ios VStack, HStack and ZStack orientation is different.
 			if(orientation.equals("vertical"))
				str += "\n\t"+keywords.get(VIEW)+"(alignment: .leading)";
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
		else if(node.getToken().Type() == TEXT) {
			str += "\n\t"+keywords.get(TEXT);
			str +=  "(\""+node.getAttribute().getKeywords().get("text")+"\") ";
		}
		else if(node.getToken().Type() == IMAGE) {
			str += "\n\t"+keywords.get(IMAGE);
			str +=  "(\""+node.getAttribute().getKeywords().get("src").split(".png")[0]+"\") ";
		}
		//Ex button layout
		//Button("text"){ ... }
		//Button(text, action: { ... })
		else if(node.getToken().Type() == BUTTON) {
			str += "\n\t"+keywords.get(BUTTON);
			
			//add text attribute to button
			str +=  "(\""+node.getAttribute().getKeywords().get("text")+"\")";
		
			str += "{ //this area created for your purpose\n";		
		}
		else
			//other tokens should start '('
			str += keywords.get(node.getToken().Type())+"(";
	       //this for ios text beacuse it should be like that Text("text inside") and its attributes.
       
        	
		
		node.setId(parentID + "_" + indexOfChildhood) ;
				
        return str;
	}
}
