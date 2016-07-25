package net.shopin.expense.mapper;

import java.util.List;

import net.shopin.expense.po.ExpenseAreaChangeDay;

public interface ExpenseAreaChangeDayMapper {

    int insert(ExpenseAreaChangeDay record);

    int insertSelective(ExpenseAreaChangeDay record);

	List<ExpenseAreaChangeDay> getChangeDaysByMonth(String month);

	void deleteByPrimaryKey(int sid);

	void updateByPrimaryKeySelective(ExpenseAreaChangeDay day);
}