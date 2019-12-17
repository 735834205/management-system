package cn.java.service;

import java.util.List;
import java.util.Map;

public interface RepairsInfoService {
	List<Map<String,Object>> selectRepairsInfos(Integer pageNum,Integer pageSize);
	
	boolean batchDelRepairs(String idAttr);
	
	List<Map<String,Object>> selectMyPartsInfo(Long user_id);
	
	int insertRepair(Long part_id,Long user_id,String other,String repairtime);
}
