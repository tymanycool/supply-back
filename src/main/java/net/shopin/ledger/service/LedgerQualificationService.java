package net.shopin.ledger.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.shopin.ledger.po.LedgerQualificationCustom;
import net.shopin.ledger.po.LedgerQualificationExample;
import net.shopin.ledger.po.LedgerQualificationInspectionExample;
import net.shopin.ledger.po.LedgerQualificationSupplyInfo;
import net.shopin.ledger.po.LedgerQualificationSupplyInfoExample;
import net.shopin.supply.domain.vo.ActingLevelVO;
import net.shopin.supply.domain.vo.QualiErrorInfoVO;

import org.apache.ibatis.annotations.Param;

public interface LedgerQualificationService {

	/**
	 * 新增资质台账
	 * 
	 * @throws Exception
	 */
	public void insertLedgerQualificationCustom(
			LedgerQualificationCustom ledgerQualificationCustom)
			throws Exception;

	/**
	 * 根据供应商编码查询供应商基本信息
	 * 
	 * @param supplierCode
	 *            供应商编码
	 * @return 返回的供应商基本信息
	 * @throws Exception
	 */
	public LedgerQualificationSupplyInfo selectBySupplierCodeOfSupplyInfo(
			String supplierCode) throws Exception;

	/**
	 * 综合分页查询资质台账
	 * 
	 * @param index
	 *            分页查询索引
	 * @param pageSize
	 *            分页查询每页数目
	 * @param example1
	 *            资质台账基本信息查询条件
	 * @param exmaple2
	 *            供应商基本信息查询条件
	 * @param example3
	 *            质检报告查询条件
	 * @return 返回查询到的资质台账
	 * @throws Exception
	 */
	public List<LedgerQualificationCustom> selectLedgerQualificationCustomByPagingAndExample(
			@Param("index") Integer index, @Param("pageSize") Integer pageSize,
			@Param("example1") LedgerQualificationExample example1,
			@Param("exmaple2") LedgerQualificationSupplyInfoExample exmaple2,
			@Param("example3") LedgerQualificationInspectionExample example3)
			throws Exception;

	/**
	 * 综合查询资质台账总数
	 * @param example1
	 *            资质台账基本信息查询条件
	 * @param exmaple2
	 *            供应商基本信息查询条件
	 * @return 返回查询到的资质台账总数
	 * @throws Exception
	 */
	public int selectLedgerQualificationCustomCountByPagingAndExample(
			@Param("example1") LedgerQualificationExample example1,
			@Param("exmaple2") LedgerQualificationSupplyInfoExample exmaple2)
			throws Exception;


	/**
	 * 更新资质台账基本信息
	 * @param ledgerQualificationCustom	资质台账基本信息
	 * @throws Exception
	 */
	public void updateLedgerQualificationCustom(LedgerQualificationCustom ledgerQualificationCustom) throws Exception;
	
	/**
	 * 根据资质台账基本信息主键id更新资质台账基本信息中的审核状态、审核人、审核日期
	 * @param approvalAllInfoJSONStr	审核信息的json字符串
	 * @throws Exception
	 */
	public void updateLedgerQualificationOfReviewByPrimaryKey(String approvalAllInfoJSONStr) throws Exception;
	
	/**
	 * 导入Excel
	 * @param ledgerQualificationCustoms
	 */
	public void insertExcelOfLedgerQualificationCustom(HashMap<String, List<String>> hashMap,int startRow) throws Exception;;
	
	/**
	 * 代理商级别表导出(在柜的)
	 * @throws Exception
	 */
	public List<List<ActingLevelVO>> selectLedgerQualificationCustomByActingLevel() throws Exception;
	
	/**
	 * 因资质不能结算明细导出
	 * @param today
	 * @return
	 * @throws Exception
	 */
	public List<QualiErrorInfoVO> selectLedgerQualificationCustomForQualiError(Date today) throws Exception;
}
