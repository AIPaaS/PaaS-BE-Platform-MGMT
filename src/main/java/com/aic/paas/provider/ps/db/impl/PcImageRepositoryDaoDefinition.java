package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcImageRepository;
import com.aic.paas.provider.ps.bean.PcImageRepository;


/**
 * 镜像库表[PC_IMAGE_REPOSITORY]数据访问对象定义实现
 */
public class PcImageRepositoryDaoDefinition implements DaoDefinition<PcImageRepository, CPcImageRepository> {


	@Override
	public Class<PcImageRepository> getEntityClass() {
		return PcImageRepository.class;
	}


	@Override
	public Class<CPcImageRepository> getConditionClass() {
		return CPcImageRepository.class;
	}


	@Override
	public String getTableName() {
		return "PC_IMAGE_REPOSITORY";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcImageRepository record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcImageRepository cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcImageRepository record, String creator) {
	}


	@Override
	public void setModifierValue(PcImageRepository record, String modifier) {
	}


}


