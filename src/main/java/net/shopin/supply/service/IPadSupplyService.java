package net.shopin.supply.service;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.GuideSupply;
import net.shopin.supply.domain.entity.PadSupply;

public interface IPadSupplyService {
	
	/** 
	* @Title: selectListByParam 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<PadSupply>    返回类型 
	* @throws 
	*/
	List<PadSupply> selectListByParam(Map paramMap);
	
	/**
	 * Pad绑定供应商和门店
	* @Title: savePadSupply 
	* @param @param padSupply
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer savePadSupply(PadSupply padSupply,String username,String userSid);
	
	/**
	 * 解除pad与供应商关系
	* @Title: delPadSupplyInfo 
	* @param @param sid
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer delPadSupplyInfo(long sid,String username);
	
	/**
	 * 根据pad编号获取pad信息
	* @Title: selectPadSupplyByPadNo 
	* @Description: TODO(....) 
	* @param @param padno
	* @param @return    设定文件 
	* @return PadSupply    返回类型 
	* @throws
	 */
	public PadSupply selectPadSupplyByPadNo(Map<String,Object> map);
}
