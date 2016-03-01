package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppMgr;
import com.aic.paas.provider.ps.bean.PcAppMgr;


/**
 * 应用管理员表[PC_APP_MGR]数据访问对象定义实现
 */
public class PcAppMgrDaoDefinition implements DaoDefinition<PcAppMgr, CPcAppMgr> {


	@Override
	public Class<PcAppMgr> getEntityClass() {
		return PcAppMgr.class;
	}


	@Override
	public Class<CPcAppMgr> getConditionClass() {
		return CPcAppMgr.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_MGR";
	}


	@Override
	public boolean hasDataStatusField() {
		return false;
	}


	@Override
	public void setDataStatusValue(PcAppMgr record, int status) {
	}


	@Override
	public void setDataStatusValue(CPcAppMgr cdt, int status) {
	}


	@Override
	public void setCreatorValue(PcAppMgr record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppMgr record, String modifier) {
	}


}


