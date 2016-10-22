/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlertest;

import java.io.IOException;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements ;


/**
 *
 * @author christones
 */
public class Crawlertest {

    public static MyLucene lucene ;
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
       // TODO code application logic here
        
       // Name of the directory that holds the index 
    String directory = "indexx"; 
    String[] urls = {"http://www.akwajobs.com/job/infrastructure-engineer-at-vairified#.V-uE0dFJy1E","http://www.akwajobs.com/job/german-teacher-via-employment-house-buea#.V-zpVNFJy1E","http://www.akwajobs.com/job/offre-demploi-logisticienne-chez-actelec-cameroun#.V-zpjNFJy1E","http://www.akwajobs.com/job/sales-supervisor-superviseur-salle-via-men-builder#.V-zpvtFJy1E","http://www.ngolajobs.com/job/brand-manager-guinness-and-malta-africa-regional-markets-arm#.V-zp-9FJy1E","http://www.ngolajobs.com/job/women-protection-and-empowerment-coordinator-cameroon-international-rescue-committee-irc#.V-zqItFJy1E","http://www.ngolajobs.com/job/responsable-commercial-jlm-group-sa#.V-zqXdFJy1E","http://www.ngolajobs.com/job/professeurs-permanents-filiere-information-et-communication-institut-universitaire-siantou#.V-zsf9FJy1E","http://www.ngolajobs.com/job/cotateur-confirme-bollore-transport-logistics#.V-zs1dFJy1E","http://www.ngolajobs.com/job/charge-des-relations-publiques#.V-ztAtFJy1E"} ;
        
       // extracting informations from the webpages of three different sites
       //looping on and dowloading from six  web pages
    for (int i=0 ; i <9 ; i ++){
    Document doc1 = Jsoup.connect(urls[i]).get();
    Elements jobtitle1 = doc1.select(".media h2");
    Elements jobcontent1 = doc1.select(".media");
    System.out.println("job title on that page: "+jobtitle1.text());
    System.out.println("job description on that page: "+jobcontent1.text());
         
     // Instantiate a new Lucene tool 
    lucene = new MyLucene(); 
  
    // Open the directory 
    lucene.openIndex(directory, false); 
 
    // Add a few documents 
    lucene.addDoc(jobtitle1.text(),jobcontent1.text());
    
    // Close the index 
    lucene.closeIndex(); 
       }
  } 
}
