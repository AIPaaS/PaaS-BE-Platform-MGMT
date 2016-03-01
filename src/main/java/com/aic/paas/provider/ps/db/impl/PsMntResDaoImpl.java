package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcAppRes;
import com.aic.paas.provider.ps.bean.CPsMntRes;
import com.aic.paas.provider.ps.bean.PsMntRes;
import com.aic.paas.provider.ps.db.PsMntResDao;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;


/**
 * 租户资源表[PS_MNT_RES]数据访问对象实现
 */
public class PsMntResDaoImpl extends IBatisDaoTemplate<PsMntRes, CPsMntRes> implements PsMntResDao {

	
	
	

	@Override
	public int increTotalResById(Long id, Long cpuCount, Long memSize, Long diskSize) {
		BinaryUtils.checkEmpty(id, "id");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("cpuCount", cpuCount);
		map.put("memSize", memSize);
		map.put("diskSize", diskSize);
		
		return getSqlMapClientTemplate().update(getTableName()+".increTotalResById", map);
	}
	
	
	
	@Override
	public int increResById(Long id, Long cpuCount, Long memSize, Long diskSize, Boolean validity) {
		BinaryUtils.checkEmpty(id, "id");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("cpuCount", cpuCount);
		map.put("memSize", memSize);
		map.put("diskSize", diskSize);
		
		if(Boolean.TRUE.equals(validity)) {
			if(cpuCount!=null && cpuCount.longValue()<0) {
				map.put("cdtCpuCount", Math.abs(cpuCount));
			}
			if(memSize!=null && memSize.longValue()<0) {
				map.put("cdtMemSize", Math.abs(memSize));
			}
			if(diskSize!=null && diskSize.longValue()<0) {
				map.put("cdtDiskSize", Math.abs(diskSize));
			}
		}
		
		return getSqlMapClientTemplate().update(getTableName()+".increResById", map);
	}




	@SuppressWarnings("unchecked")
	@Override
	public PsMntRes releaseRes(Long id) {
		int count = deleteById(id);
		if(count == 0) return null;
		
		CPcAppRes cdt = new CPcAppRes();
		cdt.setId(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		map.put("orders", "ID");
		List<PsMntRes> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectList", map);
		
		return list.size()>0 ? list.get(0) : null;
	}
	
	
	
	
}


