package net.shopin.expense.mapper;

import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.ShopSupplyBrandPad;

public interface ShopSupplyBrandPadMapper {

    int insert(ShopSupplyBrandPad record);

    int insertSelective(ShopSupplyBrandPad record);

	ShopSupplyBrandPad getPadInfo(ExpenseSupplyArea area);

	ShopSupplyBrandPad getPadByPad(ShopSupplyBrandPad pad);

	void update(ShopSupplyBrandPad pad);
}