package cn.java.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.java.mapper.RepairsInfoMapper;
import cn.java.service.RepairsInfoService;
@Service
public class RepairsInfoServiceImpl implements RepairsInfoService{

	@Autowired
	private RepairsInfoMapper repairsInfoMapper;
	@Override
	public List<Map<String, Object>> selectRepairsInfos(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return repairsInfoMapper.getAllRepairsInfos();
	}
	@Override
	public boolean batchDelRepairs(String idAttr) {
		// TODO Auto-generated method stub
		idAttr=idAttr.substring(0, idAttr.length()-1);
		System.out.println(idAttr);
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("idAttr", idAttr);
		int flag=repairsInfoMapper.batchDelRepairs(paramMap);
		return flag>=1?true:false;
	}
	@Override
	public List<Map<String, Object>> selectMyPartsInfo(Long user_id) {
		return repairsInfoMapper.getMyPartsInfos(user_id);
	}
	@Override
	public int insertRepair(Long part_id, Long user_id, String other, String repairtime) {
		return repairsInfoMapper.insertRepair(part_id, user_id, other, repairtime);
	}
}
