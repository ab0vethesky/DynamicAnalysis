/*
 * CyclomaticComplexity.java created by Oladipo Oyekanmi, 28 December 2015
 * Create an array to hold keywords to be check for complexity
 * read the file using Buffered Reader
 * Divide 
 */

package weka.dynamicAnalysis;

import java.io.*;
import java.util.*;


public class CyclomaticComplexity {

    public int check() {
    	//this is the variable that holds the cyclomatic complexity
        int complexity = 1;
        
        //This is the file to be read
        String fileName;
        
        //This array holds all the java keywords that are to be checked on each line
        String[] keywords = {"if", "while", "case", "for", "&&","||", "?", ":", "catch","?.","?:", "else if"};
        String words = "";
        String line = null;
        try {

        //Declare the file name		
        fileName = "C:\\Users\\Tara\\git\\weka\\weka\\src\\main\\java\\weka\\dynamicAnalysis\\Cc.java";
     
        
        FileReader fr = new FileReader(fileName); 
       // Read the file line by line with BufferedReader
        BufferedReader br = new BufferedReader(fr);	
         line = br.readLine();
        // Check each line 
            while (line != null) 
            {
            	// Divide each line into tokens using StringTokenizer
                StringTokenizer stTokenizer = new StringTokenizer(line);
                while (stTokenizer.hasMoreTokens()) {
                    words = stTokenizer.nextToken();
                    for (int i = 0; i < keywords.length; i++) {                        
                    	if (keywords[i].equals("\\")) {
                            break;
                        } else {
                        	// if the token equals the keyword then the complexity variable is incremente by 1
                            if (keywords[i].equals(words)) {
                                complexity++;
                            }
                        }
                    }
                }
                line = br.readLine();
                
            }
            br.close();
   
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (complexity);
        
    }
	    
    public void showCyclomaticComplexity(int ccValue) {
    	// This part displays the results of the complexity check
        System.out.println("\nThe Cyclomatic Complexity is : " + ccValue);
        System.out.print("\nResult : ");
        if (ccValue > 50) {
            System.out.print("Most complex and highly unstable method ");
        } else if (ccValue >= 21 && ccValue <= 50) {
            System.out.print("High risk");
        } else if (ccValue >= 11 && ccValue <= 20) {
            System.out.print("Moderate risk");
        } else {
            System.out.print("Low risk program");
        }
    }
 
    public static void main(String ss[]) {
        CyclomaticComplexity cc = new CyclomaticComplexity();
        cc.showCyclomaticComplexity(cc.check());
    }
}