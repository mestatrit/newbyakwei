package com.hk.frame.util.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

import com.hk.frame.util.DataUtil;

public class SearchUtil {

	private static final String KEY_PATTERN = "([a-zA-Z0-9]+)";

	private static Pattern pattern;

	private static PatternCompiler compiler = new Perl5Compiler();
	static {
		try {
			pattern = compiler.compile(KEY_PATTERN);
		}
		catch (MalformedPatternException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 格式化查询关键词
	 * 
	 * @param key
	 * @return
	 *         2010-9-11
	 */
	public static String formatKey(String key) {
		if (key == null) {
			return null;
		}
		PatternMatcher matcher = new Perl5Matcher();
		return Util.substitute(matcher, pattern, new Perl5Substitution(" $1 ",
				Perl5Substitution.INTERPOLATE_ALL), filterKey(key),
				Util.SUBSTITUTE_ALL);
	}

	private static String filterKey(String key) {
		return key.replaceAll("\\+", "").replaceAll("&", "").replaceAll("NOT ",
				"").replaceAll("AND ", "").replaceAll("\\-", "").replaceAll(
				"[\\[\\]\\{\\}\\*\\(\\)\\-\\+&]+", "");
	}

	public static <T> List<T> searchUseIK(String key, String dir_path,
			String[] field, Sort sort, int begin, int size,
			ResultMapper<T> mapper) throws IOException {
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			File dir = new File(dir_path);
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			isearcher.setSimilarity(new IKSimilarity());
			Occur[] occur = new Occur[field.length];
			for (int i = 0; i < occur.length; i++) {
				occur[i] = Occur.SHOULD;
			}
			Query query = IKQueryParser.parseMultiField(field, key, occur);
			int localSize = begin + size;
			int beginIdx = begin;
			int endIdx = localSize - 1;
			ScoreDoc[] hits = null;
			if (sort == null) {
				TopDocs topDocs = isearcher.search(query, null, localSize);
				hits = topDocs.scoreDocs;
			}
			else {
				TopFieldDocs tfd = isearcher.search(query, null, localSize,
						sort);
				hits = tfd.scoreDocs;
			}
			if (hits.length < localSize) {
				endIdx = hits.length - 1;
			}
			List<T> rslist = new ArrayList<T>();
			for (int i = beginIdx; i <= endIdx; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				rslist.add(mapper.mapRow(hitDoc));
			}
			return rslist;
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
		}
	}

	public static <T> List<T> search(String key, String dir_path,
			String[] field, Sort sort, int begin, int size,
			ResultMapper<T> mapper) throws ParseException, IOException {
		String _key = formatKey(key);
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			File dir = new File(dir_path);
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			String[] qs = new String[field.length];
			for (int i = 0; i < qs.length; i++) {
				qs[i] = _key;
			}
			Occur[] occur = new Occur[field.length];
			for (int i = 0; i < occur.length; i++) {
				occur[i] = Occur.SHOULD;
			}
			Analyzer analyzer = new HkDefStandardAnalyzer(Version.LUCENE_30);
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_30, _key,
					field, occur, analyzer);
			int localSize = begin + size;
			int beginIdx = begin;
			int endIdx = localSize - 1;
			ScoreDoc[] hits = null;
			if (sort == null) {
				TopDocs topDocs = isearcher.search(query, null, localSize);
				hits = topDocs.scoreDocs;
			}
			else {
				TopFieldDocs tfd = isearcher.search(query, null, localSize,
						sort);
				hits = tfd.scoreDocs;
			}
			if (hits.length < localSize) {
				endIdx = hits.length - 1;
			}
			List<T> rslist = new ArrayList<T>();
			// Iterate through the results:
			for (int i = beginIdx; i <= endIdx; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				rslist.add(mapper.mapRow(hitDoc));
			}
			return rslist;
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
		}
	}

	public static void removeDocFromIndex(String dir_path, String field,
			String text) throws IOException {
		Directory directory = null;
		IndexReader reader = null;
		try {
			directory = FSDirectory.open(new File(dir_path));
			reader = IndexReader.open(directory, false);
			reader.deleteDocuments(new Term(field, text));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (reader != null) {
					reader.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
		}
	}

	public static <T> void indexDocUseIK(boolean create, String dir_path,
			List<T> list, AddDocProc<T> addDocProc) throws IOException {
		IndexWriter iwriter = null;
		Directory directory = null;
		try {
			DataUtil.mkdir(dir_path);
			directory = FSDirectory.open(new File(dir_path));
			iwriter = new IndexWriter(directory, new IKAnalyzer(), create,
					IndexWriter.MaxFieldLength.LIMITED);
			for (T t : list) {
				iwriter.addDocument(addDocProc.buildDoc(t));
			}
			iwriter.optimize();
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			try {
				if (iwriter != null) {
					iwriter.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
		}
	}

	public static <T> void indexDoc(boolean create, String dir_path,
			List<T> list, AddDocProc<T> addDocProc) throws IOException {
		IndexWriter iwriter = null;
		Directory directory = null;
		try {
			DataUtil.mkdir(dir_path);
			directory = FSDirectory.open(new File(dir_path));
			iwriter = new IndexWriter(directory, new HkDefStandardAnalyzer(
					Version.LUCENE_30), create,
					IndexWriter.MaxFieldLength.LIMITED);
			for (T t : list) {
				iwriter.addDocument(addDocProc.buildDoc(t));
			}
			iwriter.optimize();
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			try {
				if (iwriter != null) {
					iwriter.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
		}
	}
}