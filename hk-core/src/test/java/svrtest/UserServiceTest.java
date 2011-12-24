package svrtest;

import java.io.File;

import com.hk.bean.Randnum;
import com.hk.bean.RegFrom;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.P;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.LoginException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.svr.user.validate.UserValidate;

public class UserServiceTest extends HkServiceTest {

	private UserService userService;

	public void testUpdateNickName() {
		long userId = 1;
		String nickName = "yuanwei11";
		nickName.replaceAll("　", "");
		nickName = nickName.toLowerCase();
		this.userService.updateNickName(userId, nickName);
		// this.commit();
	}

	public void ttestUpdateHead() {
		File headFile = new File("d:/test/woman.png");
		long userId = 1;
		try {
			this.userService.updateHead(userId, headFile);
			// this.commit();
		}
		catch (ImageException e) {
			e.printStackTrace();
		}
		catch (NotPermitImageFormatException e) {
			e.printStackTrace();
		}
		catch (OutOfSizeException e) {
			e.printStackTrace();
		}
	}

	public void ttestCreateUserfortext() {
		String pwd = "asdasd";
		for (int i = 0; i < 10; i++) {
			P.println(i);
			String input = "testtest@163.com" + i;
			try {
				this.userService.createUser(input, pwd, null);
			}
			catch (EmailDuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (MobileDuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void ttestCreateRegfromUser() {
		long userId = 99999;
		this.userService.createRegfromUser(userId, RegFrom.BOSEE, 0);
	}

	public void ttestUpdateIntro() {
		long userId = 1;
		String intro = "hello哈哈哈哈哈";
		this.userService.updateIntro(userId, intro);
		// this.commit();
	}

	public void ttestUpdateSex() {
		long userId = 1;
		this.userService.updateSex(userId, User.SEX_MALE);
		// this.commit();
	}

	public void tttestUpdateNickName() {
		String mobile = "13552156827";
		UserOtherInfo info = this.userService.getUserOtherInfoByMobile(mobile);
		long userId = info.getUserId();
		String nickName = "akweii";
		this.userService.updateNickName(userId, nickName);
	}

	public void ttestUpdateBirthday() {
		String mobile = "13552156827";
		UserOtherInfo info = this.userService.getUserOtherInfoByMobile(mobile);
		long userId = info.getUserId();
		int month = 12;
		int date = 9;
		if (UserValidate.validateBirthday(month, date) != Err.SUCCESS) {
			throw new RuntimeException("validte error");
		}
		this.userService.updateBirthday(userId, month, date);
		this.commit();
	}

	public void ttestUpdatePwd() {
		String mobile = "13552156827";
		UserOtherInfo info = this.userService.getUserOtherInfoByMobile(mobile);
		long userId = info.getUserId();
		String pwd = "asdasd";
		if (UserValidate.validatePassword(pwd) != Err.SUCCESS) {
			throw new RuntimeException("validte error");
		}
		this.userService.updateNewPwd(userId, pwd);
	}

	public void ttestBindMoible2() {
		int randvalue = 4190;
		Randnum o = this.userService.getRandnumByRandvalue(randvalue);
		System.out.println(o.getUserId());
		System.out.println(o.getInuse());
	}

	public void ttestLogin() throws LoginException {
		String input = "akwei";
		String password = "asdasd";
		String ip = "123.121.208.108";
		this.userService.loginByNickName(input, password, ip);
		this.commit();
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}