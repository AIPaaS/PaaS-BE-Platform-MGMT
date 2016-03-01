package com.aic.paas.provider.ps.res.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.comm.util.CommUtil;
import com.aic.paas.comm.util.SystemUtil;
import com.aic.paas.provider.ps.bean.CPsMntRes;
import com.aic.paas.provider.ps.bean.CPsMntResApply;
import com.aic.paas.provider.ps.bean.PsMntRes;
import com.aic.paas.provider.ps.bean.PsMntResApply;
import com.aic.paas.provider.ps.db.PsMntResApplyDao;
import com.aic.paas.provider.ps.db.PsMntResDao;
import com.aic.paas.provider.ps.res.PsMntResApplySvc;
import com.aic.paas.provider.ps.res.PsMntResSvc;
import com.aic.paas.provider.ps.res.PsResCenterResSvc;
import com.aic.paas.provider.ps.res.bean.ResItems;
import com.aic.paas.provider.ps.res.bean.WsMerchent;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.User;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PsMntResApplySvcImpl implements PsMntResApplySvc {
	
	
	@Autowired
	PsMntResApplyDao mntResApplyDao;
	
	
	@Autowired
	PsResCenterResSvc resCenterResSvc;
	
	
	@Autowired
	PsMntResDao mntResDao;
	
	
	@Autowired
	PsMntResSvc mntResSvc;
	
	
	
	@Override
	public Page<PsMntResApply> queryPage(Integer pageNum, Integer pageSize, CPsMntResApply cdt, String orders) {
		return mntResApplyDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	
	

	@Override
	public List<PsMntResApply> queryList(CPsMntResApply cdt, String orders) {
		return mntResApplyDao.selectList(cdt, orders);
	}

	
	
	@Override
	public PsMntResApply queryById(Long id) {
		return mntResApplyDao.selectById(id);
	}

	
	
	@Override
	public Long saveOrUpdate(PsMntResApply record) {
		BinaryUtils.checkEmpty(record, "record");
		
		Long id = record.getId();
		boolean add = id == null;
		if(add) {
			BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
			BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			BinaryUtils.checkEmpty(record.getNetZoneId(), "record.netZoneId");
			BinaryUtils.checkEmpty(record.getCpuCount(), "record.cpuCount");
			BinaryUtils.checkEmpty(record.getMemSize(), "record.memSize");
			BinaryUtils.checkEmpty(record.getDiskSize(), "record.diskSize");
		}else {
			if(record.getMntId()!=null) BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
			if(record.getDataCenterId()!=null) BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			if(record.getResCenterId()!=null) BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			if(record.getNetZoneId()!=null) BinaryUtils.checkEmpty(record.getNetZoneId(), "record.netZoneId");
			if(record.getCpuCount()!=null) BinaryUtils.checkEmpty(record.getCpuCount(), "record.cpuCount");
			if(record.getMemSize()!=null) BinaryUtils.checkEmpty(record.getMemSize(), "record.memSize");
			if(record.getDiskSize()!=null) BinaryUtils.checkEmpty(record.getDiskSize(), "record.diskSize");
			record.setCode(null);
		}
		
		if(record.getApplyTime() == null) {
			record.setApplyTime(BinaryUtils.getNumberDateTime());
		}
		if(record.getAppliorId() == null) {
			User user = SystemUtil.getLoginUser();
			record.setAppliorId(user.getId());
			record.setAppliorName(user.getUserName());
		}
		record.setStatus(0);		//0=待审批  1=审批通过  2=审批退回    9=撤销
		
		if(add) {
			id = mntResApplyDao.save(record);
			PsMntResApply up = new PsMntResApply();
			String strid = CommUtil.fillPrefixZero(id, 8);
			if(strid.length() > 8) strid = strid.substring(0, 8);
			String code = BinaryUtils.getNumberDate() + strid;
			up.setCode(Long.valueOf(code));
			mntResApplyDao.updateById(up, id);
		}else {
			CPsMntResApply cdt = new CPsMntResApply();
			cdt.setId(id);
			cdt.setStatus(0);
			mntResApplyDao.updateByCdt(record, cdt);
		}
		
		return id;
	}
	
	
	
	
	

	@Override
	public void cannelApply(Long id) {
		BinaryUtils.checkEmpty(id, "id");
		
		//0=待审批  1=审批通过  2=审批退回    9=撤销
		int count = mntResApplyDao.checkResApply(id, 9, null, null, null);
		if(count == 0) {
			throw new ServiceException(" Apply order does not exist or the status cannot be cannel! ");
		}
	}

	
	
	@Override
	public void checkApply(Long id, Boolean pass, String checkDesc, WsMerchent mnt) {
		BinaryUtils.checkEmpty(id, "id");
		BinaryUtils.checkEmpty(pass, "pass");
		BinaryUtils.checkEmpty(checkDesc, "checkDesc");
		
		User user = SystemUtil.getLoginUser();
		Long checkerId = user.getId();
		String checkName = user.getUserName();
		
		PsMntResApply apply = mntResApplyDao.selectById(id);
		if(apply == null) throw new ServiceException(" not found MntResApply by id '"+id+"'! ");
		
		//如果审批通过则
		if(pass.booleanValue()) {
//			WsMerchent mnt = merchentDao.selectById(apply.getMntId());
			if(mnt == null) {
				throw new ServiceException(" not found mnt by id '"+apply.getMntId()+"'! ");
			}
			
			ResItems items = new ResItems();
			items.setCpuCount(apply.getCpuCount());
			items.setMemSize(apply.getMemSize());
			items.setDiskSize(apply.getDiskSize());
			
			//扣减平台资源
			int rc = resCenterResSvc.reduceResByNetZoneId(apply.getNetZoneId(), items, checkName, "租户["+mnt.getMntCode()+"]["+mnt.getMntName()+"]资源申请单["+apply.getCode()+"]被审批通过，扣减平台资源。", true);
			if(rc == 0) {
				throw new ServiceException(" Deducting the platform resources failed, please check platform resource adequacy! ");
			}
			
			//补充租户资源
			CPsMntRes mntrescdt = new CPsMntRes();
			mntrescdt.setMntId(apply.getMntId());
			mntrescdt.setResCenterId(apply.getResCenterId());
			mntrescdt.setNetZoneId(apply.getNetZoneId());
			List<PsMntRes> mntresls = mntResDao.selectList(mntrescdt, null);
			if(mntresls.size() == 0) {
				//throw new ServiceException(" not found MntRes by applyId '"+id+"'! ");
				//如果租户资源不存在则添加 (不支持幂等操作)
				PsMntRes res = mntResSvc.createRes(apply.getMntId(), apply.getDataCenterId(), apply.getResCenterId(), apply.getNetZoneId());
				mntresls.add(res);
			}
			if(mntresls.size() > 1) throw new ServiceException(" has multiple MntRes by applyId '"+id+"'! ");
			PsMntRes res = mntresls.get(0);
			
			//添加资源
			rc = mntResSvc.addRes(res.getId(), items, checkName, "资源申请单["+apply.getCode()+"]被审批通过，增加资源。", id);
			if(rc == 0) {
				throw new ServiceException(" Deducting the platform resources failed, please check platform resource adequacy! ");
			}
			
			//添加总资源
			mntResSvc.addTotalRes(res.getId(), items);
		}
		
		int count = mntResApplyDao.checkResApply(id, pass.booleanValue()?1:2, checkerId, checkName, checkDesc);
		if(count == 0) {
			throw new ServiceException(" MntResApply's status cannot be check! ");
		}
	}
	
	
	

}
