package com.neonex.npa.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parboiled.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neonex.npa.model.ImageInfo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class ImageDaoTest {
	
	@Autowired
	private ImageDao imageDao;
	
	private ImageInfo imageInfoForTest;
	
	@Before
    public void setup() {
		imageInfoForTest = new ImageInfo();
		imageInfoForTest.setImgNm("test.jpg");
		imageInfoForTest.setOriginalLoc("/temp/testdir");;
		imageInfoForTest.setThumbnailLoc("/temp/thumbnaildir");
		imageInfoForTest.setRegUserEmail("tester");
		imageInfoForTest.setUptUserEmail("tester");
    }

	/**********************************************
	 *  <pre>
	 *  개요 : InsertBackgroundImg 테스트 시나리오
	 *  1. Background 이미지 정보를 DB 에 insert 한다.
	 *  2. 리턴되는 ResultCount 가 0 이면 안된다.
	 *  3. test data 를 삭제 한다.
	 *  </pre>
	 * 	@Method testInsertBackgroundImg 
	 *  @throws Exception
	 **********************************************/
	@Test
	public void testInsertBackgroundImg() throws Exception{

		// when
		int insertResultCount = imageDao.insertBackgroundImg(imageInfoForTest);

		// then
		assertThat(insertResultCount, is(not(0)));
		
		// Test data 삭제
		int deleteResultCount = imageDao.deleteBackgroundImg(imageInfoForTest.getRegUserEmail());
		assertThat(deleteResultCount, is(not(0)));
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : InsertUserImg 테스트 시나리오
	 *  1. UserImage 정보를 DB 에 insert 한다.
	 *  2. 리턴되는 ResultCount 가 0 이면 안된다.
	 *  3. test data 를 삭제 한다.
	 *  </pre>
	 * 	@Method testInsertSubImg 
	 *  @throws Exception
	 **********************************************/
	@Test
	public void testInsertUserImg() throws Exception{

		// when
		int insertResultCount = imageDao.insertUserImg(imageInfoForTest);

		// then
		assertThat(insertResultCount, is(not(0)));
		
		// Test data 삭제
		int deleteResultCount = imageDao.deleteUserImg(imageInfoForTest.getImgSeq());
		assertThat(deleteResultCount, is(not(0)));
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : GetBackgroundImg 테스트 시나리오
	 *  1. Background 이미지 정보를 DB 에 insert 한다.
	 *  2. 리턴되는 ImgSeq 를 기반으로 조회를 하였을 경우 Null 이 아니여야 한다.
	 *  3. test data 를 삭제 한다.
	 *  </pre>
	 * 	@Method testGetBackgroundImg 
	 *  @throws Exception
	 **********************************************/
	@Test
	public void testGetBackgroundImg() throws Exception{

		// given(이미지 추가)
		imageDao.insertBackgroundImg(imageInfoForTest);

		// when
		ImageInfo resultInfo = imageDao.getBackgroundImageInfo(imageInfoForTest.getImgSeq());

		// then
		assertNotNull(resultInfo);
		// Test data 삭제
		int deleteResultCount = imageDao.deleteBackgroundImg(imageInfoForTest.getRegUserEmail());
		assertThat(deleteResultCount, is(not(0)));

	}

	/**********************************************
	 *  <pre>
	 *  개요 : GetUserImg 테스트 시나리오
	 *  1. UserImg 이미지 정보를 DB 에 insert 한다.
	 *  2. 리턴되는 ImgSeq 를 기반으로 조회를 하였을 경우 Null 이 아니여야 한다.
	 *  3. test data 를 삭제 한다.
	 *  </pre>
	 * 	@Method testGetUserImg 
	 *  @throws Exception
	 **********************************************/
	@Test
	public void testGetUserImg() throws Exception{
		
		imageDao.insertUserImg(imageInfoForTest);
		
		// when
		ImageInfo resultInfo = imageDao.getUserImageInfo(imageInfoForTest.getImgSeq());

		// then
		assertNotNull(resultInfo);
		
		
		// Test data 삭제
		int deleteResultCount = imageDao.deleteUserImg(imageInfoForTest.getImgSeq());
		assertThat(deleteResultCount, is(not(0)));
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : DeleteBackgroundImg 테스트 시나리오
	 *  1. Background 이미지 정보를 DB 에 insert 한다.
	 *  2. insert 된 정보를 삭제를 한다.
	 *  3. 삭제된 정보를 조회 하였을 경우 값이 없어야 한다.
	 *  </pre>
	 * 	@Method testDeleteBackgroundImg 
	 *  @throws Exception
	 **********************************************/
	@Test
	public void testDeleteBackgroundImg() throws Exception{

		int insertResultCount = imageDao.insertBackgroundImg(imageInfoForTest);
		assertThat(insertResultCount, is(not(0)));

		// when
		int deleteResultCount = imageDao.deleteBackgroundImg(imageInfoForTest.getRegUserEmail());

		// then
		assertThat(deleteResultCount, is(not(0)));
		ImageInfo resultInfo = imageDao.getBackgroundImageInfo(imageInfoForTest.getImgSeq());
		assertNull(resultInfo);
		
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : 테스트 시나리오
	 *  1. UserImage 이미지 정보를 DB 에 insert 한다.
	 *  2. insert 된 정보를 삭제를 한다.
	 *  3. 삭제된 정보를 조회 하였을 경우 값이 없어야 한다.
	 *  </pre>
	 * 	@Method testDeleteUserImage 
	 *  @throws Exception
	 **********************************************/
	@Test
	public void testDeleteUserImage() throws Exception{
		
		int insertResultCount = imageDao.insertUserImg(imageInfoForTest);
		assertThat(insertResultCount, is(not(0)));
		
		// when
		int deleteResultCount = imageDao.deleteUserImg(imageInfoForTest.getImgSeq());
		
		// then
		assertThat(deleteResultCount, is(not(0)));
		ImageInfo resultInfo = imageDao.getUserImageInfo(imageInfoForTest.getImgSeq());
		assertNull(resultInfo);
	}
	
	@Test
	public void testGetBackgroundImageInfoByRegUser() throws Exception{

		// given 
		String userEmail = "tester";
		int insertResultCount = imageDao.insertBackgroundImg(imageInfoForTest);
		assertThat(insertResultCount, is(not(0)));

		// when
		List<ImageInfo> imageInfo = imageDao.getBackgroundImageInfoByRegUser(userEmail);

		// then
		assertNotNull(imageInfo);
		
		// test data 삭제
		 imageDao.deleteUserImg(imageInfoForTest.getImgSeq());
		

	}
}
