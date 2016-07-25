package net.shopin.supply.service;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.GuideSupply;
import net.shopin.supply.domain.entity.PadSupply;

public interface IPadSupplyRelationService {
	
	/** 
	* @Title: selectListByParam 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<PadSupply>    返回类型 
	* @throws 
	*/
	List<PadSupply> selectListByParam(Map paramMap);
	
	/**
	 * 修改与门店之间的关系
	* @Title: savePadSupply 
	* @param @param padSupply
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer savePadSupply(PadSupply padSupply,String username,String userSid,String flag);
	
	/**
	 * 
	 *保存PAD和门店供应商的关系
	 * @param padSupply
	 * @param username
	 * @param userSid
	 * @return
	 */
	public Integer savePadSupplyNew(PadSupply padSupply,String username,String userSid);
	/**
	 * 根据pad编号获取pad信息
	* @Title: selectPadSupplyByPadNo 
	* @Description: TODO(....) 
	* @param @param padno
	* @param @return    设定文件 
	* @return PadSupply    返回类型 
	* @throws
	 */
	public PadSupply selectPadSupplyRelationByPadNo(Map<String,Object> map);
	
	/**
	 * 解除pad与供应商关系
	* @Title: delPadSupplyInfo 
	* @param @param sid
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer delPadSupplyInfo(long sid,String username,String userSid,String flag);
}
