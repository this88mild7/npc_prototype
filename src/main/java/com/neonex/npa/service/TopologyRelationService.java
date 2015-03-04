package com.neonex.npa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neonex.npa.dao.TopologyRelationDao;
import com.neonex.npa.model.TopologyRelationInfo;

@Service
public class TopologyRelationService {
	
	@Autowired
	private TopologyRelationDao relationDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopologyRelationService.class);

	/**
	 * 필수 값
	 * relationObjectCode, relationLineColor,lineWidth, lineTypeCode, regUserEmail, objectCode
	 * @param relInfo
	 * @return
	 */
	public boolean insert(TopologyRelationInfo relInfo) {
		try {
			
			// relation info null check
			if(relInfo == null || relInfo.getObjectCode() == null){
				LOGGER.debug("[SERVICE] relation info is null");
				return false;
			}
			
			
			
			// insert 
			// 다수의 relation object code 를 받을 수 있으므로
			// for 처리
			int insertResultCount = 0;
			
			if(relInfo.getRelationObjectCodes() != null && relInfo.getRelationObjectCodes().length > 0){
				// 이미 해당 relation 이 있는지 체크
				for(int x=0;x<relInfo.getRelationObjectCodes().length;x++){
					relInfo.setRelationObjectCode(relInfo.getRelationObjectCodes()[x]);
					relInfo.setRelationLineColor(relInfo.getRelationLineColors()[x]);
					int aleadyHasCount = relationDao.aleadyHasCount(relInfo);
					if(aleadyHasCount > 0){
						relationDao.update(relInfo);
					}else{
						relationDao.insert(relInfo);
					}
					insertResultCount++;
				}
			}else{
				int aleadyHasCount = relationDao.aleadyHasCount(relInfo);
				if(aleadyHasCount > 0){
					relationDao.update(relInfo);
				}else{
					relationDao.insert(relInfo);
				}
			}
			
			
			if(insertResultCount > 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("[SERVICE] relation insert error",  e);
			return false;
		}
		
	}

	public boolean deleteByRelationObjectCode(TopologyRelationInfo relInfo) {
		try {
			relationDao.deleteByRelationObjectCode(relInfo.getObjectCode());
			return true;
		} catch (Exception e) {
			LOGGER.error("[SERVICE] deleteByRelationObjectCode error", e);
			return false;
		}
	}

	public List<TopologyRelationInfo> selectRelationList(TopologyRelationInfo relInfo) {
		return null;
	}
	
	public boolean update(TopologyRelationInfo relInfo){
		
		// update 는 기존 relation 을 삭제 후 insert
		try {
			deleteByObjectCode(relInfo.getObjectCode());
			insert(relInfo);
			return true;
		} catch (Exception e) {
			LOGGER.error("[SERVICE] relation update error", e);
			return false;
		}
	}

	public int aleadyHasCount(TopologyRelationInfo relInfo) throws Exception{
		return relationDao.aleadyHasCount(relInfo);
	}

	/**
	 * 필수 objectCode
	 * @param relInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteByObjectCode(String objectCode) throws Exception{
		relationDao.deleteByObjectCode(objectCode);
		relationDao.deleteByRelationObjectCode(objectCode);
		return 1;
		
	}

	public List<TopologyRelationInfo> selectList(TopologyRelationInfo relInfo) {
		try {
			return relationDao.selectList(relInfo);
		} catch (Exception e) {
			LOGGER.error("[SERVICE] selectList error",  e);
			return null;
		}
	}
	
	public TopologyRelationInfo selectRelation(TopologyRelationInfo relInfo) {
		try {
			return relationDao.select(relInfo);
		} catch (Exception e) {
			LOGGER.error("[SERVICE] selectRelation error",  e);
			return null;
		}
	}


	
	
}
