package com.hk.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * @author akwei
 */
@Table(name = "invitecodedata")
public class InviteCodeData {

	/**
	 * 预先定义要使用的字符数组，所有位置的字符都使用这里的
	 */
	private static char[] base = new char[] { 'k', '3', 'h', 'c', '0', 'p',
			'q', '6', '1', 'v', 'i', '5', 'a', 'u', 't', 'j', '4', 's', 'z',
			'd', 'o', 'w', '7', 'r', '2', 'l', 'g', 'b', 'm', 'f', 'y', '9',
			'n', '8', 'x', 'e' };

	/**
	 * 保存到数据库中的id
	 */
	@Id
	private int oid;

	/**
	 * 保存到数据库中的下标字符串例如 data=" 1,4,5,6"
	 */
	@Column
	private String data;

	/**
	 * 存储每个字符位置所使用的字符数组(由于每个位置的字符数组都会不同，所以通过map单独存储)<br/>
	 */
	private static final Map<Integer, char[]> basemap = new HashMap<Integer, char[]>();
	/**
	 * 此代码块的功能就是当class加载的时候，初始化basemap
	 */
	static {
		List<String> list = new ArrayList<String>();
		// 把预先定义的字符放入一个集合当中
		for (int i = 0; i < base.length; i++) {
			list.add(String.valueOf(base[i]));
		}
		// 这个for循环的作用是为了生成不同的字符数组所用
		// 实现效果例如:
		// 第一个数组为 abcdefg
		// 那么第二个数字就是bcdefga
		// 第三个就是cdefgab
		// 第四个就是defgabc
		// 第五个就是efgabcd
		// ......
		// 依次循环
		for (int i = 0; i < base.length; i++) {
			// 获取基础字符集合的前i个值，组成一个新的集合endlist
			List<String> endlist = new ArrayList<String>(list.subList(0, i));
			// 获取基础字符集合的从i到结尾的所有字符集合，组成一个新的集合reslist
			List<String> reslist = new ArrayList<String>(list.subList(i, list
					.size()));
			// 然后把endlist放入添加到reslist的末尾
			// reslist就是我们需要的其中一个字符集合
			reslist.addAll(endlist);
			// 通过StringBuffer把字符集合中的字符组成一个字符串，然后生一个一个字符数组
			StringBuffer sb = new StringBuffer();
			for (String s : reslist) {
				sb.append(s);
			}
			char[] base_i = sb.toString().toCharArray();
			basemap.put(i, base_i);
		}
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 把data的值转化为一个int[],此int[]中存储的就是每个字符最后使用过的字符数组的下标<br/>
	 * 例如:<br/>
	 * data=1,2,4,2<br/>
	 * basemap中的key-value <br/>
	 * key:0 value:[a,b,c,d,e,f,g]<br/>
	 * key:1 value:[b,c,d,e,f,g,a]<br/>
	 * key:2 value:[c,d,e,f,g,a,b]<br/>
	 * key:3 value:[d,e,f,g,a,b,c]<br/>
	 * 那么最后一次使用的短网址关键字就是bdgf，每个字符都是与basemap中相应位置的数组对应的
	 * 
	 * @return 把data的值转化为一个int[]
	 */
	private int[] getDataList() {
		String[] s = this.data.split(",");
		int[] arr = new int[s.length];
		int i = 0;
		for (String ss : s) {
			arr[i++] = Integer.parseInt(ss);
		}
		return arr;
	}

	/**
	 * 根据最后一次的下标数组int[]，计算获得一个可以使用的int[]<br/>
	 * 例如<br/>
	 * data=1,2,4,2<br/>
	 * basemap中的key-value <br/>
	 * key:0 value:[a,b,c,d,e,f,g]<br/>
	 * key:1 value:[b,c,d,e,f,g,a]<br/>
	 * key:2 value:[c,d,e,f,g,a,b]<br/>
	 * key:3 value:[d,e,f,g,a,b,c]<br/>
	 * key:4 value:[e,f,g,a,b,c,d]<br/>
	 * 通过此方法，返回 一个数字数组[1,2,4,3]<br/>
	 * 如果data=1,2,4,6<br/>
	 * 通过此方法，返回 一个数字数组[1,2,5,0]<br/>
	 * 如果data=6,6,6,6<br/>
	 * 由于4位的字符已经用完，需要升级位数到5为，返回 一个数字数组[0,0,0,0,0]<br/>
	 * 运行此方法，data的值会相应的变化
	 * 
	 * @return 返回一个可以使用的int[]下标数组
	 */
	private int[] getNextDataList() {
		// 获得最后使用的下标数组
		int[] arr = this.getDataList();
		// 此参数的意义是是否数组长度发生了变化
		boolean is_arr_length_changed = false;
		for (int i = arr.length - 1; i >= 0; i--) {
			int res = this.add(i, arr[i]);
			arr[i] = res;
			if (res == 0) {
				// 如果==0，说明下标已经到了最后一位,+1后会返回0，(类似于1+9=10,结果变为2位数，需要进位)
				is_arr_length_changed = true;
				// 变为0后，前一位下标肯定要进行变化，所以继续循环，把前一位下标也+1
				continue;
			}
			// 如果>0，说明不需要进位
			if (res > 0) {
				is_arr_length_changed = false;
				break;
			}
		}
		StringBuilder sb = new StringBuilder();
		// 如果int[]数组长度没有变化，则直接给data赋值
		if (!is_arr_length_changed) {
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i]).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			this.data = sb.toString();
			return arr;
		}
		// 如果int[]数组长度改变了,那么就用一个新的数组，新数组的值为arr中的值，最后一位值为0
		int[] arr2 = new int[arr.length + 1];
		for (int i = 0; i < arr2.length; i++) {
			arr2[i] = 0;
			sb.append(arr2[i]).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		this.data = sb.toString();
		return arr2;
	}

	/**
	 * 获得一个可以用的缩短后的url关键字<br/>
	 * 例如<br/>
	 * data=1,2,4,2<br/>
	 * basemap中的key-value <br/>
	 * key:0 value:[a,b,c,d,e,f,g]<br/>
	 * key:1 value:[b,c,d,e,f,g,a]<br/>
	 * key:2 value:[c,d,e,f,g,a,b]<br/>
	 * key:3 value:[d,e,f,g,a,b,c]<br/>
	 * key:4 value:[e,f,g,a,b,c,d]<br/>
	 * 通过此方法，返回 一个数字数组[1,2,4,3]，通过这个数组到basemap中获得相应位置的字符，返回值为对应[1,2,4,3]的 bdgg<br/>
	 * 如果data=1,2,4,6<br/>
	 * 通过此方法，返回 一个数字数组[1,2,5,0]，返回值为对应[1,2,5,0]的 bdae<br/>
	 * 如果data=6,6,6,6<br/>
	 * 由于4位的字符已经用完，需要升级位数到5为，返回 一个数字数组[0,0,0,0,0]，返回值为对应[0,0,0,0,0]的 abcde<br/>
	 * 
	 * @return 返回的字符串就是我们需要的缩短url后的关键字
	 */
	public String getNextShortKey() {
		int[] arr = this.getNextDataList();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			// sb.append(base[arr[i]]);
			char[] ch = basemap.get(i);
			sb.append(ch[arr[i]]);
		}
		return sb.toString();
	}

	/**
	 * 字符数组下标+1，如果到尾部，则归0 <br/>
	 * 例如<br/>
	 * basemap中的key-value <br/>
	 * key:0 value:[a,b,c,d,e,f,g]<br/>
	 * key:1 value:[b,c,d,e,f,g,a]<br/>
	 * key:2 value:[c,d,e,f,g,a,b]<br/>
	 * key:3 value:[d,e,f,g,a,b,c]<br/>
	 * key:4 value:[e,f,g,a,b,c,d]<br/>
	 * data=1,2,4,6<br/>
	 * 1对应basemap中key=0时的数组中字符b<br/>
	 * 2对应basemap中key=1时的数组中字符c<br/>
	 * 4对应basemap中key=2时的数组中字符g<br/>
	 * 6对应basemap中key=3时的数组中字符c<br/>
	 * add(0,1)=2<br/>
	 * 参数0代表1,2,4,6中的下标0<br/>
	 * 参数1代表1,2,4,6中的下标0的值1，也就是key=0时的数组[a,b,c,d,e,f,g]的下标1<br/>
	 * 返回值2代表1下标后移一位后的结果,也就是key=0时的数组[a,b,c,d,e,f,g]的下标2<br/>
	 * add(3,6)=0<br/>
	 * 返回值0代表下标已经在最后一位了，如果再次后移，就要让下标移到开始位置，也就是key:4 value:[e,f,g,a,b,c,d]中<br/>
	 * 下标6代表数组中最后一位，不能再次后移，只能回到0的位置，(简单的说就是1+9=10，个位肯定是0，需要进位)
	 * 
	 * @param digits 字符所在位数 例如 abcd c所在第2位
	 * @param i 字符数组下标
	 * @return
	 */
	private int add(int digits, int i) {
		char[] ch = basemap.get(digits);
		int res = (i + 1) % ch.length;
		return res;
	}

	public static void main(String[] args) {
		InviteCodeData o = getShortUrlDataFromDB();
		System.out.println(o.getNextShortKey());
		System.out.println(o.getData());
		// 例如从数据库取出
	}

	/**
	 * 假设是从数据库中取出
	 * 
	 * @return
	 */
	private static InviteCodeData getShortUrlDataFromDB() {
		InviteCodeData o = new InviteCodeData();
		o.setOid(1);
		o.setData("5,2,1,35");
		// o.setData("35,35,35");
		return o;
	}
}