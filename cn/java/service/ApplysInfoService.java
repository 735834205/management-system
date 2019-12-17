package cn.java.service;

import java.util.List;
import java.util.Map;

public interface ApplysInfoService {
	List<Map<String,Object>> selectMyApplysInfos(Integer pageNum,Integer pageSize,Long userid);
	
	List<Map<String,Object>> selectAllApplysInfos(Integer pageNum,Integer pageSize);
	
	boolean batchPass(String idAttr,String otherAttr,String user_idAttr,String part_idAttr);
	
	boolean batchRefuse(String idAttr,String otherAttr);
	
	List<Map<String,Object>> selectFreePartsid();
	
	int insertApplys(Long part_id,Long user_id,String reason);
}
