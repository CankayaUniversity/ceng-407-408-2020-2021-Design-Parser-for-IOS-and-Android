package com;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class IosMapAttribute {
	private static final Map<String, String> keywords;
	private static final Map<String, String> frame;

	static  {
		keywords = new HashMap<>();
		keywords.put("background-color", ".background(");
		keywords.put("text-color", ".foregroundColor(");

		/*
		 * frame layout
		 * frame(width: val, height: val, aligment: val)
		 * so width, height and alignment should be accumulated.
		 */
		frame = new HashMap<>();
		frame.put("width", "width");
		frame.put("height", "height");
		frame.put("alignment", "alignment");
	}

	public String toString(Node node, String parentID, int indexOfChildhood){
		String str = "";
        
		Map<String, String> nodeAttr = node.getAttribute().getKeywords();

		//convert map to array
		//this now work for color it will be converted.
		for(Object attr : keywords.keySet().toArray()) {
			if(nodeAttr.containsKey(attr)) {
				
				Color color = HexToColor(nodeAttr.get(attr));
				double blue = color.getBlue();
				double red = color.getRed();
				double green = color.getGreen();
				
				str += "\n"+ keywords.get(attr) + "Color(red:"+red +", green:"+green+", blue: "+blue + "))\n";
				//str += "\n"+ keywords.get(attr) +  nodeAttr.get(attr) + ")\n";
				
				System.out.println("pair 2 : " + keywords.get(attr) +  nodeAttr.get(attr) + ")") ;
			}
		}
		
		
		String frameAccumulator = "";
		int i=0;
		for(Object attr : frame.keySet().toArray()) {
			if(nodeAttr.containsKey(attr)) {
				//this for frame(width: 100, height: 200) in first index ',' will not add to head.
				if(i != 0)
					frameAccumulator += ", ";
				
				frameAccumulator += frame.get(attr)+": ";
				if(nodeAttr.get(attr).contains("px"))
					frameAccumulator += nodeAttr.get(attr).replace("px", "");
				else
					frameAccumulator += nodeAttr.get(attr);
					
				System.out.println("pair 2 : " + keywords.get(attr) +  nodeAttr.get(attr) + ")") ;
				i++;
			}
		}
		//if there is setted frame attributes.
		if(!frameAccumulator.equals(""))
			str += "\n.frame("+ frameAccumulator + ")\n";

			
        return str;		
	}
	
	//this convert if entered color hex type it will be converted RGB type. 
	public Color HexToColor(String hex) 
	{
	    hex = hex.replace("#", "");
	    System.out.println(hex.length() + "");
	    switch (hex.length()) {
	        case 6:
	            return new Color(
	            Integer.valueOf(hex.substring(0, 2), 16),
	            Integer.valueOf(hex.substring(2, 4), 16),
	            Integer.valueOf(hex.substring(4, 6), 16));
	        case 7:
	            return new Color(
	            Integer.valueOf(hex.substring(1, 3), 16),
	            Integer.valueOf(hex.substring(3, 5), 16),
	            Integer.valueOf(hex.substring(5, 7), 16));
	    }
	    return null;
	}
}
