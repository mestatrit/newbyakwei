package web.epp.mgr.action;

import com.hk.bean.CmpActorSvrRef;
import com.hk.bean.CmpSvr;

public class CmpSvrVo {

	private CmpSvr cmpSvr;

	private boolean selected;

	private CmpActorSvrRef cmpActorSvrRef;

	public CmpSvr getCmpSvr() {
		return cmpSvr;
	}

	public void setCmpSvr(CmpSvr cmpSvr) {
		this.cmpSvr = cmpSvr;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public CmpActorSvrRef getCmpActorSvrRef() {
		return cmpActorSvrRef;
	}

	public void setCmpActorSvrRef(CmpActorSvrRef cmpActorSvrRef) {
		this.cmpActorSvrRef = cmpActorSvrRef;
	}
}