package com;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.Views.Attribute;
import com.Views.Text;
import com.Views.View;

import static com.TokenType.*;

public class Lexer {

    private final String source;
    private int current;
    private int start;
    private int line;
    private final List<Token> tokens = new ArrayList<>();
    private SyntaxTree syntaxTree = new SyntaxTree();

    Lexer(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            this.start = this.current;
            this.scanToken();
        }
        this.tokens.add(new Token(EOF, "", null, this.line));
        return this.tokens;
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
        String val = this.source.substring(this.start, this.current);
        TokenType m = keywords.get(val);
        this.addToken(m);
        Attribute attribute = new Attribute();
        if (this.match(' ')) {
            attribute.setAtrribute(this.attributes());
        }
        if (m == VIEW) {
            View view = new View();
            if (attribute != null)
                view.setAttribute(attribute);
            if (syntaxTree.getRoot() == null)
                syntaxTree.setRoot(view);
            else {
                syntaxTree.addCursor(view);
            }
        } else if (m == TEXT) {
            Text text = new Text();
            this.start = this.current;
            while (this.peek() != '<' && !this.isAtEnd()) {
                this.advance();
            }
            this.start++;
            String s = this.source.substring(this.start, this.current);
            attribute.addAttribute("text", s);
            text.setAttribute(attribute);
            if (syntaxTree.getRoot() == null)
                syntaxTree.setRoot(text);
            else {
                syntaxTree.addCursor(text);
            }
        }
    }

    private String attributes() {
        this.start = this.current;
        while (this.peek() != '>' && !this.isAtEnd()) {
            this.advance();
        }
        String val = this.source.substring(this.start, this.current);
        return val;
    }

    private void addToken(TokenType type) {
        this.addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = this.source.substring(this.start, this.current);
        this.tokens.add(new Token(type, text, literal, this.line));
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
        keywords.put("style", STYLE);
    }

    public SyntaxTree getSyntaxTree() {
        return this.syntaxTree;
    }
}