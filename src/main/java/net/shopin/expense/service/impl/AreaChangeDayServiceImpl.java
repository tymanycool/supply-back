/**
 * AreaChangeDayServiceImpl.java
 * net.shopin.expense.service.impl
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月25日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.expense.mapper.ExpenseAreaChangeDayMapper;
import net.shopin.expense.po.ExpenseAreaChangeDay;
import net.shopin.expense.service.IAreaChangeDayService;

/**
 * <p>ClassName:AreaChangeDayServiceImpl</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月25日下午2:27:41
 */
@Service
public class AreaChangeDayServiceImpl implements IAreaChangeDayService {

	@Autowired
	private ExpenseAreaChangeDayMapper areaChangeDayMapper;
	
	@Override
	public List<String> getChangeDaysByMonth(String month) {

		List<String> result = new ArrayList<String>();
		
		List<ExpenseAreaChangeDay> list = areaChangeDayMapper.getChangeDaysByMonth(month);
		int size = list.size();
		for(int i=0; i<size; i++){
			int index = 0;
			String temp = list.get(0).getChangeDay();
			for(int j=1; j<list.size(); j++){
				if(Integer.parseInt(temp) > Integer.parseInt(list.get(j).getChangeDay())){
					index = j;
					temp = list.get(j).getChangeDay();
				}
			}
			list.remove(index);
			result.add(temp);
		}
		return result;
	}

	@Override
	public List<ExpenseAreaChangeDay> getChangeDaysObjByMonth(String month) {
		
		return areaChangeDayMapper.getChangeDaysByMonth(month);
		
	}

	@Override
	public void removeChangeDay(int sid) {
		
		areaChangeDayMapper.deleteByPrimaryKey(sid);
		
	}

	@Override
	public void updateChangeDay(ExpenseAreaChangeDay day) {
		
		areaChangeDayMapper.updateByPrimaryKeySelective(day);
		
	}

	@Override
	public void createChangeDay(ExpenseAreaChangeDay day) {
		
		areaChangeDayMapper.insert(day);
		
	}

}

