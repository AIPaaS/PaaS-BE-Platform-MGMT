package com.aic.paas.provider.ps.db.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcAppTask;
import com.aic.paas.provider.ps.bean.PcAppTask;
import com.aic.paas.provider.ps.db.PcAppTaskDao;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;


/**
 * 应用布署任务表[PC_APP_TASK]数据访问对象实现
 */
public class PcAppTaskDaoImpl extends IBatisDaoTemplate<PcAppTask, CPcAppTask> implements PcAppTaskDao {


	
	@SuppressWarnings("unchecked")
	@Override
	public List<PcAppTask> selectLastList(Long[] appIds) {
		if(BinaryUtils.isEmpty(appIds)) {
			return new ArrayList<PcAppTask>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appIds", Conver.toString(appIds));
		List<PcAppTask> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectLastList", map);
		return list;
	}
	
	
	
	
}


