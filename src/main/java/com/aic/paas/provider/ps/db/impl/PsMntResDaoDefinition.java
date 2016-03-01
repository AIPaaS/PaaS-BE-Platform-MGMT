package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPsMntRes;
import com.aic.paas.provider.ps.bean.PsMntRes;


/**
 * 租户资源表[PS_MNT_RES]数据访问对象定义实现
 */
public class PsMntResDaoDefinition implements DaoDefinition<PsMntRes, CPsMntRes> {


	@Override
	public Class<PsMntRes> getEntityClass() {
		return PsMntRes.class;
	}


	@Override
	public Class<CPsMntRes> getConditionClass() {
		return CPsMntRes.class;
	}


	@Override
	public String getTableName() {
		return "PS_MNT_RES";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PsMntRes record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPsMntRes cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PsMntRes record, String creator) {
	}


	@Override
	public void setModifierValue(PsMntRes record, String modifier) {
	}


}


