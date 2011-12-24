package web.epp.action;

import java.util.List;

import com.hk.bean.CmpBbs;
import com.hk.bean.CmpBbsKind;

public class CmpBbsKindVo {

	private CmpBbsKind cmpBbsKind;

	private List<CmpBbs> bbsList;

	private boolean hasMore;

	public CmpBbsKindVo(CmpBbsKind cmpBbsKind, List<CmpBbs> bbsList) {
		this.cmpBbsKind = cmpBbsKind;
		this.bbsList = bbsList;
	}

	public CmpBbsKind getCmpBbsKind() {
		return cmpBbsKind;
	}

	public void setCmpBbsKind(CmpBbsKind cmpBbsKind) {
		this.cmpBbsKind = cmpBbsKind;
	}

	public List<CmpBbs> getBbsList() {
		return bbsList;
	}

	public void setBbsList(List<CmpBbs> bbsList) {
		this.bbsList = bbsList;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
}