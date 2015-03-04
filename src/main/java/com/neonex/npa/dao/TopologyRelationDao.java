package com.neonex.npa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.neonex.npa.model.ImageInfo;
import com.neonex.npa.model.TopologyInfo;
import com.neonex.npa.model.TopologyRelationInfo;

@Repository
public interface TopologyRelationDao {

	public int insert(TopologyRelationInfo topologyRelationInfo) throws Exception;

	public int update(TopologyRelationInfo topologyRelationInfo) throws Exception;

	public TopologyRelationInfo select(TopologyRelationInfo relInfo) throws Exception;
	
	public int delete(int relationSeq) throws Exception;
	
	public List<TopologyRelationInfo> selectList(TopologyRelationInfo relInfo) throws Exception;

	public int aleadyHasCount(TopologyRelationInfo relInfo) throws Exception;

	public int deleteByRelationObjectCode(String objectCode) throws Exception;

	public int deleteByObjectCode(String objectCode) throws Exception;

}
