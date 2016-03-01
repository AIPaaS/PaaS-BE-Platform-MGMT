package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppImgSvc;
import com.aic.paas.provider.ps.bean.PcAppImgSvc;


/**
 * 应用镜像调用服务表[PC_APP_IMG_SVC]数据访问对象定义实现
 */
public class PcAppImgSvcDaoDefinition implements DaoDefinition<PcAppImgSvc, CPcAppImgSvc> {


	@Override
	public Class<PcAppImgSvc> getEntityClass() {
		return PcAppImgSvc.class;
	}


	@Override
	public Class<CPcAppImgSvc> getConditionClass() {
		return CPcAppImgSvc.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_IMG_SVC";
	}


	@Override
	public boolean hasDataStatusField() {
		return false;
	}


	@Override
	public void setDataStatusValue(PcAppImgSvc record, int status) {
	}


	@Override
	public void setDataStatusValue(CPcAppImgSvc cdt, int status) {
	}


	@Override
	public void setCreatorValue(PcAppImgSvc record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppImgSvc record, String modifier) {
	}


}


