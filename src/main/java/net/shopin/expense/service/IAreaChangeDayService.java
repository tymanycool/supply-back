/**
 * IAreaChangeDayService.java
 * net.shopin.expense.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月25日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.service;

import java.util.List;

import net.shopin.expense.po.ExpenseAreaChangeDay;

/**
 * <p>ClassName:IAreaChangeDayService</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月25日下午2:24:39
 */
public interface IAreaChangeDayService {
	List<String> getChangeDaysByMonth(String month);

	List<ExpenseAreaChangeDay> getChangeDaysObjByMonth(String month);

	void removeChangeDay(int parseInt);

	void updateChangeDay(ExpenseAreaChangeDay day);

	void createChangeDay(ExpenseAreaChangeDay day);
}

