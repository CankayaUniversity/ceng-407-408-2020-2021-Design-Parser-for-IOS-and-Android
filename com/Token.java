package com;

public class Token {

    private TokenType type;
	//it will change
    private int line;

    public Token(TokenType type, int line) {
        this.type = type;
        this.line = line;
    }

    public TokenType Type() {
        return this.type;
    }

    public void setTokenType(TokenType type){
        this.type = type;
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int line){
        this.line = line;
    }
}
