package com.neonex.npa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neonex.npa.dao.DeviceDao;
import com.neonex.npa.model.DeviceInfo;

@Service
public class DeviceService {
	
	@Autowired
	private DeviceDao devDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

	public boolean insert(DeviceInfo devInfo){
		try {
			if(isDuplicateByOjbectCode(devInfo.getObjectCode())){
				int resultCount = devDao.update(devInfo);
				if(resultCount > 0){
					return true;
				}else{
					return false;
				}
			}else{
				int resultCount = devDao.insert(devInfo);
				if(resultCount > 0){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.error("device insert error", e);
			return false;
		}
	}
	
	public boolean update(DeviceInfo devInfo){
		try {
			int resultCount = devDao.update(devInfo);
			if(resultCount > 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("device update error", e);
			return false;
		}
	}
	
	public DeviceInfo selectDeviceDetail(DeviceInfo devInfo){
		try {
			return devDao.selectDeviceDetail(devInfo);
		} catch (Exception e) {
			LOGGER.error("selectDeviceDetail error", e);
			return null;
		}
	}
	
	public List<DeviceInfo> selectList(DeviceInfo devInfo){
		try {
			return devDao.selectList(devInfo);
		} catch (Exception e) {
			LOGGER.error("selectDeviceDetail error", e);
			return null;
		}
	}
	
	public boolean isDuplicate(DeviceInfo devInfo){
		try {
			int resultCount = devDao.checkDuplicate(devInfo);
			if(resultCount > 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("selectDeviceDetail error", e);
			return true;
		}
	}
	
	private boolean isDuplicateByOjbectCode(String objectCode){
		try {
			int resultCount = devDao.checkDuplicateByObjectCode(objectCode);
			if(resultCount > 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("selectDeviceDetail error", e);
			return true;
		}
	}

	public boolean delete(DeviceInfo devInfo) {
		try {
			int deleteCount = devDao.delete(devInfo);
			if(deleteCount > 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
