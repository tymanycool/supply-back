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
	public String selectCashierListTotalMoney(Map paramMap);
	public int SupplyCashierListCount(Map paramMap);
	public List selectCashierListForExcel(Map paramMap);
	public String cashierSelectsToExcel(HttpServletResponse response,List<OmsInfoVo> list,String type);
	
	
	public List selectLongShortListForExcel(Map paramMap);
	public int SupplyLongShortListCount(Map paramMap);
	public String SupplyLongShortListTotalMoney(Map paramMap);
	public String longShortSelectsToExcel(HttpServletResponse response,List<OmsInfoVo> list,String type);
	public List selectLongShortList(Map paramMap);


}
