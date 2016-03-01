package com.aic.paas.provider.ps.db;


import com.aic.paas.provider.ps.bean.CPcAppResApply;
import com.aic.paas.provider.ps.bean.PcAppResApply;
import com.binary.framework.dao.Dao;


/**
 * 应用资源申请表[PC_APP_RES_APPLY]数据访问对象
 */
public interface PcAppResApplyDao extends Dao<PcAppResApply, CPcAppResApply> {


	
	/**
	 * 审批申请单
	 * @param id 申请单ID
	 * @param status 审批状态
	 * @param checkerId 审批人ID
	 * @param checkerName 审批人姓名
	 * @param desc 审批描述
	 * @return
	 */
	public int checkResApply(Long id, Integer status, Long checkerId, String checkerName, String desc);
	
	
}


