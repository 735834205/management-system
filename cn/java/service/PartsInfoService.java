package cn.java.service;

import java.util.List;
import java.util.Map;

public interface PartsInfoService {
	//pageNum:当前页码 pageSize:每页显示的数据量
	List<Map<String,Object>> selectAllFreePartsInfos(Integer pageNum,Integer pageSize);
	
	List<Map<String,Object>> selectAllPartsInfos(Integer pageNum,Integer pageSize);

	List<Map<String,Object>> selectAllPartsInfoByCondition(Integer pageNum,Integer pageSize,String type,String keyword);

	boolean delpartsinfo(Long id);
	
	boolean batchDel(String idAttr);
	
	boolean batchUpdateCheck(String idAttr);
	
	List<Map<String,Object>> selectCheckPartsInfos(Integer pageNum,Integer pageSize);
	
	List<Map<String,Object>> selectUsedParts(Integer pageNum,Integer pageSize);
	
	List<Map<String,Object>> selectUsedPartsByCondition(Integer id);
	
	boolean batchReturn(String idAttr);
	
	List<Map<String,Object>> selectHistoryParts(Integer pageNum,Integer pageSize);
	
	List<Map<String,Object>> selectFreePartsByCondition(Integer pageNum,Integer pageSize,String type,String keyword);
	
	int insertParts(String part_kind);
}
