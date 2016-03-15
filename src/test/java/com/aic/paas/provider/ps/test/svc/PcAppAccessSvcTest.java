package com.aic.paas.provider.ps.test.svc;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.aic.paas.provider.ps.bean.CPcAppAccess;
import com.aic.paas.provider.ps.bean.PcAppAccess;
import com.aic.paas.provider.ps.db.PcAppAccessDao;
import com.aic.paas.provider.ps.dep.PcAppAccessSvc;
import com.binary.framework.test.TestTemplate;

public class PcAppAccessSvcTest extends TestTemplate {
	
	PcAppAccessSvc appAccessSvc;
	
	PcAppAccessDao appAccessDao;
	
	@Before
	public void init() {
		appAccessSvc = getBean(PcAppAccessSvc.class);
//		appAccessDao = getBean(PcAppAccessDao.class);
	}
	
	
	@Test
	public void queryPage(){
//		PcAppAccess pc = new PcAppAccess();
//		pc.setId(1l);
//		Long appAccess = appAccessSvc.saveOrUpdate(pc);
		PcAppAccess p = appAccessSvc.queryById(1l);
		System.out.println("appAccess------"+p.toString());
//		List<PcAppAccess> appAccess2 = appAccessDao.selectListByCode("test",null, null);
//		System.out.println("appAccess2------"+appAccess2.toString());
	}
}
