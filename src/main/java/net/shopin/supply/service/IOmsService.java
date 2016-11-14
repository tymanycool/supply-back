package net.shopin.supply.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.shopin.supply.domain.vo.GuideinfoVO;
import net.shopin.supply.domain.vo.OmsInfoVo;



public interface IOmsService {
	
	/**
	 * 分页
	 * 查询所有
	 * @param supply
	 * @return
	 */
	public List selectCashierList(Map paramMap);
	
	public List selectCashierListForExcel(Map paramMap);
	/**
	 * 
	 * 按条件
	 *  查询所有条数
	 * @param supply
	 * @return
	 */
	public int SupplyCashierListCount(Map paramMap);
	
	public int SupplyLongShortListCount(Map paramMap);
	
	
	 public String cashierSelectsToExcel(HttpServletResponse response,List<OmsInfoVo> list,String type);
	 
	 public String longShortSelectsToExcel(HttpServletResponse response,List<OmsInfoVo> list,String type);
	 
	 
	 public List selectLongShortList(Map paramMap);

	
	
	

}
