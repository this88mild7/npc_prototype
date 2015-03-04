package com.neonex.npa.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.io.FileInputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.neonex.npa.model.ImageInfo;

import static org.springframework.test.web.server.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers. *;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class ImageControllerTest {
	
	
	@Autowired
	private ImageController imageController;
	
	private MockMvc mockMvc;

	private String TEST_IMG_PATH = "./src/test/resources/test_file/1.png";
	private String TEST_IMG_PATH_JPG = "./src/test/resources/test_file/upload_test_img.jpg";
	private String OVER_SIZE_IMG_PATH = "./src/test/resources/test_file/over_size.jpg";
	
	private static final String BACKGROUND_IMAGE_TYPE = "0";
	private static final String USER_IMAGE_TYPE = "1";
	
	@Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }
	
	@Test
	public void testDragNdrop() throws Exception{
		mockMvc.perform(get("/image/dragDrop"))
		.andExpect(status().isOk())
		;
	}
	
	@Test
	public void testMainUpload() throws Exception{
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg", "image", new FileInputStream(TEST_IMG_PATH_JPG));
		MvcResult result = mockMvc.perform(fileUpload("/image/upload")
							.file(testFile)
							.param("uploadType", BACKGROUND_IMAGE_TYPE)
							.param("regUserEmail", "tester")
							.param("uptUserEmail", "tester")
							)
				    .andExpect(status().isOk())
				    .andExpect(jsonPath("$.imgSeq", is(not(0))))
				    .andDo(print())
				    .andReturn();
		String response = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		ImageInfo imageInfo = mapper.readValue(response, ImageInfo.class);
		
		//test data 삭제
		mockMvc.perform(get("/image/delete/background/"+imageInfo.getImgSeq()))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void testOverSizeFileUpload() throws Exception{

		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg", "image", new FileInputStream(TEST_IMG_PATH));
		mockMvc.perform(fileUpload("/image/upload")
							.file(testFile)
							.param("uploadType", BACKGROUND_IMAGE_TYPE)
							.param("regUserEmail", "tester")
							.param("uptUserEmail", "tester")
							)
	    .andExpect(status().isOk())
	    .andExpect(jsonPath("$.code", is(not(200))))
	    .andDo(print());
	}
	
	@Test
	public void testInvaildFileExtension() throws Exception{
		
		MockMultipartFile testFile = new MockMultipartFile("test.jpf", "test.jpf", "image", new FileInputStream(TEST_IMG_PATH));
		mockMvc.perform(fileUpload("/image/upload")
				.file(testFile)
				.param("uploadType", BACKGROUND_IMAGE_TYPE)
				.param("regUserEmail", "tester")
				.param("uptUserEmail", "tester")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(not(200))))
				.andDo(print());
	}
	
	@Test
	public void testInvaildFileContentsType() throws Exception{
		
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg", "text", new FileInputStream(TEST_IMG_PATH));
		mockMvc.perform(fileUpload("/image/upload")
				.file(testFile)
				.param("uploadType", BACKGROUND_IMAGE_TYPE)
				.param("regUserEmail", "tester")
				.param("uptUserEmail", "tester")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(not(200))))
				.andDo(print());
	}
	
	@Test
	public void testGetBackgrounImage() throws Exception{

		// given 
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg", "image", new FileInputStream(TEST_IMG_PATH));
		MvcResult result = mockMvc.perform(fileUpload("/image/upload")
							.file(testFile)
							.param("uploadType", BACKGROUND_IMAGE_TYPE)
							.param("regUserEmail", "tester")
							.param("uptUserEmail", "tester")
							)
				    .andExpect(status().isOk())
				    .andExpect(jsonPath("$.imgSeq", is(not(0))))
				    .andDo(print())
				    .andReturn();
		String response = result.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		ImageInfo imageInfo = mapper.readValue(response, ImageInfo.class);

		// when
		MvcResult resultGetBackground = mockMvc.perform(get("/image/get/background/"+imageInfo.getImgSeq()))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(resultGetBackground.getResponse().getContentAsByteArray()); 
		if(resultGetBackground.getResponse().getContentAsByteArray().length == 0) fail();
		
		// test data 삭제
		mockMvc.perform(get("/image/delete/background/"+imageInfo.getImgSeq()))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testGetUserImage() throws Exception{
		
		// given 
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg", "image", new FileInputStream(TEST_IMG_PATH));
		MvcResult result = mockMvc.perform(fileUpload("/image/upload")
				.file(testFile)
				.param("uploadType", USER_IMAGE_TYPE)
				.param("regUserEmail", "tester")
				.param("uptUserEmail", "tester")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.imgSeq", is(not(0))))
				.andDo(print())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		ImageInfo imageInfo = mapper.readValue(response, ImageInfo.class);
		
		// when
		MvcResult resultGetBackground = mockMvc.perform(get("/image/get/userimg/"+imageInfo.getImgSeq()))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(resultGetBackground.getResponse().getContentAsByteArray()); 
		if(resultGetBackground.getResponse().getContentAsByteArray().length == 0) fail();
		
		// test data 삭제
		mockMvc.perform(get("/image/delete/userimg/"+imageInfo.getImgSeq()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.code", is(200)))
		;
	}
	
	@Test
	public void testDeleteBackgroundImage() throws Exception{
		
		// given 
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg", "image", new FileInputStream(TEST_IMG_PATH));
		MvcResult result = mockMvc.perform(fileUpload("/image/upload")
				.file(testFile)
				.param("uploadType", BACKGROUND_IMAGE_TYPE)
				.param("regUserEmail", "tester")
				.param("uptUserEmail", "tester")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.imgSeq", is(not(0))))
				.andDo(print())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		ImageInfo imageInfo = mapper.readValue(response, ImageInfo.class);
		
		// when
		mockMvc.perform(get("/image/delete/background/"+imageInfo.getImgSeq()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.code", is(200)));
		
		mockMvc.perform(get("/image/get/background/"+imageInfo.getImgSeq()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(404)));
	}
	
	@Test
	public void testDeleteUserImage() throws Exception{
		
		// given 
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg", "image", new FileInputStream(TEST_IMG_PATH));
		MvcResult result = mockMvc.perform(fileUpload("/image/upload")
				.file(testFile)
				.param("uploadType", USER_IMAGE_TYPE)
				.param("regUserEmail", "tester")
				.param("uptUserEmail", "tester")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.imgSeq", is(not(0))))
				.andDo(print())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		ImageInfo imageInfo = mapper.readValue(response, ImageInfo.class);
		
		// when
		mockMvc.perform(get("/image/delete/userimg/"+imageInfo.getImgSeq()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.code", is(200)));
		
		mockMvc.perform(get("/image/get/userimg/"+imageInfo.getImgSeq()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(404)));
	}
	

}
