package com.aic.paas.provider.ps.db;


import java.util.List;

import com.aic.paas.provider.ps.bean.CPcService;
import com.aic.paas.provider.ps.bean.PcService;
import com.binary.framework.dao.Dao;


/**
 * 服务表[PC_SERVICE]数据访问对象
 */
public interface PcServiceDao extends Dao<PcService, CPcService> {

	
	
	
	/**
	 * 跟据服务代码查询
	 * @param code
	 * @param cdt
	 * @param orders
	 * @return
	 */
	public List<PcService> selectListByCode(String code, CPcService cdt, String orders);
	

}


