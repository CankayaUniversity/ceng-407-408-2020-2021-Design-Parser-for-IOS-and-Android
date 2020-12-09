package com.Views;

import com.Node;
import com.Token;

import static com.TokenType.*;

public class Text extends Node {

    private String value;

    // <Text>string değeri </Text>
    public Text() {
        super(new Token(TEXT, "Text", null, 0));
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

