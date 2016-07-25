package net.shopin.supply.persistence;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.PadSupply;
import net.shopin.supply.domain.vo.PadBaseinfoVO;

import com.shopin.core.framework.base.persistence.BaseMapper;

public interface PadBaseinfoMapper extends BaseMapper<PadBaseinfo>{
	
    
    /**
     * 根据PAD编号查询
    * @Title: selectPadByPadNo 
    * @param @param padNo
    * @param @return    设定文件 
    * @return PadBaseinfo    返回类型 
    * @throws
     */
    public PadBaseinfo selectPadByPadNo(String padNo);
    
    /**
     * 查找
     * @param sid
     * @return
     */
    public PadBaseinfo selectByPrimaryKey(Long sid);
    /**
     * 获取pad和绑定供应商信息
    * @Title: getPadBaseAndSupplyList 
    * @param @param paramMap
    * @param @return    设定文件 
    * @return List    返回类型 
    * @throws
     */
    public List getPadBaseAndSupplyList(Map paramMap);
    
    /**
     * 获取绑定供应商pad的个数:(padBaseinfo 关联 padSupply)
     * @param paramMap
     * @return
     * @since JDK 1.6
     */
    public Integer getCountByPadBaseAndSupplyInfo(Map paramMap);
    
    /**
     * 根据mac地址查询
    * @Title: selectPadByMacaddress 
    * @param @param padNo
    * @param @return    设定文件 
    * @return PadBaseinfo    返回类型 
    * @throws
     */
    public PadBaseinfo selectPadByMacaddress(Map<String, Object> paramMap);
    
    /**
     * 检查pad编号是否唯一
     * @Title: checkChestCardNumIsUnique 
     * @Description: TODO
     * @param @param map
     * @param @return   
     * @return GuideLogininfo  
     * @throws
     * @author zhangqing
     * @date 2015-4-9
     */
	public PadBaseinfo checkPadinfoIsUnique(Map<String ,Object> map);
	
	/**
	 * 按sid删除信息
	 * @param sid
	 * @return
	 */
	public Integer deletePadInfoBySid(Long sid);
	
	/**
	 * 信息报表（按门店查询，一个状态一次查询）
	 * @param shopId
	 */
	public Integer selectPadInfoReport(Map<String ,Object> map);
	/**
	 * 信息报表(一个门店一次查询)
	 * @author xuanyy
	 * @param map
	 */
	public List<PadBaseinfoVO> selectPadStatusNumByShopId(Map<String, Object> map);
//	public Map<String, Integer> selectPadStatusNumByShopId(Map<String, Object> map);
	
	/**
	 * 关联订货信息查询
	 * @param paramMap
	 * @return
	 */
	public List getPadInfoAndPurchaseInfo(Map paramMap);
	
	/**
	 * 查询数目（关联pad_supply ,pad_purchase）
	 * @param paramMap
	 * @return
	 */
	public Integer getCountByPadInfoAndPurchaseInfo(Map<String, Object> paramMap);
	/**
	 * 根据批次号查询PAD数目（关联Pad_supply）
	 * @param padBatchNo  批次号
	 * @return
	 */
	public Integer getCountByPadBatchNo(Map<String, Object> paramMap);
	
	/**
	 * 获取所有可调拨的PAD
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<PadBaseinfoVO> getPadCanAllocateList(Map<String, Object> paramMap) throws Exception;

	/**
	 * 获取所有可调拨的PAD数量
	 * @param paramMap
	 * @return
	 */
	public Integer getPadCanAllocatCount(Map<String, Object> map);

	
	
	
}
