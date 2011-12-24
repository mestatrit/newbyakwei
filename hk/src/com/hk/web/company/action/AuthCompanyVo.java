package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hk.bean.AuthCompany;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;

public class AuthCompanyVo {

	private AuthCompany authCompany;

	public AuthCompany getAuthCompany() {
		return authCompany;
	}

	public void setAuthCompany(AuthCompany authCompany) {
		this.authCompany = authCompany;
	}

	public static AuthCompanyVo createVo(AuthCompany authCompany) {
		return createVo(authCompany, true, true);
	}

	public static AuthCompanyVo createVo(AuthCompany authCompany,
			boolean needCompany, boolean needUser) {
		if (needCompany) {
			CompanyService companyService = (CompanyService) HkUtil
					.getBean("companyService");
			authCompany.setCompany(companyService.getCompany(authCompany
					.getCompanyId()));
		}
		if (needUser) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			authCompany.setUser(userService.getUser(authCompany.getUserId()));
		}
		AuthCompanyVo vo = new AuthCompanyVo();
		vo.setAuthCompany(authCompany);
		return vo;
	}

	public static List<AuthCompanyVo> createVoList(List<AuthCompany> list) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		List<Long> userIdList = new ArrayList<Long>();
		List<Long> cidList = new ArrayList<Long>();
		for (AuthCompany o : list) {
			userIdList.add(o.getUserId());
			cidList.add(o.getCompanyId());
		}
		Map<Long, User> userMap = userService.getUserMapInId(userIdList);
		Map<Long, Company> cmap = companyService.getCompanyMapInId(cidList);
		for (AuthCompany o : list) {
			o.setUser(userMap.get(o.getUserId()));
			o.setCompany(cmap.get(o.getCompanyId()));
		}
		List<AuthCompanyVo> volist = new ArrayList<AuthCompanyVo>();
		for (AuthCompany o : list) {
			volist.add(createVo(o, false, false));
		}
		return volist;
	}
}