package com;

import java.util.HashMap;
import java.util.Map;

import static com.TokenType.*;

public class Lexer {

    private final String source;
    private int current;
    private int start;
    private int line;
    private SyntaxTree syntaxTree = new SyntaxTree();

    Lexer(String source) {
        this.source = source;
    }

    public void scanTokens() {
        while (!isAtEnd()) {
            this.start = this.current;
            this.scanToken();
        }
       
    }

    public void scanToken() {
        char c = this.advance();
        switch (c) {
        case '<':
            if (this.match('/')) {
                while (this.peek() != '>' && !this.isAtEnd()) {
                    this.advance();
                }
                this.syntaxTree.popCursor();
            } else {
                this.addTag();
            }
            break;
        case '>':
            break;
        case ' ':
        case '\t':
        case '\r':
            break;
        default:

            break;
        }
    }

    private void addTag() {
        this.start = this.current;
        while (this.peek() != ' ' && this.peek() != '>' && !this.isAtEnd()) {
            this.advance();
        }
        if (this.isAtEnd())
            System.out.println("OUT OF RANGE BİLADER");

        String tokenType_val = this.source.substring(this.start, this.current);
        TokenType tokenType = keywords.get(tokenType_val);
        
        Token token = new Token(tokenType, this.line);
        Node node = new Node(token);

        //read style properties
        Attribute attribute = new Attribute();
        if (this.match(' ')) 
            attribute.setAtrribute(this.attributes());
           
        if (tokenType == TokenType.TEXT) {
            this.start = this.current;
            while (this.peek() != '<' && !this.isAtEnd()) {
                this.advance();
            }
            this.start++;
            String text = this.source.substring(this.start, this.current);
            attribute.addAttribute("text", text);
        }

        if (attribute != null)
            node.setAttribute(attribute);
        if (syntaxTree.getRoot() == null)
            syntaxTree.setRoot(node);
        else
            syntaxTree.addCursor(node);
    }

    private String attributes() {
        this.start = this.current;
        while (this.peek() != '>' && !this.isAtEnd()) {
            this.advance();
        }
        String val = this.source.substring(this.start, this.current);
        return val;
    }


    private boolean match(char expected) {
        if (this.isAtEnd())
            return false;
        if (this.source.charAt(this.current) != expected)
            return false;

        this.current++;

        return true;
    }

    private char peek() {
        if (this.isAtEnd())
            return '\0';

        return this.source.charAt(this.current);
    }

    private char advance() {
        this.current++;
        return this.source.charAt(this.current - 1);
    }

    private boolean isAtEnd() {
        return this.current >= this.source.length();
    }

    private static final Map<String, TokenType> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put("View", VIEW);
        keywords.put("Text", TEXT);
        keywords.put("Button", BUTTON);
        keywords.put("style", STYLE);
    }

    public SyntaxTree getSyntaxTree() {
        return this.syntaxTree;
    }
}