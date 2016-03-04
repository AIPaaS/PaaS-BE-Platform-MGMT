package com.aic.paas.provider.ps.dep.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcAppDepHistory;
<<<<<<< HEAD
import com.aic.paas.provider.ps.bean.CPcAppDepInstance;
import com.aic.paas.provider.ps.bean.PcAppDepHistory;
import com.aic.paas.provider.ps.bean.PcAppDepInstance;
import com.aic.paas.provider.ps.db.PcAppDepHistoryDao;
import com.aic.paas.provider.ps.db.PcAppDepInstanceDao;
import com.aic.paas.provider.ps.dep.PcAppDeploySvc;
import com.aic.paas.provider.ps.dep.bean.PcImage;
import com.binary.core.util.BinaryUtils;
=======
import com.aic.paas.provider.ps.bean.PcAppDepHistory;
import com.aic.paas.provider.ps.db.PcAppDepHistoryDao;
import com.aic.paas.provider.ps.dep.PcAppDeploySvc;
import com.aic.paas.provider.ps.dep.bean.PcImage;
>>>>>>> e09f33b0f4e74920e2ab53bdee892764c711c3e9
import com.binary.jdbc.Page;



public class PcAppDeploySvcImpl implements PcAppDeploySvc {

	
	@Autowired
	PcAppDepHistoryDao depHistoryDao;
	
<<<<<<< HEAD
	@Autowired	
	PcAppDepInstanceDao instanceDao;
	
=======
>>>>>>> e09f33b0f4e74920e2ab53bdee892764c711c3e9
	
	
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
	
	
	
<<<<<<< HEAD
	@Override
	public List<PcAppDepInstance> queryDepInstance(Long depHistoryId, CPcAppDepInstance cdt, String orders) {
		BinaryUtils.checkEmpty(depHistoryId, "depHistoryId");
		if(cdt == null) cdt = new CPcAppDepInstance();
		cdt.setAppDepHistoryId(depHistoryId);
		return instanceDao.selectList(cdt, orders);
	}
	
	
=======
>>>>>>> e09f33b0f4e74920e2ab53bdee892764c711c3e9

	@Override
	public void deployApp(Long appId, Long appVnoId) {
		
		
		
		
	}



	@Override
	public void finishDeployApp(Long appId, Long appVnoId, List<PcImage> images, String remark) {
		// TODO Auto-generated method stub
		
	}

<<<<<<< HEAD



	

=======
>>>>>>> e09f33b0f4e74920e2ab53bdee892764c711c3e9
}
