package com.aic.paas.provider.ps.res.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcImageRepository;
import com.aic.paas.provider.ps.bean.PcImageRepository;
import com.aic.paas.provider.ps.db.PcImageRepositoryDao;
import com.aic.paas.provider.ps.res.PcImageRepositorySvc;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcImageRepositorySvcImpl implements PcImageRepositorySvc {
	
	
	@Autowired
	PcImageRepositoryDao imageRespDao;
	
	

	@Override
	public Page<PcImageRepository> queryPage(Integer pageNum, Integer pageSize, CPcImageRepository cdt, String orders) {
		return imageRespDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	

	@Override
	public List<PcImageRepository> queryList(CPcImageRepository cdt, String orders) {
		return imageRespDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PcImageRepository queryById(Long id) {
		return imageRespDao.selectById(id);
	}
	
	

	@Override
	public Long saveOrUpdate(PcImageRepository record) {
		BinaryUtils.checkEmpty(record, "record");
		
		if(record.getId() == null) {
			BinaryUtils.checkEmpty(record.getImgRespCode(), "record.imgRespCode");
			BinaryUtils.checkEmpty(record.getRoomId(), "record.roomId");
			BinaryUtils.checkEmpty(record.getImgRespType(), "record.imgRespType");
			BinaryUtils.checkEmpty(record.getImgRespUrl(), "record.imgRespUrl");
			BinaryUtils.checkEmpty(record.getImgRespUser(), "record.imgRespUser");
			BinaryUtils.checkEmpty(record.getImgRespPwd(), "record.imgRespPwd");
		}else {
			if(record.getImgRespCode()!=null) BinaryUtils.checkEmpty(record.getImgRespCode(), "record.imgRespCode");
			if(record.getRoomId()!=null) BinaryUtils.checkEmpty(record.getRoomId(), "record.roomId");
			if(record.getImgRespType()!=null) BinaryUtils.checkEmpty(record.getImgRespType(), "record.imgRespType");
			if(record.getImgRespUrl()!=null) BinaryUtils.checkEmpty(record.getImgRespUrl(), "record.imgRespUrl");
			if(record.getImgRespUser()!=null) BinaryUtils.checkEmpty(record.getImgRespUser(), "record.imgRespUser");
			if(record.getImgRespPwd()!=null) BinaryUtils.checkEmpty(record.getImgRespPwd(), "record.imgRespPwd");
		}
		
		Long id = record.getId();
		if(record.getImgRespCode() != null) {
			String code = record.getImgRespCode().trim();
			record.setImgRespCode(code);
			
			CPcImageRepository cdt = new CPcImageRepository();
			cdt.setImgRespCodeEqual(code);
			List<PcImageRepository> ls = imageRespDao.selectList(cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists code '"+code+"'! ");
			}
		}
		
		if(record.getImgRespType()!=null && record.getImgRespType().intValue()==1) {	//1=快照镜像库    0=非快照镜像库
			Long roomId = record.getRoomId();
			if(roomId == null) {
				PcImageRepository resp = imageRespDao.selectById(id);
				if(resp == null) {
					throw new ServiceException(" not found image-repository by id '"+id+"'! ");
				}
				roomId = resp.getRoomId();
			}
			
			CPcImageRepository cdt = new CPcImageRepository();
			cdt.setRoomId(roomId);
			cdt.setImgRespType(1);
			List<PcImageRepository> ls = imageRespDao.selectList(cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" snapshot images already exists in the current room '"+roomId+"'! ");
			}
		}
		
		return imageRespDao.save(record);
	}

	
	
	@Override
	public int removeById(Long id) {
		return imageRespDao.deleteById(id);
	}

}
