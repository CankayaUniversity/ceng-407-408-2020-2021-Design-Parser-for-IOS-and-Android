package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class App {

    public static void main(String args[]) {
        String userInput = "input.txt";      
        String source = FileProcessing.GetInstance().read(userInput);
        
        Map<String, List<String>> flags = App.readFlags(args);
        for(Map.Entry<String, List<String>> entry  : flags.entrySet()) {
        	if(entry.getKey().equals("parser-run") || entry.getKey().equals("parser-export")) {
        		for(String option : entry.getValue()) {
        			if(option.equals("ios")) {
            			FileProcessing.processIos = true;
            			System.out.println(option);
            		}
            		if(option.equals("android")) {
            			FileProcessing.processAndroid = true;
            			System.out.println(option);
            		}
            		
            	}
        	}
        	
        	if(entry.getKey().equals("parser-create")) {
        		FileProcessing.GetInstance().createFolder();
        	}
        	
        	if(entry.getKey().equals("parser-help")) {
        		System.out.println("********************");
        		System.out.println("Create project: run parser-create. This create dist folder.");
        		System.out.println("Run project: run parser-run. This create android or ios folder in the dist folder with specific arguments options.");
        		System.out.println("Export project: run parser-export. This export android or ios in dist folder.");
        	}
        }
                
        Lexer lex = new Lexer(source);
        lex.scanTokens();
        lex.getSyntaxTree().walk();
    }
    
    private static Map<String, List<String>> readFlags(String args[]) {
    	Map<String, List<String>> params = new HashMap<>();
    	
    	List<String> options = null;
    	
    	for(int i=0; i<args.length; i++) {
    		String flag = args[i];
    		
    		if(flag.charAt(0) == '-') {
    			int flagSize = 1;
    			if(flag.length() < 2) {
    				System.out.println("Error at argument "+flag);
    				return null;
    			}
    			else if(flag.charAt(1) == '-') {
    				flagSize = 2;
    			}
    			
    			options = new ArrayList<>();
    			params.put(flag.substring(flagSize), options);
    		}
    		else if(options != null) {
    			options.add(flag);
    		
    		}else {
    			System.out.println("Wrong flag");
    		}
    	}
    	return params;
    }
}
