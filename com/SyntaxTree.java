package com;

import java.util.Stack;
import static com.TokenType.*;

public class SyntaxTree implements Runnable {

    private Node root;
    private Stack<Node> cursor = new Stack<>();
    private String java_source;
    private String ios_source;
    private String temp_id;
    private IosMapAttribute iosMapAttribute;
    private IosMapToken iosMapToken;

    SyntaxTree() {
        this.root = null;
        this.iosMapAttribute =  new IosMapAttribute();
        this.iosMapToken =  new IosMapToken();
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
        	if (current.getToken().getTokenType() == VIEW) {
        		//these are for ios and android soruce code starting tags.
                this.java_source = "LinearLayout ll = new LinearLayout(this); \n";
                this.java_source += "ll.setOrientation(LinearLayout.VERTICAL); \n";
                
                temp_id = "ll";
                current.setId(temp_id);
 
                if (current.getAttribute().getKeywords().get("background-color") != null)
                    this.java_source += current.getId() + ".setBackgroundColor(Color.parseColor(" + "\""
                            + current.getAttribute().getKeywords().get("background-color") + "\"" + ")); \n";
                
                if (current.getAttribute().getKeywords().get("text") != null)
                    this.java_source += current.getId() + ".setText(" + current.getAttribute().getKeywords().get("text")
                            + "); \n";
                
                //androidWalker(current);
                
                this.ios_source = "VStack{";

                iosWalker(current);
                
            }

            //these are for end of android and ios source code closing tags.
            this.java_source += "LinearLayout general = findViewById(R.id.general);\n";
            this.java_source += "general.setOrientation(LinearLayout.VERTICAL);\n";
            this.java_source += "general.addView(ll);\n";

            this.ios_source += "} \n";
            this.ios_source += this.iosMapAttribute.toString(current, current.getId(),0);

            //Print for fast test
            System.out.println("*****************************************");
            System.out.println(this.java_source);
            System.out.println("*****************************************");
            System.out.println(this.ios_source);
            
            FileProcessing.GetInstance().createFolder();
            FileProcessing.GetInstance().createFileAndWrite("Ios", ios_source);
        }
    }

    public void androidWalker(Node node) {
        Node current = node;

        for (int i = 0; i < current.getChildren().size(); i++) {
            AndroidMapToken amp = new AndroidMapToken();
            this.java_source += amp.ToString(current.getChildren().get(i), current.getId(), i);
    }
        
        for (int i = 0; i < current.getChildren().size(); i++) {
            if (current.getChildren().get(i).getChildren().size() > 0) {
            	androidWalker(current.getChildren().get(i));                 
            }
        }
    }
    
    public void iosWalker(Node node) {
    	Node current = node;
    	
    	for(int i=0; i<current.getChildren().size(); i++) {
    		//System.out.print("\n" + current.getChildren().get(i).getToken().getTokenType() + " ::: ");
    
            this.ios_source += this.iosMapToken.toString(current.getChildren().get(i), current.getId(), i);

            this.iosWalker(current.getChildren().get(i));
    		
    		if(current.getChildren().get(i).getToken().getTokenType() == VIEW || current.getChildren().get(i).getToken().getTokenType() == VIEW_H )
            	this.ios_source += "} \n";
    		    		
            this.ios_source += this.iosMapAttribute.toString(current.getChildren().get(i), current.getId(), i);
    	}
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
	}
}
