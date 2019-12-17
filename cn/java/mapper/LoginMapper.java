package cn.java.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cn.java.entity.OneMenu;

public interface LoginMapper {
	
	//登录 username 账号 pwd 密文
	@Select("SELECT id FROM system_user WHERE username=#{username} AND pwd=#{pwd}")
	Long login(String username,String pwd);
	
	List<OneMenu> getMenusByUserId(Long userId);
}
