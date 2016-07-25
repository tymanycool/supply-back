package net.shopin.supply.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.PadLog;
import net.shopin.supply.domain.vo.PadBaseinfoVO;

public interface IPadBaseinfoService {
	
	/**
	 * 查询pad及供应商信息
	* @Title: getPadBaseAndSupplyList 
	* @Description: TODO(....) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List    返回类型 
	* @throws
	 */
	public List getPadBaseAndSupplyList(Map paramMap);
	
	/**
	 * 获取关联供应商PAD的总数（padBaseInfo 关联  padSupply）
	 * @param paramMap
	 * @return
	 * @since JDK 1.6
	 */
	public Integer getCountByPadBaseAndSupplyInfo(Map paramMap);
	
	public Integer save(PadBaseinfo padBaseinfo,String username,String userSid);
	public void update(PadBaseinfo padBaseinfo);
	
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
	 * pad信息总数(不关联其他表)
	* @Title: getCountByParam 
	* @Description: TODO(....) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer getCountByParam(Map<String, Object> paramMap);
	

	
	/**
	 * 根据mac地址查询
	* @Title: selectPadByMacaddress 
	* @Description: TODO(....) 
	* @param @param padNo
	* @param @return    设定文件 
	* @return PadBaseinfo    返回类型 
	* @throws
	 */
	public PadBaseinfo selectPadByMacaddress(Map<String, Object> paramMap);
	
	/**
	 * 
	 * @Title: checkPadNoIsUnique 
	 * @Description: TODO
	 * @param @param map
	 * @param @return   
	 * @return PadBaseinfo  
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
	public Integer deletePadInfoBySid(Long sid,String username,String userSid);
	
	/**
	 * 保存调拨信息
	 * @param padNo
	 * @param targetShopId
	 * @param targetShopName
	 * @return
	 */
	public void savePadAllocationInfo(String[] padNo,String targetShopId,String targetShopName,Map<String, String> info) throws Exception;
	
	/**
	 * 保存收货信息
	 * @param padNo
	 */
	public void savePadReceiveInfo(String[] padNo,Map<String, String> info) throws Exception;
	
	/**
	 * 信息报表（按门店查询）
	 * @param shopId
	 */
	public Map<String, Integer> selectPadInfoReport(String shopId);

	/**
	 * 导入Excel
	 * @param resultList
	 * @return
	 * @throws Exception
	 */
	public String savePadInfoOfExcel(HashMap<String, Object> resultList,Map<String, String> info) throws Exception;
	
	/**
	 * 查看历史记录
	 * @param padNo
	 * @return
	 */
	public List<PadLog> selectPadOperateLogByPadNo(String padNo);
	
	/**
	 * 关联订货信息查询
	 * @param paramMap
	 * @return
	 */
	public List getPadInfoAndPurchaseInfo(Map paramMap);
	/**
	 * 关联订货信息查询,获得数目
	 * @param paramMap
	 * @return
	 */
	public Integer getCountByPadInfoAndPurchaseInfo(Map<String, Object> paramMap);
	/**
	 * 查询pad信息报表
	 * @param paramMap
	 * @return
	 */
	public Map<String, String> selectPadInfoReportByPadStatus(HashMap<String, Object> paramMap);

	/**
	 * 根据主键查询
	 * @param primaryKey
	 */
	public PadBaseinfo selectPadbyPk(Long primaryKey);
	
	/**
	 * 判断PAD录入数量是否已达上限
	 * @param padPurchaseBatchNo
	 *            PAD批次号
	 * @param addedNum
	 *            添加PAD的数量
	 * @return true:录入pad已达上限。
	 */
	public boolean enteredNumIsEnough(String padPurchaseBatchNo, Integer addedNum);
	/**
	 * 判断macAddress是否唯一
	 * @param macAddress
	 * @return  true:唯一（即db不存在）
	 */
	public boolean macAddressIsUnique(String macAddress);
	/**
	 * 判断padNo是否唯一
	 * @param padNo
	 * @return
	 */
	public boolean padNoIsUnique(String padNo);
	
	/**
	 * 获取所有可调拨的PAD信息
	 * @author xuanyy
	 * @param paramMap
	 */
	public List<PadBaseinfoVO> selectPadCanAllocateList(Map<String,Object> paramMap)throws Exception;

	/**
	 * 获取所有可调拨的PAD数量
	 * @param map
	 * @return
	 */
	public Integer getPadCanAllocateCount(Map<String, Object> map);
	
}
