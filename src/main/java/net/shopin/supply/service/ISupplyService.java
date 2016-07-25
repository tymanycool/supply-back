package net.shopin.supply.service;

import java.util.List;

import net.shopin.supply.domain.entity.Supply;


public interface ISupplyService {
	/**
	 * 添加
	 * @param supply
	 * @return
	 * @throws Exception
	 */
	
	public int insert(Supply supply)throws Exception  ;
	/**
	 * 修改
	 * @param supply
	 * @return 
	 */
	int updateSupply (Supply supply)throws Exception;

	/**删除
	 * @param sid
	 */
	public void deleteByPrimaryKey(Integer sid);
	/**
	 * 分页
	 * 查询所有
	 * @param supply
	 * @return
	 */
	public List selectAllSupply(Supply supply);
	/**
	 * 
	 * 按条件
	 *  查询所有条数
	 * @param supply
	 * @return
	 */
	public int SupplyCount(Supply supply);

	/**
	 * 根据id查询
	 * @param supply
	 * @return
	 */
	public Supply findSid(Integer sid);
	
	

}
