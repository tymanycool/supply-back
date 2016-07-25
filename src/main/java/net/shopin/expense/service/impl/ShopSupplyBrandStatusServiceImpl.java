/**
 * ShopSupplyBrandStatusServiceImpl.java
 * net.shopin.expense.service.impl
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月30日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.expense.mapper.ShopSupplyBrandChargestatusMapper;
import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.ShopSupplyBrandChargestatus;
import net.shopin.expense.po.ShopSupplyBrandPad;
import net.shopin.expense.service.IShopSupplyBrandStatusService;

/**
 * <p>ClassName:ShopSupplyBrandStatusServiceImpl</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月30日下午2:30:48
 */
@Service
public class ShopSupplyBrandStatusServiceImpl implements IShopSupplyBrandStatusService {

	@Autowired
	ShopSupplyBrandChargestatusMapper statusMapper;
	
	@Override
	public ShopSupplyBrandChargestatus getStatus(ExpenseSupplyArea area) {

		return statusMapper.getStatus(area);

	}

	@Override
	public void update(ShopSupplyBrandChargestatus status) {
		statusMapper.update(status);
	}

	@Override
	public void insert(ShopSupplyBrandChargestatus status) {
		statusMapper.insert(status);
	}

	@Override
	public boolean has(ShopSupplyBrandChargestatus status) {
		
		ShopSupplyBrandChargestatus result = statusMapper.getStatusByStatus(status);
		if(result != null){
			return true;
		} else{
			return false;
		}
		
	}

}

