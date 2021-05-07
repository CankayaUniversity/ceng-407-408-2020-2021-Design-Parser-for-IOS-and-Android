package com;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IosMapAttribute {
	private static final Map<String, String> keywords;
	private static final Map<String, String> viewKeywords;
	private static final Map<String, String> frame;
	private static final Map<String, String> frameMinMax;


	static  {
		keywords = new HashMap<>();
		keywords.put("background-color", ".background(");
		keywords.put("text-color", ".foregroundColor(");

		viewKeywords = new HashMap<>();
		viewKeywords.put("background-color", ".background(");
		viewKeywords.put("alignment", "alignment");
		//viewKeywords also include frame, frameMinMax
		
		/*
		 * frame layout
		 * frame(width: val, height: val, aligment: val)
		 * so width, height and alignment should be accumulated.
		 */
		frame = new HashMap<>();
		frame.put("width", "width");
		frame.put("height", "height");
		frame.put("alignment", "alignment");
		
		frameMinMax = new HashMap<>();
		frameMinMax.put("minHeight", "minHeight");
		frameMinMax.put("minWidth", "minWidth");
		frameMinMax.put("maxHeight", "maxHeight");
		frameMinMax.put("maxWidth", "maxWidth");
	}

	public String toString(Node node, String parentID, int indexOfChildhood){
		String str = "";
        
		Map<String, String> nodeAttr = node.getAttribute().getKeywords();

		switch (node.getToken().getTokenType()) {
		case VIEW: 
			str += this.viewAttribute(nodeAttr);
			break;
		case TEXT:
			 
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

			break;
		default:
			break;
		}
		
        return str;		
	}
	
	
	public String setBackgroundColor(String attr, String color) {
		Color color1 = HexToColor(color);
		double blue = color1.getBlue();
		double red = color1.getRed();
		double green = color1.getGreen();

		return "\n"+ attr + "Color(red:"+red +", green:"+green+", blue: "+blue + "))\n";
	}
	
	public String viewAttribute(Map<String, String> nodeAttr) {
		String str = "";
 
		//convert map to array
		//this now work for color it will be converted.
		for(Object attr : viewKeywords.keySet().toArray()) {
			if(nodeAttr.containsKey(attr)) {
				
				
				System.out.println("pair 2 : " + keywords.get(attr) +  nodeAttr.get(attr) + ")") ;
			}
		}
		
		if(nodeAttr.containsKey("background-color")) {
			str += setBackgroundColor(keywords.get("background-color"), nodeAttr.get("background-color"));
		}
		
		String frameAccumulatorStr = this.frameAccumulator(nodeAttr, frame);
		if(!frameAccumulatorStr.equals(""))
			str += "\n.frame("+ frameAccumulatorStr + ")\n";
		
		String frameMinMaxAccumulatorStr = this.frameAccumulator(nodeAttr, frameMinMax);
		if(!frameMinMaxAccumulatorStr.equals(""))
			str += "\n.frame("+ frameMinMaxAccumulatorStr + ")\n";
		
		return str;
	}
	
	//Accumulate all frame attributes width, height, minHeight, maxWidth etc. with it spcefici frame map variable
	public String frameAccumulator(Map<String, String> nodeAttr, Map<String, String> frame){
		String accumulator = "";
		int i=0;
		
		for(Object attr :frame.keySet().toArray()) {
			if(nodeAttr.containsKey(attr)) {
				//this for frame(width: 100, height: 200) in first index ',' will not add to head.
				if(i != 0)
					accumulator += ", ";
				
				accumulator += frame.get(attr)+": ";
				if(nodeAttr.get(attr).contains("px"))
					accumulator += nodeAttr.get(attr).replace("px", "");
				else
					//alignment
					accumulator += "."+ nodeAttr.get(attr);
					
				System.out.println("pair 2 : " + keywords.get(attr) +  nodeAttr.get(attr) + ")") ;
				i++;
			}
		}
		System.out.println("accumulator : "+ accumulator);
		return accumulator;
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
