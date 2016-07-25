package net.shopin.supply.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.PadLog;
import net.shopin.supply.domain.entity.PadSupply;
import net.shopin.supply.persistence.PadBaseinfoMapper;
import net.shopin.supply.persistence.PadLogMapper;
import net.shopin.supply.persistence.PadSupplyMapper;
import net.shopin.supply.service.IPadBaseinfoService;
import net.shopin.supply.service.IPadSupplyRelationService;
import net.shopin.supply.util.LogUtil;

@Service
public class PadSupplyRelationServiceImpl implements IPadSupplyRelationService {
	private Logger logger = Logger.getLogger(PadSupplyServiceImpl.class);

	@Autowired
	private PadSupplyMapper padSupplyMapper;

	@Autowired
	private PadLogMapper padLogMapper;
	
	@Autowired
	private PadBaseinfoMapper padBaseinfoMapper;
	
	@Autowired
	private IPadBaseinfoService padBaseinfoService;
	
	
	@Override
	public PadSupply selectPadSupplyRelationByPadNo(Map<String, Object> map) {
		PadSupply padSupply = this.padSupplyMapper.selectPadSupplyByPadNo(map);
		return padSupply;
	}

	@Override
	public List<PadSupply> selectListByParam(Map paramMap) {
		List<PadSupply> list = this.padSupplyMapper.selectListByParam(paramMap);
		return list;
	}

	@Override
	public Integer savePadSupply(PadSupply padSupply, String username, String userSid,String flag) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Integer result = 0;
		String padNo = padSupply.getPadNo();
		
		//pad绑定供应商自动修改pad状态为卖场
		PadBaseinfo padBaseinfo = this.padBaseinfoMapper.selectPadByPadNo(padNo);
		padBaseinfo.setPadStatus(1);//pad状态 0在库 1卖场 2送修3停用
		this.padBaseinfoMapper.update(padBaseinfo);
		//绑定门店
		if ("".equals(flag)) {
			this.padSupplyMapper.updateByPrimaryKeySelective(padSupply);
		}else {
			result = this.padSupplyMapper.insert(padSupply);
			if(result!=1){
				return 0;
			}
		}
		//供应商绑定成功修改导购基本信息表中操作人
		PadBaseinfo padinfo = new PadBaseinfo();
		padinfo.setOperator(username);
		padinfo.setOperatorSid(Integer.parseInt(userSid));
		this.padBaseinfoMapper.update(padinfo);
		//TODO
		PadLog padLog = new PadLog();
		padLog.setPadNo(padSupply.getPadNo());
		padLog.setOperator(username);
		padLog.setOperatTime(new Date());
		padLog.setOperatorSid(Integer.valueOf(userSid));
		String description = "操作：绑定。系统时间:"+df.format(new Date())+"  系统用户:"+username +"  绑定PAD编号为："+padNo+"  的PAD与供应商SID为 "+padSupply.getSupplyId()+"   的关系。   -------PAD详情如下：";
		String jsonData = LogUtil.createPadLogDesc(padBaseinfo, description);
		padLog.setDescription(jsonData);
		
		result = this.padLogMapper.insert(padLog);
		if(result!=1){
			return 0;
		}
		return result;
	}

	@Override
	public Integer delPadSupplyInfo(long sid, String username,String userSid,String flag) {
		
		Integer result = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sid", sid);
		PadSupply padSupply = null;
		List<PadSupply> padSupplyList = this.padSupplyMapper.selectListByParam(map);
		if (padSupplyList.size() > 0) {
				padSupply = padSupplyList.get(0);
			// 解除绑定
			if ("".equals(flag)) {
				//只有一条绑定：置空供应商信息，
				padSupply.setSupplyId(null);
				padSupply.setSupplyName("");
				this.padSupplyMapper.updateNoSelective(padSupply);
				//解除所有绑定：更新PAD
				String padNo1 = padSupply.getPadNo();
				PadBaseinfo pad1 = padBaseinfoService.selectPadByPadNo(padNo1);
				pad1.setUseType(-1);   //-1: 无使用类型，表示未绑定供应商
				pad1.setUseTypeDesc("");
				pad1.setPadStatus(0);  // pad状态 0在库 1卖场 2送修3停用
				padBaseinfoService.save(pad1, username,userSid);
				
			} else {
				//多条绑定：直接删除绑定信息。
				result = this.padSupplyMapper.delete(sid);
				if (result != 1) {
					return 0;
				}
			}

			PadLog padLog = new PadLog();
			padLog.setPadNo(padSupply.getPadNo());
			padLog.setOperator(username);
			padLog.setOperatTime(new Date());
			padLog.setOperatorSid(Integer.valueOf(userSid));
			String description = "操作：解绑。系统时间:"+df.format(new Date())+"  系统用户:"+username +"  解除PAD编号为："+padSupply.getPadNo()+"  的PAD与供应商SID为 "+padSupply.getSupplyId()+"   的关系。   -------PAD详情如下：";
			PadBaseinfo padBaseinfo = this.padBaseinfoMapper.selectPadByPadNo(padSupply.getPadNo());
			String jsonData = LogUtil.createPadLogDesc(padBaseinfo, description);
			padLog.setDescription(jsonData);
			result = this.padLogMapper.insert(padLog);
			
			if (result != 1) {
				return 0;
			}
		}
		return result;
	}

	@Override
	public Integer savePadSupplyNew(PadSupply padSupply, String username, String userSid) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Integer result = 0;
		String padNo = padSupply.getPadNo();
		
		//pad绑定供应商自动修改pad状态为卖场
		PadBaseinfo padBaseinfo = this.padBaseinfoMapper.selectPadByPadNo(padNo);
		padBaseinfo.setPadStatus(1);//pad状态 0在库 1卖场 2送修3停用
		this.padBaseinfoMapper.update(padBaseinfo);
		//绑定门店
		Integer sid = padSupply.getSid();
		if(sid ==null){//新建
			result = this.padSupplyMapper.insert(padSupply);
			if(result!=1)
				return 0;
		}else{//更新
			result = this.padSupplyMapper.updateByPrimaryKeySelective(padSupply);
			if(result!=1)
				return 0;
		}
		//供应商绑定成功修改导购基本信息表中操作人
		PadBaseinfo padinfo = new PadBaseinfo();
		padinfo.setOperator(username);
		padinfo.setOperatorSid(Integer.parseInt(userSid));
		this.padBaseinfoMapper.update(padinfo);
		//TODO
		PadLog padLog = new PadLog();
		padLog.setPadNo(padSupply.getPadNo());
		padLog.setOperator(username);
		padLog.setOperatTime(new Date());
		padLog.setOperatorSid(Integer.valueOf(userSid));
		String description = "操作：绑定。系统时间:"+df.format(new Date())+"  系统用户:"+username +"  绑定PAD编号为："+padNo+"  的PAD与供应商SID为 "+padSupply.getSupplyId()+"   的关系。   -------PAD详情如下：";
		String jsonData = LogUtil.createPadLogDesc(padBaseinfo, description);
		padLog.setDescription(jsonData);
		
		result = this.padLogMapper.insert(padLog);
		if(result!=1){
			return 0;
		}
		return result;
		
	}

}
