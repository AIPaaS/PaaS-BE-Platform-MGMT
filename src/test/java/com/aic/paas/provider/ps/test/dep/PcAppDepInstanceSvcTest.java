package com.aic.paas.provider.ps.test.dep;

import org.junit.Before;
import org.junit.Test;

import com.aic.paas.provider.ps.bean.PcAppDepInstance;
import com.aic.paas.provider.ps.dep.PcAppDepInstanceSvc;
import com.binary.framework.test.TestTemplate;

public class PcAppDepInstanceSvcTest extends TestTemplate {

	
	PcAppDepInstanceSvc svc;
	
	
	@Before
	public void init() {
		svc = getBean(PcAppDepInstanceSvc.class);
	}
	
	
	
	@Test
	public void addDepInstanceByAppImgFullName() {
		String appImgFullName = "aaa";
		PcAppDepInstance record = new PcAppDepInstance();
		record.setServerIp("1.1.1.1");
		record.setTime(20160101010101l);
		record.setInstanceName("test-APP02-con1");
		
		Long id = svc.addDepInstanceByAppImgFullName(appImgFullName, record);
		
		System.out.println("-------------------------------------------------------------");
		System.out.println(id);
	}
	
	
	
	@Test
	public void disableDepInstanceByInstanceName() {
		String instanceName = "aaa";
		Integer count = svc.disableDepInstanceByInstanceName(instanceName);
		
		System.out.println("-------------------------------------------------------------");
		System.out.println(count);
	}
	
	
}
