package com;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Token token;
    private List<Node> children = new ArrayList<>();
    private Attribute attribute;
    private String id;

    public Node(Token token) {
        this.token = token;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public void addNode(Node child) {
        this.children.add(child);
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public Token getToken() {
        return this.token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}