package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPsResCenterRes;
import com.aic.paas.provider.ps.bean.PsResCenterRes;


/**
 * 资源中心资源表[PS_RES_CENTER_RES]数据访问对象定义实现
 */
public class PsResCenterResDaoDefinition implements DaoDefinition<PsResCenterRes, CPsResCenterRes> {


	@Override
	public Class<PsResCenterRes> getEntityClass() {
		return PsResCenterRes.class;
	}


	@Override
	public Class<CPsResCenterRes> getConditionClass() {
		return CPsResCenterRes.class;
	}


	@Override
	public String getTableName() {
		return "PS_RES_CENTER_RES";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PsResCenterRes record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPsResCenterRes cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PsResCenterRes record, String creator) {
	}


	@Override
	public void setModifierValue(PsResCenterRes record, String modifier) {
	}


}


