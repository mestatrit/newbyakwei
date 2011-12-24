package com.hk.svr.pub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import weibo4j.Configuration;
import weibo4j.IDs;
import weibo4j.Status;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.ImageItem;
import weibo4j.http.RequestToken;

import com.hk.frame.util.DataUtil;

public class SinaUtil {

	/**
	 * 获取当前登录用户的数据
	 * 
	 * @param access_token
	 * @param token_secret
	 * @return
	 * @throws WeiboException
	 *             2010-8-30
	 */
	public static List<Long> getFriendIdList(String access_token,
			String token_secret, String uid) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		IDs ids = weibo.getFriendsIDs(Integer.valueOf(uid));
		List<Long> idList = new ArrayList<Long>();
		int[] idarr = ids.getIDs();
		for (int i = 0; i < idarr.length; i++) {
			idList.add(Long.valueOf(idarr[i]));
		}
		return idList;
	}

	public static User getUser(String access_token, String token_secret,
			String uid) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		return weibo.showUser(uid);
	}

	/**
	 * @param access_token
	 * @param token_secret
	 * @param friendid 用户的新浪微博uid
	 * @throws WeiboException
	 *             2010-9-20
	 */
	public static void followUser(String access_token, String token_secret,
			String friendid) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		weibo.createFriendship(friendid, true);
	}

	public static RequestToken getRequestToken(String back_url)
			throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		return weibo.getOAuthRequestToken(back_url);
	}

	public static AccessToken getAccessToken(String request_token,
			String request_tokenSecret, String oauth_verifier)
			throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		return weibo.getOAuthAccessToken(request_token, request_tokenSecret,
				oauth_verifier);
	}

	public static Status updateStatus(String access_token, String token_secret,
			String content) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		return weibo.updateStatus(content);
	}

	public static Status updateStatus(String access_token, String token_secret,
			String content, File imgFile) throws WeiboException {
		if (imgFile == null) {
			return updateStatus(access_token, token_secret, content);
		}
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		return weibo.uploadStatus(DataUtil.urlEncoder(content), imgFile);
	}

	public static Status updateStatus(String access_token, String token_secret,
			String content, ImageItem imageItem) throws WeiboException {
		if (imageItem == null) {
			return updateStatus(access_token, token_secret, content);
		}
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		return weibo.uploadStatus(DataUtil.urlEncoder(content), imageItem);
	}

	public static void deleteStatus(String access_token, String token_secret,
			long statusId) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		weibo.destroyStatus(statusId);
	}

	public static Status showStatus(String access_token, String token_secret,
			long statusid) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		return weibo.showStatus(statusid);
	}

	public static void main(String[] args) throws Exception {
		// String access_token = "8a6fb4fb8df128c64c22dfb2d936bf30";
		// String token_secret = "f721d7590c36af69ad065a18ddb609dc";
		// String img_url =
		// "http://img05.taobaocdn.com/bao/uploaded/i5/T1LE8GXdBLXXaOHGk._110940.jpg";
		// HttpUtil httpUtil = new HttpUtil();
		// byte[] content = httpUtil.getByteArrayResult(img_url);
		// ImageItem imageItem = new ImageItem("pic", content);
		// // File file = new File("d:/00aaaaaaaaahttp.jpg");
		// Status status = SinaUtil.updateStatus(access_token, token_secret,
		// "发送图片测试", imageItem);
		// P.println(status.getId() + " | " + status.getText());
		// long statusid = 2795433581L;
		// Status status = SinaUtil.showStatus(access_token, token_secret,
		// statusid);
		// P.println(status.getId() + " | " + status.getText());
		// P.println(status.getThumbnail_pic());
		// P.println(status.getBmiddle_pic());
		// P.println(status.getOriginal_pic());
		// User user=SinaUtil.getUser(access_token, token_secret, "1639525917");
	}
}