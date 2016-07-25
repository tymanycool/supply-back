package net.shopin.ledger.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.shopin.ledger.po.Approval;
import net.shopin.ledger.po.LedgerQualificationCustom;
import net.shopin.ledger.po.LedgerQualificationExample;
import net.shopin.ledger.po.LedgerQualificationInspection;
import net.shopin.ledger.po.LedgerQualificationInspectionExample;
import net.shopin.ledger.po.LedgerQualificationSupplyInfo;
import net.shopin.ledger.po.LedgerQualificationSupplyInfoExample;
import net.shopin.ledger.po.LedgerQualificationWithBLOBs;
import net.shopin.supply.domain.vo.ActingLevelVO;
import net.shopin.supply.domain.vo.QualiErrorInfoVO;

/**
 * 资质台账mapper
 * 
 * @author lcl
 * 
 */
public interface LedgerQualificationMapperCustom {

	/**
	 * 向资质台账表中插入资质台账基本信息（不为空的数据）
	 * 
	 * @param ledgerQualificationWithBLOBs
	 *            资质台账基本信息
	 * @throws Exception
	 */
	public void insertSelectiveOfLedgerQualification(
			LedgerQualificationWithBLOBs ledgerQualificationWithBLOBs)
			throws Exception;

	/**
	 * 向供应商基本信息表中插入供应商基本信息（不为空的数据）
	 * 
	 * @param ledgerQualificationSupplyInfo
	 *            供应商基本信息
	 * @throws Exception
	 */
	public void insertSelectiveOfSupplyInfo(
			LedgerQualificationSupplyInfo ledgerQualificationSupplyInfo)
			throws Exception;

	/**
	 * 向质检报告表中插入质检报告信息（不为空的数据）
	 * 
	 * @param ledgerQualificationInspection
	 *            质检报告信息
	 * @throws Exception
	 */
	public void insertSelectiveOfInspection(
			LedgerQualificationInspection ledgerQualificationInspection)
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
	 * 根据供应商编码更新供应商基本信息
	 * 
	 * @param ledgerQualificationSupplyInfo
	 *            供应商基本信息
	 * @throws Exception
	 */
	public void updateBySupplierCodeSelectiveOfSupplyInfo(
			LedgerQualificationSupplyInfo ledgerQualificationSupplyInfo)
			throws Exception;

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
			@Param("example2") LedgerQualificationSupplyInfoExample exmaple2,
			@Param("example3") LedgerQualificationInspectionExample example3)
			throws Exception;

	/**
	 * 综合查询资质台账总数
	 * 
	 * @param example1
	 *            资质台账基本信息查询条件
	 * @param exmaple2
	 *            供应商基本信息查询条件
	 * @return 返回查询到的资质台账总数
	 * @throws Exception
	 */
	public int selectLedgerQualificationCustomCountByPagingAndExample(
			@Param("example1") LedgerQualificationExample example1,
			@Param("example2") LedgerQualificationSupplyInfoExample exmaple2)
			throws Exception;


	/**
	 * 根据资质台账基本信息id查询质检报告信息
	 * @param id	资质台账基本信息id
	 * @return		返回的质检报告信息列表
	 * @throws Exception
	 */
	public List<LedgerQualificationInspection> selectByLedgerQualificationIdOfInspection(Integer id) throws Exception;
	
	
	/**
	 * 根据质检报告主键id，更新质检报告	
	 * @param ledgerQualificationInspection	质检报告信息
	 * @throws Exception
	 */
	public void updateByPrimaryKeySelectiveOfInspection(LedgerQualificationInspection ledgerQualificationInspection) throws Exception;
	
	/**
	 * 根据资质台账基本信息主键id，更新资质台账基本信息
	 * @param ledgerQualificationWithBLOBs	资质台账基本信息
	 * @throws Exception
	 */
	public void updateByPrimaryKeySelectiveOfLedgerQualificationWithBLOBs(LedgerQualificationWithBLOBs ledgerQualificationWithBLOBs) throws Exception;
	
	/**
	 * 根据资质台账基本信息主键id删除质检报告
	 * @param ledgerQualificationId		资质台账基本信息主键id
	 * @throws Exception
	 */
	public void deleteByLedgerQualificationIdOfInspection(Integer ledgerQualificationId) throws Exception;
	
	/**
	 * 根据资质台账基本信息主键id更新资质台账基本信息中的审核状态、审核人、审核日期
	 * @param approval	审核信息
	 * @throws Exception
	 */
	public void updateLedgerQualificationOfReviewByPrimaryKey(Approval approval) throws Exception;

	/**
	 * 按注册证号码查询资质台帐中是否某个条目
	 * @param registerNumer
	 * @return
	 */
	public Integer selectLegerQulicationByRegisterNumer(LedgerQualificationCustom ledgerQualificationWithBLOBs);

	/**
	 * 代理商级别表导出，按品类查
	 * @return
	 * @throws Exception
	 */
	public List<ActingLevelVO> selectLedgerQualificationCustomByActingLevel(String category) throws Exception;
	
	/**
	 * 因资质不能结算
	 * @param today
	 * @return
	 * @throws Exception
	 */
	public List<QualiErrorInfoVO> selectLedgerQualificationCustomForQualiError(Date today) throws Exception;
}
