package svrtest;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import com.hk.bean.IndexLaba;
import com.hk.bean.Laba;
import com.hk.bean.LabaDelInfo;
import com.hk.bean.LabaTag;
import com.hk.bean.UrlInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;
import com.hk.svr.LabaService;
import com.hk.svr.laba.parser.CopyOfLabaOutPutParser;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.laba.parser.LabaOutPutParser;
import com.hk.svr.pub.Err;

public class LabaServiceTest extends HkServiceTest {
	private LabaService labaService;

	public void setLabaService(LabaService labaService) {
		this.labaService = labaService;
	}

	public void testUpdateUrlLaba() {
		List<Laba> list = this.labaService.getLabaList();
		for (Laba laba : list) {
			LabaOutPutParser parser = new LabaOutPutParser();
			laba.setContent(parser.getText(laba.getContent()));
			if (laba.getLongContent() != null) {
				laba.setLongContent(parser.getText(laba.getLongContent()));
			}
			LabaInPutParser inPutParser = new LabaInPutParser(
					"http://u.huoku.com");
			String content = null;
			if (laba.getLongContent() != null
					&& laba.getLongContent().length() > 0) {
				content = laba.getLongContent();
			}
			else {
				content = laba.getContent();
			}
			content = DataUtil.toTextRow(content);
			LabaInfo info = inPutParser.parse(content);
			laba.setContent(info.getParsedContent());
			laba.setLongContent(info.getLongParsedContent());
			this.labaService.updateLaba(laba.getLabaId(), laba.getContent(),
					laba.getLongContent());
		}
		this.commit();
	}

	public void ttestUpdateNewLaba() {
		List<Laba> list = this.labaService.getLabaList();
		for (Laba laba : list) {
			CopyOfLabaOutPutParser parser = new CopyOfLabaOutPutParser();
			laba.setContent(parser.getText(laba.getContent()));
			if (laba.getLongContent() != null) {
				laba.setLongContent(parser.getText(laba.getLongContent()));
			}
			LabaInPutParser inPutParser = new LabaInPutParser(
					"http://u.huoku.com");
			String content = null;
			if (laba.getLongContent() != null
					&& laba.getLongContent().length() > 0) {
				content = laba.getLongContent();
			}
			else {
				content = laba.getContent();
			}
			content = DataUtil.toTextRow(content);
			P.println(content);
			LabaInfo info = inPutParser.parse(content);
			laba.setContent(info.getParsedContent());
			laba.setLongContent(info.getLongParsedContent());
			this.labaService.updateLaba(laba.getLabaId(), laba.getContent(),
					laba.getLongContent());
		}
	}

	public void ttestgetTagList() {
		long labaId = 100;
		this.labaService.getTagList(labaId, LabaTag.ACCESSIONAL_Y);
	}

	public void ttestGetLaba() {
		long labaId = 12634;
		Laba laba = this.labaService.getLaba(labaId);
		LabaOutPutParser parser = new LabaOutPutParser();
		UrlInfo urlInfo = new UrlInfo();
		urlInfo.setCompanyUrl("/cmp.do?companyId={0}");
		urlInfo.setTagUrl("/tag.do?tagId={0}");
		urlInfo.setUserUrl("/home.do?userId={0}");
		System.out.println(parser.getText(laba.getContent()));
		System.out.println(parser.getHtml(urlInfo, laba.getContent(), 1));
		P.println(parser.getText(laba.getLongContent()));
		P.println(parser.getHtml(urlInfo, laba.getLongContent(), 1));
	}

	public void ttestIndex() {
		int count = this.labaService.count();
		List<Laba> list = this.labaService.getLabaList(0, count);
		List<IndexLaba> idxLabaList = new ArrayList<IndexLaba>();
		for (Laba o : list) {
			LabaOutPutParser parser = new LabaOutPutParser();
			IndexLaba indexLaba = new IndexLaba();
			indexLaba.setContent(parser.getText(o.getContent()));
		}
		labaService.indexLaba(idxLabaList);
	}

	public void ttestRemoveLaba() {
		long labaId = 7826;
		long userId = 1;
		this.labaService.removeLaba(userId, labaId, false);
		this.commit();
	}

	public void ttestCreateLaba() {
		String content = "一个简单的的喇叭侧四 ,, '' ;; [心飞扬的数码城]#3729306 #ak478 @akweii http://www.163.com www.163.com #akwei < >   & \" 都来 http://static.youku.com/v1.0.0091/v/swf/qplayer.swf?VideoIDS=XMTQyODMyMDg4&embedid=MTIzLjEyMS4yMjEuMTA3AjM1NzA4MDIyAnd3dy41Z21lLmNvbQIvc3BhY2UtMjUtZG8tYmxvZy1pZC05MzM4Ni5odG1s&showAd=0  啊哈哈 大厦股份 傻大个打个 个底色是个  申达股份 地方都老了 第三季度颗粒感 额可如果  wemsdg 在地方都ieug http://quwei.techweb.com.cn/archives/category/php http://quwei.techweb.com.cn/archives/category/php http://quwei.techweb.com.cn/archives/category/php http://quwei.techweb.com.cn/archives/category/php 啊哈哈 大厦股份 傻大个打个 个底色是个  申达股份 地方都老了 第三季度颗粒感 额可如果 啊哈哈 大厦股份 傻大个打个 个底色是个  申达股份 地方都老了 第三季度颗粒感 额可如果";
		long userId = 1;
		LabaInPutParser parser = new LabaInPutParser("http://u.huoku.com");
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setUserId(userId);
		labaInfo.setIp("192.168.1.1");
		labaInfo.setSendFrom(Laba.SENDFROM_SMS);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			Assert.fail("validate fail error code [ " + code + " ]");
		}
		this.labaService.createLaba(labaInfo);
	}

	public void ttestReRemoveLaba() {
		LabaDelInfo labaDelInfo = new LabaDelInfo();
		labaDelInfo.setLabaId(272);
		labaDelInfo.setOpuserId(2);
		labaDelInfo.setOptime(1245983519750L);
		this.labaService.reRemoveLaba(labaDelInfo);
	}

	public void ttestParseLaba() {
		String s = "sdfsdfj@yuanwei, sdir #哇哈哈df @yuanwei i'm in#麦当劳, #奇遇花园., d @flyshow http://www.bosee.cn/sdie.do";
		LabaInPutParser impl = new LabaInPutParser("u.huoku.com");
		LabaInfo info = impl.parse(s);
		for (String ss : info.getUrlList()) {
			System.out.println("[" + ss + "]");
		}
		for (String ss : info.getNickNameList()) {
			System.out.println("[" + ss + "]");
		}
		for (String ss : info.getTagValueList()) {
			System.out.println("[" + ss + "]");
		}
		System.out.println(s);
		System.out.println(info.getParsedContent());
	}
}