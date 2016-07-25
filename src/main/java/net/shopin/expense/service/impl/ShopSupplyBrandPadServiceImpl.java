/**
 * ShopSupplyBrandPadServiceImpl.java
 * net.shopin.expense.service.impl
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月23日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.expense.mapper.ShopSupplyBrandPadMapper;
import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.ShopSupplyBrandPad;
import net.shopin.expense.service.IShopSupplyBrandPadService;

/**
 * <p>ClassName:ShopSupplyBrandPadServiceImpl</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月23日下午2:38:43
 */
@Service
public class ShopSupplyBrandPadServiceImpl implements IShopSupplyBrandPadService {

	@Autowired
	private ShopSupplyBrandPadMapper padMapper;
	@Override
	public ShopSupplyBrandPad getPadNums(ExpenseSupplyArea area) {
		
		return padMapper.getPadInfo(area);
		
	}
	@Override
	public boolean has(ShopSupplyBrandPad pad) {
		ShopSupplyBrandPad result = padMapper.getPadByPad(pad);
		if(result != null){
			return true;
		} else{
			return false;
		}
	}
	@Override
	public void update(ShopSupplyBrandPad pad) {
		padMapper.update(pad);
	}
	@Override
	public void insert(ShopSupplyBrandPad pad) {
		padMapper.insert(pad);
	}

}

