package com.neonex.npa.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neonex.npa.exception.ImageException;
import com.neonex.npa.model.ImageInfo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class ImageServiceTest {
	
	
	private String TEST_IMG_PATH = "./src/test/resources/test_file/upload_test_img.jpg";
	private String OVER_SIZE_IMG_PATH = "./src/test/resources/test_file/over_size.jpg";
	
	private static final String BACKGROUND_IMAGE_TYPE = "0";
	private static final String USER_IMAGE_TYPE = "1";
	
	@Autowired
	private ImageService imageService;
	
	private MockMultipartHttpServletRequest request = null;
	
	@Before
	public void setUp(){
		request = new MockMultipartHttpServletRequest();
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : ImageProcessing 테스트 시나리오
	 *  1. MockMultipartHttpServletRequest 을 이용하여 파일 업로드된 상황을 만든다.
	 *  2. imageProcessing 을 호출 하고 호출된 결과 값이 null 이 아니여야 한다.
	 *  </pre>
	 * 	@Method testImageProcessing 
	 **********************************************/
	@Test
	public void testImageProcessing() throws Exception{

		// given 
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg",
				"image", new FileInputStream(TEST_IMG_PATH));
		request.addFile(testFile);
		request.addParameter("uploadType", BACKGROUND_IMAGE_TYPE);
		request.addParameter("regUserEmail", "tester");
		request.addParameter("uptUserEmail", "tester");

		// when
		ImageInfo processingResult = imageService.imageProcessing(request);

		// then
		assertNotNull(processingResult);
		
		// test data 삭제
		int deleteResultCount = imageService.deleteBackgroundImage(processingResult.getImgSeq());
		assertThat(deleteResultCount, is(not(0)));
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : NullUploadFile 테스트 시나리오
	 *  1. MockMultipartHttpServletRequest 을 이용하여 파일 업로드된 상황을 만든다.
	 *  2. 목업 객체를 생성시 업로드 파일은 추가 하지 않는다.
	 *  3. 업로드 대상 파일이 없으므로 ImageException 이 발생 해야 한다.
	 *  </pre>
	 * 	@Method testNullUploadFile 
	 *  @throws ImageException
	 **********************************************/
	@Test(expected=ImageException.class)
	public void testNullUploadFile() throws Exception{

		// when
		imageService.imageProcessing(request);
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : OverSizeImage 테스트 시나리오
	 *  1. MockMultipartHttpServletRequest 을 이용하여 파일 업로드된 상황을 만든다.
	 *  2. 목업 객체를 생성시 업로드 파일은 업로드 제한(5M) 보다 큰 파일을 추가한다.
	 *  3. 업로드 대상 파일이 제한 크기보다 크기때문에 ImageException 이 발생 해야 한다.
	 *  </pre>
	 * 	@Method testOverSizeImage 
	 *  @throws ImageException
	 **********************************************/
	@Test(expected=ImageException.class)
	public void testOverSizeImage() throws Exception{

		// given 
		MockMultipartFile testFile = new MockMultipartFile("over_size.jpg", "over_size.jpg",
				"image", new FileInputStream(OVER_SIZE_IMG_PATH));
		request.addFile(testFile);
		request.addParameter("uploadType", BACKGROUND_IMAGE_TYPE);
		request.addParameter("regUserEmail", "tester");
		request.addParameter("uptUserEmail", "tester");

		// when
		imageService.imageProcessing(request);
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : InvalidExtension 테스트 시나리오
	 *  1. MockMultipartHttpServletRequest 을 이용하여 파일 업로드된 상황을 만든다.
	 *  2. 목업 객체를 생성시 업로드 파일은 이미지 확장자가 아닌 파일로 한다.
	 *  3. 업로드 대상 파일이 이미지 파일 확장자가 아니므로 ImageException 이 발생 해야 한다.
	 *  </pre>
	 * 	@Method testInvalidExtension 
	 *  @throws ImageException
	 **********************************************/
	@Test(expected=ImageException.class)
	public void testInvalidExtension() throws Exception{

		// given 
		MockMultipartFile testFile = new MockMultipartFile("over_size.txt", "over_size.txt",
				"image", new FileInputStream(TEST_IMG_PATH));
		request.addFile(testFile);
		request.addParameter("uploadType", BACKGROUND_IMAGE_TYPE);
		request.addParameter("regUserEmail", "tester");
		request.addParameter("uptUserEmail", "tester");
			
		// when
		imageService.imageProcessing(request);

	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : InvalidContentsType 테스트 시나리오
	 *  1. MockMultipartHttpServletRequest 을 이용하여 파일 업로드된 상황을 만든다.
	 *  2. 목업 객체를 생성시 업로드 파일은 이미지 타입이 아닌 파일로 한다.
	 *  3. 업로드 대상 파일이 이미지 타입이 아니므로 ImageException 이 발생 해야 한다.
	 *  </pre>
	 * 	@Method testInvalidExtension 
	 *  @throws ImageException
	 **********************************************/
	@Test(expected=ImageException.class)
	public void testInvalidContentsType() throws Exception{
		
		// given 
		MockMultipartFile testFile = new MockMultipartFile("over_size.jpg", "over_size.jpg",
				"text", new FileInputStream(TEST_IMG_PATH));
		request.addFile(testFile);
		request.addParameter("uploadType", BACKGROUND_IMAGE_TYPE);
		request.addParameter("regUserEmail", "tester");
		request.addParameter("uptUserEmail", "tester");
		
		// when
		imageService.imageProcessing(request);
		
		
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : GetBackgroundImagePath 테스트 시나리오
	 *  1. MockMultipartHttpServletRequest 을 이용하여 파일 업로드된 상황을 만든다.
	 *  2. 테스트 할 이미지 파일을 insert 한다.
	 *  3. 리턴되는 값을 기준으로 해당 데이터가 있는지 확인한다.
	 *  </pre>
	 * 	@Method getBackgroundImagePath 
	 *  @throws Exception
	 **********************************************/
	@Test
	public void testGetBackgroundImagePath() throws Exception{

		// given 
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg",
				"image", new FileInputStream(TEST_IMG_PATH));
		request.addFile(testFile);
		request.addParameter("uploadType", BACKGROUND_IMAGE_TYPE);
		request.addParameter("regUserEmail", "tester");
		request.addParameter("uptUserEmail", "tester");
		ImageInfo processingResult = imageService.imageProcessing(request);
		assertNotNull(processingResult);
			
		// when
		byte[] backGroundImage= imageService.getBackgroundImagePath(String.valueOf(processingResult.getImgSeq()));

		// then
		assertNotNull(backGroundImage);
		if(backGroundImage.length == 0) fail();
		
		// test data 삭제
		int deleteResultCount = imageService.deleteBackgroundImage(processingResult.getImgSeq());
		assertThat(deleteResultCount, is(not(0)));

	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : UserImagePath 테스트 시나리오
	 *  1. MockMultipartHttpServletRequest 을 이용하여 파일 업로드된 상황을 만든다.
	 *  2. 테스트 할 이미지 파일을 insert 한다.
	 *  3. 리턴되는 값을 기준으로 해당 데이터가 있는지 확인한다.
	 *  </pre>
	 * 	@Method getUserImagePath 
	 *  @throws Exception
	 **********************************************/
	@Test
	public void testGetUserImagePath() throws Exception{
		
		// given 
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg",
				"image", new FileInputStream(TEST_IMG_PATH));
		request.addFile(testFile);
		request.addParameter("uploadType", USER_IMAGE_TYPE);
		request.addParameter("regUserEmail", "tester");
		request.addParameter("uptUserEmail", "tester");
		
		ImageInfo processingResult = imageService.imageProcessing(request);
		assertNotNull(processingResult);
			
		// when
		byte[] userImage= imageService.getUserImagePath(processingResult.getImgSeq());
		
		// then
		assertNotNull(userImage);
		if(userImage.length == 0) fail();
		
		// test data 삭제
		int deleteResultCount = imageService.deleteUserImage(processingResult.getImgSeq());
		assertThat(deleteResultCount, is(not(0)));
		
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : DeleteBackgroundImage 테스트 시나리오
	 *  1. MockMultipartHttpServletRequest 을 이용하여 파일 업로드된 상황을 만든다.
	 *  2. 테스트 할 이미지 파일을 insert 한다.
	 *  3. 리턴되는 값을 기준으로 해당 데이터를 deleteBackgroundImage 를 실행 한다.
	 *  4. 삭제된 정보를 조회 하였을 경우 ImageException(code:404) 가 발생 해야 한다.
	 *  </pre>
	 * 	@Method testDeleteBackgroundImage 
	 **********************************************/
	@Test
	public void testDeleteBackgroundImage() throws Exception{

		// given 
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg",
				"image", new FileInputStream(TEST_IMG_PATH));
		request.addFile(testFile);
		request.addParameter("uploadType", BACKGROUND_IMAGE_TYPE);
		request.addParameter("regUserEmail", "tester");
		request.addParameter("uptUserEmail", "tester");
		ImageInfo processingResult = imageService.imageProcessing(request);

		// when
		int deleteResultCount = imageService.deleteBackgroundImage(processingResult.getImgSeq());
		assertThat(deleteResultCount, is(not(0)));

		// then
		// 파일이 삭제 되었으므로 ImageException이 발생 해야 한다.
		// 다른 Exception 은 fail
		try {
			imageService.getBackgroundImagePath(String.valueOf(processingResult.getImgSeq()));
		}catch (ImageException e) {
			if(e.getCode() != 404) fail();
		}
		catch (Exception e) {
			fail();
		}

	}
	/**********************************************
	 *  <pre>
	 *  개요 : DeleteUserImage 테스트 시나리오
	 *  1. MockMultipartHttpServletRequest 을 이용하여 파일 업로드된 상황을 만든다.
	 *  2. 테스트 할 이미지 파일을 insert 한다.
	 *  3. 리턴되는 값을 기준으로 해당 데이터를 deleteUserImage 를 실행 한다.
	 *  4. 삭제된 정보를 조회 하였을 경우 ImageException(code:404) 가 발생 해야 한다.
	 *  </pre>
	 * 	@Method testDeleteUserImage 
	 *  @throws Exception
	 **********************************************/
	@Test
	public void testDeleteUserImage() throws Exception{
		
		// given 
		MockMultipartFile testFile = new MockMultipartFile("test.jpg", "test.jpg",
				"image", new FileInputStream(TEST_IMG_PATH));
		request.addFile(testFile);
		request.addParameter("uploadType", USER_IMAGE_TYPE);
		request.addParameter("regUserEmail", "tester");
		request.addParameter("uptUserEmail", "tester");
		ImageInfo processingResult = imageService.imageProcessing(request);
		
		// when
		int deleteResultCount = imageService.deleteUserImage(processingResult.getImgSeq());
		assertThat(deleteResultCount, is(not(0)));
		
		// then
		// 파일이 삭제 되었으므로 ImageException이 발생 해야 한다.
		// 다른 Exception 은 fail
		try {
			imageService.getUserImagePath(processingResult.getImgSeq());
		}catch (ImageException e) {
			if(e.getCode() != 404) fail();
		}
		catch (Exception e) {
			fail();
		}
		
	}
}
