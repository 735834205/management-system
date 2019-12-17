package cn.java.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface RepairsInfoMapper {
	List<Map<String,Object>> getAllRepairsInfos();
	
	@Delete("DELETE FROM repairs WHERE id IN (${idAttr})")
	int batchDelRepairs(Map<String,Object> paramMap);
	
	@Select("SELECT parts.* FROM parts WHERE ifexist=TRUE AND user_id=#{user_id}")
	List<Map<String,Object>> getMyPartsInfos(Long user_id);
	
	@Insert("INSERT INTO repairs(user_id,part_id,other,repairtime) VALUES(#{user_id},#{part_id},#{other},#{repairtime})")
	int insertRepair(Long part_id,Long user_id,String other,String repairtime);
}
