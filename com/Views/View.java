package com.Views;

import com.Node;
import com.Token;
import static com.TokenType.*;

public class View extends Node {

    public View() {
        super(new Token(VIEW, "View", null, 0));
    }
}
