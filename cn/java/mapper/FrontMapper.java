package cn.java.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface FrontMapper {
	@Select("select * from test")
	List<Map<String,Object>>getAll();
}
