package cn.java.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.java.mapper.PartsInfoMapper;
import cn.java.service.PartsInfoService;

@Service
public class PartsInfoServiceImpl implements PartsInfoService{
	@Autowired
	private PartsInfoMapper partsInfoMapper;
	
	@Override
	public List<Map<String,Object>>selectAllFreePartsInfos(Integer pageNum,Integer pageSize){
		PageHelper.startPage(pageNum,pageSize);
		return partsInfoMapper.getAllFreePartsInfos();
	}
	
	@Override
	public List<Map<String,Object>>selectAllPartsInfos(Integer pageNum,Integer pageSize){
		PageHelper.startPage(pageNum,pageSize);
		return partsInfoMapper.getAllPartsInfos();
	}
	
	@Override
	public List<Map<String,Object>>selectAllPartsInfoByCondition(Integer pageNum,Integer pageSize,String type,String keyword){
		PageHelper.startPage(pageNum,pageSize);
		return partsInfoMapper.getAllPartsInfoByCondition(type,keyword);
	}
	
	@Override
	public List<Map<String, Object>> selectFreePartsByCondition(Integer pageNum, Integer pageSize, String type,
			String keyword) {
		PageHelper.startPage(pageNum,pageSize);
		return partsInfoMapper.getFreePartsByCondition(type, keyword);
	}
	
	@Override
	public boolean delpartsinfo(Long id) {
		return partsInfoMapper.delpartsinfo(id)>=1?true:false;
	}
	
	@Override
	public boolean batchDel(String idAttr) {
		idAttr=idAttr.substring(0, idAttr.length()-1);
		System.out.println(idAttr);
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("idAttr", idAttr);
		int flag=partsInfoMapper.batchDel(paramMap);
		return flag>=1?true:false;
	}
	
	@Override
	public boolean batchReturn(String idAttr) {
		idAttr=idAttr.substring(0,idAttr.length()-1);
		String []part_ids=idAttr.split(",");
		for(int i=0;i<part_ids.length;i++) {
			int part_id=Integer.valueOf(part_ids[i]);
			int flag1=partsInfoMapper.batchReturn1(part_id);
			if(flag1<=0) {return false;}
			int flag2=partsInfoMapper.batchReturn2(part_id);
			if(flag2<=0) {return false;}
		}
		// TODO Auto-generated method stub
		return true;
	}

	
	@Override
	public boolean batchUpdateCheck(String idAttr) {
		idAttr=idAttr.substring(0, idAttr.length()-1);
		System.out.println(idAttr);
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("idAttr", idAttr);
		int flag=partsInfoMapper.batchUpdateCheck(paramMap);
		return flag>=1?true:false;
	}
	
	@Override
	public List<Map<String, Object>> selectCheckPartsInfos(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return partsInfoMapper.getCheckPartsInfo();
	}

	@Override
	public List<Map<String, Object>> selectUsedParts(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return partsInfoMapper.getUsedParts();
	}

	@Override
	public List<Map<String, Object>> selectUsedPartsByCondition(Integer id) {
		return partsInfoMapper.getUsedPartsByCondition(id);
	}

	@Override
	public List<Map<String, Object>> selectHistoryParts(Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return partsInfoMapper.getHistoryParts();
	}

	@Override
	public int insertParts(String part_kind) {
		return partsInfoMapper.insertParts(part_kind);
	}

	

	

	
}
