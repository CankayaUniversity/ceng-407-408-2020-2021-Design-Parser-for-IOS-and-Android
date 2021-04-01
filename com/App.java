package com;

//import java.util.List;

public class App {

    public static void main(String args[]) {
        FileRead fileRead = new FileRead();
        System.out.println();

        String source = fileRead.read("input.txt");
        Lexer lex = new Lexer(source);
        lex.scanTokens();

        lex.getSyntaxTree().walk();
    }
}

