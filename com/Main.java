package com;

public class Main {
    public static void main(String[] args) {
    	FileProcessing fileRead = FileProcessing.GetInstance();
        String source = fileRead.read("html_input.html");
        Lexer lexer = new Lexer(source);
        lexer.scanTokens();
        lexer.getSyntaxTree().walk();
    }    
}

