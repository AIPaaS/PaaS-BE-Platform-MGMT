package com.aic.paas.provider.ps.db;


import java.util.List;

import com.aic.paas.provider.ps.bean.CPcApp;
import com.aic.paas.provider.ps.bean.PcApp;
import com.binary.framework.dao.Dao;
import com.binary.jdbc.Page;


/**
 * 应用表[PC_APP]数据访问对象
 */
public interface PcAppDao extends Dao<PcApp, CPcApp> {


	
	/**
	 * 查询管理员所管理的产品
	 * @param pageNum : 指定页码
	 * @param pageSize : 指定页行数
	 * @param cdt : 条件对象
	 * @param orders : 排序字段, 多字段以逗号分隔
	 * @return 
	 */
	public Page<PcApp> selectMgrPage(long pageNum, long pageSize, Long mgrId, CPcApp cdt, String orders);


	
	
	
	/**
	 * 查询管理员所管理的产品
	 * @param cdt : 条件对象
	 * @param orders : 排序字段, 多字段以逗号分隔
	 * @return 
	 */
	public List<PcApp> selectMgrList(Long mgrId, CPcApp cdt, String orders);
	
	
	
	
	
}


