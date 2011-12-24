package web.pub.util;

import java.util.List;

import com.hk.bean.CmpNav;

public class CmpNavVo {

	public CmpNavVo(CmpNav cmpNav, List<CmpNav> children) {
		this.cmpNav = cmpNav;
		this.children = children;
	}

	private CmpNav cmpNav;

	private List<CmpNav> children;

	public CmpNav getCmpNav() {
		return cmpNav;
	}

	public void setCmpNav(CmpNav cmpNav) {
		this.cmpNav = cmpNav;
	}

	public List<CmpNav> getChildren() {
		return children;
	}

	public void setChildren(List<CmpNav> children) {
		this.children = children;
	}
}