package com.aic.paas.provider.ps.res.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPsResCenterRes;
import com.aic.paas.provider.ps.bean.CPsResFlow;
import com.aic.paas.provider.ps.bean.PsResCenterRes;
import com.aic.paas.provider.ps.bean.PsResFlow;
import com.aic.paas.provider.ps.db.PsResCenterResDao;
import com.aic.paas.provider.ps.db.PsResFlowDao;
import com.aic.paas.provider.ps.res.PsResCenterResSvc;
import com.aic.paas.provider.ps.res.bean.ResItems;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PsResCenterResSvcImpl implements PsResCenterResSvc {
	
	
	@Autowired
	PsResCenterResDao resDao;
	
	
	@Autowired
	PsResFlowDao flowDao;
	
	
	

	@Override
	public Page<PsResCenterRes> queryResPage(Integer pageNum, Integer pageSize, CPsResCenterRes cdt, String orders) {
		return resDao.selectPage(pageNum, pageSize, cdt, orders);
	}

	
	
	@Override
	public List<PsResCenterRes> queryResList(CPsResCenterRes cdt, String orders) {
		return resDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PsResCenterRes queryResById(Long id) {
		return resDao.selectById(id);
	}
	
	

	@Override
	public Page<PsResFlow> queryFlowPage(Integer pageNum, Integer pageSize, CPsResFlow cdt, String orders) {
		return flowDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	

	@Override
	public List<PsResFlow> queryFlowList(CPsResFlow cdt, String orders) {
		return flowDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PsResFlow queryFlowById(Long id) {
		return flowDao.selectById(id);
	}

	
	
	@Override
	public PsResCenterRes createRes(Long dataCenterId, Long resCenterId, Long netZoneId) {
		BinaryUtils.checkEmpty(dataCenterId, "dataCenterId");
		BinaryUtils.checkEmpty(resCenterId, "resCenterId");
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");

		CPsResCenterRes cdt = new CPsResCenterRes();
		cdt.setNetZoneId(netZoneId);
		List<PsResCenterRes> ls = resDao.selectList(cdt, null);
		if(ls.size() > 0) {
			throw new ServiceException(" the resCenter-res is exists by netZoneId:'"+netZoneId+"'! ");
		}
		
		PsResCenterRes res = new PsResCenterRes();
		res.setDataCenterId(dataCenterId);
		res.setResCenterId(resCenterId);
		res.setNetZoneId(netZoneId);
		res.setTotalCpuCount(0l);
		res.setTotalMemSize(0l);
		res.setTotalDiskSize(0l);
		res.setCpuCount(0l);
		res.setMemSize(0l);
		res.setDiskSize(0l);
		Long id = resDao.insert(res);
		res.setId(id);
		
		return res;
	}
	

	
	@Override
	public Integer addTotalResByNetZoneId(Long netZoneId, ResItems items) {
		return updateTotalResByNetZoneId(true, netZoneId, items);
	}



	@Override
	public Integer reduceTotalResByNetZoneId(Long netZoneId, ResItems items) {
		return updateTotalResByNetZoneId(false, netZoneId, items);
	}
	
	
	
	private Integer updateTotalResByNetZoneId(boolean add, Long netZoneId, ResItems items) {
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");
		BinaryUtils.checkEmpty(items, "items");
		
		Long cpuCount = items.getCpuCount();
		Long memSize = items.getMemSize();
		Long diskSize = items.getDiskSize();
		
		if(cpuCount==null && memSize==null && diskSize==null) {
			throw new ServiceException(" No effective update res's item!  ");
		}
		
		if(cpuCount != null) {
			cpuCount = Math.abs(cpuCount);
			if(!add) cpuCount = -cpuCount;
		}
		if(memSize != null) {
			memSize = Math.abs(memSize);
			if(!add) memSize = -memSize;
		}
		if(diskSize != null) {
			diskSize = Math.abs(diskSize);
			if(!add) diskSize = -diskSize;
		}
		
		return resDao.increTotalResByNetZoneId(netZoneId, cpuCount, memSize, diskSize);
	}

	
	
	

	@Override
	public Integer addResByNetZoneId(Long netZoneId, ResItems items, String upor, String remark) {
		return updateResByNetZoneId(true, netZoneId, items, upor, remark, false);
	}



	@Override
	public Integer reduceResByNetZoneId(Long netZoneId, ResItems items, String upor, String remark, Boolean validity) {
		return updateResByNetZoneId(false, netZoneId, items, upor, remark, !Boolean.FALSE.equals(validity));
	}
	
	
	
	private Integer updateResByNetZoneId(boolean add, Long netZoneId, ResItems items, String upor, String remark, Boolean validity) {
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");
		BinaryUtils.checkEmpty(items, "items");
		BinaryUtils.checkEmpty(upor, "upor");
		BinaryUtils.checkEmpty(remark, "remark");
		
		Long cpuCount = items.getCpuCount();
		Long memSize = items.getMemSize();
		Long diskSize = items.getDiskSize();
		
		if(cpuCount==null && memSize==null && diskSize==null) {
			throw new ServiceException(" No effective update res's item!  ");
		}
		
		if(cpuCount != null) {
			cpuCount = Math.abs(cpuCount);
			if(!add) cpuCount = -cpuCount;
		}
		if(memSize != null) {
			memSize = Math.abs(memSize);
			if(!add) memSize = -memSize;
		}
		if(diskSize != null) {
			diskSize = Math.abs(diskSize);
			if(!add) diskSize = -diskSize;
		}
		
		CPsResCenterRes cdt = new CPsResCenterRes();
		cdt.setNetZoneId(netZoneId);
		List<PsResCenterRes> beforels = resDao.selectList(cdt, null);
		if(beforels.size() == 0) throw new ServiceException(" not found res by netZoneId '"+netZoneId+"'! ");
		PsResCenterRes before = beforels.get(0);
		
		int count = resDao.increResById(before.getId(), cpuCount, memSize, diskSize, validity);
		if(count == 0) return 0;
		
		PsResCenterRes after = resDao.selectById(before.getId());
		
		PsResFlow flow = new PsResFlow();
		flow.setResId(before.getId());
		flow.setUpTime(BinaryUtils.getNumberDateTime());
		flow.setUpType(add?1:2); 		//1=增加    2=扣减
		flow.setBeforeCpuCount(before.getCpuCount());
		flow.setBeforeMemSize(before.getMemSize());
		flow.setBeforeDiskSize(before.getDiskSize());
		flow.setAfterCpuCount(after.getCpuCount());
		flow.setAfterMemSize(after.getMemSize());
		flow.setAfterDiskSize(after.getDiskSize());
		flow.setUpor(upor);
		flow.setUpDesc(remark);
		flowDao.insert(flow);
		
		return count;
	}



	
	
	
	

}
