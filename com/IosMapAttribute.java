package com;

import java.util.HashMap;
import java.util.Map;

public class IosMapAttribute {
	private static final Map<String, String> keywords;
	static  {
		keywords = new HashMap<>();
		keywords.put("background-color", ".background(");
		keywords.put("color", ".foregroundColor(.");
	}

	public String toString(Node node, String parentID, int indexOfChildhood){
		String str = "";
        
		Map<String, String> nodeAttr = node.getAttribute().getKeywords();
		
		//convert map to array
		for(Object attr : keywords.keySet().toArray()) {
			if(nodeAttr.containsKey(attr)) {
				str += keywords.get(attr) +  nodeAttr.get(attr) + ")\n";
				System.out.println(keywords.get(attr) +  nodeAttr.get(attr) + ")\n") ;
			}
		}
			
        return str;		
	}
}
