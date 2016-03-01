package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppRes;
import com.aic.paas.provider.ps.bean.PcAppRes;


/**
 * 应用资源表[PC_APP_RES]数据访问对象定义实现
 */
public class PcAppResDaoDefinition implements DaoDefinition<PcAppRes, CPcAppRes> {


	@Override
	public Class<PcAppRes> getEntityClass() {
		return PcAppRes.class;
	}


	@Override
	public Class<CPcAppRes> getConditionClass() {
		return CPcAppRes.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_RES";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcAppRes record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcAppRes cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcAppRes record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppRes record, String modifier) {
	}


}


