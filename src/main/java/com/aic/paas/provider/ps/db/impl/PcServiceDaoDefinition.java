package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcService;
import com.aic.paas.provider.ps.bean.PcService;


/**
 * 服务表[PC_SERVICE]数据访问对象定义实现
 */
public class PcServiceDaoDefinition implements DaoDefinition<PcService, CPcService> {


	@Override
	public Class<PcService> getEntityClass() {
		return PcService.class;
	}


	@Override
	public Class<CPcService> getConditionClass() {
		return CPcService.class;
	}


	@Override
	public String getTableName() {
		return "PC_SERVICE";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcService record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcService cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcService record, String creator) {
	}


	@Override
	public void setModifierValue(PcService record, String modifier) {
	}


}


