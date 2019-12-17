package cn.java.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import cn.java.service.PartsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "零部件信息",tags="零部件信息Controller")
public class PartsInfoController {
	
	@Autowired
	private PartsInfoService partsInfoService;
	
	@RequestMapping("/getFreePartsInfo.do")
	@ApiOperation(value = "查询空闲零部件信息",notes="在数据库中查询空闲零部件信息",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model")
	})
	public String getFreePartsInfo(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model) {
		//分页之后返回的数据
		List<Map<String,Object>>infoList=partsInfoService.selectAllFreePartsInfos(pageNum, pageSize);
		//将infoList封装奥PageInfo工具类中
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo",pageInfo);
		return "admin/bill/freepartsinfo.jsp";
	}
	
	@RequestMapping("/getAllPartsInfo.do")
	@ApiOperation(value = "查询所有零部件信息",notes="在数据库中查询所有零部件信息",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model")
	})
	public String getAllPartsInfo(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model) {
		List<Map<String,Object>>infoList=partsInfoService.selectAllPartsInfos(pageNum, pageSize);
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/bill/allpartsinfo.jsp";
	}
	
	@RequestMapping(value="/getFreePartsByCondition.do",method= {RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value = "按条件查询空闲零部件信息",notes="在数据库中按条件查询空闲零部件信息",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model"),
	    @ApiImplicitParam(name="type",value="查询的条件",dataType="String"),
	    @ApiImplicitParam(name="keyword",value="查询的关键字",dataType="String")
	})
	public String getFreePartsByCondition(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model,String type,String keyword) {
		List<Map<String,Object>>infoList=partsInfoService.selectFreePartsByCondition(pageNum, pageSize, type, keyword);
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("type",type);
		model.addAttribute("keyword",keyword);
		return "admin/bill/freepartsinfo_condition.jsp";
	}
	//所有零部件的条件查询
	
	@RequestMapping(value="/getAllPartsInfoByCondition.do",method= {RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value = "按条件查询零部件信息",notes="在数据库中按条件查询零部件信息",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model"),
	    @ApiImplicitParam(name="type",value="查询的条件",dataType="String"),
	    @ApiImplicitParam(name="keyword",value="查询的关键字",dataType="String")
	})
	public String getAllPartsInfoByCondition(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model,String type,String keyword) {
		List<Map<String,Object>>infoList=partsInfoService.selectAllPartsInfoByCondition(pageNum,pageSize,type,keyword);
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("type",type);
		model.addAttribute("keyword",keyword);
		return "admin/bill/allpartsinfo_condition.jsp";
	}
	
	/*@RequestMapping("/delpartsinfo.do")
	public @ResponseBody boolean delpartsinfo(Long id) {
		System.out.println("ee");
		boolean flag=partsInfoService.delpartsinfo(id);
		System.out.println(flag);
		return flag;
	}*/
	
	@ApiOperation(value = "批量删除零部件",notes="在数据库中批量删除零部件信息",httpMethod = "POST",response=boolean.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="idAttr",value="需要删除的零部件编号",dataType="String"),
	})
	@RequestMapping("/batchDel.do")
	public @ResponseBody boolean batchDel(String idAttr) {
		boolean flag=partsInfoService.batchDel(idAttr);
		System.out.println("flag="+flag);
		return flag;
	}
	
	@ApiOperation(value = "批量确认归还零部件",notes="在数据库中批量修改零部件表的状态，添加新的历史项",httpMethod = "POST",response=boolean.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="idAttr",value="需要确认归还的零部件编号",dataType="String"),
	})
	@RequestMapping("/batchReturn.do")
	public @ResponseBody boolean batchReturn(String idAttr) {
		boolean flag=partsInfoService.batchReturn(idAttr);
		return flag;
	}
	
	@ApiOperation(value = "批量检查零部件",notes="在数据库中批量修改零部件表的检查时间",httpMethod = "POST",response=boolean.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="idAttr",value="需要检查的零部件编号",dataType="String"),
	})
	@RequestMapping("/batchUpdateCheck.do")
	public @ResponseBody boolean batchUpdateCheck(String idAttr) {
		boolean flag=partsInfoService.batchUpdateCheck(idAttr);
		System.out.println("flag="+flag);
		return flag;
	}
	
	@ApiOperation(value = "显示待检查的零部件信息",notes="在数据库中查询零部件表中检查时间距今20天以上的零部件",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="idAttr",value="需要检查的零部件编号",dataType="String"),
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model")
	})
	@RequestMapping("/getCheckPartsInfo.do")
	public String getCheckPartsInfo(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model) {
		List<Map<String,Object>>infoList=partsInfoService.selectCheckPartsInfos(pageNum, pageSize);
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/bill/allcheckpartsinfo.jsp";
	}
	
	@ApiOperation(value = "显示正被使用的零部件信息",notes="在数据库中查询零部件表中状态为true的零部件",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model")
	})
	@RequestMapping("/getUsedParts.do")
	public String getUsedParts(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model) {
		List<Map<String,Object>>infoList=partsInfoService.selectUsedParts(pageNum, pageSize);
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/bill/usedpartsinfo.jsp";
	}
	
	@RequestMapping(value="/getUsedPartsByCondition.do",method= {RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value = "按零部件编号查询正被使用的零部件信息",notes="在数据库中按零部件编号查询零部件表中状态为true的零部件",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="keyword",value="要查询的零部件编号",dataType="String"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model")
	})
	public String getUsedPartsByCondition(String keyword,Model model) {
		List<Map<String,Object>>infoList=partsInfoService.selectUsedPartsByCondition(Integer.parseInt(keyword));
		model.addAttribute("infoList", infoList);
		return "admin/bill/usedpartsinfobycondition.jsp";
	}
	
	@ApiOperation(value = "显示零部件使用历史记录",notes="在数据库中查询零部件使用历史表",httpMethod = "GET",response=String.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="pageSize",value="分页所需",dataType="Integer"),
	    @ApiImplicitParam(name="model",value="传参所需",dataType="Model")
	})
	@RequestMapping("/getHistoryPartsInfo.do")
	public String getHistoryParsInfo(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize,Model model) {
		List<Map<String,Object>>infoList=partsInfoService.selectHistoryParts(pageNum, pageSize);
		PageInfo<Map<String,Object>>pageInfo=new PageInfo<Map<String,Object>>(infoList);
		model.addAttribute("pageInfo",pageInfo);
		return "admin/bill/historypartsinfo.jsp";
	}
	
	@ApiOperation(value = "添加新的零部件",notes="在数据库中零部件表中添加新的项",httpMethod = "POST",response=boolean.class)
	@ApiImplicitParams({
	    @ApiImplicitParam(name="part_kind",value="新零部件的详细描述",dataType="String")
	})
	@RequestMapping("/addNewParts.do")
	public  @ResponseBody boolean addNewParts(String part_kind) {
		System.out.println(part_kind);
		int flag=partsInfoService.insertParts(part_kind);
		return true;
	}
}
