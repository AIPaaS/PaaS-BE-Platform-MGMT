package com.aic.paas.provider.ps.db;


import java.util.List;

import com.aic.paas.provider.ps.bean.CPcAppAccess;
import com.aic.paas.provider.ps.bean.PcAppAccess;
import com.binary.framework.dao.Dao;


/**
 * 应用访问表[PC_APP_ACCESS]数据访问对象
 */
public interface PcAppAccessDao extends Dao<PcAppAccess, CPcAppAccess> {

	/**
	 * 跟据名称查询
	 * @param code
	 * @param cdt
	 * @param orders
	 * @return
	 */
	public List<PcAppAccess> selectListByCode(String code, CPcAppAccess cdt, String orders);
}


