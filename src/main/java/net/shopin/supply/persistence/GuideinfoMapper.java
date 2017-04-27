package net.shopin.supply.persistence;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.Guideinfo;
import net.shopin.supply.domain.vo.GuideinfoVO;

import com.shopin.core.framework.base.persistence.BaseMapper;

public interface GuideinfoMapper extends BaseMapper<Guideinfo>{
	
	public int updateValidBitStatus(Guideinfo guideinfo);
	
	public int delValidBitStatus(Guideinfo guideinfo);

	/**
	 * 通过供应商门店获取导购组
	 * @param paramMap
	 * @return
	 */
    public List<Guideinfo> selectGuideListByParam(Map<String,Object> paramMap);
    
    /**
     * 获取导购基本信息和对应导购胸卡编号信息列表(带分页)
    * @Title: getAllGuideInfoListPage 
    * @param @param paramMap
    * @param @return    设定文件 
    * @return List<GuideinfoVO>    返回类型 
    * @throws
     */
    public List<GuideinfoVO> getAllGuideInfoListPage(Map<String,Object> paramMap);
    
    /**
     * 获取导购基本信息和对应导购胸卡编号信息列表
    * @Title: getAllGuideInfoList 
    * @param @param paramMap
    * @param @return    设定文件 
    * @return List<GuideinfoVO>    返回类型 
    * @throws
     */
    public List<GuideinfoVO> getAllGuideInfoList(Map<String,Object> paramMap);
    
    public Guideinfo selectByPrimaryKey(int sid);
    
    public Integer delete(int sid);
    
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
     * 导购变价权限记录job查询
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
     * 根据导购编号修改
     * @Title: updateByGuideNo 
     * @Description: TODO
     * @param @param paramMap
     * @param @return   
     * @return int  
     * @throws
     * @author zhangqing
     * @date 2015-3-26
     */
    public int updateByGuideNo(Guideinfo guideinfo);
    
    /**
     * 根据导购编号查询
     * @Title: selectByGuideNo 
     * @Description: TODO
     * @param @param guideNo
     * @param @return   
     * @return Guideinfo  
     * @throws
     * @author zhangqing
     * @date 2015-4-21
     */
    public GuideinfoVO selectByGuideNo(int guideNo);
}
