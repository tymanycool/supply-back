package net.shopin.ledger.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.ledger.mapper.LedgerContractMapperCustom;
import net.shopin.ledger.po.LedgerContract;
import net.shopin.ledger.po.LedgerContractCourse;
import net.shopin.ledger.po.LedgerContractCustom;
import net.shopin.ledger.po.LedgerContractExample;
import net.shopin.ledger.po.LedgerContractSupplyInfo;
import net.shopin.ledger.po.LedgerContractSupplyInfoExample;
import net.shopin.ledger.service.LedgerContractService;
import net.shopin.supply.domain.vo.DifferDeductionVo;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shopin.core.util.regExp.BigDecimalUtil;
@Service
public class LedgerContractServiceImpl implements LedgerContractService {
	private static Logger logger = Logger.getLogger(LedgerContractServiceImpl.class);
	@Autowired 
	private LedgerContractMapperCustom ledgerContractMapperCustom;
	@Override
	public Integer insertLedgerContractCustom(
			LedgerContractCustom ledgerContractCustom) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
		
		
		//避免重复--根据供应商编码、品牌名称、品类、门店编码、开始日期、结束日期、合同截止日期确定唯一（其实也是无法确定的，人工的，如果重复置为无效）
		Integer num = this.ledgerContractMapperCustom.selectLegerContractByCriteria(ledgerContractCustom);
		if (num != 0) {
			throw new Exception("系统中已存在以下合同信息：供应商编码：" + ledgerContractCustom.getSupplierCode() + ",品牌名称："
					+ ledgerContractCustom.getBrandName() + ",品类：" + ledgerContractCustom.getCategory() + ",门店编码："
					+ ledgerContractCustom.getStoreEncoding() + ",开始日期：" + ledgerContractCustom.getStartDate()
					+ ",结束日期：" + ledgerContractCustom.getEndDate() + ",合同截止日期："
					+ ledgerContractCustom.getContractDeadline());
		}
		
		//新增合同台账业务流程：
		
		//step1：新增合同台账基本信息；
		//copy ledgerContractCustom对象中的合同台账基本信息到ledgerContract对象中
		LedgerContract ledgerContract = new LedgerContract();
		PropertyUtils.copyProperties(ledgerContract, ledgerContractCustom);
		//将合同台账基本信息插入数据库中
		ledgerContractMapperCustom.insertSelectiveOfLedgerContract(ledgerContract);
		
		
		//获取自增长的合同台账基本信息主键id
		Integer ledgerContractId = ledgerContract.getId();
		
		
		//step2：新增供应商基本信息
		//因为合同台账基本信息的主键id是采用自增长的方式，通过主键返回，所以ledgerContract对象中的id不为空，但是ledgerContractCustom对象的id依旧为空，所以不用担心主键的问题。
		//copy ledgerContractCustom对象的供应商基本信息到ledgerContractSupplyInfo对象中
		LedgerContractSupplyInfo ledgerContractSupplyInfo = new LedgerContractSupplyInfo();
		PropertyUtils.copyProperties(ledgerContractSupplyInfo, ledgerContractCustom);
		//得到供应商的基本信息后，先别急着去新增该供应商，首先判断该供应商是否已经存在，如果不存在则新增该供应商，如果存在，则应该更新该供应商的基本信息
		//首先根据供应商编码查询供应商基本信息
		String supplierCode = ledgerContractCustom.getSupplierCode();
		LedgerContractSupplyInfo supplyInfo  = ledgerContractMapperCustom.selectBySupplierCodeOfSupplyInfo(supplierCode);
		if(supplyInfo == null) {
			//该供应商不存在，新增该供应商
			ledgerContractMapperCustom.insertSelectiveOfSupplyInfo(ledgerContractSupplyInfo);
		}else {
			//该供应商存在，更新该供应商的基本信息
			ledgerContractMapperCustom.updateBySupplierCodeSelectiveOfSupplyInfo(ledgerContractSupplyInfo);
		}
		
		//step3:新增历程基本信息
		//历程基本信息存储在courseAllInfo json字符串中，首先要解析该json字符串
		/*
		 格式：
		[
			{courseTime:'Wed Oct 26 2016 00:00:00 GMT+0800',courseContent:'2'},
			{courseTime:'Sat Sep 26 2015 00:00:00 GMT+0800',courseContent:'1'}
		]
		 */
		//获取历程基本信息的json字符串
		String courseAllInfoJsonStr = ledgerContractCustom.getCourseAllInfo();
		JSONArray courseAllInfoData = JSONArray.fromObject(courseAllInfoJsonStr);
		for(int i = 0; i < courseAllInfoData.size(); i++) {
			JSONObject object = courseAllInfoData.getJSONObject(i);
			
			LedgerContractCourse ledgerContractCourse = new LedgerContractCourse();
			
			
			Date contractReturnDate = null;
			String contractReturnDateStr = object.getString("contractReturnDate");
			if(contractReturnDateStr != null && !"".equals(contractReturnDateStr) && !"null".equals(contractReturnDateStr)) {
				contractReturnDate = new Date(contractReturnDateStr);
//				contractReturnDate = sdf.parse(contractReturnDateStr);
				
			}
			String contractType = object.getString("contractType");
			Date contractBeginDate = null;
			String contractBeginDateStr = object.getString("contractBeginDate");
			if(contractBeginDateStr != null && !"".equals(contractBeginDateStr) && !"null".equals(contractBeginDateStr)) {
				contractBeginDate = new Date(contractBeginDateStr);
//				contractBeginDate = sdf.parse(contractBeginDateStr);
			}
			Date contractEndDate = null;
			String contractEndDateStr = object.getString("contractEndDate");
			if(contractEndDateStr != null && !"".equals(contractEndDateStr) && !"null".equals(contractEndDateStr)) {
				contractEndDate = new Date(contractEndDateStr);
//				contractEndDate = sdf.parse(contractEndDateStr);
			}
			
			String contractReviewStatus = object.getString("contractReviewStatus");
			String contractReviewer = object.getString("contractReviewer");
			Date contractReviewDate = null;
			String contractReviewDateStr = object.getString("contractReviewDate");
			if(contractReviewDateStr != null && !"".equals(contractReviewDateStr) && !"null".equals(contractReviewDateStr)) {
				contractReviewDate = new Date(contractReviewDateStr);
//				contractReviewDate = sdf.parse(contractReviewDateStr);
			}

			
			ledgerContractCourse.setLedgerContractId(ledgerContractId);
			ledgerContractCourse.setContractReturnDate(contractReturnDate);
			ledgerContractCourse.setContractType(contractType);
			ledgerContractCourse.setContractBeginDate(contractBeginDate);
			ledgerContractCourse.setContractEndDate(contractEndDate);
			ledgerContractCourse.setContractReviewStatus(contractReviewStatus);
			ledgerContractCourse.setContractReviewer(contractReviewer);
			ledgerContractCourse.setContractReviewDate(contractReviewDate);

			
			
			ledgerContractMapperCustom.insertSelectiveOfCourse(ledgerContractCourse);
		}
		return ledgerContractId;
	}
	@Override
	public LedgerContractSupplyInfo selectBySupplierCodeOfSupplyInfo(
			String supplierCode) throws Exception {
		
		
		return ledgerContractMapperCustom.selectBySupplierCodeOfSupplyInfo(supplierCode);
	}
	@Override
	public List<LedgerContractCustom> selectLedgerContractCustomByPaginAndExample(
			Integer index, Integer pageSize, LedgerContractExample example1,
			LedgerContractSupplyInfoExample example2) throws Exception {
		//step1：获取合同台账基本信息和供应商基本信息
		List<LedgerContractCustom> ledgerContractCustomList = ledgerContractMapperCustom.selectLedgerContractCustomByPaginAndExample(index, pageSize, example1, example2);
		//step2：遍历获取到的合同台账基本信息和供应商基本信息列表，对每一个列表中的LedgerContractCustom类对象，获取其对应的制定的某一年的协议历程信息
		for(LedgerContractCustom ledgerContractCustom : ledgerContractCustomList) {
			//获取供应商基本信息
			LedgerContractSupplyInfo ledgerContractSupplyInfo  = ledgerContractCustom.getLedgerContractSupplyInfo();
			//将供应商基本信息填充到ledgerContractCustom对象中
			ledgerContractCustom.setSupplierCode(ledgerContractSupplyInfo.getSupplierCode());
			ledgerContractCustom.setSupplierName(ledgerContractSupplyInfo.getSupplierName());
			ledgerContractCustom.setLegalRepresentative(ledgerContractSupplyInfo.getLegalRepresentative());
			ledgerContractCustom.setAttorney(ledgerContractSupplyInfo.getAttorney());
			ledgerContractCustom.setMobilePhone1(ledgerContractSupplyInfo.getMobilePhone1());
			ledgerContractCustom.setMobilePhone1Name(ledgerContractSupplyInfo.getMobilePhone1Name());
			ledgerContractCustom.setMobilePhone2(ledgerContractSupplyInfo.getMobilePhone2());
			ledgerContractCustom.setMobilePhone2Name(ledgerContractSupplyInfo.getMobilePhone2Name());
			ledgerContractCustom.setFixedTelephone(ledgerContractSupplyInfo.getFixedTelephone());
			ledgerContractCustom.setFixedTelephoneName(ledgerContractSupplyInfo.getFixedTelephoneName());
			ledgerContractCustom.setBasicSigningDate(ledgerContractSupplyInfo.getBasicSigningDate());
			//单独处理基础扣率
			Double deductionRate = ledgerContractCustom.getDeductionRate();   //数据库存储为：0.18
			if(deductionRate!=null){
				BigDecimal bd1 = new BigDecimal(new Double(100).toString());
				BigDecimal bd2 = new BigDecimal(deductionRate.toString());
				double result = bd1.multiply(bd2).doubleValue();
				ledgerContractCustom.setDeductionRate(result);         //18
			}
			//获取合同台账基本信息的主键id
			int ledgerContractId = ledgerContractCustom.getId();
			
			/*
			 	填充ledgerContractCustom对象中的协议历程json字符串
			 	注：这里为了方便以后管理协议历程，所以根据合同台账基本信息主键id获取所有的协议历程信息转为json字符串
			 */
			//根据合同台账基本信息主键id获取所有的协议历程信息
			List<LedgerContractCourse> ledgerContractCourseAllInfoList = ledgerContractMapperCustom.selectByLedgerContractIdOfCourse(ledgerContractId);
			//将查询到的协议历程集合转换为json字符串
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String ledgerContractCourseJsonStr = gson.toJson(ledgerContractCourseAllInfoList);
			ledgerContractCustom.setCourseAllInfo(ledgerContractCourseJsonStr);
			ledgerContractCustom.setLedgerContractCourseList(ledgerContractCourseAllInfoList);
		}
		return ledgerContractCustomList;
	}

	
	@Override
	public int selectLedgerContractCustomCountByPaginAndExample(
			LedgerContractExample example1,
			LedgerContractSupplyInfoExample example2) throws Exception {
		return ledgerContractMapperCustom.selectLedgerContractCustomCountByPaginAndExample(example1, example2);
	}
	
	@Override
	public void updateLedgerContractCustom(LedgerContractCustom ledgerContractCustom) throws Exception{
		
		//step1：更新合同台账基本信息
		//copy ledgerContractCustom对象中合同台账基本信息到ledgerContract对象中
		LedgerContract ledgerContract = new LedgerContract();
		PropertyUtils.copyProperties(ledgerContract, ledgerContractCustom);
		//将合同台账基本信息更新到数据库
		ledgerContractMapperCustom.updateByPrimaryKeySelectiveOfLedgerContract(ledgerContract);
		
		//step2：更新供应商基本信息
		//copy ledgerContractCustom对象中的供应商基本信息到ledgerContractSupplyInfo对象中
		LedgerContractSupplyInfo ledgerContractSupplyInfo = new LedgerContractSupplyInfo();
		PropertyUtils.copyProperties(ledgerContractSupplyInfo, ledgerContractCustom);
		
		//将供应商基本信息更新到数据库
		//ledgerContractMapperCustom.updateBySupplierCodeSelectiveOfSupplyInfo(ledgerContractSupplyInfo);
		
		//首先根据供应商编码判断该供应商是否已经存在，如果存在，更新该供应商，如果不存在，新增该供应商
		String supplierCode = ledgerContractCustom.getSupplierCode();
		LedgerContractSupplyInfo supplyInfo = ledgerContractMapperCustom.selectBySupplierCodeOfSupplyInfo(supplierCode);
		if(supplyInfo != null) {
			//该供应商存在
			ledgerContractMapperCustom.updateBySupplierCodeSelectiveOfSupplyInfo(ledgerContractSupplyInfo);
		}else {
			//供应商不存在
			ledgerContractMapperCustom.insertSelectiveOfSupplyInfo(ledgerContractSupplyInfo);
		}
		
		//step3：更新协议历程
		//获取合同台账基本信息主键id
		int ledgerContractId = ledgerContractCustom.getId();
		//根据合同台账基本信息主键id删除该合同台账下所有的协议历程
		ledgerContractMapperCustom.deleteByLedgerContractIdOfCourse(ledgerContractId);
		
		//获取该合同台账下所有协议历程基本信息json字符串
		String courseAllInfoJsonStr = ledgerContractCustom.getCourseAllInfo();
		JSONArray courseAllInfoData = JSONArray.fromObject(courseAllInfoJsonStr);
		for(int i = 0; i < courseAllInfoData.size(); i++) {
			JSONObject object = courseAllInfoData.getJSONObject(i);
			
			LedgerContractCourse ledgerContractCourse = new LedgerContractCourse();
			
			
			Date contractReturnDate = null;
			String contractReturnDateStr = object.getString("contractReturnDate");
			if(contractReturnDateStr != null && !"".equals(contractReturnDateStr) && !"null".equals(contractReturnDateStr)) {
				contractReturnDate = new Date(contractReturnDateStr);
			}
			String contractType = object.getString("contractType");
			Date contractBeginDate = null;
			String contractBeginDateStr = object.getString("contractBeginDate");
			if(contractBeginDateStr != null && !"".equals(contractBeginDateStr) && !"null".equals(contractBeginDateStr)) {
				contractBeginDate = new Date(contractBeginDateStr);
			}
			Date contractEndDate = null;
			String contractEndDateStr = object.getString("contractEndDate");
			if(contractEndDateStr != null && !"".equals(contractEndDateStr) && !"null".equals(contractEndDateStr)) {
				contractEndDate = new Date(contractEndDateStr);
			}
			
			String contractReviewStatus = object.getString("contractReviewStatus");
			String contractReviewer = object.getString("contractReviewer");
			Date contractReviewDate = null;
			String contractReviewDateStr = object.getString("contractReviewDate");
			if(contractReviewDateStr != null && !"".equals(contractReviewDateStr) && !"null".equals(contractReviewDateStr)) {
				contractReviewDate = new Date(contractReviewDateStr);
			}
			
			
			
			
			ledgerContractCourse.setLedgerContractId(ledgerContractId);
			ledgerContractCourse.setContractReturnDate(contractReturnDate);
			ledgerContractCourse.setContractType(contractType);
			ledgerContractCourse.setContractBeginDate(contractBeginDate);
			ledgerContractCourse.setContractEndDate(contractEndDate);
			ledgerContractCourse.setContractReviewStatus(contractReviewStatus);
			ledgerContractCourse.setContractReviewer(contractReviewer);
			ledgerContractCourse.setContractReviewDate(contractReviewDate);
			
			ledgerContractMapperCustom.insertSelectiveOfCourse(ledgerContractCourse);
		}
	}
	@Override
	public void insertOfExcelLedgerContractCustom(HashMap<String, List<String>> hMap, int startRow) throws Exception {
		logger.info("*************insertOfExcelLedgerContractCustom()开始***********");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String start = null;
		String end = null;
		Integer tmpId = null;
		for (int i = 0; i < hMap.size(); i++) {
			LedgerContractCustom ledger = new LedgerContractCustom();
			List<String> tmpList = hMap.get("row" + (i + startRow));
			if (!"".equals(tmpList.get(0))) {
				ledger.setSupplierCode(tmpList.get(0));
			}
			ledger.setSupplierName(tmpList.get(1));
			ledger.setLegalRepresentative(tmpList.get(2));
			ledger.setAttorney(tmpList.get(3));
			ledger.setMobilePhone1(tmpList.get(4));
			ledger.setMobilePhone1Name(tmpList.get(5));
			ledger.setMobilePhone2(tmpList.get(6));
			ledger.setMobilePhone2Name(tmpList.get(7));
			ledger.setFixedTelephone(tmpList.get(8));
			ledger.setFixedTelephoneName(tmpList.get(9));
			ledger.setCabinetStatus(tmpList.get(10));
			ledger.setSettlementStatus(tmpList.get(11));
			ledger.setSignMark(tmpList.get(12));
			if (!"".equals(tmpList.get(13).trim())) {
				ledger.setAuthorizedStartDate(sdf.parse(tmpList.get(13).trim()));
			}
			if (!"".equals(tmpList.get(14).trim())) {
				ledger.setAuthorizedEndDate(sdf.parse(tmpList.get(14).trim()));
			}
			ledger.setCategory(tmpList.get(15));
			ledger.setBrandLevel(tmpList.get(16));
			ledger.setBrandName(tmpList.get(17));
			ledger.setStoreEncoding(tmpList.get(18));
			ledger.setStoreName(tmpList.get(19));
			// 基础扣率，折扣带
			String ss = tmpList.get(20);
			if(ss !=null && !ss.trim().equals("")){
				double col20 = Double.parseDouble(ss.replace("%",""));   //存储格式：0.18
				ledger.setDeductionRate(col20);
			}
			// 清算类型
			ledger.setClearType(tmpList.get(21));
			// 清算阈值
			ledger.setClearThreshold(tmpList.get(22));
			// 清算扣率
			String clearDeduction = tmpList.get(23);
			if(clearDeduction !=null && !clearDeduction.trim().equals("")){
				if(clearDeduction.contains("/")){
					ledger.setClearDeduction(clearDeduction);    //数据库保存为： 16%/14%
				}else{
					double clearDeductionDouble = BigDecimalUtil.mul(Double.parseDouble(clearDeduction), 100);
					ledger.setClearDeduction(clearDeductionDouble+"%");      //数据库保存为： 10%
				}
			}
			ledger.setAssessmentIndicator(tmpList.get(24));
			if (!"".equals(tmpList.get(25).trim())) {
				ledger.setBasicSigningDate(sdf.parse(tmpList.get(25).trim()));
			}
			if (!"".equals(tmpList.get(26).trim())) {
				ledger.setSigningDate(sdf.parse(tmpList.get(26).trim()));
			}
			if (!"".equals(tmpList.get(27).trim())) {
				ledger.setStartDate(sdf.parse(tmpList.get(27).trim()));
			}
			if (!"".equals(tmpList.get(28).trim())) {
				ledger.setEndDate(sdf.parse(tmpList.get(28).trim()));
			}
			if (!"".equals(tmpList.get(29).trim())) {
				ledger.setContractDeadline(sdf.parse(tmpList.get(29).trim()));
			}
			if ("有效".equals(tmpList.get(30).trim())) {
				ledger.setValid(true);
			}
			if ("有".equals(tmpList.get(31).trim())) {
				ledger.setDecorateRules(true);
			} else if ("无".equals(tmpList.get(31).trim())) {
				ledger.setDecorateRules(false);
			}
			ledger.setSupplemental(tmpList.get(32).trim());
			if (!"".equals(tmpList.get(33).trim())) {
				ledger.setManagementFees(Double.parseDouble(tmpList.get(33).trim()));
			}
			ledger.setCardFees(tmpList.get(34).trim());
			ledger.setArea(tmpList.get(35).trim());
			// 基本条款合同版本
			ledger.setContractVersion(tmpList.get(36).trim());
			// 商务条款合同版本
			ledger.setBizContractVersion(tmpList.get(37).trim());
			// 合同返回日期
			// 合同开始与结束日期
			String returnDate = tmpList.get(38).trim();
			String beginAndEndDate = tmpList.get(39).trim();
			if (!"".equals(returnDate) && !"".equals(beginAndEndDate)
					|| ("".equals(returnDate) && "".equals(beginAndEndDate))) {
				String array = getCourseInfo(returnDate, beginAndEndDate);
				ledger.setCourseAllInfo(array);
			}
			if (!"".equals(tmpList.get(40).trim())) {
				ledger.setCabinetDate(sdf.parse(tmpList.get(39).trim()));
			}
			ledger.setCooperationWay(tmpList.get(41).trim());
			ledger.setMark(tmpList.get(42).trim());

			String flag = tmpList.get(43).trim();

			logger.info("合同台帐-开始导入第" + (i + startRow + 1) + "行数据：" + ledger.toString());
			// 若存在补充条款
			if (!"".equals(flag)) {
				if (flag.endsWith("0")) {
					start = flag.substring(0, 1);
					end = flag.substring(1, 2);
					ledger.setParentId(0);// 存0
					tmpId = this.insertLedgerContractCustom(ledger);
				} else if (flag.equals(start)) {
					ledger.setParentId(tmpId);
					this.insertLedgerContractCustom(ledger);
				}
			} else {
				start = null;
				end = null;
				tmpId = null;
				ledger.setParentId(0);
				this.insertLedgerContractCustom(ledger);
			}
		}
	}

	private String getCourseInfo(String returnDate, String beginAndEndDate) throws Exception {
		if ("".equals(returnDate) && "".equals(beginAndEndDate)) {
			return "[]";
		}
		String[] dates1 = returnDate.split("/");
		String[] dates2 = beginAndEndDate.split("/");
		Map<String, Object> map = new HashMap<String, Object>();
		int count = dates1.length;
		
		JSONArray jsa = new JSONArray();
		
		for (int k = 0; k < dates2.length; k++) {
			JSONObject jsonb = new JSONObject();
			if(k<=count-1){
				jsonb.put("contractReturnDate", dates1[k]);
			}else{
				jsonb.put("contractReturnDate", "");
			}
			
			String[] dates3 = dates2[k].split("-");
			jsonb.put("contractBeginDate", dates3[0]);
			jsonb.put("contractEndDate", dates3[1]);
			jsonb.put("contractType", "");// 合同类型置为空,下同
			jsonb.put("contractReviewStatus", "");
			jsonb.put("contractReviewDate", "");
			jsonb.put("contractReviewer", "");
			
			jsa.add(jsonb);
		}
//		Gson gson = new Gson();
//		String json = gson.toJson(map);
		return jsa.toString();
	}
	@Override
	public List<DifferDeductionVo> selectByDifferDeduction() throws Exception {
		//查询门店多于1的：
		List<DifferDeductionVo> criteriaLists = this.ledgerContractMapperCustom.selectByDifferDeductionGetCriteria();
		System.out.println(criteriaLists);
		DifferDeductionVo differDeductionVo=new DifferDeductionVo();
		List<DifferDeductionVo> resultLists=null;
		for (int i = 0; i < criteriaLists.size(); i++) {
			//拼接查询条件
			String tmpCode=criteriaLists.get(i).getSupplierCode();
			String tmpName=criteriaLists.get(i).getBrandName();
			differDeductionVo.setSupplierCode(tmpCode);
			differDeductionVo.setBrandName(tmpName);
			List<DifferDeductionVo> tmpresultLists = this.ledgerContractMapperCustom.selectByDifferDeductionGetResultByCriteria(differDeductionVo);
			//减去扣率都相同的
		    boolean flag=handleResultLists(tmpresultLists);
		    if (flag) {
		    	if (resultLists==null) {
		    		resultLists=tmpresultLists;
				} else {
					resultLists.addAll(tmpresultLists);
				}
			}
			System.out.println(resultLists);
		}
		return resultLists;
	}
	private boolean handleResultLists(List<DifferDeductionVo> resultLists) {
		//选择
		for (int i = 0; i < resultLists.size()-1; i++) {
			for (int j = i+1; j < resultLists.size(); j++) {
				Double tmpi = resultLists.get(i).getDeductionRate();
				Double tmpj = resultLists.get(j).getDeductionRate();
				if (tmpi != tmpj) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public List<DifferDeductionVo> selectDeductionByRange(Map<String,Integer> map) throws Exception {
		List<DifferDeductionVo> lists = this.ledgerContractMapperCustom.selectDeductionByRange(map);
		return lists;
	}
	@Override
	public List<DifferDeductionVo> selectDifferDeductionByDifferSupply() throws Exception {
		// 同一品牌下，供应商多于1的
		List<DifferDeductionVo> criteriaLists = this.ledgerContractMapperCustom
				.selectDifferDeductionByDifferSupplyGetCriteria();
		DifferDeductionVo differDeductionVo = new DifferDeductionVo();
		List<DifferDeductionVo> resultLists = new ArrayList<DifferDeductionVo>();
		for (int i = 0; i < criteriaLists.size(); i++) {
			String tmpBrandName = criteriaLists.get(i).getBrandName();
			differDeductionVo.setBrandName(tmpBrandName);
			List<DifferDeductionVo> tmpresultLists = this.ledgerContractMapperCustom
					.selectDifferDeductionSupplyGetResultByDifferSupplyCriteria(differDeductionVo);
			// 去掉供应商各个门店与其他供应商各个门店之间扣率都相同的
			boolean flag = handleResultLists(tmpresultLists);
			if (flag) {
				resultLists.addAll(tmpresultLists);
			}
		}
		return resultLists;
	}
	@Override
	public List<LedgerContractCustom> selectLedgerContractCustomListByParentId(Integer parentId) throws Exception {
		List<LedgerContractCustom> lists = this.ledgerContractMapperCustom.selectLedgerContractCustomListByParentId(parentId);
		return lists;
	}
	
	@Override
	public LedgerContractCustom selectLedgerContractCustomListById(Integer id) throws Exception {
		LedgerContractCustom obj = this.ledgerContractMapperCustom.selectLedgerContractCustomListById(id);
		return obj;
	}
	
	@Override
	public List<LedgerContractCourse> selectByLedgerContractIdOfCourse(Integer ledgerContractId) throws Exception {
		List<LedgerContractCourse> ledgerContractCourseAllInfoList = ledgerContractMapperCustom.selectByLedgerContractIdOfCourse(ledgerContractId);
		return ledgerContractCourseAllInfoList;
	}
}
