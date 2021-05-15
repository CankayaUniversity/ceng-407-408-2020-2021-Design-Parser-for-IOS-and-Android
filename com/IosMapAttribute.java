package com;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IosMapAttribute {
	private static final Map<String, String> viewKeywords;
	private static final Map<String, String> textKeywords;
	private static final Map<String, String> buttonKeywords;
	private static final Map<String, String> frame;
	private static final Map<String, String> frameMinMax;
	
	static  {
		viewKeywords = new HashMap<>();
		viewKeywords.put("background-color", ".background(");
		viewKeywords.put("alignment", "alignment");
		viewKeywords.put("width", "width");
		viewKeywords.put("height", "height");
		viewKeywords.put("alignment", "alignment");
		viewKeywords.put("orientation", "orientation");
		viewKeywords.put("minHeight", "minHeight");
		viewKeywords.put("minWidth", "minWidth");
		viewKeywords.put("maxHeight", "maxHeight");
		viewKeywords.put("maxWidth", "maxWidth");
		
		textKeywords = new HashMap<>();
		textKeywords.put("background-color", ".background(");
		textKeywords.put("text-color", ".foregroundColor(");
		textKeywords.put("alignment", "alignment");
		textKeywords.put("width", "width");
		textKeywords.put("height", "height");
		textKeywords.put("alignment", "alignment");
		textKeywords.put("minHeight", "minHeight");
		textKeywords.put("minWidth", "minWidth");
		textKeywords.put("maxHeight", "maxHeight");
		textKeywords.put("maxWidth", "maxWidth");
		textKeywords.put("text", "text");
		
		buttonKeywords = new HashMap<>();
		buttonKeywords.put("background-color", ".background(");
		buttonKeywords.put("text-color", ".foregroundColor(");
		buttonKeywords.put("text-size", "text-size");
		buttonKeywords.put("alignment", "alignment");
		buttonKeywords.put("width", "width");
		buttonKeywords.put("padding", "padding");
		buttonKeywords.put("height", "height");
		buttonKeywords.put("alignment", "alignment");
		buttonKeywords.put("minHeight", "minHeight");
		buttonKeywords.put("minWidth", "minWidth");
		buttonKeywords.put("maxHeight", "maxHeight");
		buttonKeywords.put("maxWidth", "maxWidth");
		buttonKeywords.put("text", "text");
		
		
		/*
		 * frame layout
		 * frame(width: val, height: val, aligment: val)
		 * so width, height and alignment should be accumulated.
		 */
		frame = new HashMap<>();
		frame.put("width", "width");
		frame.put("height", "height");
		frame.put("alignment", "alignment");
		//frameMinMax same as frame
		frameMinMax = new HashMap<>();
		frameMinMax.put("minHeight", "minHeight");
		frameMinMax.put("minWidth", "minWidth");
		frameMinMax.put("maxHeight", "maxHeight");
		frameMinMax.put("maxWidth", "maxWidth");
	}

	public String toString(Node node, String parentID, int indexOfChildhood){
		String str = "";
        
		Map<String, String> nodeAttr = node.getAttribute().getKeywords();

		switch (node.getToken().Type()) {
		case VIEW: 
			str += this.viewAttribute(nodeAttr);
			break;
		case TEXT:
			str += this.textAttribute(nodeAttr);
			break;
		case BUTTON:
			str += this.buttonAttribute(nodeAttr);
		default:
			break;
		}
		
        return str;		
	}
	
	public String viewAttribute(Map<String, String> nodeAttr) {
		String str = "";
		boolean widthHeightAlignFlag = true;
		boolean minMaxWidthHeightFlag = true;
		
		//convert map to array
		//this now work for color it will be converted.
		for(String attr :  nodeAttr.keySet()) {
			if(viewKeywords.containsKey(attr)) {
			
				//if element has width, height, alignment attributes it will be puted frame
				if(widthHeightAlignFlag && (attr.equals("width") || attr.equals("height") || attr.equals("alignment"))) {
					widthHeightAlignFlag = false;
					str += this.frameAccumulator(nodeAttr, frame);
				}
				//if element has minWidth, minHeight, maxWidth, maxHeight attributes it will be puted frame
				else if(minMaxWidthHeightFlag && (attr.equals("minWidth") || attr.equals("minHeight") || attr.equals("maxHeight") || attr.equals("maxWidth"))) {
					minMaxWidthHeightFlag = false;
					str += this.frameAccumulator(nodeAttr, frameMinMax);
				}
				
				if(attr.equals("background-color"))
					str += this.setElementColor(viewKeywords.get("background-color"), nodeAttr.get("background-color"));
				
				if(attr.equals("text-color"))
					str += this.setElementColor(viewKeywords.get("text-color"), nodeAttr.get("background-color"));
				
			}else{
				System.out.println("ERROR: " + attr + " is not attribute of VIEW");
			}
		}
		return str;
	}
		
	public String textAttribute(Map<String, String> nodeAttr) {
		String str = "";
		boolean widthHeightAlignFlag = true;
		boolean minMaxWidthHeightFlag = true;
		
		//convert map to array
		//this now work for color it will be converted.
		for(String attr :  nodeAttr.keySet()) {
			if(textKeywords.containsKey(attr)) {
			
				//if element has width, height, alignment attributes it will be puted frame
				if(widthHeightAlignFlag && (attr.equals("width") || attr.equals("height") || attr.equals("alignment"))) {
					widthHeightAlignFlag = false;
					str += this.frameAccumulator(nodeAttr, frame);
				}
				//if element has minWidth, minHeight, maxWidth, maxHeight attributes it will be puted frame
				else if(minMaxWidthHeightFlag && (attr.equals("minWidth") || attr.equals("minHeight") || attr.equals("maxHeight") || attr.equals("maxWidth"))) {
					minMaxWidthHeightFlag = false;
					str += this.frameAccumulator(nodeAttr, frameMinMax);
				}
				
				if(attr.equals("background-color"))
					str += this.setElementColor(textKeywords.get("background-color"), nodeAttr.get("background-color"));
				
				if(attr.equals("text-color"))
					str += this.setElementColor(textKeywords.get("text-color"), nodeAttr.get("background-color"));
				
			}else{
				System.out.println("ERROR: " + attr + " is not attribute of TEXT");
			}
		}
		
		return str;
	}
	
	public String buttonAttribute(Map<String, String> nodeAttr) {
		String str = "";
		boolean widthHeightAlignFlag = true;
		boolean minMaxWidthHeightFlag = true;
		
		//convert map to array
		//this now work for color it will be converted.
		for(String attr :  nodeAttr.keySet()) {
			if(buttonKeywords.containsKey(attr)) {
			
				//if element has width, height, alignment attributes it will be puted frame
				if(widthHeightAlignFlag && (attr.equals("width") || attr.equals("height") || attr.equals("alignment"))) {
					widthHeightAlignFlag = false;
					str += this.frameAccumulator(nodeAttr, frame);
				}
				//if element has minWidth, minHeight, maxWidth, maxHeight attributes it will be puted frame
				else if(minMaxWidthHeightFlag && (attr.equals("minWidth") || attr.equals("minHeight") || attr.equals("maxHeight") || attr.equals("maxWidth"))) {
					minMaxWidthHeightFlag = false;
					str += this.frameAccumulator(nodeAttr, frameMinMax);
				}
				
				if(attr.equals("background-color"))
					str += this.setElementColor(buttonKeywords.get("background-color"), nodeAttr.get("background-color"));
				
				if(attr.equals("text-color"))
					str += this.setElementColor(buttonKeywords.get("text-color"), nodeAttr.get("background-color"));
				
				if(attr.equals("padding"))
					str += this.setPadding(nodeAttr.get("padding"));
				
				if(attr.equals("text-size"))
					str += this.setElementFontColor("" ,nodeAttr.get("text-size"));
 
				
			}else{
				System.out.println("ERROR: " + attr + " is not attribute of Button");
			}
		}
		
		return str;
	}
	
	
	//set color with given attributre and its color
	//ex: setElementColor("foregroundColor", "#ff0000")
	public String setElementColor(String attr, String color) {
		Color color1 = HexToColor(color);
		double blue = color1.getBlue();
		double red = color1.getRed();
		double green = color1.getGreen();

		return "\n"+ attr + "Color(red:"+red +", green:"+green+", blue: "+blue + "))\n";
	}
	
	//Accumulate all frame attributes width, height, minHeight, maxWidth etc. with it spcefici frame map variable
	public String frameAccumulator(Map<String, String> nodeAttr, Map<String, String> frame){
		String accumulator = "";
		int i=0;
		
		for(Object attr : frame.keySet().toArray()){
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
					
				i++;
			}
		}
		
		if(!accumulator.equals("")) return ".frame("+accumulator+ ")\n";
		else return "";
	}
	
	//this for padding tag
	public String setPadding(String val) {
		return ".padding("+val.replace("px", "")+")";
	}
	
	//this for font color tag
	public String setElementFontColor(String font, String size) {
		return ".font(.custom(\"\", size:"+size.replace("px", "")+" ))\n";
	}
	
	
	//this convert if entered color hex type it will be converted RGB type. 
	public Color HexToColor(String hex) 
	{
	    hex = hex.replace("#", "");
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
