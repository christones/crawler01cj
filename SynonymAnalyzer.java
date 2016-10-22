/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlertest;

import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

/**
 *
 * @author christones
 */
public class SynonymAnalyzer extends Analyzer {
    
private final SynonymEngine engine;

public SynonymAnalyzer(SynonymEngine engine) {
    
this.engine = engine;
}

@Override
public TokenStream tokenStream (String fieldName, Reader reader) {

TokenStream result = new SynonymFilter (
new StopFilter(true,
new LowerCaseFilter(
new StandardFilter(
new StandardTokenizer(
Version.LUCENE_30, reader))),
StopAnalyzer.ENGLISH_STOP_WORDS_SET),
engine
);
return result;
}
}

/*
SynonymAnalyzer’s purpose is to first detect the occurrence of words that have syn-
onyms, and second to insert the synonyms at the same position...
*/




