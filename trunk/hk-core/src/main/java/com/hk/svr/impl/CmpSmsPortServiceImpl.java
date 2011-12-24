package com.hk.svr.impl;

import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpSmsPort;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpSmsPortService;
import com.hk.svr.company.exception.NoAvailableCmpSmsPortException;

public class CmpSmsPortServiceImpl implements CmpSmsPortService {
	@Autowired
	private QueryManager manager;

	public void batchCreateCmpSmsPort(int portLen, int size) {
		CmpSmsPort o = this.getLast();
		int nbegin = 1001;
		if (o != null) {
			int n = Integer.parseInt(o.getPort()) + 1;
			if (nbegin < n) {
				nbegin = n;
			}
		}
		int end = nbegin + size;
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < portLen; k++) {
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		for (int i = nbegin; i < end; i++) {
			if (String.valueOf(nbegin).length() > portLen) {
				break;
			}
			String port = df.format(i);
			CmpSmsPort cmpSmsPort = new CmpSmsPort();
			cmpSmsPort.setPort(port);
			this.create(cmpSmsPort);
		}
	}

	private void create(CmpSmsPort cmpSmsPort) {
		Query query = manager.createQuery();
		query.addField("companyid", cmpSmsPort.getCompanyId());
		query.addField("port", cmpSmsPort.getPort());
		query.insert(CmpSmsPort.class);
	}

	private CmpSmsPort getLast() {
		Query query = this.manager.createQuery();
		query.setTable(CmpSmsPort.class);
		query.orderByDesc("portid");
		return query.getObject(CmpSmsPort.class);
	}

	public synchronized CmpSmsPort createAvailableCmpSmsPort(long companyId)
			throws NoAvailableCmpSmsPortException {
		CmpSmsPort o2 = this.getCmpSmsPortByCompanyId(companyId);
		if (o2 != null) {
			return o2;
		}
		Query query = manager.createQuery();
		CmpSmsPort o = query.getObject(CmpSmsPort.class, "companyid=?",
				new Object[] { 0 }, "portid asc");
		if (o == null) {
			throw new NoAvailableCmpSmsPortException(
					"no more smsport for company");
		}
		o.setCompanyId(companyId);
		this.updateCmpSmsPort(o);
		return o;
	}

	public CmpSmsPort getCmpSmsPortByPort(String port) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpSmsPort.class, "port=?",
				new Object[] { port });
	}

	public void updateCmpSmsPort(CmpSmsPort cmpSmsPort) {
		Query query = manager.createQuery();
		query.addField("companyid", cmpSmsPort.getCompanyId());
		query.addField("port", cmpSmsPort.getPort());
		query.update(CmpSmsPort.class, "portid=?", new Object[] { cmpSmsPort
				.getPortId() });
	}

	public CmpSmsPort getCmpSmsPortByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpSmsPort.class, "companyid=?",
				new Object[] { companyId });
	}

	public int countAvailableCmpSmsPort() {
		Query query = manager.createQuery();
		return query.count(CmpSmsPort.class, "companyid=?", new Object[] { 0 });
	}
}