package net.shopin.supply.persistence;

import java.util.Map;

import net.shopin.supply.domain.entity.PadSupply;

import com.shopin.core.framework.base.persistence.BaseMapper;

public interface PadSupplyMapper extends BaseMapper<PadSupply>{
	
	/**
	 * 根据pad编号获取pad信息
	* @Title: selectPadSupplyByPadNo 
	* @param @param padno
	* @param @return    设定文件 
	* @return PadSupply    返回类型 
	* @throws
	 */
	public PadSupply selectPadSupplyByPadNo(Map<String,Object> map);
	
	/**
	 * 获取总条数(用于分页)
	 */
	public Integer getCountByParam(Map<String, Object> paramMap);
	/**
	 * 修改Pad供应商信息
	 * @param padSupplyInfo
	 * @return
	 */
	public Integer updateByPrimaryKeySelective(PadSupply padSupplyInfo);
	
	/**
	 * 
	 * @param padSupplyInfo
	 * @return
	 */
	public Integer updateNoSelective(PadSupply padSupplyInfo);
	
	/**
	 * 删除
	 * @param padNo
	 * @return
	 */
	public Integer deleteByPadNo(String padNo);
	
}
