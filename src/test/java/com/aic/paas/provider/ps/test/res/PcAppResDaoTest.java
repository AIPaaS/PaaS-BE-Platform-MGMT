package com.aic.paas.provider.ps.test.res;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.aic.paas.provider.ps.bean.PcAppRes;
import com.aic.paas.provider.ps.db.PcAppResDao;
import com.binary.framework.test.TestTemplate;

public class PcAppResDaoTest extends TestTemplate {
	
	
	
	PcAppResDao dao;
	
	
	
	@Before
	public void init() {
		dao = getBean(PcAppResDao.class);
	}
	
	
	@Test
	public void selectAppGroupList() {
		Long[] appIds = new Long[]{1l,2l,3l};
		List<PcAppRes> ls = dao.selectAppGroupList(appIds);
		printList(ls);
	}
	
	
	
	

}
