package com;

public class App {

    public static void main(String args[]) {
        String userInput = "input.txt";      
        String source = FileProcessing.GetInstance().read(userInput);
        
        Lexer lex = new Lexer(source);
        lex.scanTokens();
        lex.getSyntaxTree().walk();
    }
}
