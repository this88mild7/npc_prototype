package com.neonex.npa.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.neonex.npa.model.ImageInfo;

@Repository
public interface ImageDao {

	public int insertBackgroundImg(ImageInfo imageInfo) throws Exception;

	public int insertUserImg(ImageInfo imageInfo) throws Exception;

	public int deleteBackgroundImg(String regUserEmail) throws Exception;

	public int deleteUserImg(int imgSeq) throws Exception;
	
	public String getUserImagePath(int imgSeq) throws Exception;

	public ImageInfo getBackgroundImageInfo(int imgSeq) throws Exception;

	public ImageInfo getUserImageInfo(int imgSeq) throws Exception;

	public List<ImageInfo> getBackgroundImageInfoByRegUser(String regUserEmail) throws Exception;

	public List<Map<String, Object>> getDefaultImageSeqList() throws Exception;

	public List<Integer> getUserImageSeqList(String userEmail) throws Exception;

	public int getBackgroundTopOneSeq() throws Exception;

}
