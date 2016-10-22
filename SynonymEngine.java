/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlertest;

import java.io.IOException;

/**
 *
 * @author christones
 */
public interface SynonymEngine {
String[] getSynonyms(String s) throws IOException;
}
