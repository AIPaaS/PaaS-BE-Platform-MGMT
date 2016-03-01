package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPsResCenterRes;
import com.aic.paas.provider.ps.bean.PsResCenterRes;
import com.aic.paas.provider.ps.db.PsResCenterResDao;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;


/**
 * 资源中心资源表[PS_RES_CENTER_RES]数据访问对象实现
 */
public class PsResCenterResDaoImpl extends IBatisDaoTemplate<PsResCenterRes, CPsResCenterRes> implements PsResCenterResDao {

	
	
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
	public int increTotalResByNetZoneId(Long netZoneId, Long cpuCount, Long memSize, Long diskSize) {
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("netZoneId", netZoneId);
		map.put("cpuCount", cpuCount);
		map.put("memSize", memSize);
		map.put("diskSize", diskSize);
		
		return getSqlMapClientTemplate().update(getTableName()+".increTotalResByNetZoneId", map);
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
	
	

	@Override
	public int increResByNetZoneId(Long netZoneId, Long cpuCount, Long memSize, Long diskSize, Boolean validity) {
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("netZoneId", netZoneId);
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
		
		return getSqlMapClientTemplate().update(getTableName()+".increResByNetZoneId", map);
	}



	

}


