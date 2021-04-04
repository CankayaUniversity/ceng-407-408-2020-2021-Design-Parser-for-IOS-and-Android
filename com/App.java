package com;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    public static void main(String args[]) {
        String userInput = "input.txt";      
        String source = FileProcessing.read(userInput);
        
        Lexer lex = new Lexer(source);
        lex.scanTokens();
        lex.getSyntaxTree().walk();
    }
}
