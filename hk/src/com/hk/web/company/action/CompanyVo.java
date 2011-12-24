package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;

import com.hk.bean.Company;
import com.hk.svr.pub.HkSvrUtil;

public class CompanyVo {
	private Company company;

	private String starRate;

	public String getStarRate() {
		return starRate;
	}

	/**
	 * x~x.3之内是x个星星 x.3~x.7是x.5个星星 x.7~x+1是x+1个星星
	 * 
	 * @param company
	 */
	public CompanyVo(Company company) {
		this.company = company;
		this.starRate = HkSvrUtil.makeCssStarRate(this.company.getTotalScore(),
				this.company.getTotalVote());
	}

	public static void main(String[] args) {
		System.out.println(HkSvrUtil.makeCssStarRate(11, 4));
		System.out.println((double) 11 / 4);
	}

	public Company getCompany() {
		return company;
	}

	public static List<CompanyVo> createVoList(List<Company> list) {
		List<CompanyVo> volist = new ArrayList<CompanyVo>();
		for (Company c : list) {
			CompanyVo vo = new CompanyVo(c);
			volist.add(vo);
		}
		return volist;
	}

	public static CompanyVo createVo(Company company) {
		CompanyVo vo = new CompanyVo(company);
		return vo;
	}
}