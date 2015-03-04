package com.neonex.npa.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class TopologyServiceTest {
	
	
	
	@Autowired
	private TopologyService topologyService;
	
	private String userEmail;
	
	
	@Before
	public void setUp(){
		userEmail = "tester";
	}
	
	@Test
	public void testMakeRoot() throws Exception{

		// given

		// when
		int resultCount = topologyService.makeRootIfNull(userEmail);

		// then
		assertThat(resultCount, is(not(0)));
		
		// test data 삭제
		int deleteCount = topologyService.deleteRoot(userEmail);
		assertThat(deleteCount, is(not(0)));
		
		
	}
	
	@Test
	public void testDeleteRoot() throws Exception{

		// given 
		topologyService.makeRootIfNull(userEmail);

		// when
		int resultCount = topologyService.deleteRoot(userEmail);

		// then
		assertThat(resultCount, is(not(0)));
		

	}
	
}
