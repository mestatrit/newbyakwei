package com.hk.frame.util.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.junit.Test;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;

public class SearchUtilTest {

	String path = "d:/indextest/";

	@Test
	public void testIndexDoc() throws IOException {
		DataUtil.mkdir(path);
		List<String> list = new ArrayList<String>();
		list.add("中国上海天安门手机购买");
		list.add("美国北京纽约手机e71电池购买");
		list.add("也能启动tomcat6的，我的环境下不包含任何tomcat的信息哦。"
				+ " 重命名了jdk的文件夹，记住修改jdk的环境变量哈。"
				+ "你安装的6.0 myeclipse下不能启动，就去查下myeclipse下tomcat的配置，"
				+ "和你jdk的配置是否正确。tomcat的启动");
		list.add("不错啊");
		list.add("我手机不错，哈哈");
		list.add("我的tomcat不能启动，大家帮我看看");
		list.add("tom and lily");
		SearchUtil.indexDocUseIK(true, path, list, new AddDocProc<String>() {

			@Override
			public Document buildDoc(String t) {
				Document doc = new Document();
				doc.add(new Field("name", t, Store.YES, Index.ANALYZED));
				return doc;
			}
		});
	}

	@Test
	public void testSearch() throws IOException {
		String key = "tomcat";
		Sort sort = new Sort();
		sort.setSort(new SortField("name", SortField.SCORE));
		List<String> list = SearchUtil.searchUseIK(key, path,
				new String[] { "name", }, sort, 0, 2,
				new ResultMapper<String>() {

					@Override
					public String mapRow(Document doc) {
						String v = doc.get("name");
						return v;
					}
				});
		for (String s : list) {
			P.println(s);
		}
	}
}