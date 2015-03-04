package com.neonex.npa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.neonex.npa.model.DeviceInfo;


@Repository
public interface DeviceDao {

	public int insert(DeviceInfo devInfo) throws Exception;

	public int delete(DeviceInfo devInfo) throws Exception;

	public List<DeviceInfo> selectList(DeviceInfo devInfo) throws Exception;

	public int update(DeviceInfo updateInfo) throws Exception;

	public DeviceInfo selectDeviceDetail(DeviceInfo updateInfo)throws Exception;

	public int checkDuplicate(DeviceInfo devInfo) throws Exception;

	public int checkDuplicateByObjectCode(String objectCode) throws Exception;



}
