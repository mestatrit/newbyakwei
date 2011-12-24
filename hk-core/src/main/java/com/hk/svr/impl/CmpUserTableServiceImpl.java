package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpUserTableData;
import com.hk.bean.CmpUserTableDataOption;
import com.hk.bean.CmpUserTableDataValue;
import com.hk.bean.CmpUserTableField;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpUserTableService;

public class CmpUserTableServiceImpl implements CmpUserTableService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createCmpUserTableData(CmpUserTableData cmpUserTableData) {
		cmpUserTableData.setDataId(this.manager.createQuery().insertObject(
				cmpUserTableData).longValue());
	}

	@Override
	public void createCmpUserTableDataOption(
			CmpUserTableDataOption cmpUserTableDataOption) {
		cmpUserTableDataOption.setOptionId(this.manager.createQuery()
				.insertObject(cmpUserTableDataOption).longValue());
	}

	@Override
	public void createCmpUserTableField(CmpUserTableField cmpUserTableField) {
		cmpUserTableField.setFieldId(this.manager.createQuery().insertObject(
				cmpUserTableField).longValue());
	}

	@Override
	public void deleteCmpUserTableData(CmpUserTableData cmpUserTableData) {
		this.manager.createQuery().deleteObject(cmpUserTableData);
	}

	@Override
	public void deleteCmpUserTableDataOption(
			CmpUserTableDataOption cmpUserTableDataOption) {
		this.manager.createQuery().deleteObject(cmpUserTableDataOption);
	}

	@Override
	public void deleteCmpUserTableField(CmpUserTableField cmpUserTableField) {
		if (cmpUserTableField != null) {
			Query query = this.manager.createQuery();
			query.deleteObject(cmpUserTableField);
			query.delete(CmpUserTableDataOption.class, "fieldid=?",
					new Object[] { cmpUserTableField.getFieldId() });
		}
	}

	@Override
	public CmpUserTableData getCmpUserTableData(long cmpNavOid) {
		return this.manager.createQuery().getObjectById(CmpUserTableData.class,
				cmpNavOid);
	}

	@Override
	public CmpUserTableDataOption getCmpUserTableDataOption(long optionId) {
		return this.manager.createQuery().getObjectById(
				CmpUserTableDataOption.class, optionId);
	}

	@Override
	public List<CmpUserTableDataOption> getCmpUserTableDataOptionListByCmpNavOid(
			long cmpNavOid) {
		return this.manager.createQuery().listEx(CmpUserTableDataOption.class,
				"cmpnavoid=?", new Object[] { cmpNavOid }, "orderflg desc");
	}

	@Override
	public List<CmpUserTableDataOption> getCmpUserTableDataOptionListByFieldId(
			long fieldId) {
		return this.manager.createQuery().listEx(CmpUserTableDataOption.class,
				"fieldid=?", new Object[] { fieldId }, "orderflg desc");
	}

	@Override
	public CmpUserTableField getCmpUserTableField(long fieldId) {
		return this.manager.createQuery().getObjectById(
				CmpUserTableField.class, fieldId);
	}

	@Override
	public List<CmpUserTableField> getCmpUserTableFieldListByDataId(
			long cmpNavOid) {
		return this.manager.createQuery().listEx(CmpUserTableField.class,
				"dataid=?", new Object[] { cmpNavOid }, "orderflg desc");
	}

	@Override
	public void updateCmpUserTableData(CmpUserTableData cmpUserTableData) {
		this.manager.createQuery().updateObject(cmpUserTableData);
	}

	@Override
	public void updateCmpUserTableDataOption(
			CmpUserTableDataOption cmpUserTableDataOption) {
		this.manager.createQuery().updateObject(cmpUserTableDataOption);
	}

	@Override
	public void updateCmpUserTableField(CmpUserTableField cmpUserTableField) {
		this.manager.createQuery().updateObject(cmpUserTableField);
	}

	@Override
	public void createCmpUserTableDataValue(
			CmpUserTableDataValue cmpUserTableDataValue) {
		cmpUserTableDataValue.setVid(this.manager.createQuery().insertObject(
				cmpUserTableDataValue).longValue());
	}

	@Override
	public List<CmpUserTableDataValue> getCmpUserTableDataValueListByDataId(
			long companyId, long dataId, int begin, int size) {
		return this.manager.createQuery().listEx(CmpUserTableDataValue.class,
				"companyid=? and dataid=?", new Object[] { companyId, dataId },
				"vid desc", begin, size);
	}

	@Override
	public CmpUserTableData getCmpUserTableDataByCmpNavOid(long cmpNavOid) {
		return this.manager.createQuery().getObjectEx(CmpUserTableData.class,
				"cmpnavoid=?", new Object[] { cmpNavOid });
	}

	@Override
	public void saveCmpUserTableData(CmpUserTableData cmpUserTableData) {
		if (cmpUserTableData.getDataId() > 0) {
			this.updateCmpUserTableData(cmpUserTableData);
		}
		else {
			this.createCmpUserTableData(cmpUserTableData);
		}
	}
}