package com;

import java.util.Stack;
import static com.TokenType.*;

public class SyntaxTree {

    private Node root;
    private Stack<Node> cursor = new Stack<>();
    private String java_source;
    private String temp_id;

    SyntaxTree() {

        this.root = null;

    }

    public void setRoot(Node root) {
        this.root = root;
        this.cursor.push(root);
    }

    public Node getRoot() {
        return this.root;
    }

    public Node getCursor() {
        return this.cursor.peek();
    }

    // push cursor to stack and add node to tree
    public void addCursor(Node c) {

        this.cursor.peek().addNode(c);
        this.cursor.push(c);
    }

    public Node popCursor() {
        return this.cursor.pop();
    }

    public void walk() {
        Node current = root;

        if (current != null) {
            System.out.print("\n" + current.getToken().getTokenType() + " ::: ");

            if (current.getToken().getTokenType() == VIEW) {
                this.java_source = "LinearLayout ll = new LinearLayout(this); \n";
                this.java_source += "ll.setOrientation(LinearLayout.VERTICAL); \n";
                temp_id = "ll";
                current.setId(temp_id);
            } 
            System.out.println(current.getAttribute().getKeywords().toString());

            if (current.getAttribute().getKeywords().get("background-color") != null)
                this.java_source += current.getId() + ".setBackgroundColor(Color.parseColor(" + "\""
                        + current.getAttribute().getKeywords().get("background-color") + "\"" + ")); \n";
            if (current.getAttribute().getKeywords().get("text") != null)
                this.java_source += current.getId() + ".setText(" + current.getAttribute().getKeywords().get("text")
                        + "); \n";

            rec(current);

            this.java_source += "LinearLayout general = findViewById(R.id.general);\n";
            this.java_source += "general.setOrientation(LinearLayout.VERTICAL);\n";
            this.java_source += "general.addView(ll);\n";

            System.out.println(this.java_source);
        }
    }

    public void rec(Node node) {
        Node current = node;

        for (int i = 0; i < current.getChildren().size(); i++) {
            System.out.print("\n" + current.getChildren().get(i).getToken().getTokenType() + " ::: ");

            if (current.getChildren().get(i).getToken().getTokenType() == VIEW) {
                this.java_source += "LinearLayout " + current.getId() + "_" + i + " = new LinearLayout(this); \n";
                current.getChildren().get(i).setId(current.getId() + "_" + i);
            } else if (current.getChildren().get(i).getToken().getTokenType() == TEXT) {
                this.java_source += "TextView " + current.getId() + "_" + i + " = new TextView(this); \n";
                current.getChildren().get(i).setId(current.getId() + "_" + i);
            }

            System.out.print(current.getChildren().get(i).getAttribute().getKeywords().toString());

            if (current.getChildren().get(i).getAttribute().getKeywords().get("background-color") != null)
                this.java_source += current.getChildren().get(i).getId() + ".setBackgroundColor(Color.parseColor("
                        + "\"" + current.getChildren().get(i).getAttribute().getKeywords().get("background-color")
                        + "\"" + ")); \n";

            if (current.getChildren().get(i).getAttribute().getKeywords().get("text") != null)
                this.java_source += current.getChildren().get(i).getId() + ".setText(" + "\""
                        + current.getChildren().get(i).getAttribute().getKeywords().get("text") + "\"" + "); \n";

            this.java_source += current.getId() + ".addView(" + current.getChildren().get(i).getId() + "); \n";
        }
        for (int i = 0; i < current.getChildren().size(); i++) {
            if (current.getChildren().get(i).getChildren().size() > 0)
                rec(current.getChildren().get(i));
        }
    }

    public void walk_print() {
        Node current = root;

        if (current != null) {
            System.out.print("\n" + current.getToken().getTokenType() + " ::: ");
            if (current.getAttribute() != null) {
                System.out.print(current.getAttribute().getKeywords().toString());

                // this.source = "LinearLayout ll = findViewById(R.id.idsi);";
            }
            rec_print(current);
        }
    }

    public void rec_print(Node node) {
        Node current = node;

        for (int i = 0; i < current.getChildren().size(); i++) {
            System.out.print("\n" + current.getChildren().get(i).getToken().getTokenType() + " ::: ");
            if (current.getChildren().get(i).getAttribute() != null) {
                System.out.print(current.getChildren().get(i).getAttribute().getKeywords().toString());
                if (current.getChildren().get(i).getToken().getTokenType() == TEXT) {

                    /*
                     * this.source += "\nTextView tw = new TextView(this);"; Map a =
                     * current.getChildren().get(i).getAttribute().getAttributes(); if
                     * (a.get("background-color") != null) { this.source +=
                     * "\n tw.setBackgroundColor(Color.Parse(" + a.get("background-color") + "));";
                     * } if (a.get("width") != null) this.source += "\n tw.setWidth(" +
                     * a.get("width") + ");"; this.source += "\n ll.addView(tw);";
                     * System.out.println("\n" + this.source);
                     */
                }
            }
        }
        for (int i = 0; i < current.getChildren().size(); i++) {
            if (current.getChildren().get(i).getChildren().size() > 0)
                rec_print(current.getChildren().get(i));
        }
    }

    public void putTokens(Node now_node) {

    }
}
