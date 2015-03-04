package com.neonex.npa.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import oracle.jdbc.driver.DatabaseError;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.parboiled.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neonex.npa.controller.ImageController;
import com.neonex.npa.dao.ImageDao;
import com.neonex.npa.exception.ImageException;
import com.neonex.npa.model.ImageInfo;

/*****************************************************************************
 * 
 * 	Image Resize 서비스로 가듯
 *  @packageName : com.neonex.npa.service
 *  @fileName : ImageService.java
 *  @author : jisoon
 *  @since 2014. 7. 11.
 *  @version 1.0 
 *  @see  :  
 *  @revision : 2014. 7. 11.
 *  
 *  <pre>
 *  << Modification Information >>
 *    DATE	           NAME			DESC
 *     -----------	 ----------   ---------------------------------------
 *     2014. 7. 11.        jisoon       create ImageService.java
 *  </pre>
 ******************************************************************************/
@Service
public class ImageService {
	
	@Value("#{config['dir.img.resized']}")
	private String RESIEZED_SAVE_DIR;
	
	@Value("#{config['img.limit.size']}")
	private int LIMIT_UPLOAD_SIZE;
	
	@Value("#{config['img.extensions']}")
	private String ABLE_EXTENSION;
	
	@Value("#{config['img.resize.main.width']}")
	private int BACKGROUND_RESIZE_WIDTH;
	
	@Value("#{config['img.resize.main.height']}")
	private int BACKGROUND_RESIZE_HEIGHT;
	
	@Value("#{config['img.resize.sub.width']}")
	private int USER_IMG_RESIZE_WIDTH;
	
	@Value("#{config['img.resize.sub.height']}")
	private int USER_IMG_RESIZE_HEIGHT;
	
	@Value("#{config['img.resize.thumb.width']}")
	private int THUMB_RESIZE_WIDTH;
	
	@Value("#{config['img.resize.thumb.height']}")
	private int THUMB_RESIZE_HEIGHT;
	
	@Value("#{config['dir.img.thumbnail']}")
	private String THUMBNAIL_SAVE_DIR;
	
	private int resizedWidth = BACKGROUND_RESIZE_WIDTH;
	private int resizedHeight = BACKGROUND_RESIZE_HEIGHT;
	
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private TopologyService topologyService;
	
	private static final String MAIN_UPLOAD_TYPE = "0";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);
	
	public ImageService(){
		
	}
	
	public ImageService(String resizedSaveDir, String thumbnailSaveDir){
		// thumbnail 저장 디렉토리 생성 
		File thumbnailDirectory = new File(thumbnailSaveDir);
		if(!thumbnailDirectory.exists()){
			try {
				if(thumbnailDirectory.mkdirs()){
					LOGGER.debug("thumbnail directory is created");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// resized 저장 디렉토리 생성
		File resizedDirectory = new File(resizedSaveDir);
		if(!resizedDirectory.exists()){
			try {
				if(resizedDirectory.mkdirs()){
					LOGGER.debug("Resized directory is created");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**********************************************
	 *  <pre>
	 *  개요 : 가로 세로 파라미터로 리사이즈 하는 메소드
	 *  </pre>
	 * 	@Method resize 
	 *  @param request
	 *  @param width
	 *  @param height
	 *  @return
	 *  @throws ImageException
	 **********************************************/
	public ImageInfo imageProcessing(MultipartHttpServletRequest request) throws ImageException{
		
		String uploadType = request.getParameter("uploadType");
		if(StringUtils.isEmpty(uploadType)){
			uploadType = MAIN_UPLOAD_TYPE;
		}
		
		if(isBackGroundUpload(uploadType)){
			resizedWidth = BACKGROUND_RESIZE_WIDTH;
			resizedHeight = BACKGROUND_RESIZE_HEIGHT;		
		}else{
			resizedWidth = USER_IMG_RESIZE_WIDTH;
			resizedHeight = USER_IMG_RESIZE_HEIGHT;		
		}
		
		Iterator<String> requestFiles = request.getFileNames();
		
		ImageInfo resizeInfo = new ImageInfo();
		if(!hasImage(requestFiles)){
			throw new ImageException(1000, "업로드 이미지가 없습니다.");
		}else{
			MultipartFile mpf = request.getFile(requestFiles.next());
			
			// 디스크 용량 체크
			if(isFullDisk(mpf)){				
				throw new ImageException(1001, "저장 할 수 있는 디스크 여유 공간이 없습니다.");
			}
			
			// 파일용량 제한
			if(isOverSizeImage(mpf)){ 
				throw new ImageException(1002, "업로드 제한 크기보다 큰 파일 입니다.");
			}
			
			// 이미지 여부 체크
			// 이미지 타입 체크가 있음 확인 필요
			if(isNotImage(mpf)){
				throw new ImageException(1003, "이미지 파일이 아닙니다.");
			}
			
			try {
				Map<String, String> resizedImageInfo = resize(resizedWidth, resizedHeight, RESIEZED_SAVE_DIR, mpf);
				Map<String, String> thumbImageInfo = resize(THUMB_RESIZE_WIDTH, THUMB_RESIZE_HEIGHT, THUMBNAIL_SAVE_DIR, mpf);
				
				String userEmail = (String)request.getSession().getAttribute("userEmail");
				resizeInfo.setOriginalLoc(resizedImageInfo.get("imageLoc"));
				resizeInfo.setThumbnailLoc(thumbImageInfo.get("imageLoc"));
				resizeInfo.setImgNm(resizedImageInfo.get("resizedName"));
				resizeInfo.setRegUserEmail(userEmail);
				resizeInfo.setUptUserEmail(userEmail);
				
				// DB insert
				if(isBackGroundUpload(uploadType)){
					// 기존 background 이미지가 있을 경우 삭제
					//deleteAleadyBackgroundImage(resizeInfo.getRegUserEmail());
					imageDao.insertBackgroundImg(resizeInfo);
					
					// Exception 처리는 우짜지...
					// insert 된 background-image를 변경
					if(StringUtils.isEmpty(userEmail)){
						// 로그인 에러 처리 필요
					}else{
						String objectCode = request.getParameter("objectCode");
						topologyService.updateBackgroundImg(objectCode, userEmail, String.valueOf(resizeInfo.getImgSeq()));
					}
				}else{
					imageDao.insertUserImg(resizeInfo);
				}
				
				// resultCode setting
				resizeInfo.setCode(200);
				resizeInfo.setMsg("ok");
				
			} catch (IOException e) {
				LOGGER.debug("resize exception {}", e);
				throw new ImageException(1004, "이미지 리사이징 중에 에러가 발생 하였습니다.");
			} catch (Exception e) {
				// DB insert Error 발생시 해당 파일을 삭제
				e.printStackTrace();
				throw new ImageException(500, "이미지 정보 DB insert 중 에러 발생");
			}
		}
		return resizeInfo;
	}
	
	

	public byte[] getBackgroundImagePath(String imgSeq) throws ImageException{
		try {
			ImageInfo resultInfo = imageDao.getBackgroundImageInfo(Integer.parseInt(imgSeq));
			return IOUtils.toByteArray(new FileInputStream(resultInfo.getOriginalLoc()));
		} catch (Exception e) {
			throw new ImageException(404, "이미지를 조회 할 수 없습니다.");
		}
	}
	
	public byte[] getUserImagePath(int imgSeq) throws ImageException{
		try {
			ImageInfo resultInfo = imageDao.getUserImageInfo(imgSeq);
			
			return IOUtils.toByteArray(new FileInputStream(resultInfo.getOriginalLoc()));
		} catch (Exception e) {
			//LOGGER.error("get user image path error ", e);
			throw new ImageException(404, "이미지를 조회 할 수 없습니다.");
		} finally{
			
		}
	}
	
	public byte[] getCanvasUserImagePath(int imgSeq) throws ImageException{
		try {
			String imagPath = imageDao.getUserImagePath(imgSeq);
			
			return IOUtils.toByteArray(new FileInputStream(imagPath));
		} catch (Exception e) {
			//LOGGER.error("get user image path error ", e);
			throw new ImageException(404, "이미지를 조회 할 수 없습니다.");
		} finally{
			
		}
	}
	
	public String getUserImagePathString(int imgSeq) throws ImageException{
		try {
			ImageInfo resultInfo = imageDao.getUserImageInfo(imgSeq);
			
			return resultInfo.getOriginalLoc();
		} catch (Exception e) {
			LOGGER.error("get user image path error ", e);
			throw new ImageException(404, "이미지를 조회 할 수 없습니다.");
		} finally{
			
		}
	}
	
	
	public int deleteBackgroundImage(int imgSeq){
		try {
			ImageInfo imageInfoForDelete = imageDao.getBackgroundImageInfo(imgSeq);
			
			// DB 삭제
			imageDao.deleteBackgroundImg(imageInfoForDelete.getRegUserEmail());
			
			// 파일 삭제
			deleteFile(imageInfoForDelete);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	
	public int deleteUserImage(int imgSeq) {
		try {
			ImageInfo imageInfoForDelete = imageDao.getUserImageInfo(imgSeq);
			
			
			// flag 값만 변경
			imageDao.deleteUserImg(imgSeq);
			
			
			// DB 삭제
			// 파일 삭제
			//deleteFile(imageInfoForDelete);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private void deleteFile(ImageInfo imageInfo) throws Exception{
		File originFile = new File(imageInfo.getOriginalLoc());
		originFile.delete();
		
		File thumbFile = new File(imageInfo.getThumbnailLoc());
		thumbFile.delete();
	}
	
	private void deleteAleadyBackgroundImage(String regUserEmail) throws Exception{
		List<ImageInfo> resultInfo = imageDao.getBackgroundImageInfoByRegUser(regUserEmail);
		if(resultInfo != null || resultInfo.size() >0 ){
			imageDao.deleteBackgroundImg(regUserEmail);
		}
	}

	private boolean isBackGroundUpload(String uploadType) {
		return uploadType.equals(MAIN_UPLOAD_TYPE);
	}
	
	/**********************************************
	 *  <pre>
	 *  개요 : 이미지 리사이즈 내부 메소드
	 *  </pre>
	 * 	@Method resize 
	 *  @param width
	 *  @param height
	 *  @param savePath
	 *  @param mpf
	 *  @return Map(resizedName, imageLoc)
	 *  @throws IOException
	 **********************************************/
	private Map<String, String> resize(int width, int height, String savePath, MultipartFile mpf) throws IOException {
		Map<String, String> resizedResult = new HashMap<String, String>();
		String requestFileName = mpf.getOriginalFilename();
		String fileExtension = requestFileName.substring(requestFileName.length()-3).toLowerCase();
		BufferedImage resizeBufferImg = null;
		
		// JPG 이미지 처리 성능을 위해 분기처리
		if(fileExtension.equals("jpg") || fileExtension.equals("jpeg")){
			Image requestImage = new ImageIcon(mpf.getBytes()).getImage();
			resizeBufferImg = Scalr.resize(toBufferedImage(requestImage), Mode.FIT_EXACT, width, height, Scalr.OP_ANTIALIAS);;
		}else{
			resizeBufferImg = Scalr.resize(ImageIO.read(mpf.getInputStream()), Mode.FIT_EXACT, width, height, Scalr.OP_ANTIALIAS);;
		}
//		BufferedImage bufferImg = ImageIO.read(mpf.getInputStream()); // load image in buffer
		String resizedName = makeFileNameToSave(requestFileName, fileExtension);
		String saveImageLoc = savePath+resizedName;
		
		File resizedFile = new File(saveImageLoc);
		ImageIO.write(resizeBufferImg, fileExtension, resizedFile);
		resizedResult.put("resizedName", resizedName);
		resizedResult.put("imageLoc", saveImageLoc);
		
		return resizedResult;
	}
	
	private BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

	
	private boolean hasImage(Iterator<String> requestFile){
		if(requestFile.hasNext()){
			return true;
		}else{
			return false;
		}
	}

	private boolean isNotImage(MultipartFile mpf) {
		String fileContentsType = mpf.getContentType(); 
		String fileExt = mpf.getOriginalFilename();
		String fileExtension = fileExt.substring(fileExt.length()-3).toLowerCase();
		
		if(StringUtils.isEmpty(fileExtension) || !ABLE_EXTENSION.contains(fileExtension)
				|| !fileContentsType.contains("image")){
			return true;
		}else{
			return false;
		}
	}

	private boolean isOverSizeImage(MultipartFile mpf) {
		if(mpf.getSize()/1024/1024 > LIMIT_UPLOAD_SIZE){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isFullDisk(MultipartFile mpf) {
		File file = new File(RESIEZED_SAVE_DIR);
		if(file.getFreeSpace() < mpf.getSize()){
			LOGGER.debug("Free space : {}", file.getFreeSpace());
			LOGGER.debug("File Size : {}", mpf.getSize());
			return true;
		}else{
			return false;
		}
	}
	
	private String makeFileNameToSave(String requestFileName, String fileExtension){
		Date now = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmssZ");
		String originFileName = requestFileName.substring(0, requestFileName.length()-4).toLowerCase();
		return originFileName+"_"+formater.format(now)+"."+fileExtension;
	}

	public List<Map<String, Object>> getDefaultImageSeqList() throws Exception{
		return imageDao.getDefaultImageSeqList();
	}

	public List<Integer> getUserImageSeqList(String userEmail) throws Exception{
		return imageDao.getUserImageSeqList(userEmail);
	}

	
}
