/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlertest;


import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author christones
 */
public class Searchtest {
    
    
    
     public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
    System.out.println("Enter a keyword: ");
    Scanner scanner = new Scanner(System.in);
    String keyword = scanner.nextLine();
    String[] query = new String[] {keyword};
    System.out.println(query[0]); 
    MyLucene lucene = new MyLucene();
    //open the index created by crawler code
    lucene.openIndex("indexx", false);
    lucene.search(query);
    }
    
}
    
    
    
       
        
        
        
        
        
        
        
        
        
        
        
        
     
        
       
    

