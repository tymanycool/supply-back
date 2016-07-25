package net.shopin.supply.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.shopin.supply.domain.entity.Guideinfo;
import net.shopin.supply.domain.entity.GuideinfoAttach;
import net.shopin.supply.domain.vo.GuideinfoVO;

public interface IGuideinfoService {
	
	/**
	 * 查询导购信息
	 * @param paramMap
	 * @return
	 */
	public List<Guideinfo> selectListByParam(Map paramMap);
	
	/**
	 * 通过供应商门店获取导购组
	* @Title: selectGuideListByParam 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<PadGuideinfo>    返回类型 
	* @throws
	 */
	public List<Guideinfo> selectGuideListByParam(Map paramMap);
	
	/**
	 * 删除导购信息(只修改导购信息的有效状态)
	 * @param guideinfo
	 */
	public String updateValidBitStatus(Guideinfo guideinfo);
	
	/**
	 * 获取导购基本信息和对应导购胸卡编号信息列表(带分页)
	* @Title: getAllGuideInfoListPage 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<GuideinfoVO>    返回类型 
	* @throws
	 */
	public List<GuideinfoVO> getAllGuideInfoListPage(Map paramMap);
	
	/**
	 * 获取导购基本信息和导购胸卡编号信息列表
	* @Title: getAllGuideInfoList 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<GuideinfoVO>    返回类型 
	* @throws
	 */
	public List<GuideinfoVO> getAllGuideInfoList(Map paramMap);
	
	/**
	 * 保存导购信息
	* @Title: save 
	* @param @param guideinfo
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer save(Guideinfo guideinfo);
	
	public Integer updateByGuideNo(Guideinfo guideinfo);
	
	/**
	 * 保存导购自助注册信息
	* @Title: saveGuideInfo 
	* @param @param guideinfo
	* @param @param familyObj
	* @param @param workObj
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer saveGuideInfo(Guideinfo guideinfo,String familyObj,String workObj);
	
	/**
	 * 保存现有导购注册信息
	* @Title: existingGuideinfosave 
	* @param @param guideinfo
	* @param @param familyObj
	* @param @param workObj
	* @param @param chestcardNum
	* @param @param shop
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer existingGuideinfosave(Guideinfo guideinfo,String familyObj,String workObj,String chestcardNum,String shop);
	
	/**
	 * 根据id获取导购自助填写信息明细
	* @Title: selectByPrimaryKey 
	* @param @param sid
	* @param @return    设定文件 
	* @return Guideinfo    返回类型 
	* @throws
	 */
	public Guideinfo selectByPrimaryKey(int sid);
	
	public List<GuideinfoAttach> selectFamilyByguideNo(Map map);
	
	/**
	 * 保存导购附属信息(家庭成员、工作经验等信息)
	* @Title: saveGuideAttach 
	* @param @param guideinfoAttach
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer saveGuideAttach(GuideinfoAttach guideinfoAttach);
	
	/**
	 * 删除导购附属信息
	* @Title: deleteGuideAttach 
	* @param @param sid
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer deleteGuideAttach(long sid);
	
	/**
	 * 根据导购编号查询导购信息
	* @Title: selectByGuideNo 
	* @param @param guideNo
	* @param @return    设定文件 
	* @return GuideLogininfo    返回类型 
	* @throws
	 */
//	public GuideLogininfo selectByGuideNo(Map paramMap);
	
	/**
	 * 获取导购信息总数
	* @Title: getCountByParam 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer getCountByParam(Map<String, Object> paramMap);
	
	/**
	 * 删除导购信息
	* @Title: delGuidinfo 
	* @param @param sid
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer delGuidinfo(int sid);
	
	/**
	 * 按门店查询导购信息列表(带分页)
	* @Title: getAllGuideInfoListByShopPage 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<GuideinfoVO>    返回类型 
	* @throws
	 */
	public List<GuideinfoVO> getAllGuideInfoListByShopPage(Map<String,Object> paramMap);
	
	/**
	 * 按门店查询导购信息列表
	* @Title: getAllGuideInfoListByShop 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return List<GuideinfoVO>    返回类型 
	* @throws
	 */
	public List<GuideinfoVO> getAllGuideInfoListByShop(Map<String,Object> paramMap);
	
	/**
     * 按门店查询导购信息总条数
    * @Title: getCountByParamByShop 
    * @param @param paramMap
    * @param @return    设定文件 
    * @return Integer    返回类型 
    * @throws
     */
    public Integer getCountByParamByShop(Map<String,Object> paramMap);
    
    /**
     * 按供应商查询导购信息列表(带分页)
    * @Title: getAllGuideInfoListBySupplyPage 
    * @param @param paramMap
    * @param @return    设定文件 
    * @return List<GuideinfoVO>    返回类型 
    * @throws
     */
    public List<GuideinfoVO> getAllGuideInfoListBySupplyPage(Map<String,Object> paramMap);
    
    /**
     * 按供应商查询导购信息列表
    * @Title: getAllGuideInfoListBySupply 
    * @param @param paramMap
    * @param @return    设定文件 
    * @return List<GuideinfoVO>    返回类型 
    * @throws
     */
    public List<GuideinfoVO> getAllGuideInfoListBySupply(Map<String,Object> paramMap);
    
    
    /**
     * 导购变价权限修改记录job查询
     * @Methods Name getAllGuideInfoListForAuthorizeGuideJob
     * @Create In 2015年6月25日 By Administrator
     * @param paramMap
     * @return List<GuideinfoVO>
     */
    public List<GuideinfoVO> getAllGuideInfoListForAuthorizeGuideJob(Map<String,Object> paramMap);
    
    
    /**
     * 按供应商查询导购信息总条数
    * @Title: getCountByParamBySupply 
    * @param @param paramMap
    * @param @return    设定文件 
    * @return Integer    返回类型 
    * @throws
     */
    public Integer getCountByParamBySupply(Map<String,Object> paramMap);
    
    /**
     * 导出导购基本信息excel
    * @Title: guideinfoReportToExcel 
    * @param @param list
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public String exportGuideinfoToExcel(HttpServletResponse response,List<GuideinfoVO> list,String type);
    
    /**
     * 
     * @Title: selectByParam 
     * @Description: TODO
     * @param @param map
     * @param @return   
     * @return Guideinfo  
     * @throws
     * @author zhangqing
     * @date 2015-4-21
     */
    public GuideinfoVO selectByGuideNo(int guideNo);
}
