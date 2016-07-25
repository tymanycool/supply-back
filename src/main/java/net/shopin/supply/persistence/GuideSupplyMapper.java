package net.shopin.supply.persistence;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.GuideSupply;

import com.shopin.core.framework.base.persistence.BaseMapper;

public interface GuideSupplyMapper extends BaseMapper<GuideSupply>{

	Integer delete(int sid);
	
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
	 * 删除供应商信息(用于删除导购信息时修改所绑定供应商状态为无效)
	* @Title: updateValidBitStatus 
	* @Description: TODO(....) 
	* @param @param guideSupply
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int updateValidBitStatus(GuideSupply guideSupply);
	
	public GuideSupply selectGuideSupplyByParam(Map<String ,Object> map);
	
	/**
	 * 导购转场
	* @Title: changeGuideSupply 
	* @param @param guideSupply
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int changeGuideSupply(GuideSupply guideSupply);
	
	public int updateBrandSSDSid(GuideSupply guideSupply);
	public int updateChangeBrandSSDSid(GuideSupply guideSupply);
}
