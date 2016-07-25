/**
 * IShopSupplyBrandPadService.java
 * net.shopin.expense.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月23日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.service;

import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.ShopSupplyBrandPad;

/**
 * <p>ClassName:IShopSupplyBrandPadService</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月23日下午2:38:05
 */
public interface IShopSupplyBrandPadService {

	ShopSupplyBrandPad getPadNums(ExpenseSupplyArea area);

	boolean has(ShopSupplyBrandPad pad);

	void update(ShopSupplyBrandPad pad);

	void insert(ShopSupplyBrandPad pad);

}

