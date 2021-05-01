package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class App {

    public static void main(String args[]) {
        String userInput = "input.txt";      
        String source = FileProcessing.GetInstance().read(userInput);
                
        Lexer lex = new Lexer(source);
        lex.scanTokens();
        lex.getSyntaxTree().walk();
        
        Map<String, List<String>> a = App.readFlags(args);
        for(Map.Entry<String, List<String>> entry  : a.entrySet()) {
        	/*
        	System.out.println(entry.getKey() + " : ");
        	if(entry.getKey().equals("d")) {
        		for(String option : entry.getValue()) {
            		System.out.print(option + " ---");
            	}
        	}else if(entry.getKey().equals("device")) {
        		for(String option : entry.getValue()) {
            		System.out.print(option + " ---");
            	}
        	}
        	
        	System.out.println();*/
        }
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
