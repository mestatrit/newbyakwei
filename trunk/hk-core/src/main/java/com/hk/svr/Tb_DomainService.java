package com.hk.svr;

import java.util.List;

import com.hk.bean.taobao.Tb_Domain;

public interface Tb_DomainService {

	/**
	 * @param tbDomain
	 * @return true:创建成功,false:domain已经存在，不再创建
	 *         2010-9-20
	 */
	boolean createTb_Domain(Tb_Domain tbDomain);

	boolean updateTb_Domain(Tb_Domain tbDomain);

	boolean deleteTb_Domain(int domainid);

	Tb_Domain getTb_Domain(int domainid);

	List<Tb_Domain> getTb_DomainList(String domain, int begin, int size);
}