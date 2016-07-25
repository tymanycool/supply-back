/**
 * SupplyAreaServiceImpl.java
 * net.shopin.expense.service.impl
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月22日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.expense.mapper.ExpenseSupplyAreaMapper;
import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.QueryPo;
import net.shopin.expense.service.ISupplyAreaService;

/**
 * <p>ClassName:SupplyAreaServiceImpl</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月22日下午4:54:33
 */
@Service
public class SupplyAreaServiceImpl implements ISupplyAreaService {

	@Autowired
	private ExpenseSupplyAreaMapper supplyAreaMapper;
	@Override
	public void insert(ExpenseSupplyArea supplyArea) {
		supplyAreaMapper.insert(supplyArea);
	}
	@Override
	public List<ExpenseSupplyArea> querySupplyAreaInfo(QueryPo query) {
		return supplyAreaMapper.querySupplyAreaInfo(query);
	}
	@Override
	public List<ExpenseSupplyArea> querySupplyAreaDetail(ExpenseSupplyArea area, String month) {
		
		Map<String, Object> coll = new HashMap<String, Object>();
		coll.put("brandSid", area.getBrandSid());
		coll.put("supplySid", area.getSupplySid());
		coll.put("shopSid", area.getShopSid());
		coll.put("month", month);
		
		return supplyAreaMapper.querySupplyAreaDetail(coll);
		
	}
	@Override
	public boolean isExits(ExpenseSupplyArea supplyArea) {
		
		Map<String, Object> coll = new HashMap<String, Object>();
		coll.put("brandSid", supplyArea.getBrandSid());
		coll.put("supplySid", supplyArea.getSupplySid());
		coll.put("shopSid", supplyArea.getShopSid());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		coll.put("calDate", sdf.format(supplyArea.getCalDate()).substring(0, 10));
		int count = supplyAreaMapper.querySupplyAreaDetailCount(coll);
		return !(count == 0);
		
	}
	@Override
	public List<ExpenseSupplyArea> querySupplyLayerAreaInfo(QueryPo query) {
		
		return supplyAreaMapper.querySupplyLayerAreaInfo(query);
		
	}
	@Override
	public List<ExpenseSupplyArea> querySupplyLayerAreaDetail(ExpenseSupplyArea area, String month) {
		
		Map<String, Object> coll = new HashMap<String, Object>();
		coll.put("supplySid", area.getSupplySid());
		coll.put("shopSid", area.getShopSid());
		coll.put("month", month);
		
		return supplyAreaMapper.querySupplyLayerAreaDetail(coll);
		
	}
	@Override
	public List<ExpenseSupplyArea> getSupplyAreaForDay(ExpenseSupplyArea area, String day) {
		
		Map<String, Object> coll = new HashMap<String, Object>();
		coll.put("supplySid", area.getSupplySid());
		coll.put("shopSid", area.getShopSid());
		coll.put("day", day);
		
		return supplyAreaMapper.getSupplyAreaForDay(coll);
		
	}

}

