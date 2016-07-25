package net.shopin.expense.mapper;

import java.util.List;
import java.util.Map;

import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.ExpenseSupplyAreaExample;
import net.shopin.expense.po.QueryPo;

import org.apache.ibatis.annotations.Param;

public interface ExpenseSupplyAreaMapper {
    int countByExample(ExpenseSupplyAreaExample example);

    int deleteByExample(ExpenseSupplyAreaExample example);

    int deleteByPrimaryKey(Long sid);

    int insert(ExpenseSupplyArea record);

    int insertSelective(ExpenseSupplyArea record);

    List<ExpenseSupplyArea> selectByExample(ExpenseSupplyAreaExample example);

    ExpenseSupplyArea selectByPrimaryKey(Long sid);

    int updateByExampleSelective(@Param("record") ExpenseSupplyArea record, @Param("example") ExpenseSupplyAreaExample example);

    int updateByExample(@Param("record") ExpenseSupplyArea record, @Param("example") ExpenseSupplyAreaExample example);

    int updateByPrimaryKeySelective(ExpenseSupplyArea record);

    int updateByPrimaryKey(ExpenseSupplyArea record);

    List<ExpenseSupplyArea> querySupplyAreaInfo(QueryPo query);

	List<ExpenseSupplyArea> querySupplyAreaDetail(Map<String, Object> coll);

	int querySupplyAreaDetailCount(Map<String, Object> coll);

	List<ExpenseSupplyArea> querySupplyLayerAreaInfo(QueryPo query);

	List<ExpenseSupplyArea> querySupplyLayerAreaDetail(Map<String, Object> coll);

	List<ExpenseSupplyArea> getSupplyAreaForDay(Map<String, Object> coll);
}