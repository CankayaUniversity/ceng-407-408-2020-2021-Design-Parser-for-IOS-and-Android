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
        FileProcessing.GetInstance().createFolder();

        if (current != null) {
        	if (current.getToken().Type() == VIEW) {
        		if(FileProcessing.processAndroid) {
        			//these are for ios and android source code starting tags.
        			this.java_source = "LinearLayout ll = new LinearLayout(this); \n";
        			this.java_source += "ll.setOrientation(LinearLayout.VERTICAL); \n";
                
        			//ll mean LinearLayout
        			temp_id = "ll";
                	current.setId(temp_id);
 
                	if (current.getAttribute().getKeywords().get("background-color") != null)
                		this.java_source += current.getId() + ".setBackgroundColor(Color.parseColor(" + "\""
                				+ current.getAttribute().getKeywords().get("background-color") + "\"" + ")); \n";
                
                	if (current.getAttribute().getKeywords().get("text") != null)
                		this.java_source += current.getId() + ".setText(" + current.getAttribute().getKeywords().get("text")
                		+ "); \n";
                
                	androidWalker(current);
                		
                	//these are for end of android and ios source code closing tags.
                    this.java_source += "LinearLayout general = findViewById(R.id.general);\n";
                    this.java_source += "general.setOrientation(LinearLayout.VERTICAL);\n";
                    this.java_source += "general.addView(ll);\n";
                    
                    FileProcessing.GetInstance().createFileAndWrite("Android", java_source);
        		}
        		
        		if(FileProcessing.processIos) {
        			this.ios_source = "VStack{";

                    iosWalker(current);
                   
                    this.ios_source += "\n Spacer() \n } \n";
                    current.getAttribute().addAttribute("alignment", "top");
                    this.ios_source += this.iosMapAttribute.toString(current, current.getId(),0);
                    
                    this.ios_source += "\n .flipsForRightToLeftLayoutDirection(true)";
                    this.ios_source += "\n .environment(\\.layoutDirection, .leftToRight)";
                    
                    FileProcessing.GetInstance().createFileAndWrite("Ios", ios_source);

        		}
                
                
            }
            //Print for fast test
            System.out.println("*****************************************");
            System.out.println(this.java_source);
            System.out.println("*****************************************");
            System.out.println(this.ios_source);
            
            System.out.println(FileProcessing.processAndroid + " : " + FileProcessing.processIos);
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
    		//System.out.print("\n" + current.getChildren().get(i).getToken().Type() + " ::: ");
    
            this.ios_source += this.iosMapToken.toString(current.getChildren().get(i), current.getId(), i);

            this.iosWalker(current.getChildren().get(i));
    		
            TokenType currentToken = current.getChildren().get(i).getToken().Type();
    		if(currentToken == VIEW || currentToken == VIEW_H || currentToken == BUTTON) {
    			
    			this.ios_source += "} \n";
    			
    			if(current.getChildren().get(i).getAttribute().getKeywords().containsKey("width"))
    				if(current.getChildren().get(i).getAttribute().getKeywords().get("width").trim().equals("match_parent"))
    					this.ios_source += ".frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)\n";
    		
    		}
            	
    		    		
            this.ios_source += this.iosMapAttribute.toString(current.getChildren().get(i), current.getId(), i);
    	}
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
	}
}
