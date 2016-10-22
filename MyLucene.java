package crawlertest;

/**
 *
 * @author christones
 */
import java.io.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class MyLucene {
	// The index object, points to a repository
	Directory indexx;

	// Specify the analyzer for tokenizing text.
	// The same analyzer should be used for indexing and searching
	StandardAnalyzer analyzer;

	// Writing object, linked to the repository
	org.apache.lucene.index.IndexWriter writer = null;

	/**
	 * @param args
	 */
	MyLucene() {
		// Instantiate the analyzer
		analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
	}

	/* Open or create the index */
	public void openIndex(String directory, boolean newIndex) {
		try {
			// Link the directory on the FileSystem to the application
			indexx = FSDirectory.open(new File(directory));

			// Check whether the index has already been locked
			// (or not properly closed).
			if (IndexWriter.isLocked(indexx))
				IndexWriter.unlock(indexx);
			if (writer == null)
				// Link the repository to the IndexWriter
				writer = new IndexWriter(indexx, analyzer, newIndex,
						IndexWriter.MaxFieldLength.LIMITED);
		} catch (Exception e) {
			System.out.println("Got an Exception: " + e.getMessage());
		}
	}
       
        
        
        public void search(String[] args) {
            
		// Nothing given? Search for "Web".
		String querystr = args.length > 0 ? args[0] : "offre";

		try {
			// Instantiate a query parser
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "content",
					analyzer);
			// Parse
			Query q = parser.parse(querystr);
			// We look for top-10 results
			int hitsPerPage = 10;
                    // Ranker
                    try ( // Instantiate a searcher
                            IndexSearcher searcher = new IndexSearcher(indexx, true)) {
                        // Ranker
                        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage,
                                true);
                        // Search!
                        searcher.search(q, collector);
                        // Retrieve the top-10 documents
                        ScoreDoc[] hits = collector.topDocs().scoreDocs;
                        // Display results
                        System.out.println("Found " + hits.length + " hits.");
                        for (int i = 0; i < hits.length; ++i) {
                            int docId = hits[i].doc;
                            Document d = searcher.doc(docId);
                            System.out.println((i + 1) + ". " + d.get("content"));
                        }
                        // Close the searcher
                    }
		} catch (ParseException | IOException e) {
			System.out.println("Got an Exception: " + e.getMessage());
		}
	}
        
        public void addDoc(String title, String content) {
		try {
			// Instantiate a new document
			Document doc = new Document();
			// Put the value in a field name content
			Field f = new Field("title", title, Field.Store.YES,
					Field.Index.ANALYZED);
                        Field f2 = new Field("content", content, Field.Store.YES,
					Field.Index.ANALYZED);
			// Add the field to the document
			doc.add(f);
                        doc.add(f2);
			// And add the document to the index
			writer.addDocument(doc);
		} catch (Exception e) {
			System.out.println("Got an Exception: " + e.getMessage());
		}
	}

	// Close the index
	public void closeIndex() {
		try {
			writer.optimize();
			writer.close();
		} catch (Exception e) {
			System.out.println("Got an Exception: " + e.getMessage());
		}
	}
        
    }
    