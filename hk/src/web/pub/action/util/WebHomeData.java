package web.pub.action.util;

import java.util.HashMap;
import java.util.Map;

import com.hk.bean.CmpNav;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

/**
 * 首页要显示的数据
 * 
 * @author akwei
 */
public abstract class WebHomeData {

	private static final Map<Integer, WebHomeData> map = new HashMap<Integer, WebHomeData>();
	static {
		map.put(CmpNav.REFFUNC_BOX, new BoxData());
		map.put(CmpNav.REFFUNC_COUPON, new CouponData());
		map.put(CmpNav.REFFUNC_BBS, new BbsData());
		map.put(CmpNav.REFFUNC_MSG, new MsgData());
		map.put(CmpNav.REFFUNC_MAP, new MapData());
		map.put(CmpNav.REFFUNC_SINGLECONTENT, new ArticleData());
		map.put(CmpNav.REFFUNC_SINGLECONTENT, new ArticleData());
	}

	public abstract void loadData(HkRequest req, HkResponse resp);
}