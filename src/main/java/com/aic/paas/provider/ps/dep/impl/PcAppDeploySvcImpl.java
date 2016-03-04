package com.aic.paas.provider.ps.dep.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcAppDepHistory;
import com.aic.paas.provider.ps.bean.PcAppDepHistory;
import com.aic.paas.provider.ps.db.PcAppDepHistoryDao;
import com.aic.paas.provider.ps.dep.PcAppDeploySvc;
import com.aic.paas.provider.ps.dep.bean.PcImage;
import com.binary.jdbc.Page;



public class PcAppDeploySvcImpl implements PcAppDeploySvc {

	
	@Autowired
	PcAppDepHistoryDao depHistoryDao;
	
	
	
	@Override
	public Page<PcAppDepHistory> queryDepHistoryPage(Integer pageNum, Integer pageSize, CPcAppDepHistory cdt, String orders) {
		return depHistoryDao.selectPage(pageNum, pageSize, cdt, orders);
	}

	
	
	
	@Override
	public List<PcAppDepHistory> queryDepHistoryList(CPcAppDepHistory cdt, String orders) {
		return depHistoryDao.selectList(cdt, orders);
	}
	
	
	
	@Override
	public PcAppDepHistory queryDepHistoryById(Long id) {
		return depHistoryDao.selectById(id);
	}
	
	
	

	@Override
	public void deployApp(Long appId, Long appVnoId) {
		
		
		
		
	}



	@Override
	public void finishDeployApp(Long appId, Long appVnoId, List<PcImage> images, String remark) {
		// TODO Auto-generated method stub
		
	}

}
