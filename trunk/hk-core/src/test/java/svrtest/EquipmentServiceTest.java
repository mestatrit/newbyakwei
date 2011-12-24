package svrtest;

import java.util.List;

import com.hk.bean.CheckInResult;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.Company;
import com.hk.bean.Equipment;
import com.hk.bean.User;
import com.hk.bean.UserEquipment;
import com.hk.frame.util.P;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.CompanyService;
import com.hk.svr.EquipmentService;
import com.hk.svr.equipment.HandleEquipmentProcessor;
import com.hk.svr.pub.EquipmentConfig;

public class EquipmentServiceTest extends HkServiceTest {

	private EquipmentService equipmentService;

	private HandleEquipmentProcessor handleEquipmentProcessor;

	private CmpCheckInService cmpCheckInService;

	private CompanyService companyService;

	public void setCmpCheckInService(CmpCheckInService cmpCheckInService) {
		this.cmpCheckInService = cmpCheckInService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setHandleEquipmentProcessor(
			HandleEquipmentProcessor handleEquipmentProcessor) {
		this.handleEquipmentProcessor = handleEquipmentProcessor;
	}

	public void setEquipmentService(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	public void testGetEquipment3() {
		long userId = 1;
		for (int i = 1; i <= 9; i++) {
			long eid = i;
			Equipment equipment = EquipmentConfig.getEquipment(eid);
			equipment.setRate(1);
			this.handleEquipmentProcessor.processGet(userId, equipment);
		}
		this.commit();
	}

	public void ttestUseEquipment() throws InterruptedException {
		long userId = 1;
		long enjoyUserId = 3;
		long companyId = 1;
		this.initEquipmentList(userId);
		Thread.sleep(5000);
		this.initEquipmentList(enjoyUserId);
		List<UserEquipment> userEquipments = this.equipmentService
				.getUserEquipmentListByUserId(userId, UserEquipment.USEFLG_N,
						0, 1000);
		for (UserEquipment userEquipment : userEquipments) {
			this.equipmentService
					.useEquipmentToUser(enjoyUserId, userEquipment);
		}
		this.checkIn(companyId);
	}

	public void ttestGetEquipment2() {
		long userId = 1;
		long enjoyUserId = 3;
		long companyId = 1;
		long eid = 2;
		Equipment equipment = EquipmentConfig.getEquipment(eid);
		equipment.setRate(1);
		this.handleEquipmentProcessor.processGet(userId, equipment);
		List<UserEquipment> userEquipments = this.equipmentService
				.getUserEquipmentListByUserId(userId, UserEquipment.USEFLG_N,
						0, 1000);
		for (UserEquipment userEquipment : userEquipments) {
			this.equipmentService
					.useEquipmentToUser(enjoyUserId, userEquipment);
		}
		this.checkIn(companyId);
	}

	public void checkIn(long companyId) {
		Company company = this.companyService.getCompany(companyId);
		CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
		cmpCheckInUserLog.setCompanyId(112);
		cmpCheckInUserLog.setUserId(1);
		cmpCheckInUserLog.setSex(User.SEX_MALE);
		cmpCheckInUserLog.setKindId(1);
		cmpCheckInUserLog.setParentId(1);
		CheckInResult checkInResult = this.cmpCheckInService.checkIn(
				cmpCheckInUserLog, false, company);
		P.println(checkInResult.isCheckInSuccess());
	}

	private void initEquipmentList(long userId) {
		for (int i = 0; i < 100; i++) {
			handleEquipmentProcessor.processGet(userId, EquipmentConfig
					.getEquipments());
		}
	}

	public void ttestGetEquipment() {
		long userId = 1;
		int sum = 0;
		for (int i = 0; i < 100; i++) {
			UserEquipment userEquipment = handleEquipmentProcessor.processGet(
					userId, EquipmentConfig.getEquipments());
			if (userEquipment != null) {
				sum++;
			}
		}
		P.println(sum);
	}
}