package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcAppRes;
import com.aic.paas.provider.ps.bean.PcAppRes;
import com.aic.paas.provider.ps.db.PcAppResDao;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;
import com.binary.jdbc.JdbcOperator;


/**
 * 应用资源表[PC_APP_RES]数据访问对象实现
 */
public class PcAppResDaoImpl extends IBatisDaoTemplate<PcAppRes, CPcAppRes> implements PcAppResDao {

	

	@Override
	public List<PcAppRes> selectAppGroupList(Long[] appIds) {
		BinaryUtils.checkEmpty(appIds, "appIds");
		StringBuilder sb = new StringBuilder();
		sb.append(" select APP_ID appId, sum(CPU_COUNT) cpuCount, sum(MEM_SIZE) memSize, sum(DISK_SIZE) diskSize ")
			.append("		from PC_APP_RES ")
			.append("		where DATA_STATUS=1 and APP_ID in ("+Conver.toString(appIds)+") ")
			.append("		group by APP_ID ")
			.append("		order by APP_ID ");
		
		JdbcOperator jo = getJdbcOperator();
		List<PcAppRes> ls = jo.executeQuery(sb.toString(), PcAppRes.class);
		return ls;
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
	public PcAppRes releaseRes(Long id) {
		int count = deleteById(id);
		if(count == 0) return null;
		
		CPcAppRes cdt = new CPcAppRes();
		cdt.setId(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		map.put("orders", "ID");
		List<PcAppRes> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectList", map);
		
		return list.size()>0 ? list.get(0) : null;
	}






	
	
}


