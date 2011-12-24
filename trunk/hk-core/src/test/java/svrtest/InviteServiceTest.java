package svrtest;

import junit.framework.Assert;
import com.hk.bean.Invite;
import com.hk.bean.InviteCode;
import com.hk.bean.UserInviteConfig;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.P;
import com.hk.svr.InviteService;
import com.hk.svr.UserService;
import com.hk.svr.invite.exception.OutOfInviteLimitException;

public class InviteServiceTest extends HkServiceTest {

	private InviteService inviteService;

	private UserService userService;

	public void setInviteService(InviteService inviteService) {
		this.inviteService = inviteService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void testCreateInviteCode() {
		long userId = 1;
		for (int i = 0; i < 10; i++) {
			UserInviteConfig userInviteConfig = this.inviteService
					.getUserInviteConfig(userId);
			if (userInviteConfig == null) {
				userInviteConfig = new UserInviteConfig();
				userInviteConfig.setUserId(1);
				userInviteConfig.setInviteNum(5);
			}
			if (userInviteConfig.getInviteNum() > 0) {
				int old = userInviteConfig.getInviteNum();
				InviteCode inviteCode = this.inviteService
						.createInviteCode(userId);
				P.println(inviteCode.getData());
				userInviteConfig.addInviteNum(-1);
				this.inviteService.saveUserInviteConfig(userInviteConfig);
				userInviteConfig = this.inviteService
						.getUserInviteConfig(userId);
				int current = userInviteConfig.getInviteNum();
				assertEquals(old - 1, current);
			}
			else {
				assertEquals(0, userInviteConfig.getInviteNum());
			}
		}
	}

	public void ttestUpdateInvite() {
		long inviteId = 16;
		Invite o = this.inviteService.getInvite(inviteId);
		this.inviteService.updateInvite(o);
	}

	public void ttestCreateNewInvite() {
		long userId = 1;
		long friendId = 2;
		this.inviteService.acceptNewInvite(userId, friendId,
				Invite.INVITETYPE_LINK, Invite.ADDHKBFLG_N);
	}

	public void ttestCreateInvite() {
		long userId = 1;
		String email = "test04@163.com";
		UserOtherInfo info = this.userService.getUserOtherInfoByeEmail(email);
		if (info != null) {
			Assert.fail("email [ " + email + " ] is already exist");
		}
		try {
			long inviteId = this.inviteService.createInvite(userId, email,
					Invite.INVITETYPE_EMAIL);
			System.out.println("inviteId [ " + inviteId + " ]");
			this.commit();
		}
		catch (OutOfInviteLimitException e) {
			Assert.fail(e.getMessage());
		}
	}
}