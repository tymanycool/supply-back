/**
 * 
 */
package net.shopin.supply.ogg.persistence;

import java.util.List;
import java.util.Map;


public interface OmsMapper {
	
	
	/**
	 * 查询销售流水list
	 */
	public List selectCashierList(Map paramMap);
	
	
	public List selectLongShortList(Map paramMap);
	
	public List selectCashierListForExcel(Map paramMap);
	
	public List selectLongShortListForExcel(Map paramMap);
	
	
	public int supplyCashierListCount(Map paramMap) ;
	
	public int selectLongShortListCount(Map paramMap) ;


}
