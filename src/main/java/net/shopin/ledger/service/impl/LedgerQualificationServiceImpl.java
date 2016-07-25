package net.shopin.ledger.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.ledger.mapper.LedgerQualificationMapperCustom;
import net.shopin.ledger.po.Approval;
import net.shopin.ledger.po.LedgerQualificationCustom;
import net.shopin.ledger.po.LedgerQualificationExample;
import net.shopin.ledger.po.LedgerQualificationInspection;
import net.shopin.ledger.po.LedgerQualificationInspectionExample;
import net.shopin.ledger.po.LedgerQualificationSupplyInfo;
import net.shopin.ledger.po.LedgerQualificationSupplyInfoExample;
import net.shopin.ledger.po.LedgerQualificationWithBLOBs;
import net.shopin.ledger.service.LedgerQualificationService;
import net.shopin.supply.domain.vo.ActingLevelVO;
import net.shopin.supply.domain.vo.QualiErrorInfoVO;
@Service
public class LedgerQualificationServiceImpl implements
		LedgerQualificationService {

	@Autowired
	LedgerQualificationMapperCustom ledgerQualificationMapperCustom;
	private static Logger logger = Logger.getLogger(LedgerQualificationServiceImpl.class);
	@Override
	public void insertLedgerQualificationCustom(
			LedgerQualificationCustom ledgerQualificationCustom)
			throws Exception {
		
		//copy LedgerQualificationCustom的资质台账基本信息到LedgerQualificationWithBLOBs
		LedgerQualificationWithBLOBs ledgerQualificationWithBLOBs = new LedgerQualificationWithBLOBs();
		//使用PropertyUtils copy属性时，当整型或者浮点型为空时copy值也为空，使用BeanUtils则会变为0
		PropertyUtils.copyProperties(ledgerQualificationWithBLOBs, ledgerQualificationCustom);
		//将资质台账基本信息插入数据库
		
		//根据注册证号码和供应商编码判断是否有重复数据,注册证号码与供应商编码唯一确定一条数据！
		Integer num = ledgerQualificationMapperCustom.selectLegerQulicationByRegisterNumer(ledgerQualificationCustom);
		if (num != 0) {
			throw new Exception("已存在供应商编码为："+ledgerQualificationCustom.getSupplierCode()+"注册证号为："+ledgerQualificationCustom.getRegistrationNumber()+"的条目！");
		}
		ledgerQualificationMapperCustom.insertSelectiveOfLedgerQualification(ledgerQualificationWithBLOBs);
		
		//获取资质台账基本信息插入数据库后生成的主键id
		Integer id  = ledgerQualificationWithBLOBs.getId();
		
		
		//获取供应商编码
		String supplierCode = ledgerQualificationCustom.getSupplierCode();
		//判断该供应商是否已经存在
		LedgerQualificationSupplyInfo supplyInfo = ledgerQualificationMapperCustom.selectBySupplierCodeOfSupplyInfo(supplierCode);
		//copy LedgerQualificationCustom的供应商基本信息到LedgerQualificationSupplyInfo
		LedgerQualificationSupplyInfo ledgerQualificationSupplyInfo = new LedgerQualificationSupplyInfo();
		PropertyUtils.copyProperties(ledgerQualificationSupplyInfo, ledgerQualificationCustom);
		//因为使用了主键自增长：select LAST_INSERT_ID()，所以ledgerQualificationCustom对象中此时的id不再为空。
		ledgerQualificationSupplyInfo.setId(null);
		if(supplyInfo != null) {
			//该供应商已存在，根据供应商编码更新该供应商
			ledgerQualificationMapperCustom.updateBySupplierCodeSelectiveOfSupplyInfo(ledgerQualificationSupplyInfo);
		}else {
			//该供应商不存在，增加该供应商
			ledgerQualificationMapperCustom.insertSelectiveOfSupplyInfo(ledgerQualificationSupplyInfo);
		}

		//获取质检报告json字符串
		String jsonStr = ledgerQualificationCustom.getInspectionAllInfo();
		//解析jsonStr
		/*
			[	
				{hasInspectionDate:'false',validityInspection:'Mon Sep 21 2015 00:00:00 GMT+0800',inspectionContent:'3'},
				{hasInspectionDate:'false',validityInspection:'Thu Sep 17 2015 00:00:00 GMT+0800',inspectionContent:'2'},
				{hasInspectionDate:'true',validityInspection:'Wed Sep 09 2015 00:00:00 GMT+0800',inspectionContent:'1'}
			]
		 */
		JSONArray data = JSONArray.fromObject(jsonStr);
		for(int i = 0; i < data.size(); i++) {
			JSONObject object = data.getJSONObject(i);
			LedgerQualificationInspection ledgerQualificationInspection = new LedgerQualificationInspection();
			
			
			Integer ledgerQualificationId = id;//资质台账基本信息主键值
			String inspectionType = object.getString("inspectionType");
			
			String inspectionDateStr = object.getString("inspectionDate");
			Date inspectionDate = null;
			if(inspectionDateStr != null && !"".equals(inspectionDateStr) && !"null".equals(inspectionDateStr)) {
				inspectionDate = new Date(inspectionDateStr);
			}
			
			String inspectionContent = object.getString("inspectionContent");
			
			ledgerQualificationInspection.setLedgerQualificationId(ledgerQualificationId);
			ledgerQualificationInspection.setInspectionType(inspectionType);
			ledgerQualificationInspection.setInspectionDate(inspectionDate);
			ledgerQualificationInspection.setInspectionContent(inspectionContent);
			
			
			ledgerQualificationMapperCustom.insertSelectiveOfInspection(ledgerQualificationInspection);
		}
		
	}

	@Override
	public LedgerQualificationSupplyInfo selectBySupplierCodeOfSupplyInfo(
			String supplierCode) throws Exception {
		
		return ledgerQualificationMapperCustom.selectBySupplierCodeOfSupplyInfo(supplierCode);
	}

	@Override
	public List<LedgerQualificationCustom> selectLedgerQualificationCustomByPagingAndExample(
			Integer index, Integer pageSize,
			LedgerQualificationExample example1,
			LedgerQualificationSupplyInfoExample exmaple2,
			LedgerQualificationInspectionExample example3) throws Exception {
			//System.out.println(example1.getOredCriteria().size());
		List<LedgerQualificationCustom> ledgerQualificationCustomList = ledgerQualificationMapperCustom.selectLedgerQualificationCustomByPagingAndExample(index, pageSize, example1, exmaple2, example3);
		for(LedgerQualificationCustom ledgerQualificationCustom : ledgerQualificationCustomList) {
			LedgerQualificationSupplyInfo supplyInfo = ledgerQualificationCustom.getLedgerQualificationSupplyInfo();
			ledgerQualificationCustom.setSupplierName(supplyInfo.getSupplierName());
			ledgerQualificationCustom.setBusinessLicense(supplyInfo.getBusinessLicense());
			ledgerQualificationCustom.setNationalTaxRegistration(supplyInfo.getNationalTaxRegistration());
			ledgerQualificationCustom.setLandTaxRegistration(supplyInfo.getLandTaxRegistration());
			ledgerQualificationCustom.setGeneralTaxpayer(supplyInfo.getGeneralTaxpayer());
			ledgerQualificationCustom.setIsblacklist(supplyInfo.getIsblacklist());
			
			//根据资质台账基本信息id获取质检报告
			Integer id = ledgerQualificationCustom.getId();
			List<LedgerQualificationInspection> ledgerQualificationInspectionList = ledgerQualificationMapperCustom.selectByLedgerQualificationIdOfInspection(id);
			//将查询到的质检报告集合转换为json字符串
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String ledgerQualificationInspectionJsonStr = gson.toJson(ledgerQualificationInspectionList);
			
			ledgerQualificationCustom.setInspectionAllInfo(ledgerQualificationInspectionJsonStr);
			ledgerQualificationCustom.setLedgerQualificationInspectionList(ledgerQualificationInspectionList);
		}
		return ledgerQualificationCustomList;
	}

	@Override
	public int selectLedgerQualificationCustomCountByPagingAndExample(
			LedgerQualificationExample example1,
			LedgerQualificationSupplyInfoExample exmaple2) throws Exception {
		int count = ledgerQualificationMapperCustom.selectLedgerQualificationCustomCountByPagingAndExample(example1, exmaple2);
		return count;
	}

	@Override
	public void updateLedgerQualificationCustom(
			LedgerQualificationCustom ledgerQualificationCustom)
			throws Exception {
		
		//copy ledgerQualificationCustom对象的资质台账基本信息到ledgerQualificationWithBLOBs对象
		LedgerQualificationWithBLOBs ledgerQualificationWithBLOBs = new LedgerQualificationWithBLOBs();
		PropertyUtils.copyProperties(ledgerQualificationWithBLOBs, ledgerQualificationCustom);
		//更新资质台账基本信息
		ledgerQualificationMapperCustom.updateByPrimaryKeySelectiveOfLedgerQualificationWithBLOBs(ledgerQualificationWithBLOBs);
		
		//获取供应商编码
		String supplierCode = ledgerQualificationCustom.getSupplierCode();
		//根据供应商编码查询该供应商
		LedgerQualificationSupplyInfo supplyInfo = ledgerQualificationMapperCustom.selectBySupplierCodeOfSupplyInfo(supplierCode);
		
		//copy ledgerQualificationCustom的供应商基本信息到ledgerQualificationSupplyInfo对象
		LedgerQualificationSupplyInfo ledgerQualificationSupplyInfo = new LedgerQualificationSupplyInfo();
		PropertyUtils.copyProperties(ledgerQualificationSupplyInfo, ledgerQualificationCustom);
		//由于上面的供应商基本信息copy会将资质台账基本信息的id主键值copy到供应商基本信息的id主键中，所以这里需要将id置为null
		ledgerQualificationSupplyInfo.setId(null);
		
		if(supplyInfo != null) {
			//该供应商存在，则更新该供应商基本信息
			ledgerQualificationMapperCustom.updateBySupplierCodeSelectiveOfSupplyInfo(ledgerQualificationSupplyInfo);
		}else {
			//该供应商不存在，新增该供应商
			ledgerQualificationMapperCustom.insertSelectiveOfSupplyInfo(ledgerQualificationSupplyInfo);
		}
		
		//获取质检报告信息的json字符串
		String inspectionJsonStr = ledgerQualificationCustom.getInspectionAllInfo();
		//解析inspectionJsonStr
		/*
			[
				{id:'5',ledgerQualificationId:'3',hasInspectionDate:'true',validityInspection:'Tue Sep 22 2015 00:00:00 GMT+0800',inspectionContent:'22'},
				{id:'6',ledgerQualificationId:'3',hasInspectionDate:'true',validityInspection:'Tue Sep 22 2015 00:00:00 GMT+0800',inspectionContent:'2'}
			]
		 */
		JSONArray inspectionData = JSONArray.fromObject(inspectionJsonStr);
		
		
		
		//根据资质台账基本信息主键id删除其对应的所有的质检报告
		//首先获取资质台账基本信息主键id
		Integer ledgerQualificationId = ledgerQualificationWithBLOBs.getId();//资质台账基本信息主键id
		//删除质检报告
		ledgerQualificationMapperCustom.deleteByLedgerQualificationIdOfInspection(ledgerQualificationId);
		for(int i = 0; i < inspectionData.size(); i++) {
			JSONObject inspectionObject = inspectionData.getJSONObject(i);
			LedgerQualificationInspection ledgerQualificationInspection = new LedgerQualificationInspection();
			
			Integer id = inspectionObject.getInt("id");//获取质检报告主键id
			
			String inspectionType = inspectionObject.getString("inspectionType");
			
			String inspectionDateStr = inspectionObject.getString("inspectionDate");
			Date inspectionDate = null;
			if(inspectionDateStr != null && !"".equals(inspectionDateStr) && !"null".equals(inspectionDateStr)) {
				inspectionDate = new Date(inspectionDateStr);
			}
			
			String inspectionContent = inspectionObject.getString("inspectionContent");
			
			//此段业务逻辑旨在解决质检报告的删除问题。为了简化操作，同时质检报告的数据量不大，所以这里采用简化的方式：
			//先删除该资质台账下所有的质检报告，然后将前端页面传过来的质检报告新增。
			//1.根据资质台账基本信息主键id删除其对应的所有的质检报告；该段代码应该放入到for循环外部去执行。
			//2.新增前端页面传过来的质检报告；
			ledgerQualificationInspection.setId(null);//因为是新增，id设置为空，采用自增长的方式设置主键id；
			ledgerQualificationInspection.setLedgerQualificationId(ledgerQualificationId);
			ledgerQualificationInspection.setInspectionType(inspectionType);
			ledgerQualificationInspection.setInspectionDate(inspectionDate);
			ledgerQualificationInspection.setInspectionContent(inspectionContent);
			ledgerQualificationMapperCustom.insertSelectiveOfInspection(ledgerQualificationInspection);
		}
	}

	@Override
	public void updateLedgerQualificationOfReviewByPrimaryKey(String approvalAllInfoJSONStr)
			throws Exception {
		
		//解析json字符串
		JSONArray approvalAllInfoJSONArray = JSONArray.fromObject(approvalAllInfoJSONStr);
		for(int i = 0; i < approvalAllInfoJSONArray.size(); i++) {
			JSONObject approvalJSONObject = approvalAllInfoJSONArray.getJSONObject(i);
			
			
			String idStr = approvalJSONObject.getString("id");
			Integer id = null;
			if(idStr != null && !"".equals(idStr) && !"null".equals(idStr)) {
				id = new Integer(idStr);
			}
			
			String approvalStatus = approvalJSONObject.getString("approvalStatus");
			String reviewName = approvalJSONObject.getString("reviewName");
			
			String reviewDateStr = approvalJSONObject.getString("reviewDate");
			Date reviewDate = null;
			if(reviewDateStr != null && !"".equals(reviewDateStr) && !"null".equals(reviewDateStr)) {
				reviewDate = new Date(reviewDateStr);
			}
			
			Approval approval = new Approval();
			approval.setId(id);
			approval.setApprovalStatus(approvalStatus);
			approval.setReviewDate(reviewDate);
			approval.setReviewName(reviewName);
			ledgerQualificationMapperCustom.updateLedgerQualificationOfReviewByPrimaryKey(approval);
		}
		
		
	}

	@Override
	public void insertExcelOfLedgerQualificationCustom(HashMap<String, List<String>> hMap, int startRow)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < hMap.size(); i++) {
			LedgerQualificationCustom ledger = new LedgerQualificationCustom();
			List<String> tmpList = hMap.get("row" + (i + startRow));
			if (!"".equals(tmpList.get(0))) {
				ledger.setExpirationDate(sdf.parse(tmpList.get(0)));
			}
			if (!"".equals(tmpList.get(1))) {
				ledger.setStatus(tmpList.get(1));
			}
			if (!"".equals(tmpList.get(2)))
				ledger.setMobilePhone1(tmpList.get(2));
			if (!"".equals(tmpList.get(3)))
				ledger.setBrandLevel(tmpList.get(3));
			if (!"".equals(tmpList.get(4)))
				ledger.setSupplierCode(tmpList.get(4));
			if (!"".equals(tmpList.get(5)))
				ledger.setSupplierName(tmpList.get(5));
			if (!"".equals(tmpList.get(6)))
				ledger.setBusinessLicense(tmpList.get(6));
			if ("有".equals(tmpList.get(7).trim())) {
				ledger.setNationalTaxRegistration(true);
			} else if ("无".equals(tmpList.get(7).trim())) {
				ledger.setNationalTaxRegistration(false);
			}
			if ("有".equals(tmpList.get(8).trim())) {
				ledger.setLandTaxRegistration(true);
			} else if ("无".equals(tmpList.get(8).trim())) {
				ledger.setLandTaxRegistration(false);
			}
			if (!"".equals(tmpList.get(9)))
				ledger.setGeneralTaxpayer(tmpList.get(9).trim());
			if (!"".equals(tmpList.get(10)))
				ledger.setCategory(tmpList.get(10).trim());
			if (!"".equals(tmpList.get(11)))
				ledger.setBrandName(tmpList.get(11).trim());
			if (!"".equals(tmpList.get(12)))
				ledger.setRegistrationNumber(tmpList.get(12).trim());
			if (!"".equals(tmpList.get(13)))
				ledger.setApprovedCategory(tmpList.get(13).trim());
			if (!"".equals(tmpList.get(14).trim())) {
				ledger.setEndDate(sdf.parse(tmpList.get(14).trim().replace(".", "-")));
			}
			if (!"".equals(tmpList.get(15).trim())) {
				ledger.setApplicationDate(sdf.parse(tmpList.get(15).trim().replace(".", "-")));
			}
			if (!"".equals(tmpList.get(16)))
				ledger.setTrademarkHolderName(tmpList.get(16).trim());
			if (!"".equals(tmpList.get(17)))
				ledger.setCertificateNumber(tmpList.get(17).trim());// 持有人身份证
			if (!"".equals(tmpList.get(18)))
				ledger.setSingleStoreAuthorization(tmpList.get(18).trim());
			if (!"".equals(tmpList.get(19)))
				ledger.setActingLevel(tmpList.get(19).trim());
			if (!"".equals(tmpList.get(20).trim()))
				ledger.setFirstProxyDate(sdf.parse(tmpList.get(20).trim().replace(".", "-")));
			if (!"".equals(tmpList.get(21).trim()))
				ledger.setSecondProxyDate(sdf.parse(tmpList.get(21).trim().replace(".", "-")));
			if (!"".equals(tmpList.get(22).trim()))
				ledger.setThirdProxyDate(sdf.parse(tmpList.get(22).trim().replace(".", "-")));
			if (!"".equals(tmpList.get(23).trim()))
				ledger.setForthProxyDate(sdf.parse(tmpList.get(23).trim().replace(".", "-")));
			if (!"".equals(tmpList.get(24).trim()))
				ledger.setFifthProxyDate(sdf.parse(tmpList.get(24).trim().replace(".", "-")));
			// 六级代理日期，db,新增字段
			if (!"".equals(tmpList.get(25).trim()))
				ledger.setSixthProxyDate(sdf.parse(tmpList.get(25).trim().replace(".", "-")));
			// 质检表 有日期 26,无日期 27,两个日期都存在时是不可能的，
			// 根据Excel表中质检表栏目中是否有无日期来处理怎么存储到质检表ledger_qualification_inspection中
			if ("".equals(tmpList.get(26).trim())) {
				if ("".equals(tmpList.get(27).trim())) {
					map.put("inspectionType", "无质检");
					map.put("inspectionDate", "");
					map.put("inspectionContent", "");
				} else {
					map.put("inspectionType", "有质检（无限期）");
					map.put("inspectionDate", "");
					map.put("inspectionContent", "");
				}
			} else if ("".equals(tmpList.get(27).trim())) {
				map.put("inspectionType", "有质检(有限期)");
				map.put("inspectionDate", sdf.parse(tmpList.get(26).trim().replace(".", "-")));
				map.put("inspectionContent", "");
			} else {
				// 质检表日期填写错误！
				logger.error("质检表日期填写有误");
			}
			Gson gson = new Gson();
			String json = gson.toJson(map);
			ledger.setInspectionAllInfo("[" + json + "]");
			if (!"".equals(tmpList.get(28)))
				ledger.setDeclaration(tmpList.get(28).trim());
			if (!"".equals(tmpList.get(29)))
				ledger.setDescriptionProblem(tmpList.get(29).trim());
			// 初审日期 30
			// 初审人 31
			if (!"".equals(tmpList.get(32).trim()))
				ledger.setInformationMaintenanceDate(sdf.parse(tmpList.get(32).trim().replace(".", "-")));
			if (!"".equals(tmpList.get(33)))
				ledger.setInformationMaintenanceName(tmpList.get(33).trim());
			if (!"".equals(tmpList.get(34).trim()))
				ledger.setReviewDate(sdf.parse(tmpList.get(34).trim().replace(".", "-")));
			if (!"".equals(tmpList.get(35)))
				ledger.setReviewName(tmpList.get(35).trim());
			if (!"".equals(tmpList.get(36)))
				ledger.setMark(tmpList.get(36).trim());
			// 置为有效
			ledger.setValid(true);
			// 插入至数据库
			logger.info("资质台帐-开始导入第" + (i + startRow + 1) + "行数据：" + ledger.toString());
			this.insertLedgerQualificationCustom(ledger);
		}
	}

	@Override
	public List<List<ActingLevelVO>> selectLedgerQualificationCustomByActingLevel() throws Exception {
		String[] targetCategory = { "女装", "内衣", "儿童", "毛纺", "服饰", "运动", "鞋", "户外", "皮具", "休闲", "男装", "羽绒"};
		List<List<ActingLevelVO>> baseLists=new ArrayList<List<ActingLevelVO>>();
		//只查在柜的
		for (int i = 0; i < targetCategory.length; i++) {
			List<ActingLevelVO> lists = this.ledgerQualificationMapperCustom.selectLedgerQualificationCustomByActingLevel(targetCategory[i]);
			baseLists.add(lists);
		}
		return baseLists;
	}

	@Override
	public List<QualiErrorInfoVO> selectLedgerQualificationCustomForQualiError(Date today) throws Exception {
		List<QualiErrorInfoVO> lists = this.ledgerQualificationMapperCustom.selectLedgerQualificationCustomForQualiError(today);
		return lists;
	}
}
