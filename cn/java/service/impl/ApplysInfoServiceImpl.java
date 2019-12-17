package cn.java.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.java.mapper.ApplysInfoMapper;
import cn.java.service.ApplysInfoService;

@Service
public class ApplysInfoServiceImpl implements ApplysInfoService{
	@Autowired
	private ApplysInfoMapper applysInfoMapper;
	@Override
	public List<Map<String, Object>> selectMyApplysInfos(Integer pageNum, Integer pageSize, Long userid) {
		PageHelper.startPage(pageNum,pageSize);
		return applysInfoMapper.getMyApplysInfos(userid);
	}
	@Override
	public List<Map<String, Object>> selectAllApplysInfos(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return applysInfoMapper.getAllApplysInfos();
	}
	@Override
	public boolean batchPass(String idAttr,String otherAttr,String user_idAttr,String part_idAttr) {
		idAttr=idAttr.substring(0, idAttr.length()-1);
		otherAttr=otherAttr.substring(0,otherAttr.length()-1);
		//System.out.println(idAttr);
		user_idAttr=user_idAttr.substring(0,user_idAttr.length()-1);
		part_idAttr=part_idAttr.substring(0,part_idAttr.length()-1);
		String []s1=idAttr.split(",");
		String []s2=otherAttr.split(",");
		String []s3=user_idAttr.split(",");
		String []s4=part_idAttr.split(",");
		/*Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("idAttr", idAttr);
		int flag=applysInfoMapper.batchPass(paramMap);
		return flag>=1?true:false;*/
		for(int i=0;i<s1.length;i++) {
			int flag1=applysInfoMapper.batchPass(s1[i],s2[i]);
			int flag2=applysInfoMapper.batchPass2(s3[i], s4[i]);
			if(flag1<=0||flag2<=0)return false;
		}
		return true;
	}
	@Override
	public boolean batchRefuse(String idAttr,String otherAttr) {
		idAttr=idAttr.substring(0, idAttr.length()-1);
		otherAttr=otherAttr.substring(0,otherAttr.length()-1);
		//System.out.println(idAttr);
		String []s1=idAttr.split(",");
		String []s2=otherAttr.split(",");
		/*System.out.println(s2.length);
		for(int i=0;i<s2.length;i++) {
			System.out.println(i+":"+s2[i]);
		}*/
		for(int i=0;i<s1.length;i++) {
			int flag=applysInfoMapper.batchRefuse(s1[i],s2[i]);
			if(flag<=0)return false;
		}
		return true;
		/*Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("idAttr", idAttr);
		int flag=applysInfoMapper.batchRefuse(paramMap);
		return flag>=1?true:false;*/
	}
	@Override
	public List<Map<String, Object>> selectFreePartsid() {
		return applysInfoMapper.getFreePartsid();
	}
	@Override
	public int insertApplys(Long part_id, Long user_id, String reason) {
		return applysInfoMapper.insertApplys(part_id, user_id, reason);
	}

}
