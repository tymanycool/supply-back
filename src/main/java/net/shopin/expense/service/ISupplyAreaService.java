/**
 * ISupplyAreaService.java
 * net.shopin.expense.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月22日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.service;

import java.util.List;

import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.QueryPo;

/**
 * <p>ClassName:ISupplyAreaService</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月22日下午4:52:33
 */
public interface ISupplyAreaService {

	void insert(ExpenseSupplyArea supplyArea);

	List<ExpenseSupplyArea> querySupplyAreaInfo(QueryPo query);

	List<ExpenseSupplyArea> querySupplyAreaDetail(ExpenseSupplyArea area, String month);

	boolean isExits(ExpenseSupplyArea supplyArea);

	List<ExpenseSupplyArea> querySupplyLayerAreaInfo(QueryPo query);

	List<ExpenseSupplyArea> querySupplyLayerAreaDetail(ExpenseSupplyArea area, String month);

	List<ExpenseSupplyArea> getSupplyAreaForDay(ExpenseSupplyArea area, String day);
	
}

