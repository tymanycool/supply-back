package net.shopin.supply.service;

import java.util.List;
import java.util.Map;
import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.PadBaseinfo;

public interface IGuideLogininfoService {
	
	public List<GuideLogininfo> selectListByParam(Map paramMap);
	
	/**
	 * 获取导购登录信息总数
	* @Title: getCountByParam 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer getCountByParam(Map<String, Object> paramMap);
	
	/**
	 * 保存导购登录信息
	* @Title: saveGuideLoginInfo 
	* @param @param guideLogininfo
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer saveGuideLoginInfo(GuideLogininfo guideLogininfo);
	
	/**
	 * 删除导购登录信息(只修改导购登录信息的有效状态)
	* @Title: updateValidBitStatus 
	* @param @param guideLogininfo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateValidBitStatus(GuideLogininfo guideLogininfo);
	
	/**
	 * 根据用户名、密码查询导购登录信息
	* @Title: selectGuideLoginByUsername 
	* @Description: TODO(....) 
	* @param @param map
	* @param @return    设定文件 
	* @return GuideLogininfo    返回类型 
	* @throws
	 */
	public GuideLogininfo selectGuideLoginByUsername(Map<String ,Object> map);
	
	/**
	  * 现有导购信息注册，坚持胸卡编号是否唯一
	 * @Title: checkChestCardNumIsUnique 
	 * @param @param map
	 * @param @return    设定文件 
	 * @return GuideLogininfo    返回类型 
	 * @throws
	  */
	 public GuideLogininfo checkChestCardNumIsUnique(Map<String ,Object> map);
	 
	 /**
	  * 生成胸卡编号
	 * @Title: saveLogininfo 
	 * @param @param guideLogininfo
	 * @param @param username
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	  */
	 public Integer saveLogininfo(GuideLogininfo guideLogininfo,String username);
	 

	/**
	 * 检查导购在某门店是否已有胸卡
	* @Title: selectGuideChestCartList 
	* @Description: TODO(....) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<GuideLogininfo>    返回类型 
	* @throws
	 */
	public List<GuideLogininfo> selectGuideChestCartList(Map paramMap);
	
	/**
	 * 删除导购胸卡编号
	* @Title: delGuideChestCard 
	* @Description: TODO(....) 
	* @param @param sid
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer delGuideChestCard(long sid);
	
	/**
	 * 检查唯一性
	 * @Title: checkLoginNameIsUnique 
	 * @Description: TODO
	 * @param @param map
	 * @param @return   
	 * @return GuideLogininfo  
	 * @throws
	 * @author zhangqing
	 * @date 2015-4-27
	 */
	public GuideLogininfo checkIsUnique(Map<String ,Object> map);
	
}
