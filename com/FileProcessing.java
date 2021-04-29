package com;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files


public class FileProcessing {

	private static FileProcessing instance;
	private  String directoryPath;
	private  String androidFolder;
	private String iosFolder;

	public FileProcessing(){
		directoryPath = System.getProperty("user.dir");
		androidFolder = "";
		iosFolder = "";
   	} 
	
    // static method to create instance of Singleton class
    public static FileProcessing GetInstance(){
        if (instance == null)
            instance = new FileProcessing();
  
        return instance;
    }

    public String read(String fileName){
      String txt = "";

      try {
        File myObj = new File(directoryPath + "/src/" + fileName);
        Scanner myReader = new Scanner(myObj);
        String data;

        while (myReader.hasNextLine()) {
          data = myReader.nextLine();
          txt += data;
        }
        myReader.close();

      }catch(FileNotFoundException e) {
        e.printStackTrace();
      }
      
      return txt;
    }
    
    public void createFolder() {
    	//user code will be createad at dist folder in project directory
    	File folderPath = new File(directoryPath + "/dist");
    	
        folderPath.mkdir();
        
        createPlatformFolders(folderPath.toString());
    }
    
    private void createPlatformFolders(String distFolder) {
    	iosFolder = directoryPath + "/dist/ios";
    	androidFolder = directoryPath + "/dist/android";
    	
    	File iosFile = new File(iosFolder);
    	iosFile.mkdir();
    	
    	File androidFile = new File(androidFolder);
       	androidFile.mkdir();
    }
    
    public void createFileAndWrite(String platform, String source) {
    	FileWriter writer;
    	
    	if(platform.equals("Ios")) {   
   			try {
				writer = new FileWriter(iosFolder+"/ios.txt", false);
				writer.write(source);
	    		writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	else if(platform.equals("Android")) {
    		
    		try {
    			writer = new FileWriter(androidFolder, false);
    			writer.write(source);
    			writer.close();
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
}
