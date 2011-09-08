package tuxiazi.web.util;

import halo.util.DataUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import weibo4j.Configuration;
import weibo4j.IDs;
import weibo4j.Paging;
import weibo4j.RateLimitStatus;
import weibo4j.Status;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.ImageItem;
import weibo4j.http.RequestToken;

public class SinaUtil {

	public static List<User> getFriendList(String access_token,
			String token_secret, String uid, int page, int size)
			throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		Paging paging = new Paging(page, size);
		return weibo.getFriendsStatuses(uid, paging);
	}

	public static List<User> getFansList(String access_token,
			String token_secret, String uid, int page, int size)
			throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		Paging paging = new Paging(page, size);
		return weibo.getFollowersStatuses(uid, paging);
	}

	/**
	 * 获取当前登录用户的关注人id集合
	 * 
	 * @param access_token
	 * @param token_secret
	 * @return
	 * @throws WeiboException
	 *             2010-8-30
	 */
	public static List<Long> getFriendIdList(String access_token,
			String token_secret, long uid) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		Paging paging = new Paging(1, 5000);
		IDs ids = weibo.getFriendsIDSByUserId(uid + "", paging);
		List<Long> idList = new ArrayList<Long>();
		long[] id = ids.getIDs();
		for (int i = 0; i < id.length; i++) {
			idList.add(id[i]);
		}
		return idList;
	}

	/**
	 * 获取用户的粉丝id集合
	 * 
	 * @param access_token
	 * @param token_secret
	 * @return
	 * @throws WeiboException
	 *             2010-8-30
	 */
	public static List<Long> getFansIdList(String access_token,
			String token_secret, long uid) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		Paging paging = new Paging(1, 5000);
		IDs ids = weibo.getFollowersIDSByUserId(uid + "", paging);
		List<Long> idList = new ArrayList<Long>();
		long[] idarr = ids.getIDs();
		for (int i = 0; i < idarr.length; i++) {
			idList.add(idarr[i]);
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
		weibo.createFriendshipByUserid(friendid + "");
	}

	public static List<Status> getWeiboList(long userid, String access_token,
			String token_secret, int page, int size) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		Paging paging = new Paging(page, size);
		return weibo.getUserTimeline(String.valueOf(userid), paging);
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

	public static RateLimitStatus getRateLimitStatus(String access_token,
			String token_secret) throws WeiboException {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		weibo.setOAuthAccessToken(access_token, token_secret);
		return weibo.getRateLimitStatus();
	}

	public static void main(String[] args) throws WeiboException {
		String access_token = "fe251807d45e2157f7a5d45c8cbf6921";
		String token_secret = "30423d96a6429dbf8957ae7cd9c45773";
//		SinaUtil.getFriendIdList(access_token, token_secret, 1752481465);
		 List<User> list = SinaUtil.getFriendList(access_token, token_secret,
		 "1639525917", 1, 6);
		// for (User o : list) {
		// P.println(o.getScreenName() + " | "
		// + o.getProfileImageURL().toString());
		// }
		// ==========
//		SinaUtil.updateStatus(access_token, token_secret, "test api");
	}
}