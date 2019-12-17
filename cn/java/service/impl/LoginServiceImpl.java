package cn.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.java.entity.OneMenu;
import cn.java.mapper.LoginMapper;
import cn.java.service.LoginService;
import cn.java.utils.MD5;

@Service
@Transactional(readOnly=false)
public class LoginServiceImpl implements LoginService{
	@Autowired
	private LoginMapper loginMapper;
	
	@Transactional(readOnly=true)
	@Override
	public Long isLoginSuccess(String username,String pwd) throws Exception {
		if(username==null||pwd==null) {return null;}
		String regex1="\\w{3,12}";
		String regex2="\\w{6,20}";
		boolean flag1=username.matches(regex1);
		boolean flag2=pwd.matches(regex2);
		if(flag1&&flag2) {
			String miwenPwd=MD5.finalMD5(pwd);//加密
		//if(username!=null&&pwd!=null) {
			Long flag=loginMapper.login(username, miwenPwd);
			return flag;
		}
		return null;
	}
	@Override
	public List<OneMenu> selectMenusById(Long userId) {
		return loginMapper.getMenusByUserId(userId);
	}
}
