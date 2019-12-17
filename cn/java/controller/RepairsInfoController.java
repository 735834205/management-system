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

import cn.java.service.RepairsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Controller
@Api(value = "报修",tags="报修信息Controller")
public class RepairsInfoController {
	
	@Autowired
	private RepairsInfoService repairsInfoService;
	
	@RequestMapping("/getRepairsInfo.do")
	@ApiOperation(value = "查询提交的报修单",notes="获取数据库中的报修项表用于显示",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model")
	})
	public String getRepairsInfo(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model) {
		System.out.println("in");
		List<Map<String,Object>>infoList=repairsInfoService.selectRepairsInfos(pageNum, pageSize);
		System.out.println("out");
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/bill/allrepairsinfo.jsp";
	}
	
	@RequestMapping("/batchDelRepairs.do")
	@ApiOperation(value = "确认处理报修",notes="在数据库中根据报修项编号，批量删除报修项",httpMethod = "POST",response=boolean.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="idAttr",value="确认处理的报修项编号",dataType="String")
	})
	public @ResponseBody boolean batchDelRepairs(String idAttr) {
		boolean flag=repairsInfoService.batchDelRepairs(idAttr);
		System.out.println("flag="+flag);
		return flag;
	}
	
	@RequestMapping("/setRepairs.do")
	@ApiOperation(value = "报修零部件页面",notes="根据用户id跳转到添加报修项的页面",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="user_id",value="当前进行操作的用户编号",dataType="Long"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model"),
	})
	public String setRepairs(Long user_id,Model model) {
		List<Map<String,Object>>parts_ids=repairsInfoService.selectMyPartsInfo(user_id);
		model.addAttribute("user_id",user_id);
		model.addAttribute("parts_ids",parts_ids);
		model.addAttribute("when",1);
		return "admin/bill/setrepairs.jsp";
	}
	
	
	@RequestMapping("insertrepairs.do")
	@ApiOperation(value = "添加报修项",notes="在数据库中的报修表添加新的报修项，并且返回到原页面",httpMethod = "POST",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="part_id",value="报修的零部件编号",dataType="String"),
	    @ApiImplicitParam(name="user_id",value="当前操作的用户id",dataType="Long"),
	    @ApiImplicitParam(name="other",value="报修的备注",dataType="String"),
	    @ApiImplicitParam(name="repairtime",value="员工希望检查员上门维修的时间",dataType="String"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model")
	})
	public String insertrepairs(String part_id,Long user_id,String other,String repairtime,Model model) {
		/*System.out.println(part_id);
		System.out.println(user_id);
		System.out.println(other);
		System.out.println(repairtime);*/
		String []s=part_id.split("——");
		System.out.println("s[0]="+s[0]);
		Long pid=Long.valueOf(s[0]);
		int flag=repairsInfoService.insertRepair(pid, user_id, other, repairtime);
		
		List<Map<String,Object>>parts_ids=repairsInfoService.selectMyPartsInfo(user_id);
		model.addAttribute("user_id",user_id);
		model.addAttribute("parts_ids",parts_ids);
		model.addAttribute("when",2);
		return "admin/bill/setrepairs.jsp";
	}
}
