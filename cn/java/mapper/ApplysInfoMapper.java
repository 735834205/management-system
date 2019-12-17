package cn.java.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ApplysInfoMapper {
	List<Map<String,Object>> getMyApplysInfos(Long userid);
	
	List<Map<String,Object>> getAllApplysInfos();
	
	@Update("UPDATE applys SET state=TRUE,approval=TRUE,other=#{other} WHERE id = #{id}")
	int batchPass(String id,String other);
	
	@Update("UPDATE parts SET state=TRUE,user_id=#{user_id},usetime=CURRENT_DATE WHERE id=#{part_id}")
	int batchPass2(String user_id,String part_id);
	
	@Update("UPDATE applys SET state=FALSE,approval=TRUE,other=#{other} WHERE id = #{id}")
	int batchRefuse(String id,String other);
	
	@Select("SELECT parts.* FROM parts WHERE ifexist=TRUE AND state=FALSE")
	List<Map<String,Object>> getFreePartsid();
	
	@Insert("INSERT INTO applys(user_id,parts_id,reason,approval) VALUES(#{user_id},#{part_id},#{reason},FALSE)")
	int insertApplys(Long part_id,Long user_id,String reason);
	
	
}
