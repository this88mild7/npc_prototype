package com.neonex.npa.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neonex.npa.model.TopologyInfo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class TopologyDaoTest {
	
	private TopologyInfo topologyInfo;
	
	private final static Logger LOGGER =  LoggerFactory.getLogger(TopologyDaoTest.class);
	
	@Autowired
	private TopologyDao topoDao;

	private String objCodeForTest; 
	
	private final String USER_EMAIL = "tester";
	
	private final String ROOT_OBJECTCODE = "1111";
	
	@Autowired
	private ImageDao imgDao;
	
	
	@Before
	public void setup(){
		topologyInfo = new TopologyInfo();
		objCodeForTest = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
	}
	
	@Test
	public void testInsertBackgroundObject() throws Exception{

		// given 
		topologyInfo.setObjectCode(objCodeForTest);
		
		int backgroundImgSeq = imgDao.getBackgroundTopOneSeq();
		topologyInfo.setBackgroundImgSeq(String.valueOf(backgroundImgSeq));;
		LOGGER.info("backgroundImgSeq : {}", backgroundImgSeq);
		
		topologyInfo.setImei("0000000000");
		topologyInfo.setInneringCoorX("100");
		topologyInfo.setInneringCoorY("100");
		topologyInfo.setInneringDepth("1");
		topologyInfo.setInneringHeight("100");
		topologyInfo.setInneringWidth("100");
		topologyInfo.setObjectNm("testObject");
		topologyInfo.setObjectTypeCode("000");
		topologyInfo.setOrderOrder("1");
		topologyInfo.setObjectUpperCode("0");
		topologyInfo.setRegUserEmail(USER_EMAIL);
		topologyInfo.setUptUserEmail(USER_EMAIL);

		// 이미 존재 하느제 체크
		TopologyInfo rootTopoInfo = topoDao.select(ROOT_OBJECTCODE);
		
		LOGGER.info("rootTopoInfo : {}", rootTopoInfo);
		
		if(rootTopoInfo == null){
			// when
			int insertResultCount = topoDao.insert(topologyInfo);
			
			// then
			assertThat(insertResultCount, is(not(0)));
		}

		
		//test data 삭제
		int deleteResultCount = topoDao.delete(rootTopoInfo);
		assertThat(deleteResultCount, is(not(0)));
		
	}
	
	@Test
	public void testInsertUserImageObject() throws Exception{

		// given 
		topologyInfo.setObjectCode(objCodeForTest);
		topologyInfo.setImgSeq(String.valueOf(0));
		topologyInfo.setImei("0000000000");
		topologyInfo.setInneringCoorX("100");
		topologyInfo.setInneringCoorY("100");
		topologyInfo.setInneringDepth("1");
		topologyInfo.setInneringHeight("100");
		topologyInfo.setInneringWidth("100");
		topologyInfo.setObjectNm("testObject");
		topologyInfo.setObjectTypeCode("000");
		topologyInfo.setOrderOrder("1");
		topologyInfo.setObjectUpperCode("0");
		topologyInfo.setRegUserEmail(USER_EMAIL);
		topologyInfo.setUptUserEmail(USER_EMAIL);

		// when
		int insertResultCount = topoDao.insert(topologyInfo);

		// then
		assertThat(insertResultCount, is(not(0)));		
		TopologyInfo topologyInfo = topoDao.select(objCodeForTest);
		assertNotNull(topologyInfo);
		
		//test data 삭제
		int deleteResultCount = topoDao.delete(topologyInfo);
		assertThat(deleteResultCount, is(not(0)));

	}
	
	@Test
	public void testSelectObject() throws Exception{

		// given 
		topologyInfo.setObjectCode(objCodeForTest);
		topologyInfo.setImei("0000000000");
		topologyInfo.setInneringCoorX("100");
		topologyInfo.setInneringCoorY("100");
		topologyInfo.setInneringDepth("1");
		topologyInfo.setInneringHeight("100");
		topologyInfo.setInneringWidth("100");
		topologyInfo.setObjectNm("testObject");
		topologyInfo.setObjectTypeCode("000");
		topologyInfo.setOrderOrder("1");
		topologyInfo.setObjectUpperCode("0");
		topologyInfo.setTopYn("N");
		topologyInfo.setRegUserEmail(USER_EMAIL);
		topologyInfo.setUptUserEmail(USER_EMAIL);
		topoDao.insert(topologyInfo);

		// when
		TopologyInfo topologyInfo = topoDao.select(objCodeForTest);

		// then
		assertNotNull(topologyInfo);
		
		//test data 삭제
		int deleteResultCount = topoDao.delete(topologyInfo);
		assertThat(deleteResultCount, is(not(0)));
	}
	
	@Test
	public void testUpdateObject() throws Exception{

		// given 
		topologyInfo.setObjectCode(objCodeForTest);
		topologyInfo.setImei("0000000000");
		topologyInfo.setInneringCoorX("100");
		topologyInfo.setInneringCoorY("100");
		topologyInfo.setInneringDepth("1");
		topologyInfo.setInneringHeight("100");
		topologyInfo.setInneringWidth("100");
		topologyInfo.setObjectNm("testObject");
		topologyInfo.setObjectTypeCode("000");
		topologyInfo.setOrderOrder("1");
		topologyInfo.setObjectUpperCode("0");
		topologyInfo.setTopYn("N");
		topologyInfo.setRegUserEmail(USER_EMAIL);
		topologyInfo.setUptUserEmail(USER_EMAIL);
		topoDao.insert(topologyInfo);
		
		
		TopologyInfo updateInfo = new TopologyInfo (); 
		updateInfo.setObjectCode(objCodeForTest);
		updateInfo.setImgSeq(String.valueOf(22));
		topologyInfo.setImei("0000000000");
		topologyInfo.setInneringCoorX("100");
		topologyInfo.setInneringCoorY("100");
		topologyInfo.setInneringDepth("1");
		topologyInfo.setInneringHeight("100");
		topologyInfo.setInneringWidth("100");
		topologyInfo.setObjectNm("testObject");
		topologyInfo.setObjectTypeCode("000");
		topologyInfo.setOrderOrder("1");
		topologyInfo.setObjectUpperCode("0");
		topologyInfo.setTopYn("N");
		updateInfo.setUptUserEmail(USER_EMAIL);

		// when
		int updateResultCount = topoDao.update(updateInfo);

		// then
		assertThat(updateResultCount, is(not(0)));		
		TopologyInfo selectInfo = topoDao.select(objCodeForTest);
		assertNotNull(selectInfo);
		
		//test data 삭제
		int deleteResultCount = topoDao.delete(updateInfo);
		assertThat(deleteResultCount, is(not(0)));		

	}
	
	@Test
	public void testDelete() {
		try {
			// given 
			
			
			int backgroundImgSeq = imgDao.getBackgroundTopOneSeq();
			topologyInfo.setBackgroundImgSeq(String.valueOf(backgroundImgSeq));
			LOGGER.info("backgroundImgSeq : {}", backgroundImgSeq);
			
			topologyInfo.setObjectCode(objCodeForTest);
			topologyInfo.setImei("0000000000");
			topologyInfo.setInneringCoorX("100");
			topologyInfo.setInneringCoorY("100");
			topologyInfo.setInneringDepth("1");
			topologyInfo.setInneringHeight("100");
			topologyInfo.setInneringWidth("100");
			topologyInfo.setObjectNm("testObject");
			topologyInfo.setObjectTypeCode("000");
			topologyInfo.setOrderOrder("1");
			topologyInfo.setObjectUpperCode("0");
			topologyInfo.setTopYn("N");
			topologyInfo.setRegUserEmail(USER_EMAIL);
			topologyInfo.setUptUserEmail(USER_EMAIL);
			topoDao.insert(topologyInfo);

			// when
			int deleteResultCount = topoDao.delete(topologyInfo);

			// then
			assertThat(deleteResultCount, is(not(0)));		
			TopologyInfo selectInfo = topoDao.select(objCodeForTest);
			assertNull(selectInfo);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Test
	public void testSELECtList() throws Exception{

		// given 
		topologyInfo.setRegUserEmail(USER_EMAIL);
		topologyInfo.setObjectUpperCode(ROOT_OBJECTCODE);

		// when
		List result = topoDao.selectTopologyList(topologyInfo);

		// then
		assertThat(result.size(), is(not(0)));
		

	}
	
	@Test
	public void testSelectRootBackgroundImgSeq() throws Exception{
		
		// given 
		topologyInfo.setRegUserEmail(USER_EMAIL);
		topologyInfo.setObjectUpperCode(ROOT_OBJECTCODE);
		
		// when
		int result = topoDao.selectRootBackgroundImgSeq(USER_EMAIL);
		
	}

}
