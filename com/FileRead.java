package com;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class FileRead {

   public FileRead(){
    
   } 

    public String read(String fileName){
      String txt = "";
      try {
        File myObj = new File(fileName);
        Scanner myReader = new Scanner(myObj);
        String data;

        while (myReader.hasNextLine()) {
          data = myReader.nextLine();
          txt += data;
        }
        myReader.close();

      }   catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      
      return txt;
    }
}
