package com.neonex.npa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.neonex.npa.model.ImageInfo;
import com.neonex.npa.model.TopologyInfo;

@Repository
public interface TopologyDao {

	public int insert(TopologyInfo topologyInfo) throws Exception;

	public int update(TopologyInfo topologyInfo) throws Exception;

	public TopologyInfo select(String objectCode) throws Exception;
	
	public int delete(TopologyInfo topologyInfo) throws Exception;
	
	public int deleteDeviceByObjectCode(String objectCode) throws Exception;

	public int hasRoot(String userEmail) throws Exception;

	public int makeRoot(String userEmail) throws Exception;

	public int deleteRoot(String userEmail) throws Exception;

	public void updateBackgroundImg(TopologyInfo topologyInfo) throws Exception;

	public int insertMinInfo(TopologyInfo topologyInfo) throws Exception;

	/**********************************************
	 *  <pre>
	 *  개요 : Topology List 조회
	 *  필수 값 : userEmail(regUserEamil), upperObjectCode
	 *  </pre>
	 * 	@Method selectTopologyList 
	 *  @param topologInfo
	 *  @return
	 *  @throws Exception
	 **********************************************/
	public List<TopologyInfo> selectTopologyList(TopologyInfo topologInfo) throws Exception;

	public int selectRootBackgroundImgSeq(String userEmail) throws Exception;
	
	public int hasTopology(TopologyInfo topologInfo) throws Exception;

	public int updateTitle(TopologyInfo topologyInfo) throws Exception;

	public int deleteBackground(TopologyInfo topoInfo) throws Exception;


}
