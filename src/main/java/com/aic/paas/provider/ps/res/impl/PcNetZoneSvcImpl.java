package com.aic.paas.provider.ps.res.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcNetZone;
import com.aic.paas.provider.ps.bean.PcNetZone;
import com.aic.paas.provider.ps.db.PcNetZoneDao;
import com.aic.paas.provider.ps.res.PcNetZoneSvc;
import com.aic.paas.provider.ps.res.PsResCenterResSvc;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcNetZoneSvcImpl implements PcNetZoneSvc {

	
	@Autowired
	PcNetZoneDao pcNetZoneDao;
	
	
	@Autowired
	PsResCenterResSvc resSvc;
	
	
	
	@Override
	public Page<PcNetZone> queryPage(Integer pageNum, Integer pageSize, CPcNetZone cdt, String orders) {
		return pcNetZoneDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	
	@Override
	public List<PcNetZone> queryPage2(Integer pageNum, Integer pageSize, CPcNetZone cdt, String orders) {
		return pcNetZoneDao.selectList(pageNum, pageSize, cdt, orders);
	}
	
	
	

	@Override
	public List<PcNetZone> queryList(CPcNetZone cdt, String orders) {
		return pcNetZoneDao.selectList(cdt, orders);
	}
	
	
	

	@Override
	public PcNetZone queryById(Long id) {
		return pcNetZoneDao.selectById(id);
	}
	
	
	

	@Override
	public Long saveOrUpdate(PcNetZone record) {
		BinaryUtils.checkEmpty(record, "record");
		
		if(record.getId() == null) {
			BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			BinaryUtils.checkEmpty(record.getZoneCode(), "record.zoneCode");
			BinaryUtils.checkEmpty(record.getZoneName(), "record.zoneName");
			BinaryUtils.checkEmpty(record.getNetSegExp(), "record.netSegExp");
		}else {
			if(record.getDataCenterId()!=null) BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			if(record.getResCenterId()!=null) BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			if(record.getZoneCode()!=null) BinaryUtils.checkEmpty(record.getZoneCode(), "record.zoneCode");
			if(record.getZoneName()!=null) BinaryUtils.checkEmpty(record.getZoneName(), "record.zoneName");
			if(record.getNetSegExp()!=null) BinaryUtils.checkEmpty(record.getNetSegExp(), "record.netSegExp");
		}
				
		Long id = record.getId();
		boolean add = id == null;
		
		if(record.getZoneCode() != null) {
			String code = record.getZoneCode().trim();
			record.setZoneCode(code);
			
			CPcNetZone cdt = new CPcNetZone();
			cdt.setZoneCodeEqual(code);
			List<PcNetZone> ls = pcNetZoneDao.selectList(cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists code '"+code+"'! ");
			}
		}
				
		id = pcNetZoneDao.save(record);
		//如果是新增网络区域, 则创建资源中心资源池
		if(add) {
			resSvc.createRes(record.getDataCenterId(), record.getResCenterId(), id);
		}
		
		return id;
	}
	
	
	
	

	@Override
	public int removeById(Long id) {
		return pcNetZoneDao.deleteById(id);
	}

	
	
	
}
