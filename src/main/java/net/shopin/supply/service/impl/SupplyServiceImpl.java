package net.shopin.supply.service.impl;

import java.util.List;

import net.shopin.supply.domain.entity.Supply;
import net.shopin.supply.persistence.SupplyMapper;
import net.shopin.supply.service.ISupplyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Scope("prototype")
@Transactional
@Service
public class SupplyServiceImpl implements ISupplyService {
	@Autowired
	private SupplyMapper supplyMapper;

	/**
	 * 添加
	 */
	public int insert(Supply supply) throws Exception {
		return this.supplyMapper.insertBrandLOGO(supply);
	}

	/**
	 * 修改
	 */
	public int updateSupply(Supply supply) throws Exception {
		return this.supplyMapper.updateSupply(supply);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shopin.supply.service.service.SupplyService#deleteByPrimaryKey(java
	 * .lang.Integer)
	 */
	public void deleteByPrimaryKey(Integer sid) {
		this.supplyMapper.deleteByPrimaryKey(sid);
	}

	/**
	 * 条件查询所有
	 */
	public List selectAllSupply(Supply supply) {
		return this.supplyMapper.selectListSupply(supply);
	}

	
	/**
	 * 根据id查询
	 */
	public Supply findSid(Integer sid) {
	return this.supplyMapper.selectBySupplyKey(sid);
}
	/**
	 * 条件查询
	 * 总条数
	 */
	public int SupplyCount(Supply supply) {
		// TODO Auto-generated method stub
		return this.supplyMapper.supplyCount(supply);
	}

}
