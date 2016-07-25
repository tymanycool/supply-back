package net.shopin.supply.service;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.GuideSupply;
import net.shopin.supply.domain.entity.Guideinfo;
import net.shopin.supply.domain.entity.SupplyInfo;

public interface IGuideSupplyService {
	
	/**
	 * 导购绑定供应商和门店
	 * @param guideSupply
	 * @return
	 */
	public Integer saveGuideSupply(GuideSupply guideSupply,String username);
	
	/**
	 * 解除导购与供应商关系(删除)
	* @Title: delGuideSupply 
	* @param @param sid
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer delGuideSupply(int sid,String username);

	/** 
	* @Title: selectListByParam 
	* @Description: TODO(....) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<GuideSupply>    返回类型 
	* @throws 
	*/
	List<GuideSupply> selectListByParam(Map paramMap);
	
	/**
	 * 获取所有供应商信息
	* @Title: selectSupplyInfoListByParam 
	* @Description: TODO(....) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<SupplyInfo>    返回类型 
	* @throws
	 */
	List<SupplyInfo> selectSupplyInfoListByParam(Map paramMap);
	
	/**
	 * 导购登录pad验证是否绑定该pad供应商
	* @Title: loginSupplyValide 
	* @param @param map
	* @param @return    设定文件 
	* @return GuideSupply    返回类型 
	* @throws
	 */
	public List<GuideSupply> loginSupplyValide(Map<String ,Object> map);
	
	/**
	 * 根据id获取导购与供应商信息
	* @Title: get 
	* @param @param sid
	* @param @return    设定文件 
	* @return GuideSupply    返回类型 
	* @throws
	 */
	public GuideSupply get(long sid);
	
	/**
	 * 导购转场
	* @Title: changeGuideSupply 
	* @Description: TODO(....) 
	* @param @param guideSupply
	* @param @param username
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int changeGuideSupply(GuideSupply guideSupply,String username);
	
	public int saveBrandSSDSid(GuideSupply guideSupply);
	public int saveChangeBrandSSDSid(GuideSupply guideSupply);
	
	public int update(GuideSupply guideSupply);
}
