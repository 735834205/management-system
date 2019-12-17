package cn.java.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.java.entity.OneMenu;
import cn.java.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@Api(value = "登录",tags="登录功能Controller")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/login.do")
	@ApiOperation(value = "登录跳转",notes="将传入的用户名和密码和数据库中进行比较，根据结果决定跳转（同时携带该用户对应的功能权限列表）",httpMethod = "POST",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="username",value="用户名",dataType="String"),
	    @ApiImplicitParam(name="pwd",value="密码",dataType="String"),
	    @ApiImplicitParam(name="session",value="传参所需",dataType="HttpSession")
	})
	public String login(String username,String pwd,HttpSession session) throws Exception {
		//调用业务方法
		Long flag=loginService.isLoginSuccess(username, pwd);
		//更具业务方法返回的结果，决定跳转
		
		if(flag!=null&&flag!=0) {
			
			session.setAttribute("username", username);
			session.setAttribute("user_id",flag);
			List<OneMenu> oneMenuList=loginService.selectMenusById(flag);
			session.setAttribute("oneMenuList",oneMenuList);
			return "redirect:/pages/admin/index.jsp";
		}else {
			return "redirect:/pages/admin/login.jsp";
		}
		
		
	}
	
	@RequestMapping("/getAllMenus.do")
	@ApiIgnore
	@ResponseBody
	public List<OneMenu>getAllMenus(){
		return loginService.selectMenusById(1L);
	}
}
