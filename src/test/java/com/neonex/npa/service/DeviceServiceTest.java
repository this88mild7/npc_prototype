package com.neonex.npa.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neonex.npa.model.DeviceInfo;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class DeviceServiceTest {
	
	
	
	@Autowired
	private DeviceService devService;
	
	private final String USER_EMAIL = "tester";
	
	private String objCodeFortest = null;
	
	
	@Before
	public void setUp(){
		objCodeFortest = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
	}
	
	@Test
	public void testInsert(){
		
		try {
			// given
			DeviceInfo devInfo = new DeviceInfo();
			devInfo.setObjectCode(objCodeFortest);
			devInfo.setObjectUpperCode("N4ba7feb");
			devInfo.setImei("1111111");
			devInfo.setInneringCoorX("0");
			devInfo.setInneringCoorY("0");
			devInfo.setInneringWidth("0");
			devInfo.setInneringHeight("0");
			devInfo.setInneringDepth("0");
			devInfo.setOrderOrder("1");
			devInfo.setRegUserEmail(USER_EMAIL);
			devInfo.setMainDevYn("Y");
			devInfo.setMdn("1111111");
			devInfo.setIp("0.0.0.0");
			devInfo.setSm("0.0.0.0");
			devInfo.setGw("0.0.0.0");

			// when
			boolean resultCount = devService.insert(devInfo);

			// then
			assertThat(resultCount, is(true));
			
			//delete test data
			boolean deleteResultCount = devService.delete(devInfo);
			assertThat(deleteResultCount , is(true));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSelectDeviInfo() {
		try {
			// given 
			DeviceInfo devInfo = new DeviceInfo();
			devInfo.setObjectCode(objCodeFortest);
			devInfo.setObjectUpperCode("N4ba7feb");
			devInfo.setImei("1111111");
			devInfo.setInneringCoorX("0");
			devInfo.setInneringCoorY("0");
			devInfo.setInneringWidth("0");
			devInfo.setInneringHeight("0");
			devInfo.setInneringDepth("0");
			devInfo.setOrderOrder("1");
			devInfo.setRegUserEmail(USER_EMAIL);
			devInfo.setMainDevYn("Y");
			devInfo.setMdn("1111111");
			devInfo.setIp("0.0.0.0");
			devInfo.setSm("0.0.0.0");
			devInfo.setGw("0.0.0.0");
			devService.insert(devInfo);
			
			// when
			List<DeviceInfo> devInfoList = devService.selectList(devInfo);
			
			// then
			assertThat(devInfoList.size(), is(not(0)));
			
			// test data delete
			boolean deleteResultCount = devService.delete(devInfo);
			assertThat(deleteResultCount , is(true));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testUpdate() {
		try {
			// given 
			DeviceInfo devInfo = new DeviceInfo();
			devInfo.setObjectCode(objCodeFortest);
			devInfo.setObjectUpperCode("N4ba7feb");
			devInfo.setImei("1111111");
			devInfo.setInneringCoorX("0");
			devInfo.setInneringCoorY("0");
			devInfo.setInneringWidth("0");
			devInfo.setInneringHeight("0");
			devInfo.setInneringDepth("0");
			devInfo.setOrderOrder("1");
			devInfo.setRegUserEmail(USER_EMAIL);
			devInfo.setUptUserEmail(USER_EMAIL);
			devInfo.setMainDevYn("Y");
			devInfo.setMdn("1111111");
			devInfo.setIp("0.0.0.0");
			devInfo.setSm("0.0.0.0");
			devInfo.setGw("0.0.0.0");
			devInfo.setGw("0.0.0.0");
			devService.insert(devInfo);
			
			DeviceInfo updateInfo = devInfo;
			updateInfo.setIp("1.1.1.1");
			updateInfo.setSm("1.1.1.1");
			updateInfo.setGw("1.1.1.1");
			updateInfo.setImei("22222");
			updateInfo.setMdn("22222");
			updateInfo.setMainDevYn("N");

			// when
			boolean updateResultCount = devService.update(updateInfo);

			// then
			assertThat(updateResultCount , is(true));
			
			DeviceInfo selectDeviceInfo  = devService.selectDeviceDetail(updateInfo);
			assertNotNull(selectDeviceInfo);
			assertThat(selectDeviceInfo.getGw(), is("1.1.1.1"));
			
			// test data delete
			boolean deleteResultCount = devService.delete(devInfo);
			assertThat(deleteResultCount , is(true));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testIsDuplicate() {
		try {
			// given 
			DeviceInfo devInfo = new DeviceInfo();
			devInfo.setObjectCode(objCodeFortest);
			devInfo.setObjectUpperCode("N4ba7feb");
			devInfo.setImei("1111111");
			devInfo.setInneringCoorX("0");
			devInfo.setInneringCoorY("0");
			devInfo.setInneringWidth("0");
			devInfo.setInneringHeight("0");
			devInfo.setInneringDepth("0");
			devInfo.setOrderOrder("1");
			devInfo.setRegUserEmail(USER_EMAIL);
			devInfo.setUptUserEmail(USER_EMAIL);
			devInfo.setMainDevYn("Y");
			devInfo.setMdn("1111111");
			devInfo.setIp("0.0.0.0");
			devInfo.setSm("0.0.0.0");
			devInfo.setGw("0.0.0.0");
			devInfo.setGw("0.0.0.0");
			devService.insert(devInfo);

			// when
			boolean resultCount = devService.isDuplicate(devInfo);

			// then
			assertThat(resultCount, is(true));
			
			// test data delete
			boolean deleteResultCount = devService.delete(devInfo);
			assertThat(deleteResultCount , is(true));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		

		
	}
	
}
