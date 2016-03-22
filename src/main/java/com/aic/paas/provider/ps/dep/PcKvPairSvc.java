package com.aic.paas.provider.ps.dep;

import java.util.List;

import com.aic.paas.provider.ps.bean.PcKvPair;

public interface PcKvPairSvc {
	
	public List<PcKvPair> getPcKvPairs(Long sourceId,Integer typeId);
}
