package com.hk.svr.pub;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import com.hk.svr.impl.LabaIndxField;

public class SearchUtil {

	public List<Document> getLabaListForSearch(String dirpath, String key,
			int begin, int size) {
		List<Document> idList = new ArrayList<Document>();
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			LabaIndxField indxField = new LabaIndxField();
			File dir = new File(dirpath);
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			QueryParser parser = new QueryParser(Version.LUCENE_30, indxField
					.getContentField(), analyzer);
			Query query = parser.parse(key);
			Sort sort = new Sort();
			sort.setSort(new SortField(indxField.getLabaIdField(),
					SortField.DOC, false));
			int localSize = begin + size;
			TopFieldDocs tfd = isearcher.search(query, null, localSize, sort);
			ScoreDoc[] hits = tfd.scoreDocs;
			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				idList.add(hitDoc);
			}
			if (idList.size() > size) {
				idList = idList.subList(idList.size() - size, idList.size());
			}
			return idList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Document>();
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void index(String dirpath, boolean rebuild, BatchIdx batchIdx)
			throws Exception {
		IndexWriter iwriter = null;
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			File dir = new File(dirpath);
			Directory directory = FSDirectory.open(dir);
			iwriter = new IndexWriter(directory, analyzer, rebuild,
					new IndexWriter.MaxFieldLength(25000));
			int size = batchIdx.getSize();
			for (int i = 0; i < size; i++) {
				Document doc = batchIdx.getDocument(i);
				iwriter.addDocument(doc);
			}
			iwriter.optimize();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			try {
				if (iwriter != null) {
					iwriter.close();
				}
			}
			catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}