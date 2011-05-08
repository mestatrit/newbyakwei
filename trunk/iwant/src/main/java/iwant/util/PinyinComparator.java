package iwant.util;

import java.util.Comparator;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinyinComparator implements Comparator<String> {

	private static PinyinComparator pinyinComparator = new PinyinComparator();

	public static PinyinComparator getInstance() {
		return pinyinComparator;
	}

	private PinyinComparator() {
	}

	@Override
	public int compare(String o1, String o2) {
		String key1 = o1.toString();
		String key2 = o2.toString();
		for (int i = 0; i < key1.length() && i < key2.length(); i++) {
			int codePoint1 = key1.charAt(i);
			int codePoint2 = key2.charAt(i);
			if (Character.isSupplementaryCodePoint(codePoint1)
					|| Character.isSupplementaryCodePoint(codePoint2)) {
				i++;
			}
			if (codePoint1 != codePoint2) {
				if (Character.isSupplementaryCodePoint(codePoint1)
						|| Character.isSupplementaryCodePoint(codePoint2)) {
					return codePoint1 - codePoint2;
				}
				String pinyin1 = pinyin((char) codePoint1);
				String pinyin2 = pinyin((char) codePoint2);
				if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
					if (!pinyin1.equals(pinyin2)) {
						return pinyin1.compareTo(pinyin2);
					}
				}
				else {
					return codePoint1 - codePoint2;
				}
			}
		}
		return key1.length() - key2.length();
	}

	private String pinyin(char c) {
		String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
		if (pinyins == null) {
			return null; // 如果转换结果为空，则返回null
		}
		return pinyins[0]; // 如果为多音字返回第一个音节
	}
}
