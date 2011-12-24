package com.hk.svr.cache.impl;

import com.hk.bean.Tag;
import com.hk.svr.cache.TagCache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class TagCacheImpl implements TagCache {
	private GeneralCacheAdministrator tagCacheMgr;

	public void setTagCacheMgr(GeneralCacheAdministrator tagCacheMgr) {
		this.tagCacheMgr = tagCacheMgr;
	}

	public Tag getTag(long tagId) {
		try {
			Tag tag = (Tag) tagCacheMgr.getFromCache(tagId + "");
			return tag;
		}
		catch (NeedsRefreshException e) {
			this.tagCacheMgr.cancelUpdate(tagId + "");
			return null;
		}
	}

	public void putTag(Tag tag) {
		if (tag == null) {
			return;
		}
		try {
			tagCacheMgr.putInCache(tag.getTagId() + "", tag);
		}
		catch (Exception e) {
			this.tagCacheMgr.cancelUpdate(tag.getTagId() + "");
		}
	}

	public void removeTag(long tagId) {
		this.tagCacheMgr.removeEntry(tagId + "");
	}
}