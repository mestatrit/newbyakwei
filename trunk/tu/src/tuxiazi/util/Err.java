package tuxiazi.util;

public interface Err {

	int SUCCESS = 0;

	/**
	 * 没有该新浪用户
	 */
	int API_NO_SINA_USER = 1;

	/**
	 * 新浪服务出错
	 */
	int API_SINA_ERR = 2;

	/**
	 * 用户授权信息错误
	 */
	int API_SINA_USER_TOKEN_ERR = 3;

	/**
	 * 系统出错
	 */
	int API_SYS_ERR = 4;

	/**
	 * 评论内容不能超过200字
	 */
	int PHOTOCMT_CONTENT_ERROR = 5;

	/**
	 * 图片不存在
	 */
	int PHOTO_NOTEXIST = 6;

	/**
	 * 没有权限操作对象
	 */
	int OP_NOPOWER = 7;

	/**
	 * 评论不存在
	 */
	int PHOTOCMT_NOTEXIST = 8;

	/**
	 * 用户不存在
	 */
	int USER_NOTEXIST = 9;

	/**
	 * 邀请失败
	 */
	int INVITE_ERROR = 10;

	/**
	 * 上传的图片文件不存在
	 */
	int PHOTO_FILE_NOTEXIST = 11;
}