package com;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner; // Import the Scanner class to read text files


public class FileProcessing {

	private static String directoryPath = System.getProperty("user.dir");
	private static String androidFolder = "";
	private static String iosFolder = "";

	public FileProcessing(){
    
   	} 

    public static String read(String fileName){
      String txt = "";

      try {
        File myObj = new File(directoryPath + "/src/" +fileName);
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
    
    public static void createFolder() {
    	//user code will be createad at dist folder in project directory
    	File folderPath = new File(directoryPath + "/dist");
    	
        folderPath.mkdir();
        
        createPlatformFolders(folderPath.toString());
        	
    }
    
    private static void createPlatformFolders(String distFolder) {
    	FileProcessing.iosFolder = directoryPath + "/dist/ios";
    	FileProcessing.androidFolder = directoryPath + "/dist/android";
    	
    	File iosFile = new File(FileProcessing.iosFolder);
    	iosFile.mkdir();
    	
    	File androidFile = new File(FileProcessing.androidFolder);
       	androidFile.mkdir();
    }
    
    public static void createFileAndWrite(String platform, String source) {
    	FileWriter writer;
    	
    	if(platform.equals("Ios")) {   
   			try {
				writer = new FileWriter(FileProcessing.iosFolder+"/ios.txt", false);
				writer.write(source);
	    		writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    	}
    	else if(platform.equals("Android")) {
    		
    		try {
    			writer = new FileWriter(FileProcessing.androidFolder, false);
    			writer.write(source);
    			writer.close();
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
}
