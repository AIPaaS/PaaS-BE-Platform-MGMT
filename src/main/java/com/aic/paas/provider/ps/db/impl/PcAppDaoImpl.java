package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcApp;
import com.aic.paas.provider.ps.bean.PcApp;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;
import com.binary.framework.ibatis.IBatisUtils;
import com.binary.jdbc.Page;


/**
 * 应用表[PC_APP]数据访问对象实现
 */
public class PcAppDaoImpl extends IBatisDaoTemplate<PcApp, CPcApp> implements PcAppDao {

	
	
	
	@Override
	public Page<PcApp> selectMgrPage(long pageNum, long pageSize, Long mgrId, CPcApp cdt, String orders) {
		BinaryUtils.checkEmpty(mgrId, "mgrId");
		
		if(cdt == null) cdt = newCondition();
		setDataStatusValue(cdt, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		map.put("orders", orders);
		map.put("mgrId", mgrId);
		
		Page<PcApp> page = IBatisUtils.selectPage(getSqlMapClientTemplate(), getTableName()+".selectMgrList", map, pageNum, pageSize, true);
		return page;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PcApp> selectMgrList(Long mgrId, CPcApp cdt, String orders) {
		BinaryUtils.checkEmpty(mgrId, "mgrId");
		
		if(cdt == null) cdt = newCondition();
		setDataStatusValue(cdt, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		map.put("orders", orders);
		map.put("mgrId", mgrId);
		
		List<PcApp> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectMgrList", map);
		return list;
	}
	
	
	


}


