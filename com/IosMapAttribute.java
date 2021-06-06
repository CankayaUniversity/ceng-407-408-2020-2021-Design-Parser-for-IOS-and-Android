package com;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class IosMapAttribute {
	private static final Map<String, String> viewKeywords;
	private static final Map<String, String> textKeywords;
	private static final Map<String, String> buttonKeywords;
	private static final Map<String, String> imageKeywords;	
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
		viewKeywords.put("padding", "padding");
		viewKeywords.put("maxWidth", "maxWidth");
		
		imageKeywords = new HashMap<>();
		imageKeywords.put("background-color", ".background(");
		imageKeywords.put("alignment", "alignment");
		imageKeywords.put("width", "width");
		imageKeywords.put("height", "height");
		imageKeywords.put("alignment", "alignment");
		imageKeywords.put("minHeight", "minHeight");
		imageKeywords.put("minWidth", "minWidth");
		imageKeywords.put("maxHeight", "maxHeight");
		imageKeywords.put("maxWidth", "maxWidth");
		imageKeywords.put("text", "text");
		imageKeywords.put("padding", "padding");
		
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
		textKeywords.put("padding", "padding");
		textKeywords.put("text", "text");
		textKeywords.put("text-size", "text-size");
		
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
			break;
		case IMAGE:
			str+= this.imageAttribute(nodeAttr);
			break;
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
					str += this.frameAccumulator(nodeAttr);
				}
				//if element has minWidth, minHeight, maxWidth, maxHeight attributes it will be puted frame
			/*	if(attr.equals("width")) {
					if(nodeAttr.get("width").trim().equals("match_parent"))
						str += this.frameMinMaxAccumulator(nodeAttr);
				}*/
				
				if(attr.equals("background-color"))
					str += this.setElementColor(viewKeywords.get("background-color"), nodeAttr.get("background-color"));
				
				if(attr.equals("text-color"))
					str += this.setElementColor(viewKeywords.get("text-color"), nodeAttr.get("background-color"));
				
				if(attr.equals("padding"))
					str += this.setPadding(nodeAttr.get("padding"));
				
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
					str += this.frameAccumulator(nodeAttr);
				}
				
				if(attr.equals("background-color"))
					str += this.setElementColor(textKeywords.get("background-color"), nodeAttr.get("background-color"));
				
				if(attr.equals("text-color"))
					str += this.setElementColor(textKeywords.get("text-color"), nodeAttr.get("text-color"));
				
				if(attr.equals("text-size"))
					str += this.setElementFontColor("" ,nodeAttr.get("text-size"));
				
				if(attr.equals("padding"))
					str += this.setPadding(nodeAttr.get("padding"));
				
			}else{
				System.out.println("ERROR: " + attr + " is not attribute of TEXT");
			}
		}
		
		return str;
	}
	
	public String imageAttribute(Map<String, String> nodeAttr) {
		String str = "";
		boolean widthHeightAlignFlag = true;
		boolean minMaxWidthHeightFlag = true;

		str += ".resizable()\n";
		str += ".scaledToFit()\n";

		//convert map to array
		//this now work for color it will be converted.
		for(String attr :  nodeAttr.keySet()) {
			if(imageKeywords.containsKey(attr)) {
				//if element has width, height, alignment attributes it will be puted frame
				if(widthHeightAlignFlag && (attr.equals("width") || attr.equals("height") || attr.equals("alignment"))) {
					widthHeightAlignFlag = false;
					str += this.frameAccumulator(nodeAttr);
				}
				if(attr.equals("background-color"))
					str += this.setElementColor(imageKeywords.get("background-color"), nodeAttr.get("background-color"));

				if(attr.equals("text-color"))
					str += this.setElementColor(imageKeywords.get("text-color"), nodeAttr.get("background-color"));
				
				if(attr.equals("padding"))
					str += this.setPadding(nodeAttr.get("padding"));

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
					str += this.frameAccumulator(nodeAttr);
				}
				//if element has minWidth, minHeight, maxWidth, maxHeight attributes it will be puted frame
		/*		if(attr.equals("width")) {
					if(nodeAttr.get("width").trim().equals("match_parent"))
						str += this.frameMinMaxAccumulator(nodeAttr);
				}*/

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
		Color color1 = HexToColor(color.trim());
		System.out.println(color);
		double blue = color1.getBlue();
		double red = color1.getRed();
		double green = color1.getGreen();

		return "\n"+ attr + "Color(red:"+red +"/255, green:"+green+"/255, blue: "+blue + "/255))\n";
	}
	
	//Accumulate all frame attributes width, height, minHeight, maxWidth etc. with it spcefici frame map variable
	public String frameAccumulator(Map<String, String> nodeAttr){
		String accumulator = "";
		int i=0;

		if(nodeAttr.containsKey("width")) {
			if(!nodeAttr.get("width").trim().equals("match_parent")) {	
				i++;
				accumulator += "width: ";
				accumulator += nodeAttr.get("width").trim();
			}			
		}
		
		if(nodeAttr.containsKey("height")) {
			if(i != 0)
				accumulator += ", ";
			accumulator += "height: ";
			accumulator += nodeAttr.get("height").trim();
			i++;
		}
		
		if(nodeAttr.containsKey("alignment")) {
			if(i != 0)
				accumulator += ", ";
			accumulator += "alignment: ";
			
			if(nodeAttr.get("alignment").trim().equals("left"))
				accumulator += ".leading";
			if(nodeAttr.get("alignment").trim().equals("top"))
				accumulator += ".top";
			if(nodeAttr.get("alignment").trim().equals("right"))
				accumulator += ".trailing";
			if(nodeAttr.get("alignment").trim().equals("bottom"))
				accumulator += ".bottom";
		}
		
		if(!accumulator.equals("")) return ".frame("+accumulator+ ")\n";
		else return "";
	}
	
	public String frameMinMaxAccumulator(Map<String, String> nodeAttr){
 		//String accumulator = "";
		
 		/*int seperatorCount=0;
		String []arr = {
				"minWidth", "maxWidth", "alignment"
		};
		String []arrDefault = {
				"0", ".infinity", ".topLeading"
		};
		
		for(int idx=0; idx<arr.length; idx++) {
			String key = arr[idx];
			if(seperatorCount != 0)
				accumulator += ", ";
			accumulator += key+": ";
			if(key.equals("alignment"))
				accumulator += (nodeAttr.get(key) != null ?  "." + nodeAttr.get(key) : arrDefault[idx]).trim();
			else
				accumulator += (nodeAttr.get(key) != null ? nodeAttr.get(key) : arrDefault[idx]).trim();
			seperatorCount++;
		}*/
		
		//if(!accumulator.equals("")) return ".frame("+accumulator+ ")\n";
		return ".frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)";
	}
	
	//this for padding tag
	public String setPadding(String val) {
		String[] points = val.split(",");
		String ac = "";

		if(points[0] != null)
			ac += ".padding(.leading, "+points[0]+")";
		if(points[1] != null)
			ac += ".padding(.top, "+points[1]+")";
		if(points[2] != null)
			ac += ".padding(.trailing, "+points[2]+")";
		if(points[3] != null)
			ac += ".padding(.bottom, "+points[3]+")";
		
		return ac;
	}
	
	//this for font color tag
	public String setElementFontColor(String font, String size) {
		System.out.println(".font(.custom(\"\", size:"+size.replace("px", "")+" ))\n");
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
	    }
	    return null;
	}
}
