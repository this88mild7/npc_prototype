package com.neonex.npa.service;

import java.util.List;
import java.util.UUID;

import org.parboiled.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neonex.npa.dao.TopologyDao;
import com.neonex.npa.exception.TopologyException;
import com.neonex.npa.model.TopologyInfo;
import com.neonex.npa.model.TopologyRelationInfo;

@Service
public class TopologyService {
	
	@Autowired
	private TopologyDao topologyDao;
	
	@Autowired
	private TopologyRelationService topoRelService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopologyRelationService.class);

	public int makeRootIfNull(String userEmail) throws TopologyException{
		int resultCount = 0 ;
		
		try {
			resultCount = topologyDao.hasRoot(userEmail);
		} catch (Exception e) {
			throw new TopologyException(500, "DB 에 연결 할 수 없습니다.");
		}
		
		if(resultCount == 0){
			try {
				topologyDao.makeRoot(userEmail);
				resultCount = 1;
			} catch (Exception e) {
				throw new TopologyException(500, "root 를 생성 할 수 없습니다.");
			}
		}else{
			resultCount = 1;
		}
		return resultCount;
	}

	public int deleteRoot(String userEmail) throws TopologyException{
		try {
			return topologyDao.deleteRoot(userEmail);
		} catch (Exception e) {
			throw new TopologyException(500, "root 를 삭제 할 수 없습니다.");
		}
	}

	public void updateBackgroundImg(String objectCode, String userEmail, String imgSeq) throws Exception{
		TopologyInfo topoInfo = new TopologyInfo();
		topoInfo.setRegUserEmail(userEmail);
		topoInfo.setBackgroundImgSeq(imgSeq);
		topoInfo.setObjectCode(objectCode);
		topologyDao.updateBackgroundImg(topoInfo);
	}


	public String insert(TopologyInfo topoInfo) throws Exception{
		
		int hasCount = topologyDao.hasTopology(topoInfo);
		
		if(hasCount > 0){
			// 기존 정보가 존재 한다면 update
			LOGGER.info("topology update");
			topologyDao.update(topoInfo);
		}else{
			LOGGER.info("topology insert");
			topologyDao.insert(topoInfo);
		}
		return topoInfo.getObjectCode();
	}
	
	// 트랜잭션 필요
	public boolean deleteByObjectCode(TopologyInfo topoInfo){
		try {
			// relation 삭제
			TopologyRelationInfo relInfo = new TopologyRelationInfo();
			relInfo.setRegUserEmail(topoInfo.getRegUserEmail());
			relInfo.setObjectCode(topoInfo.getObjectCode());
			topoRelService.deleteByObjectCode(relInfo.getObjectCode());
			
			// device 삭제
			topologyDao.deleteDeviceByObjectCode(relInfo.getObjectCode());
			
			// object 삭제
			LOGGER.info("topology delete");
			topologyDao.delete(topoInfo);
			return true;
		} catch (Exception e) {
			LOGGER.error("delete Object error", e);
			return false;
		}
	}
	
	public List<TopologyInfo> selectTopologyList(TopologyInfo topoInfo) throws Exception{
		if(StringUtils.isEmpty(topoInfo.getObjectUpperCode())){
			// root object 의  자식 object 조회 
			topoInfo.setObjectUpperCode("1111");
		}
		return topologyDao.selectTopologyList(topoInfo);
	}
	
	public int getRootImageSeq(String userEmail) throws Exception{
		return topologyDao.selectRootBackgroundImgSeq(userEmail);
	}
	
//	private String makeObjectCode(){
//		return  "N" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7);
//	}

	public String selectBackgroundImgSeq(String objectCode) {
		try {
			LOGGER.info("select param objectCode : {}", objectCode);
			TopologyInfo topoInfo = topologyDao.select(objectCode);
			LOGGER.info("background seq : {}", topoInfo.getBackgroundImgSeq());
			return topoInfo.getBackgroundImgSeq();
		} catch (Exception e) {
			return "0";
		}
	}

	public boolean updateTitle(TopologyInfo topologyInfo) {
		try {
			int updateResultCount = topologyDao.updateTitle(topologyInfo);
			if(updateResultCount > 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("update title error", e);
			return false;
		}
	}

	public TopologyInfo selectTopologyInfo(String objectCode) {
		try {
			return topologyDao.select(objectCode);
		} catch (Exception e) {
			LOGGER.error("selectTopologyInfo error", e);
			return new TopologyInfo();
		}
	}

	public boolean deleteBackground(TopologyInfo topoInfo) {
		try {
			int resultCount = topologyDao.deleteBackground(topoInfo);
			if(resultCount > 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("deleteBackground error", e);
			return false;
		}
	}

	
	
}
