package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcComputerTag;
import com.aic.paas.provider.ps.bean.PcComputerTag;


/**
 * 服务器标签[PC_COMPUTER_TAG]数据访问对象定义实现
 */
public class PcComputerTagDaoDefinition implements DaoDefinition<PcComputerTag, CPcComputerTag> {


	@Override
	public Class<PcComputerTag> getEntityClass() {
		return PcComputerTag.class;
	}


	@Override
	public Class<CPcComputerTag> getConditionClass() {
		return CPcComputerTag.class;
	}


	@Override
	public String getTableName() {
		return "PC_COMPUTER_TAG";
	}


	@Override
	public boolean hasDataStatusField() {
		return false;
	}


	@Override
	public void setDataStatusValue(PcComputerTag record, int status) {
	}


	@Override
	public void setDataStatusValue(CPcComputerTag cdt, int status) {
	}


	@Override
	public void setCreatorValue(PcComputerTag record, String creator) {
	}


	@Override
	public void setModifierValue(PcComputerTag record, String modifier) {
	}


}


