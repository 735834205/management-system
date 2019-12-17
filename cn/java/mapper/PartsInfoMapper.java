package cn.java.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PartsInfoMapper {
	
	/*分页查询空闲零部件*/
	List<Map<String,Object>> getAllFreePartsInfos();
	
	/*分页查询所有零部件*/
	List<Map<String,Object>> getAllPartsInfos();
	
	/*分页查询所有零部件的条件查询版本*/
	List<Map<String,Object>> getAllPartsInfoByCondition(String type,String keyword);
	
	@Delete("UPDATE parts SET ifexist=false WHERE id=#{id}")
	int delpartsinfo(Long id);
	
	@Update("UPDATE parts SET ifexist =0 WHERE id IN (${idAttr})")
	int batchDel(Map<String,Object> paramMap);
	
	List<Map<String,Object>> getCheckPartsInfo();
	
	@Update("UPDATE parts SET checkdate=CURRENT_DATE WHERE ifexist=TRUE AND id IN(${idAttr})")
	int batchUpdateCheck(Map<String,Object>paramMap);
	
	@Select("SELECT * FROM parts WHERE ifexist=TRUE AND state=TRUE")
	List<Map<String,Object>> getUsedParts();
	
	@Select("SELECT * FROM parts WHERE ifexist=TRUE AND state=TRUE AND id=#{id}")
	List<Map<String,Object>>getUsedPartsByCondition(Integer id);
	
	@Insert("INSERT INTO history(part_id,starttime,endtime,user_id) VALUES(#{part_id},(SELECT usetime FROM parts WHERE id=#{part_id}),CURRENT_DATE,(SELECT user_id FROM parts WHERE id=#{part_id}))")
	int batchReturn1(Integer part_id);
	
	@Update("UPDATE parts SET state=0,user_id=NULL,usetime=NULL WHERE id=#{part_id}")
	int batchReturn2(Integer part_id);
	
	List<Map<String,Object>>getHistoryParts();
	
	List<Map<String,Object>>getFreePartsByCondition(String type,String keyword);
	
	@Insert("INSERT INTO parts(kind,state,checkdate,ifexist) VALUE(#{part_kind},FALSE,CURRENT_DATE,TRUE)")
	int insertParts(String part_kind);
}

