/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlertest;

import java.io.IOException;
import java.util.Set;
import java.util.Stack;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.AttributeSource;

/**
 *
 * @author christones
 */
public class SynonymFilter extends TokenFilter {
public static final String TOKEN_TYPE_SYNONYM = "SYNONYM";
private final Stack<String> synonymStack;
private final SampleSynonyms engine;
private AttributeSource.State current;
private final TermAttribute termAtt;
private final PositionIncrementAttribute posIncrAtt;
public SynonymFilter(TokenStream in, SynonymEngine engine) {
    super(in);
synonymStack = new Stack<String>();
this.engine = (SampleSynonyms) engine;
this.termAtt = addAttribute(TermAttribute.class);
this.posIncrAtt = addAttribute(PositionIncrementAttribute.class);
}
@Override
public boolean incrementToken() throws IOException {
    if (synonymStack.size() > 0) {
String syn = synonymStack.pop();
restoreState(current);
termAtt.setTermBuffer(syn);
posIncrAtt.setPositionIncrement(0);
Set position ;
return true;
}
    if (!input.incrementToken())
return false;
if (addAliasesToStack()) {
    current = captureState();
    }
return true;
}
private boolean addAliasesToStack() throws IOException {
String[] synonyms = engine.getSynonyms(termAtt.term());
if (synonyms == null) {
    return false;
}
for (String synonym : synonyms) {
synonymStack.push(synonym);
}
return true;
}
}

/*We create a stack to hold the pending synonyms.
 After all previous token synonyms have been emitted, we read the next token.
The code successively pops the stack of buffered synonyms from the last streamed-in
token until it’s empty.
We push all synonyms of the current token onto the stack.
We save details for the current token, if it has synonyms.
We return the current (and original) token before its associated synonyms.
Synonyms are retrieved from the SynonymEngine .*/



