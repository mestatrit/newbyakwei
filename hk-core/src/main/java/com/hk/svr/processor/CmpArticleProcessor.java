package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpArticle;
import com.hk.bean.CmpArticleContent;
import com.hk.bean.CmpArticleGroup;
import com.hk.bean.CmpArticleNavPink;
import com.hk.bean.CmpArticleTag;
import com.hk.bean.CmpArticleTagRef;
import com.hk.bean.CmpFile;
import com.hk.bean.CmpPageBlock;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.util.page.SimplePage;
import com.hk.svr.CmpArticleService;
import com.hk.svr.CmpArticleTagService;
import com.hk.svr.CmpFileService;
import com.hk.svr.CmpModService;
import com.hk.svr.pub.ImageConfig;

public class CmpArticleProcessor {

	@Autowired
	private CmpArticleService cmpArticleService;

	@Autowired
	private CmpFileProcessor cmpFileProcessor;

	@Autowired
	private CmpFileService cmpFileService;

	@Autowired
	private CmpModService cmpModService;

	@Autowired
	private CmpArticleTagService cmpArticleTagService;

	/**
	 * 大于200k的文件将不能被上传
	 * 
	 * @param cmpArticle
	 * @param cmpArticleContent
	 * @param files
	 *            2010-5-10
	 * @throws IOException
	 */
	public CmpArticleUploadFileResult createCmpArticle(CmpArticle cmpArticle,
			CmpArticleContent cmpArticleContent, File[] files, int topIdx,
			List<String> tagDataList) throws Exception {
		List<CmpFile> list = null;
		CmpArticleUploadFileResult cmpArticleUploadFileResult = null;
		if (files != null) {
			cmpArticleUploadFileResult = this.saveFile(cmpArticle
					.getCompanyId(), files, topIdx);
			list = cmpArticleUploadFileResult.getCmpFileList();
		}
		this.cmpArticleService.createCmpArticle(cmpArticle, cmpArticleContent);
		// 处理标签
		this.processorCmpArticleTag(cmpArticle, tagDataList);
		if (list != null) {
			for (CmpFile o : list) {
				o.setArticleOid(cmpArticle.getOid());
				this.cmpFileService.updateCmpFile(o);
				if (cmpArticle.getFilepath() == null
						&& o.getFileflg() == CmpFile.FILEFLG_IMG) {
					cmpArticle.setFilepath(o.getPath());
					this.cmpArticleService.updateCmpArticle(cmpArticle, null);
				}
			}
		}
		if (cmpArticleUploadFileResult != null
				&& cmpArticleUploadFileResult.isFileUploadError()) {
			throw new Exception("imgupdateerror");
		}
		return cmpArticleUploadFileResult;
	}

	private void processorCmpArticleTag(CmpArticle cmpArticle,
			List<String> tagDataList) {
		// 删除与此文章有关的标签关系
		this.cmpArticleTagService
				.deleteCmpArticleTagRefByCompanyIdAndArticleId(cmpArticle
						.getCompanyId(), cmpArticle.getOid());
		// 根据数据创建新的关系
		for (String s : tagDataList) {
			CmpArticleTag cmpArticleTag = new CmpArticleTag();
			cmpArticleTag.setCompanyId(cmpArticle.getCompanyId());
			cmpArticleTag.setName(s);
			long tagId = 0;
			if (this.cmpArticleTagService.createCmpArticleTag(cmpArticleTag)) {
				tagId = cmpArticleTag.getTagId();
			}
			else {
				cmpArticleTag = this.cmpArticleTagService
						.getCmpArticleTagByCompanyIdAndName(cmpArticle
								.getCompanyId(), s);
				if (cmpArticleTag != null) {
					tagId = cmpArticleTag.getTagId();
				}
			}
			if (tagId > 0) {
				CmpArticleTagRef ref = new CmpArticleTagRef();
				ref.setCompanyId(cmpArticle.getCompanyId());
				ref.setArticleId(cmpArticle.getOid());
				ref.setTagId(tagId);
				this.cmpArticleTagService.createCmpArticleTagRef(ref);
			}
		}
	}

	/**
	 * 大于200k的文件将不能被上传
	 * 
	 * @param cmpArticle
	 * @param cmpArticleContent
	 * @param files
	 *            2010-5-10
	 */
	public CmpArticleUploadFileResult updateCmpArticle(CmpArticle cmpArticle,
			CmpArticleContent cmpArticleContent, File[] files, int topIdx,
			List<String> tagDataList) throws Exception {
		List<CmpFile> list = null;
		CmpArticleUploadFileResult cmpArticleUploadFileResult = null;
		if (files != null) {
			cmpArticleUploadFileResult = this.saveFile(cmpArticle
					.getCompanyId(), files, topIdx);
			list = cmpArticleUploadFileResult.getCmpFileList();
		}
		if (list != null) {
			for (CmpFile o : list) {
				o.setArticleOid(cmpArticle.getOid());
				this.cmpFileService.updateCmpFile(o);
				if (cmpArticle.getFilepath() == null
						&& o.getFileflg() == CmpFile.FILEFLG_IMG) {
					cmpArticle.setFilepath(o.getPath());
				}
			}
		}
		this.cmpArticleService.updateCmpArticle(cmpArticle, cmpArticleContent);
		this.processorCmpArticleTag(cmpArticle, tagDataList);
		if (cmpArticleUploadFileResult != null
				&& cmpArticleUploadFileResult.isFileUploadError()) {
			throw new Exception("imgupdateerror");
		}
		return cmpArticleUploadFileResult;
	}

	private CmpArticleUploadFileResult saveFile(long companyId, File[] files,
			int topIdx) {
		CmpArticleUploadFileResult cmpArticleUploadFileResult = new CmpArticleUploadFileResult();
		int size = files.length;
		if (size > 5) {
			size = 5;
		}
		List<CmpFile> list = new ArrayList<CmpFile>();
		for (int i = 0; i < files.length; i++) {
			try {
				boolean isImage = DataUtil.isImage(files[i]);
				CmpFile cmpFile = new CmpFile();
				cmpFile.setCompanyId(companyId);
				if (isImage) {
					cmpFile.setFileflg(CmpFile.FILEFLG_IMG);
				}
				else {
					cmpFile.setFileflg(CmpFile.FILEFLG_SWF);
				}
				if (topIdx == i) {
					cmpFile.setTopflg(CmpFile.TOPFLG_Y);
				}
				else {
					cmpFile.setTopflg(CmpFile.TOPFLG_N);
				}
				this.cmpFileProcessor.createCmpFile(cmpFile, files[i]);
				list.add(cmpFile);
			}
			catch (ImageException e) {
				cmpArticleUploadFileResult.setFileUploadError(true);
			}
			catch (NotPermitImageFormatException e) {
				cmpArticleUploadFileResult.addFmtErrorNum(1);
			}
			catch (OutOfSizeException e) {
				cmpArticleUploadFileResult.addOutOfSizeErrorNum(1);
			}
			catch (IOException e) {
				cmpArticleUploadFileResult.setFileUploadError(true);
			}
		}
		cmpArticleUploadFileResult.setCmpFileList(list);
		return cmpArticleUploadFileResult;
	}

	public void deleteCmpArticleFile(long cmpArticleOid, long cmpFileOid) {
		CmpArticle cmpArticle = this.cmpArticleService
				.getCmpArticle(cmpArticleOid);
		CmpFile cmpFile = this.cmpFileService.getCmpFile(cmpFileOid);
		if (cmpFile == null) {
			return;
		}
		this.cmpFileProcessor.deleteCmpFile(cmpFileOid);
		if (cmpArticle != null) {
			if (cmpArticle.getFilepath() != null
					&& cmpArticle.getFilepath().equals(cmpFile.getPath())) {
				cmpArticle.setFilepath(null);
				this.cmpArticleService.updateCmpArticle(cmpArticle, null);
			}
		}
	}

	public void deleteCmpArticle(long companyId, long oid) {
		this.cmpModService.deleteCmpArticleBlockByCompanyIdAndArticleId(
				companyId, oid);
		this.cmpArticleTagService
				.deleteCmpArticleTagRefByCompanyIdAndArticleId(companyId, oid);
		List<CmpFile> list = this.cmpFileService
				.getCmpFileListByCompanyIdAndArticleOid(companyId, oid);
		for (CmpFile o : list) {
			this.cmpFileProcessor.deleteCmpFile(o.getOid());
		}
		this.cmpArticleService.deleteCmpArticle(companyId, oid);
	}

	public void setMainFile(long cmpArticleOid, long cmpFileOid) {
		CmpArticle cmpArticle = this.cmpArticleService
				.getCmpArticle(cmpArticleOid);
		CmpFile cmpFile = this.cmpFileService.getCmpFile(cmpFileOid);
		if (cmpFile == null || cmpArticle == null) {
			return;
		}
		if (cmpFile.isImageShow()) {
			cmpArticle.setFilepath(cmpFile.getPath());
			this.cmpArticleService.updateCmpArticle(cmpArticle, null);
		}
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdAndCmpNavOid(
			long companyId, long cmpNavOid, boolean buildBlock,
			boolean buildGroup, String title, int begin, int size) {
		List<CmpArticle> list = this.cmpArticleService
				.getCmpArticleListByCompanyIdAndCmpNavOid(companyId, cmpNavOid,
						title, begin, size);
		if (buildBlock) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpArticle o : list) {
				idList.add(o.getPage1BlockId());
			}
			Map<Long, CmpPageBlock> map = this.cmpModService
					.getCmpPageBlockInMap(idList);
			for (CmpArticle o : list) {
				o.setPage1Block(map.get(o.getPage1BlockId()));
			}
		}
		if (buildGroup) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpArticle o : list) {
				idList.add(o.getGroupId());
			}
			Map<Long, CmpArticleGroup> map = this.cmpArticleService
					.getCmpArticleGroupMapByCompanyIdInId(companyId, idList);
			for (CmpArticle o : list) {
				o.setCmpArticleGroup(map.get(o.getGroupId()));
			}
		}
		return list;
	}

	public List<CmpArticleTagRef> getCmpArticleTagRefListByCompanyIdAndArticleId(
			long companyId, long articleId, boolean buildCmpArticleTag,
			int begin, int size) {
		List<CmpArticleTagRef> list = this.cmpArticleTagService
				.getCmpArticleTagRefListByCompanyIdAndArticleId(companyId,
						articleId, begin, size);
		if (buildCmpArticleTag) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpArticleTagRef o : list) {
				idList.add(o.getTagId());
			}
			Map<Long, CmpArticleTag> map = this.cmpArticleTagService
					.getCmpArticleTagMapByCompanyIdInId(companyId, idList);
			for (CmpArticleTagRef o : list) {
				o.setCmpArticleTag(map.get(o.getTagId()));
			}
		}
		return list;
	}

	public List<CmpArticleTagRef> getCmpArticleTagRefListByCompanyIdAndTagId(
			long companyId, long tagId, boolean buildCmpArticle, int begin,
			int size) {
		List<CmpArticleTagRef> list = this.cmpArticleTagService
				.getCmpArticleTagRefListByCompanyIdAndTagId(companyId, tagId,
						begin, size);
		if (buildCmpArticle) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpArticleTagRef o : list) {
				idList.add(o.getArticleId());
			}
			Map<Long, CmpArticle> map = this.cmpArticleService
					.getCmpArticleMapByCompanyIdAndInId(companyId, idList);
			for (CmpArticleTagRef o : list) {
				o.setCmpArticle(map.get(o.getArticleId()));
			}
		}
		return list;
	}

	private static String CMP_IDX_ARTICLEID = "01";

	private static String CMP_IDX_TITLE = "02";

	private static String CMP_IDX_CONTENT = "03";

	/**
	 * 对企业发布的文章进行索引
	 * 
	 * @param companyId
	 * @return
	 *         2010-7-6
	 */
	public void indexCmpArticleListByComapnyIdAndKey(long companyId) {
		int page = 1;
		IndexWriter iwriter = null;
		List<CmpArticle> list = null;
		CmpArticleContent cmpArticleContent = null;
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			File dir = new File(ImageConfig.getCmpIdxDir(companyId));
			Directory directory = FSDirectory.open(dir);
			iwriter = new IndexWriter(directory, analyzer, true,
					new IndexWriter.MaxFieldLength(2500000));
			while (page > 0) {
				SimplePage simplePage = new SimplePage(1000, page);
				list = this.cmpArticleService
						.getCmpArticleListByCompanyIdForIdx(companyId,
								simplePage.getBegin(), simplePage.getSize());
				if (list.size() == 0) {
					break;
				}
				page++;
				List<Long> idList = new ArrayList<Long>();
				for (CmpArticle o : list) {
					idList.add(o.getOid());
				}
				// 获取文章内容
				Map<Long, CmpArticleContent> contentmap = this.cmpArticleService
						.getCmpArticleContentMapInId(idList);
				// 把文章id标题内容写入索引
				for (CmpArticle o : list) {
					Document doc = new Document();
					doc.add(new Field(CMP_IDX_ARTICLEID, String.valueOf(o
							.getOid()), Field.Store.YES,
							Field.Index.NOT_ANALYZED));
					doc.add(new Field(CMP_IDX_TITLE, o.getTitle(),
							Field.Store.NO, Field.Index.ANALYZED));
					cmpArticleContent = contentmap.get(o.getOid());
					if (cmpArticleContent != null) {
						doc.add(new Field(CMP_IDX_CONTENT, o.getTitle(),
								Field.Store.NO, Field.Index.ANALYZED));
					}
					iwriter.addDocument(doc);
				}
			}
			iwriter.optimize();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (iwriter != null) {
					iwriter.close();
				}
			}
			catch (CorruptIndexException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public CmpArticleSearchResult searchCmpArticleListByComapnyIdAndKey(
			long companyId, String key, int begin, int size) {
		String _key = DataUtil.getSearchValue(key);
		if (DataUtil.isEmpty(_key)) {
			return null;
		}
		List<Long> idList = new ArrayList<Long>();
		IndexSearcher isearcher = null;
		Directory directory = null;
		CmpArticleSearchResult cmpArticleSearchResult = new CmpArticleSearchResult();
		try {
			File dir = new File(ImageConfig.getCmpIdxDir(companyId));
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			String[] qs = new String[] { _key, _key };
			org.apache.lucene.search.Query squery = MultiFieldQueryParser
					.parse(Version.LUCENE_30, qs, new String[] { CMP_IDX_TITLE,
							CMP_IDX_CONTENT }, analyzer);
			Sort sort = new Sort();
			sort.setSort(new SortField(CMP_IDX_ARTICLEID, SortField.INT, true));
			int localSize = begin + size;
			int beginIdx = begin;
			int endIdx = localSize - 1;
			TopFieldDocs tfd = isearcher.search(squery, null, localSize, sort);
			ScoreDoc[] hits = tfd.scoreDocs;
			cmpArticleSearchResult.setTotalCount(hits.length);
			if (hits.length < localSize) {
				endIdx = hits.length - 1;
			}
			// Iterate through the results:
			for (int i = beginIdx; i <= endIdx; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				long articleId = Long.parseLong(hitDoc.get(CMP_IDX_ARTICLEID));
				idList.add(articleId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		Map<Long, CmpArticle> map = this.cmpArticleService
				.getCmpArticleMapByCompanyIdAndInId(companyId, idList);
		List<CmpArticle> list = new ArrayList<CmpArticle>();
		for (Long l : idList) {
			list.add(map.get(l));
		}
		cmpArticleSearchResult.setCmpArticles(list);
		return cmpArticleSearchResult;
	}

	public List<CmpArticleNavPink> getCmpArticleNavPinkByCompanyIdAndNavId(
			long companyId, long navId, boolean buildArticle,
			boolean buildContent) {
		List<CmpArticleNavPink> list = this.cmpArticleService
				.getCmpArticleNavPinkByCompanyIdAndNavId(companyId, navId);
		if (buildArticle) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpArticleNavPink o : list) {
				idList.add(o.getArticleId());
			}
			Map<Long, CmpArticle> map = this.cmpArticleService
					.getCmpArticleMapByCompanyIdAndInId(companyId, idList);
			for (CmpArticleNavPink o : list) {
				o.setCmpArticle(map.get(o.getArticleId()));
			}
		}
		if (buildContent) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpArticleNavPink o : list) {
				idList.add(o.getArticleId());
			}
			Map<Long, CmpArticleContent> map = this.cmpArticleService
					.getCmpArticleContentMapInId(idList);
			for (CmpArticleNavPink o : list) {
				if (o.getCmpArticle() != null) {
					o.getCmpArticle().setCmpArticleContent(
							map.get(o.getArticleId()));
				}
			}
		}
		return list;
	}
}