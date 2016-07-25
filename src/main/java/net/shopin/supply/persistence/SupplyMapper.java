/**
 * 
 */
package net.shopin.supply.persistence;

import java.util.List;

import net.shopin.supply.domain.entity.Supply;

public interface SupplyMapper {
	
	/**
	 * 添加 Supply
	 * @param supply
	 * @return
	 */
	public int insertBrandLOGO(Supply supply);
	
	/**
	 * 修改supply
	 * @param supply
	 * @return 
	 */
	int updateSupply (Supply supply);
	
	/**
	 * 根据id查询
	 * @param supply
	 * @return
	 */
	public  List selectListSupply(Supply supply);
	
	/**
	 * @param sid
	 */
	public void deleteByPrimaryKey(Integer sid);
	
	/**
	 * 查询所有
	 * @param ad
	 * @return
	 */
	public List selectListByPrimary(Supply supply);
	/**
	 * 条件查询
	 * 显示总条数
	 * @param ad
	 * @return
	 */
	public int supplyCount(Supply supply);

	/**
	 * 根据id查询
	 * @param supply
	 * @return
	 */
	public  Supply selectBySupplyKey( Integer sid);

}
