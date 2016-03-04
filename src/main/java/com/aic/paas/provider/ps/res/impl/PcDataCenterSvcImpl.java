package com.aic.paas.provider.ps.res.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcDataCenter;
import com.aic.paas.provider.ps.bean.PcDataCenter;
import com.aic.paas.provider.ps.db.PcDataCenterDao;
import com.aic.paas.provider.ps.res.PcDataCenterSvc;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcDataCenterSvcImpl implements PcDataCenterSvc {
	
	
	@Autowired
	PcDataCenterDao dataCenterDao;
	

	
	@Override
	public Page<PcDataCenter> queryPage(Integer pageNum, Integer pageSize, CPcDataCenter cdt, String orders) {
		return dataCenterDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	
	@Override
	public List<PcDataCenter> queryPage2(Integer pageNum, Integer pageSize, CPcDataCenter cdt, String orders) {
		return dataCenterDao.selectList(pageNum, pageSize, cdt, orders);
	}
	
	

	@Override
	public List<PcDataCenter> queryList(CPcDataCenter cdt, String orders) {
		return dataCenterDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PcDataCenter queryById(Long id) {
		return dataCenterDao.selectById(id);
	}
	
	

	@Override
	public Long saveOrUpdate(PcDataCenter record) {
		BinaryUtils.checkEmpty(record, "record");
		
		if(record.getId() == null) {
			BinaryUtils.checkEmpty(record.getCode(), "record.code");
			BinaryUtils.checkEmpty(record.getName(), "record.name");
		}else {
			if(record.getCode()!=null) BinaryUtils.checkEmpty(record.getCode(), "record.code");
			if(record.getName()!=null) BinaryUtils.checkEmpty(record.getName(), "record.name");
		}
				
		Long id = record.getId();
		if(record.getCode() != null) {
			String code = record.getCode().trim();
			record.setCode(code);
			
			CPcDataCenter cdt = new CPcDataCenter();
			cdt.setCodeEqual(code);
			List<PcDataCenter> ls = dataCenterDao.selectList(cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists code '"+code+"'! ");
			}
		}
		
		return dataCenterDao.save(record);
	}
	
	
	

	@Override
	public int removeById(Long id) {
		return dataCenterDao.deleteById(id);
	}
	
	
	
	

}
