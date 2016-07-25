package net.shopin.ledger.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.shopin.ledger.po.LedgerContract;
import net.shopin.ledger.po.LedgerContractCourse;
import net.shopin.ledger.po.LedgerContractCustom;
import net.shopin.ledger.po.LedgerContractExample;
import net.shopin.ledger.po.LedgerContractSupplyInfo;
import net.shopin.ledger.po.LedgerContractSupplyInfoExample;
import net.shopin.supply.domain.vo.DifferDeductionVo;

public interface LedgerContractMapperCustom {

	/**
	 * 插入合同台账基本信息中不为空的数据
	 * 
	 * @param ledgerContract
	 *            合同台账基本信息
	 * @throws Exception
	 */
	public void insertSelectiveOfLedgerContract(LedgerContract ledgerContract)
			throws Exception;

	/**
	 * 插入供应商基本信息中不为空的数据
	 * 
	 * @param ledgerContractSupplyInfo
	 *            供应商基本信息
	 * @throws Exception
	 */
	public void insertSelectiveOfSupplyInfo(
			LedgerContractSupplyInfo ledgerContractSupplyInfo) throws Exception;

	/**
	 * 插入历程信息中不为空的数据
	 * 
	 * @param ledgerContractCourse
	 *            历程信息
	 * @throws Exception
	 */
	public void insertSelectiveOfCourse(
			LedgerContractCourse ledgerContractCourse) throws Exception;

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
	 * 根据供应商编码更新供应商基本信息
	 * 
	 * @param ledgerContractSupplyInfo
	 *            供应商基本信息
	 * @throws Exception
	 */
	public void updateBySupplierCodeSelectiveOfSupplyInfo(
			LedgerContractSupplyInfo ledgerContractSupplyInfo) throws Exception;

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
	 * @return 返回合同台账信息列表
	 * @throws Exception
	 */
	public List<LedgerContractCustom> selectLedgerContractCustomByPaginAndExample(
			@Param("index") Integer index, @Param("pageSize") Integer pageSize,
			@Param("example1") LedgerContractExample example1,
			@Param("example2") LedgerContractSupplyInfoExample example2)
			throws Exception;

	

	/**
	 * 根据合同台账基本信息和供应商基本信息查询条件综合查询合同台账信息总数
	 * 
	 * @param example1
	 *            合同台账基本信息查询条件
	 * @param example2
	 *            供应商基本信息查询条件
	 * @return 返回合同台账信息总数
	 * @throws Exception
	 */
	public int selectLedgerContractCustomCountByPaginAndExample(
			@Param("example1") LedgerContractExample example1,
			@Param("example2") LedgerContractSupplyInfoExample example2)
			throws Exception;

	/**
	 * 根据合同台账主键id更新合同台账基本信息
	 * 
	 * @param ledgerContract
	 *            合同台账基本信息
	 * @throws Exception
	 */
	public void updateByPrimaryKeySelectiveOfLedgerContract(
			LedgerContract ledgerContract) throws Exception;

	

	/**
	 * 根据合同台账基本信息主键id获取协议历程信息
	 * 
	 * @param ledgerContractId
	 *            合同台账基本信息主键id
	 * @return 返回的协议历程信息列表
	 * @throws Exception
	 */
	public List<LedgerContractCourse> selectByLedgerContractIdOfCourse(Integer ledgerContractId)
			throws Exception;

	/**
	 * 根据合同台账基本信息主键id删除协议历程
	 * 
	 * @param ledgerContractId
	 *            合同台账基本信息主键id
	 * @throws Exception
	 */
	public void deleteByLedgerContractIdOfCourse(Integer ledgerContractId) throws Exception;
	
	/**
	 * 差异性扣率导出（同一供应商同品牌不同门店）,得到查询条件
	 * @return
	 */
	public List<DifferDeductionVo> selectByDifferDeductionGetCriteria() throws Exception;
	/**
	 * 差异性扣率导出（同一供应商同品牌不同门店）,按上面查询条件得到结果
	 * @param differDeductionVo
	 * @return
	 * @throws Exception
	 */
	public List<DifferDeductionVo> selectByDifferDeductionGetResultByCriteria(DifferDeductionVo differDeductionVo) throws Exception;

	/**
	 * 差异性扣率统计表（同品牌不同供应商）,得到查询条件
	 * @return
	 * @throws Exception
	 */
	public List<DifferDeductionVo> selectDifferDeductionByDifferSupplyGetCriteria() throws Exception;
	
	/**
	 * 差异性扣率统计表（同品牌不同供应商），按上面查询条件得到结果
	 * @param differDeductionVo
	 * @return
	 * @throws Exception
	 */
	public List<DifferDeductionVo> selectDifferDeductionSupplyGetResultByDifferSupplyCriteria(DifferDeductionVo differDeductionVo) throws Exception;
	/**
	 * 品牌扣率导出（在某一范围内的品牌扣率）
	 * @param rangeStart
	 * @param rangeEnd
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
	 * 根据供应商编码、品牌名称、品类、门店编码、开始日期、结束日期、合同截止日期确定该条目唯一（其实也是无法确定的，人工的，如果重复置为无效）
	 * @param ledgerContractCustom
	 * @return
	 */
	public Integer selectLegerContractByCriteria(LedgerContractCustom ledgerContractCustom);
}
