package com.neonex.npa.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neonex.npa.dao.TopologyDao;
import com.neonex.npa.dao.TopologyRelationDaoTest;
import com.neonex.npa.model.TopologyInfo;
import com.neonex.npa.model.TopologyRelationInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class TopologyRelationServiceTest {
	
	@Autowired
	private TopologyRelationService relService;
	
	@Autowired
	private TopologyDao topologyDao;
	
	private TopologyRelationInfo relInfo;
	
	private final static Logger LOGGER =  LoggerFactory.getLogger(TopologyRelationServiceTest.class);
	
	private final String USER_EMAIL = "tester";
	
	private String objectCodeHasRelation = null;
	
	private final String LINE_TYPE_CODE = "111";
	
	private final String ROOT_OBJECT_CODE = "1111";
	
	private String LINE_COLOR = "#FFFFFF";
	
	@Before
	public void setup(){
		relInfo = new TopologyRelationInfo();
	}

	@Test
	public void testInsert() {
		try {
			// when
			boolean insertResult = insertRelation();
			
			// then
			assertTrue(insertResult);
			
			// testdata 삭제
			int deleteCount = relService.deleteByObjectCode(relInfo.getObjectCode());
			assertThat(deleteCount, is(not(0)));
			
		} catch (Exception e) {
			LOGGER.error("insert test error", e);
			fail();
		}
	}
	
	@Test
	public void testSelectList(){
		try{
			// given
			insertRelation();
			
			//when
			List<TopologyRelationInfo> relList = relService.selectList(relInfo);
			
			//then
			assertThat(relList.size(), is(not(0)));
			
		}catch(Exception e){
			LOGGER.error("testSelectList error", e);
			fail();
		}
	}
	
	@Test
	public void testDeleteByObjectCode(){
		try {
			insertRelation();
			int deleteCount = relService.deleteByObjectCode(relInfo.getObjectCode());
			assertThat(deleteCount, is(not(0)));
			
		} catch (Exception e) {
			LOGGER.error("testDeleteByObjectCode error", e);
			fail();
		}
	}
	
	@Test
	public void testUpdate(){
		try{
			//given
			insertRelation();
			relInfo.setLineWidth(100);
			
			//when
			boolean updateResult = relService.update(relInfo);
			
			//then
			assertTrue(updateResult);
			
		}catch(Exception e){
			LOGGER.error("testUpdate error", e);
			fail();
		}
		
	}
	
	
	
	private void setInsertInfo(TopologyRelationInfo relationInfo, String objectCode, String relationObjectCode, String userEmail){
		relationInfo.setObjectCode(""+objectCode);
		relationInfo.setRelationObjectCode(""+relationObjectCode);
		relationInfo.setRegUserEmail(userEmail);
		relationInfo.setLineTypeCode(LINE_TYPE_CODE);
		relationInfo.setLineWidth(10);
		relationInfo.setRelationLineColor(LINE_COLOR);
	}
	
	private List<TopologyInfo> getTopologyInfoList() throws Exception {
		TopologyInfo topologInfo = new TopologyInfo();
		topologInfo.setRegUserEmail(USER_EMAIL);
		topologInfo.setObjectUpperCode(ROOT_OBJECT_CODE);
		List<TopologyInfo> topologyList = topologyDao.selectTopologyList(topologInfo);
		return topologyList;
	}
	
	private boolean insertRelation(){
		try {
			// 먼저 Object list 조회
			// relation 은 object 가 두개 이상이 되어야 하므로 topology list 가 1 이하 이면 skip
			List<TopologyInfo> topologyList = getTopologyInfoList();
			if(topologyList != null && topologyList.size() > 1 ){
				LOGGER.info("{}", topologyList);
				
				objectCodeHasRelation = topologyList.get(1).getObjectCode();
				setInsertInfo(relInfo, topologyList.get(1).getObjectCode(), topologyList.get(0).getObjectCode(),USER_EMAIL);
				return relService.insert(relInfo);
			}else{
				return false;
			}
			
		} catch (Exception e) {
			LOGGER.error("insert test error", e);
			fail();
			return false;
		}
	}

}
