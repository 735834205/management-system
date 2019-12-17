package cn.java.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import cn.java.service.ApplysInfoService;
import cn.java.service.PartsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "申请",tags="申请信息Controller")
public class ApplysInfoController {
	
	@Autowired
	private ApplysInfoService applysInfoService;
	
	@Autowired
	private PartsInfoService partsInfoService;
	
	@RequestMapping("/getMyApplysInfo.do")
	@ApiOperation(value = "查询我提交的申请",notes="通过传入的用户id，获取数据库中的申请人为该用户的申请项",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model"),
	    @ApiImplicitParam(name="user_id",value="当前在操作系统的用户id",dataType="Long"),
	})
	public String getMyApplysInfo(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model,Long user_id) {
		
		System.out.println("user_id:"+user_id);
		List<Map<String,Object>>infoList=applysInfoService.selectMyApplysInfos(pageNum, pageSize, user_id);
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("user_id", user_id);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/bill/myapplysinfo.jsp";
	}
	/*public String getMyApplysInfo(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model,Integer userid) {
		List<Map<String,Object>>infoList=applysInfoService.selectMyApplysInfos(pageNum, pageSize, userid);
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/bill/myapplysinfo.jsp";
	}*/
	
	@RequestMapping("/getAllApplysInfo.do")
	@ApiOperation(value = "查询待处理的申请",notes="获取数据库中审核状态为false的所有申请项",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model")
	})
	public String getAllApplysInfo(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model) {
		System.out.println("in");
		List<Map<String,Object>>infoList=applysInfoService.selectAllApplysInfos(pageNum, pageSize);
		System.out.println("out");
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/bill/allapplysinfo.jsp";
	}
	
	@RequestMapping("/batchPass.do")
	@ApiOperation(value = "批量同意申请",notes="通过传入选中的申请项编号，更新数据库中的申请项的状态，修改申请零部件信息的各项字段",httpMethod = "POST",response=boolean.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="idAttr",value="传入同意申请的申请项id（批量）",dataType="String"),
	    @ApiImplicitParam(name="otherAttr",value="传入所选申请项的备注（批量）",dataType="String"),
	    @ApiImplicitParam(name="user_idAttr",value="传入所选申请项的提交者（批量）",dataType="String"),
	    @ApiImplicitParam(name="part_idAttr",value="传入所选申请项申请的零部件编号（批量）",dataType="String"),
	})
	public @ResponseBody boolean batchPass(String idAttr,String otherAttr,String user_idAttr,String part_idAttr) {
		boolean flag=applysInfoService.batchPass(idAttr,otherAttr,user_idAttr,part_idAttr);
		System.out.println("flag="+flag);
		return flag;
	}
	
	@RequestMapping("/batchRefuse.do")
	@ApiOperation(value = "批量拒绝申请",notes="通过传入选中的申请项编号，更新数据库中的申请项的状态",httpMethod = "POST",response=boolean.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="idAttr",value="传入拒绝的申请项id（批量）",dataType="String"),
	    @ApiImplicitParam(name="otherAttr",value="传入所选申请项的备注（批量）",dataType="String")
	})
	public @ResponseBody boolean batchRefuse(String idAttr,String otherAttr) {
		System.out.println("idAttr="+idAttr+";otherAttr="+otherAttr);
		boolean flag=applysInfoService.batchRefuse(idAttr,otherAttr);
		System.out.println("flag="+flag);
		return flag;
	}
	
	
	@RequestMapping("/setApplys.do")
	@ApiOperation(value = "申请零部件页面",notes="根据用户id跳转到添加申请项的页面",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="user_id",value="当前进行操作的用户编号",dataType="Long"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model"),
	})
	public String setApplys(Long user_id,Model model) {
		List<Map<String,Object>>parts_ids=applysInfoService.selectFreePartsid();
		model.addAttribute("user_id",user_id);
		model.addAttribute("parts_ids",parts_ids);
		return "admin/bill/setapplys.jsp";
	}
	
	@RequestMapping("/insertApplys.do")
	@ApiOperation(value = "添加申请项",notes="在数据库中的申请表添加新的申请项，并且返回到原页面",httpMethod = "POST",response=String.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model"),
	    @ApiImplicitParam(name="part_id",value="申请的零部件编号",dataType="String"),
	    @ApiImplicitParam(name="user_id",value="当前操作的用户id",dataType="Long"),
	    @ApiImplicitParam(name="reason",value="申请的原因",dataType="String")
	})
	public String insertApplys(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model,String part_id,Long user_id,String reason) {
		/*System.out.println(part_id);
		System.out.println(user_id);
		System.out.println(reason);*/
		String []s=part_id.split("——");
		System.out.println("s[0]="+s[0]);
		Long pid=Long.valueOf(s[0]);
		int flag=applysInfoService.insertApplys(pid, user_id, reason);
		List<Map<String,Object>>infoList=applysInfoService.selectMyApplysInfos(pageNum, pageSize, user_id);
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("user_id", user_id);
		return "admin/bill/myapplysinfo.jsp";
	}
	
}
