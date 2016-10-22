/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlertest;

import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

/**
 *
 * @author christones
 */
public class SynonymAnalyzerViewer {
    
    public static void main(String[] args) throws IOException {

SampleSynonyms engine = new SampleSynonyms();
SynonymAnalyzerViewer.displayTokensWithPositions(
new SynonymAnalyzer(engine),
"The quick brown fox jumps over the lazy dog yea by christones this is a customized analyzer from Njorku");
}
 
  public static void displayTokensWithPositions (Analyzer analyzer, String text) throws IOException {
TokenStream stream = analyzer.tokenStream("contents",
new StringReader(text));
TermAttribute term = stream.addAttribute(TermAttribute.class);
PositionIncrementAttribute posIncr =
stream.addAttribute(PositionIncrementAttribute.class);
int position = 0;
while(stream.incrementToken()) {
int increment = posIncr.getPositionIncrement();
if (increment > 0) {
position = position + increment;
System.out.println();
System.out.print(position + ": ");
}
System.out.print("[" + term.term() + "] ");
}
System.out.println();
}  
    
    
    
}
