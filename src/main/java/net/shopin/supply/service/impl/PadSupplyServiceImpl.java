package net.shopin.supply.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.Guideinfo;
import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.PadLog;
import net.shopin.supply.domain.entity.PadSupply;
import net.shopin.supply.persistence.PadBaseinfoMapper;
import net.shopin.supply.persistence.PadLogMapper;
import net.shopin.supply.persistence.PadSupplyMapper;
import net.shopin.supply.service.IPadSupplyService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopin.core.constants.ErrorCodeConstants.ErrorCode;
import com.shopin.core.framework.exception.ShopinException;

/**
* @ClassName: GuideSupplyServiceImpl 
* @author zhangq
* @date 2014-12-24 上午11:35:03 
*
 */
@Service
public class PadSupplyServiceImpl implements IPadSupplyService {

	private Logger logger = Logger.getLogger(PadSupplyServiceImpl.class);
	
	@Autowired
	private PadSupplyMapper padSupplyMapper;
	
	@Autowired
	private PadLogMapper padLogMapper;
	
	@Autowired
	private PadBaseinfoMapper padBaseinfoMapper;
	
	@Override
	public List<PadSupply> selectListByParam(Map paramMap){
		List<PadSupply> list = this.padSupplyMapper.selectListByParam(paramMap);
		return list;
	}
	
	/**
	 * pad绑定供应商
	 */
	@Override
	public Integer savePadSupply(PadSupply padSupply,String username,String userSid) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Integer result = 0;
		//绑定供应商： 状态改为卖场
		String padNo = padSupply.getPadNo();
		PadBaseinfo padBaseinfo = this.padBaseinfoMapper.selectPadByPadNo(padNo);
		padBaseinfo.setPadStatus(1);	//pad状态 0在库 1卖场 2送修3停用
		this.padBaseinfoMapper.update(padBaseinfo);
		
		Integer sid = padSupply.getSid();
		if(sid ==null){
			//新建
			result = this.padSupplyMapper.insert(padSupply);
		}else{
			//更新
			this.padSupplyMapper.update(padSupply);
		}
		
		
		
		if(result!=1){
			return 0;
		}else{
			
			//供应商绑定成功修改导购基本信息表中操作人
			PadBaseinfo padinfo = new PadBaseinfo();
			padinfo.setOperator(username);
			padinfo.setOperatorSid(Integer.parseInt(userSid));
			this.padBaseinfoMapper.update(padinfo);
			
			PadLog padLog = new PadLog();
			padLog.setPadNo(padSupply.getPadNo());
			padLog.setOperator(username);
			padLog.setOperatTime(new Date());
			padLog.setDescription("系统时间："+df.format(new Date())+" 系统用户："+username+" 为编号为： "+
					padSupply.getPadNo()+" 的pad绑定ID为："+padSupply.getSupplyId()+" 的供应商");
			
			result = this.padLogMapper.insert(padLog);
			if(result!=1){
				return 0;
			}
		}
		return result;
	}

	/**
	 * 解除pad与供应商关系
	 */
	@Override
	public Integer delPadSupplyInfo(long sid,String username) {
		
		Integer result = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sid", sid);
		PadSupply padSupply = null;
		List<PadSupply> padSupplyList = this.padSupplyMapper.selectListByParam(map);
		if(padSupplyList.size()>0){
			for(int i=0;i<padSupplyList.size();i++){
				padSupply = padSupplyList.get(0);
			}
			
			result = this.padSupplyMapper.delete(sid);
			if(result!=1){
				return 0;
			}else{
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("padNo", padSupply.getPadNo());
				List<PadSupply> padSupplys = this.padSupplyMapper.selectListByParam(paramMap);
				if(padSupplys.size()<=0){
					//pad绑定供应商自动修改pad状态为卖场
					PadBaseinfo padBaseinfo = this.padBaseinfoMapper.selectPadByPadNo(padSupply.getPadNo());
					padBaseinfo.setPadStatus(0);//pad状态 0在库 1卖场 2送修3停用
					padBaseinfo.setUseType(-1);//使用类型 0：导购；1：主管；2：内衣功能区；3：大场
					padBaseinfo.setUseTypeDesc("");
					this.padBaseinfoMapper.update(padBaseinfo);
				}
				
				PadLog padLog = new PadLog();
				padLog.setPadNo(padSupply.getPadNo());
				padLog.setOperator(username);
				padLog.setOperatTime(new Date());
				padLog.setDescription("系统时间："+df.format(new Date())+" 系统用户："+username+" 解除编号为："+
						padSupply.getPadNo()+" 的PAD与供应商SID为："+padSupply.getSupplyId()+" 的关系");
				
				result = this.padLogMapper.insert(padLog);
				if(result!=1){
					return 0;
				}
				
			}
		}
		return result;
	}

	/**
	 * 根据pad编号获取pad信息
	 */
	@Override
	public PadSupply selectPadSupplyByPadNo(Map<String,Object> map) {

		PadSupply padSupply = this.padSupplyMapper.selectPadSupplyByPadNo(map);
		return padSupply;
	}
}
