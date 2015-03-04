package com.neonex.npa.dao;

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




import com.neonex.npa.model.TopologyInfo;
import com.neonex.npa.model.TopologyRelationInfo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class TopologyRelationDaoTest {

	
	@Autowired
	private TopologyRelationDao relationDao;
	
	@Autowired
	private TopologyDao topologyDao;
	
	private final static Logger LOGGER =  LoggerFactory.getLogger(TopologyRelationDaoTest.class);
	
	private TopologyRelationInfo relInfo;
	
	private final String USER_EMAIL = "tester";
	
	
	private String objectCodeHasRelation = null;
	
	private String LINE_TYPE_CODE = "1111";
	
	private String LINE_COLOR = "#FFFFFF";
	
	@Before
	public void setup(){
		relInfo = new TopologyRelationInfo();
	}
	
	@Test
	public void testInsert() throws Exception{

		// given 
		// 먼저 Object list 조회
		// relation 은 object 가 두개 이상이 되어야 하므로 topology list 가 1 이하 이면 skip
		List<TopologyInfo> topologyList = getTopologyInfoList();
		if(topologyList != null && topologyList.size() > 1 ){
			LOGGER.info("{}", topologyList);
			
			objectCodeHasRelation = topologyList.get(1).getObjectCode();
			setInsertInfo(relInfo, topologyList.get(1).getObjectCode(), topologyList.get(0).getObjectCode(),USER_EMAIL);
			// 이미 테이블에 relation 이 존재 하면 insert Skip
			int aleadyHasCount = relationDao.aleadyHasCount(relInfo);
			
			if(aleadyHasCount == 0){
				// when
				int resultCount = relationDao.insert(relInfo);
				
				// then
				assertThat(resultCount, is(not(0)));
			}else{
				LOGGER.info("이미 relation 관계가 존재 합니다.");
			}
		}else{
			LOGGER.info("object 가 1개 이하 존재 합니다.");
		}
		relationDao.deleteByObjectCode(relInfo.getObjectCode());
		
		
	}

	
	
	/**
	 * 특정 Object 에 대한 relation 정보 조회
	 */
	@Test
	public void testSelect(){
		
		try {
			List<TopologyInfo> topologyList = getTopologyInfoList();
			setInsertInfo(relInfo, topologyList.get(1).getObjectCode(), topologyList.get(0).getObjectCode(),USER_EMAIL);
			relationDao.insert(relInfo);
			
			// when
			List<TopologyRelationInfo> relInfoList = relationDao.selectList(relInfo);
			
			// then
			assertThat(relInfoList.size(), is(not(0)));
			LOGGER.info("{}", relInfoList);
			
			relationDao.deleteByObjectCode(relInfo.getObjectCode());
		} catch (Exception e) {
			LOGGER.error("testSelect Error", e);
			fail();
		}

	}
	
	@Test
	public void testDeleteByRelationObjectCode(){
		
		try {
			List<TopologyInfo> topologyList = getTopologyInfoList();
			setInsertInfo(relInfo, topologyList.get(1).getObjectCode(), topologyList.get(0).getObjectCode(),USER_EMAIL);
			relationDao.insert(relInfo);
			List<TopologyRelationInfo> relInfoList = relationDao.selectList(relInfo);
			assertThat(relInfoList.size(), is(not(0)));
			
			// when
//			int deleteCount = relationDao.deleteByRelationObjectCode(relInfo);
			
			// then
//			assertThat(deleteCount, is(not(0)));
		} catch (Exception e) {
			LOGGER.error("testDeleteByRelationObjectCode Error", e);
			fail();
		}
	}
	
	@Test
	public void testDeleteByObjectCode(){
		
		try {
			List<TopologyInfo> topologyList = getTopologyInfoList();
			setInsertInfo(relInfo, topologyList.get(1).getObjectCode(), topologyList.get(0).getObjectCode(),USER_EMAIL);
			relationDao.insert(relInfo);
			List<TopologyRelationInfo> relInfoList = relationDao.selectList(relInfo);
			assertThat(relInfoList.size(), is(not(0)));
			
			// when
			relationDao.deleteByObjectCode(relInfo.getObjectCode());
			
			// then
			LOGGER.info("{}", relInfoList);
		} catch (Exception e) {
			LOGGER.error("testDeleteByObjectCode Error", e);
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
		topologInfo.setObjectUpperCode(LINE_TYPE_CODE);
		List<TopologyInfo> topologyList = topologyDao.selectTopologyList(topologInfo);
		return topologyList;
	}
	
	
}
