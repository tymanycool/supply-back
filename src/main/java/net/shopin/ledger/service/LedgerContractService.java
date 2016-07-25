package net.shopin.ledger.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.shopin.ledger.po.LedgerContractCourse;
import net.shopin.ledger.po.LedgerContractCustom;
import net.shopin.ledger.po.LedgerContractExample;
import net.shopin.ledger.po.LedgerContractSupplyInfo;
import net.shopin.ledger.po.LedgerContractSupplyInfoExample;
import net.shopin.supply.domain.vo.DifferDeductionVo;

public interface LedgerContractService {

	/**
	 * 新增合同台账
	 * 
	 * @param ledgerContractCustom
	 *            合同台账信息
	 * @throws Exception
	 */
	public Integer insertLedgerContractCustom(
			LedgerContractCustom ledgerContractCustom) throws Exception;

	/**
	 * 根据供应商编码查询供应商基本信息
	 * 
	 * @param supplierCode
	 *            供应商编码
	 * @return 返回的供应商基本信息
	 * @throws Exception
	 */
	public LedgerContractSupplyInfo selectBySupplierCodeOfSupplyInfo(
			String supplierCode) throws Exception;

	/**
	 * 根据合同台账基本信息和供应商基本信息查询条件综合分页查询合同台账信息
	 * 
	 * @param index
	 *            分页索引
	 * @param pageSize
	 *            分页每页数量
	 * @param example1
	 *            合同台账基本信息查询条件
	 * @param example2
	 *            供应商基本信息查询条件
	 * @return 返回的合同台账信息集合
	 * @throws Exception
	 */
	public List<LedgerContractCustom> selectLedgerContractCustomByPaginAndExample(
			Integer index, Integer pageSize,
			LedgerContractExample example1,
			LedgerContractSupplyInfoExample example2) throws Exception;

	/**
	 * 根据合同台账基本信息和供应商基本信息查询条件综合查询合同台账信息总数
	 * @param example1		合同台账基本信息查询条件
	 * @param example2		供应商基本信息查询条件
	 * @return				返回合同台账信息总数
	 * @throws Exception
	 */
	public int selectLedgerContractCustomCountByPaginAndExample(
			LedgerContractExample example1,
			LedgerContractSupplyInfoExample example2
			) throws Exception;

	/**
	 * 更新合同台账
	 * @param ledgerContractCustom	合同台账信息
	 * @throws Exception
	 */
	public void updateLedgerContractCustom(LedgerContractCustom ledgerContractCustom) throws Exception;
	
	/**
	 * 导入合同台帐
	 * @param hMap
	 * @param startRow
	 * @throws Exception
	 */
	public void insertOfExcelLedgerContractCustom(HashMap<String, List<String>> hMap, int startRow) throws Exception;
	
	/**
	 * 差异性扣率导出（同一供应商同品牌不同门店）
	 * @return
	 */
	public List<DifferDeductionVo> selectByDifferDeduction() throws Exception;
	
	/**
	 * 差异性扣率统计表（同品牌不同供应商）
	 * @return
	 * @throws Exception
	 */
	public List<DifferDeductionVo> selectDifferDeductionByDifferSupply() throws Exception;
	/**
	 * 品牌扣率导出（在某一范围内的品牌扣率）
	 * @param differDeductionVo
	 * @return
	 * @throws Exception
	 */
	public List<DifferDeductionVo> selectDeductionByRange(Map<String,Integer> map) throws Exception;
	
	/**
	 * 按parentId查询
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<LedgerContractCustom> selectLedgerContractCustomListByParentId(Integer parentId) throws Exception;
	
	/**
	 * 按Id查询
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public LedgerContractCustom selectLedgerContractCustomListById(Integer id) throws Exception;
	
	/**
	 * 查询台帐历程信息
	 * @param ledgerContractId
	 * @return
	 * @throws Exception
	 */
	public List<LedgerContractCourse> selectByLedgerContractIdOfCourse(Integer ledgerContractId) throws Exception;
	
}
