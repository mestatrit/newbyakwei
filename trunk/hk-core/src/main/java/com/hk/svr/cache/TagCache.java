package com.hk.svr.cache;

import com.hk.bean.Tag;

public interface TagCache {
	void removeTag(long tagId);

	Tag getTag(long tagId);

	void putTag(Tag tag);
}