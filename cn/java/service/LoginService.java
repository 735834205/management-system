package cn.java.service;

import java.util.List;

import cn.java.entity.OneMenu;

public interface LoginService {
	Long isLoginSuccess(String username,String password) throws Exception;
	
	List<OneMenu>selectMenusById(Long userId);
}
