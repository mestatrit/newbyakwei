package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpUserTableData;
import com.hk.bean.CmpUserTableDataOption;
import com.hk.bean.CmpUserTableDataValue;
import com.hk.bean.CmpUserTableField;

public interface CmpUserTableService {

	void createCmpUserTableData(CmpUserTableData cmpUserTableData);

	void saveCmpUserTableData(CmpUserTableData cmpUserTableData);

	void updateCmpUserTableData(CmpUserTableData cmpUserTableData);

	void deleteCmpUserTableData(CmpUserTableData cmpUserTableData);

	CmpUserTableData getCmpUserTableData(long dataId);

	CmpUserTableData getCmpUserTableDataByCmpNavOid(long cmpNavOid);

	void createCmpUserTableField(CmpUserTableField cmpUserTableField);

	void updateCmpUserTableField(CmpUserTableField cmpUserTableField);

	/**
	 * 删除字段时，把相应自定义的选项值也删除，但用户输入数据不删除
	 * 
	 * @param cmpUserTableField
	 *            2010-11-21
	 */
	void deleteCmpUserTableField(CmpUserTableField cmpUserTableField);

	CmpUserTableField getCmpUserTableField(long fieldId);

	List<CmpUserTableField> getCmpUserTableFieldListByDataId(long dataId);

	void createCmpUserTableDataOption(
			CmpUserTableDataOption cmpUserTableDataOption);

	void updateCmpUserTableDataOption(
			CmpUserTableDataOption cmpUserTableDataOption);

	void deleteCmpUserTableDataOption(
			CmpUserTableDataOption cmpUserTableDataOption);

	CmpUserTableDataOption getCmpUserTableDataOption(long optionId);

	List<CmpUserTableDataOption> getCmpUserTableDataOptionListByFieldId(
			long fieldId);

	List<CmpUserTableDataOption> getCmpUserTableDataOptionListByCmpNavOid(
			long cmpNavOid);

	void createCmpUserTableDataValue(CmpUserTableDataValue cmpUserTableDataValue);

	List<CmpUserTableDataValue> getCmpUserTableDataValueListByDataId(
			long companyId, long dataId, int begin, int size);
}