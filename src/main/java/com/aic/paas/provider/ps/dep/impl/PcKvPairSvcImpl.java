package com.aic.paas.provider.ps.dep.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcKvPair;
import com.aic.paas.provider.ps.bean.PcKvPair;
import com.aic.paas.provider.ps.db.PcKvPairDao;
import com.aic.paas.provider.ps.dep.PcKvPairSvc;

public class PcKvPairSvcImpl implements PcKvPairSvc {
	
	@Autowired
	PcKvPairDao pcKvPairDao;

	@Override
	public List<PcKvPair> getPcKvPairs(Long sourceId, Integer typeId) {
		CPcKvPair cdt = new CPcKvPair();
		cdt.setSourceId(sourceId);
		cdt.setTypeId(typeId);
		return pcKvPairDao.selectList(cdt, null);
	}

}
