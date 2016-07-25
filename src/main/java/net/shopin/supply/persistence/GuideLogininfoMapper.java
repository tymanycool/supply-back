package net.shopin.supply.persistence;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.GuideLogininfo;

import com.shopin.core.framework.base.persistence.BaseMapper;

public interface GuideLogininfoMapper extends BaseMapper<GuideLogininfo>{

	 /**
	  * 根据导购编号查询导购信息
	 * @Title: selectByGuideNo 
	 * @param @param paramMap
	 * @param @return    设定文件 
	 * @return GuideLogininfo    返回类型 
	 * @throws
	  */
	 public List<GuideLogininfo> selectByGuideNo(Map paramMap);
	 
	 public int updateValidBitStatus(GuideLogininfo guideLogininfo);
	 
	 /**
	  * 根据用户名、密码查找导购信息
	 * @Title: selectGuideLoginByUsername 
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
	 
	 public GuideLogininfo checkIsUnique(Map<String, Object> map);
}
