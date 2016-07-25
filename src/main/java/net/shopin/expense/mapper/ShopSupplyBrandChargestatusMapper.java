package net.shopin.expense.mapper;

import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.ShopSupplyBrandChargestatus;

public interface ShopSupplyBrandChargestatusMapper {

    int insert(ShopSupplyBrandChargestatus record);

    int insertSelective(ShopSupplyBrandChargestatus record);

	void update(ShopSupplyBrandChargestatus status);

	ShopSupplyBrandChargestatus getStatus(ExpenseSupplyArea area);

	ShopSupplyBrandChargestatus getStatusByStatus(ShopSupplyBrandChargestatus status);
}