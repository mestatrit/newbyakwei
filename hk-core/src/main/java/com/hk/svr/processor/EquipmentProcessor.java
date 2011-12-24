package com.hk.svr.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Company;
import com.hk.bean.Equipment;
import com.hk.bean.User;
import com.hk.bean.UserEquipment;
import com.hk.svr.CompanyService;
import com.hk.svr.EquipmentService;
import com.hk.svr.UserService;
import com.hk.svr.pub.EquipmentConfig;

public class EquipmentProcessor {

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	public List<UserEquipment> getUserEquipmentToCmp(long userId, int begin,
			int size) {
		List<UserEquipment> list = this.equipmentService
				.getUserEquipmentListByUserId(userId, UserEquipment.USEFLG_N,
						begin, size);
		List<UserEquipment> olist = new ArrayList<UserEquipment>();
		for (UserEquipment o : list) {
			Equipment equipment = EquipmentConfig.getEquipment(o.getEid());
			if (equipment.isForCmp()) {
				o.setEquipment(equipment);
				olist.add(o);
			}
		}
		return olist;
	}

	public List<UserEquipment> getUserEquipmentToUser(long userId, int begin,
			int size) {
		List<UserEquipment> list = this.equipmentService
				.getUserEquipmentListByUserId(userId, UserEquipment.USEFLG_N,
						begin, size);
		List<UserEquipment> olist = new ArrayList<UserEquipment>();
		for (UserEquipment o : list) {
			Equipment equipment = EquipmentConfig.getEquipment(o.getEid());
			if (equipment.isForUser()) {
				o.setEquipment(equipment);
				olist.add(o);
			}
		}
		return olist;
	}

	/**
	 * @param userId
	 * @param useflg
	 * @param buildEquipment
	 * @param buildCompany
	 * @param buildEnjoyUser
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-25
	 */
	public List<UserEquipment> getUserEquipmentListByUserId(long userId,
			byte useflg, boolean buildEquipment, boolean buildCompany,
			boolean buildEnjoyUser, int begin, int size) {
		List<UserEquipment> list = this.equipmentService
				.getUserEquipmentListByUserId(userId, useflg, begin, size);
		if (buildEquipment) {
			for (UserEquipment o : list) {
				o.setEquipment(EquipmentConfig.getEquipment(o.getEid()));
			}
		}
		if (buildCompany) {
			List<Long> idList = new ArrayList<Long>();
			for (UserEquipment o : list) {
				idList.add(o.getCompanyId());
			}
			Map<Long, Company> map = this.companyService
					.getCompanyMapInId(idList);
			for (UserEquipment o : list) {
				o.setCompany(map.get(o.getCompanyId()));
			}
		}
		if (buildEnjoyUser) {
			List<Long> idList = new ArrayList<Long>();
			for (UserEquipment o : list) {
				idList.add(o.getEnjoyUserId());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (UserEquipment o : list) {
				o.setEnjoyUser(map.get(o.getEnjoyUserId()));
			}
		}
		return list;
	}
}