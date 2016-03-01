package com.aic.paas.provider.ps.res.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcCompRoom;
import com.aic.paas.provider.ps.bean.PcCompRoom;
import com.aic.paas.provider.ps.db.PcCompRoomDao;
import com.aic.paas.provider.ps.res.PcCompRoomSvc;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcCompRoomSvcImpl implements PcCompRoomSvc {

	
	@Autowired
	PcCompRoomDao pcCompRoomDao;
	
	
	
	@Override
	public Page<PcCompRoom> queryPage(Integer pageNum, Integer pageSize, CPcCompRoom cdt, String orders) {
		return pcCompRoomDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	

	@Override
	public List<PcCompRoom> queryList(CPcCompRoom cdt, String orders) {
		return pcCompRoomDao.selectList(cdt, orders);
	}

	
	
	
	@Override
	public PcCompRoom queryById(Long id) {
		return pcCompRoomDao.selectById(id);
	}
	
	

	@Override
	public Long saveOrUpdate(PcCompRoom record) {
		BinaryUtils.checkEmpty(record, "record");
		
		if(record.getId() == null) {
			BinaryUtils.checkEmpty(record.getRoomCode(), "record.roomCode");
			BinaryUtils.checkEmpty(record.getRoomName(), "record.roomName");
		}else {
			if(record.getRoomCode()!=null) BinaryUtils.checkEmpty(record.getRoomCode(), "record.roomCode");
			if(record.getRoomName()!=null) BinaryUtils.checkEmpty(record.getRoomName(), "record.roomName");
		}
		
		Long id = record.getId();
		if(record.getRoomCode() != null) {
			String code = record.getRoomCode().trim();
			record.setRoomCode(code);
			
			CPcCompRoom cdt = new CPcCompRoom();
			cdt.setRoomCodeEqual(code);
			List<PcCompRoom> ls = pcCompRoomDao.selectList(cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists code '"+code+"'! ");
			}
		}
		
		return pcCompRoomDao.save(record);
	}
	
	
	
	@Override
	public int removeById(Long id) {
		return pcCompRoomDao.deleteById(id);
	}
	
	
	

}
