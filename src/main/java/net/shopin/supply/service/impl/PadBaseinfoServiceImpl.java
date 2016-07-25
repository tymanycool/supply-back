package net.shopin.supply.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.PadLog;
import net.shopin.supply.domain.entity.PadPurchaseInfo;
import net.shopin.supply.domain.entity.PadSupply;
import net.shopin.supply.domain.vo.PadBaseinfoVO;
import net.shopin.supply.persistence.PadBaseinfoMapper;
import net.shopin.supply.persistence.PadLogMapper;
import net.shopin.supply.persistence.PadSupplyMapper;
import net.shopin.supply.service.IPadBaseinfoService;
import net.shopin.supply.service.IPadPurchaseInfoService;
import net.shopin.supply.service.IPadSupplyService;
import net.shopin.supply.util.LogUtil;

import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.portlet.handler.ParameterHandlerMapping;

import com.alibaba.druid.support.logging.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shopin.core.constants.ErrorCodeConstants.ErrorCode;
import com.shopin.core.framework.exception.ShopinException;
import com.shopin.core.util.ResultUtil;

/**
 * @ClassName: PadBaseinfoServiceImpl
 * @author zhangq
 * @date 2014-12-24 上午11:07:03
 *
 */
@Service
public class PadBaseinfoServiceImpl implements IPadBaseinfoService {

	private Logger logger = Logger.getLogger(PadBaseinfoServiceImpl.class);

	@Autowired
	private PadBaseinfoMapper padBaseinfoMapper;

	@Autowired
	private PadSupplyMapper padSupplyMapper;

//	@Autowired
//	private PadOperateLogMapper padOperateLogMapper;

	@Autowired
	private PadLogMapper padLogMapper;

	@Autowired
	private IPadPurchaseInfoService padPurchaseInfoService;
	
	@Autowired
	private IPadSupplyService padSupplyService;

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");


	/**
	 * 保存
	 */
	@Override
	public Integer save(PadBaseinfo padBaseinfo, String username,String userSid) {

		Integer result = 0;
		PadLog padLog = new PadLog();
// 添加
		if (null == padBaseinfo.getSid()) {
			result = this.padBaseinfoMapper.insert(padBaseinfo);
			if (result != 1) {
				throw new ShopinException(ErrorCode.SAVE_ERROR.getErrorCode() + "_" + ErrorCode.SAVE_ERROR.getMemo());
			}
			// 更新padPurchaseInfo : 已录入PAD个数加1
			PadPurchaseInfo padPurchaseInfo = padPurchaseInfoService
					.getPadPurchseInfoByBatchNo(padBaseinfo.getPadPurchaseBatchNo());
			Integer padenteredNum = padPurchaseInfo.getPadEnteredNum();
			if(padenteredNum==null){
				//兼顾老系统
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("padPurchaseBatchNo", padBaseinfo.getPadPurchaseBatchNo());
				//TODO  获取录入的总数
				Integer enteredNum = this.getCountByParam(paramMap);
				if(enteredNum!=null){
					padPurchaseInfo.setPadEnteredNum(enteredNum);
				}
			}else{
				padPurchaseInfo.setPadEnteredNum(padenteredNum + 1);
			}
			padPurchaseInfoService.updateByPrimaryKeySelective(padPurchaseInfo);

			// pad默认门店：公司总部（1000）
			PadSupply padSupplyInfo = new PadSupply();
			padSupplyInfo.setPadNo(padBaseinfo.getPadNo());
			padSupplyInfo.setShopId(1000);
			padSupplyInfo.setShopName("公司总部");
			padSupplyInfo.setCreatetime(new Date());
			this.padSupplyMapper.insert(padSupplyInfo);

			String description = "操作：添加。 系统时间:" + df.format(new Date()) + "系统用户:" + username + " 添加编号为:"
					+ padBaseinfo.getPadNo() + "的PAD基本信息。   -------PAD详情如下：";
			String jsonData = LogUtil.createPadLogDesc(padBaseinfo, description);

			padLog.setDescription(jsonData);
			padLog.setPadNo(padBaseinfo.getPadNo());
			padLog.setOperatTime(new Date());
			padLog.setOperator(username);
			padLog.setOperatorSid(padBaseinfo.getOperatorSid());

			result = this.padLogMapper.insert(padLog);
			if (result != 1) {
				throw new ShopinException(
						ErrorCode.SAVE_PADLOG_ERROR.getErrorCode() + "_" + ErrorCode.SAVE_PADLOG_ERROR.getMemo());
			}

		} else {
//更新			
			padBaseinfo.setCreateTime(new Date());
			result = this.padBaseinfoMapper.update(padBaseinfo);
			//有一种情况：pad刚入库，没有调拨，此时修改pad信息（恰好为padNo）,那么此时pad就没有和默认的supply（公司总部）关联
			HashMap<String,Object> map1 = new HashMap<String, Object>();
			map1.put("padNo", padBaseinfo.getPadNo());
 			List<PadSupply> list = this.padSupplyService.selectListByParam(map1);
 			if(list == null || list.size() == 0){  
 				PadSupply padSupply1 = new PadSupply();
 				padSupply1.setCreatetime(new Date());
 				padSupply1.setPadNo( padBaseinfo.getPadNo());
 				padSupply1.setShopId(1000);
 				padSupply1.setShopName("公司总部");  //一旦修改padno，系统默认为新的pad，此时需要总部运维重新调拨。
 				padSupplyMapper.insert(padSupply1);
 			}else{
 				if (result != 1) {
 					throw new ShopinException(
 							ErrorCode.UPDATE_ERROR.getErrorCode() + "_" + ErrorCode.UPDATE_ERROR.getMemo());
 				} else {
 					String description = "操作：修改。系统时间:" + df.format(new Date()) + " 系统用户:" + username + " 更新编号为:"
 							+ padBaseinfo.getPadNo() + "的PAD基本信息。   -------PAD详情如下：";
 					String jsonResult = LogUtil.createPadLogDesc(padBaseinfo, description);
 					padLog.setDescription(jsonResult);
 					padLog.setPadNo(padBaseinfo.getPadNo());
 					padLog.setOperatTime(new Date());
 					padLog.setOperator(username);
 					padLog.setOperatorSid(padBaseinfo.getOperatorSid());
 					try {
 						this.padLogMapper.insert(padLog);
 					} catch (Exception e) {
 						this.logger.error("日志保存失败！", e);
 					}
 				}
 			}
		}
		return result;
	}

	@Override
	public void update(PadBaseinfo padBaseinfo) {
		try {

			this.padBaseinfoMapper.update(padBaseinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据PAD编号查询
	 */
	@Override
	public PadBaseinfo selectPadByPadNo(String padNo) {
		return this.padBaseinfoMapper.selectPadByPadNo(padNo);
		
	}

	/**
	 * PAD信息总数(不关联其他表)
	 */
	@Override
	public Integer getCountByParam(Map<String, Object> paramMap) {
		Integer count = this.padBaseinfoMapper.getCountByParam(paramMap);
		return count;
	}

	/**
	 * 获取pad和绑定供应商信息
	 */
	@Override
	public List getPadBaseAndSupplyList(Map paramMap) {
		List list = this.padBaseinfoMapper.getPadBaseAndSupplyList(paramMap);
		return list;
	}

	/**
	 * 根据mac地址查询
	 */
	@Override
	public PadBaseinfo selectPadByMacaddress(Map<String, Object> paramMap) {

		PadBaseinfo padBaseinfo = this.padBaseinfoMapper.selectPadByMacaddress(paramMap);
		return padBaseinfo;
	}

	@Override
	public PadBaseinfo checkPadinfoIsUnique(Map<String, Object> map) {
		PadBaseinfo padBaseinfo = this.padBaseinfoMapper.checkPadinfoIsUnique(map);
		return padBaseinfo;
	}

	@Override
	public Integer deletePadInfoBySid(Long sid,String username,String userSid) {
		PadBaseinfo baseinfo = this.padBaseinfoMapper.selectByPrimaryKey(sid);
		String padNo = baseinfo.getPadNo();
		Integer flag = this.padBaseinfoMapper.deletePadInfoBySid(sid);
		// 删除成功：
		if (flag == 1) {
			// 删除pad与供应商的对应关系
			this.padSupplyMapper.deleteByPadNo(padNo);

			//更新采购信息：-1
			String batchNo = baseinfo.getPadPurchaseBatchNo();
			
			PadPurchaseInfo padPurchaseInfo = padPurchaseInfoService.getPadPurchseInfoByBatchNo(batchNo);
			Integer enteredNum = padPurchaseInfo.getPadEnteredNum();
			if(enteredNum != null){//新系统
				enteredNum = padPurchaseInfo.getPadEnteredNum();
				Integer num = (enteredNum - 1 < 0)?0:(enteredNum - 1);
				padPurchaseInfo.setPadEnteredNum(num);
				padPurchaseInfoService.updateByPrimaryKeySelective(padPurchaseInfo);
			}else{//兼顾老系统
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("padPurchaseBatchNo", batchNo);
				// 当前系统该批次的总数(前面已经删除了。)
//				 Integer enteredNum1 = this.getPadCountByBatchNo(paramMap);
				 Integer enteredNum1 = this.getCountByParam(paramMap);
				 if(enteredNum1!=null){
						padPurchaseInfo.setPadEnteredNum(enteredNum1);
						padPurchaseInfoService.updateByPrimaryKeySelective(padPurchaseInfo);
					}
			}
			
			//padLog
			PadLog padLog = new PadLog();
			padLog.setPadNo(padNo);
			padLog.setOperatTime(new Date());
			padLog.setOperatorSid(Integer.valueOf(userSid));
			padLog.setOperator(username);
			String description = "操作：删除。 系统时间:" + df.format(new Date()) + "系统用户:" + username + " 删除编号为:"
					+ padNo + "的PAD基本信息。   -------PAD详情如下：";
			String jsonData = LogUtil.createPadLogDesc(baseinfo, description);
			padLog.setDescription(jsonData);
			Integer result = padLogMapper.insert(padLog);
			if(result !=null){
				logger.info("删除pad时，日志保存失败");
			}
		}
		return flag;
	}

	@Override
	public void savePadAllocationInfo(String[] padIdArr, String targetShopId, String targetShopName,
			Map<String, String> info) throws Exception{
		Integer result = 0;
		String userSid = info.get("userSid");
		String shopName = null;
		String userName = info.get("userName");
		for (int i = 0; i < padIdArr.length; i++) {
			String padNo = padIdArr[i];
			// 1.修改状态,使用类型 TODO 修改操作人，操作人id
			PadBaseinfo padBaseinfo = this.padBaseinfoMapper.selectPadByPadNo(padNo);
			padBaseinfo.setPadStatus(5);// 5:在途
			padBaseinfo.setUseType(-1);   //兼顾旧系统数据：可以调拨的PAD使用类型应该已经是 -1
			padBaseinfo.setUseTypeDesc("");
			padBaseinfo.setOperator(userName);
			if(userSid !=null){
				padBaseinfo.setOperatorSid(Integer.valueOf(userSid));
			}
			this.padBaseinfoMapper.update(padBaseinfo);
			// 2.修改目的门店,pad所在门店会在收货时改变
			HashMap<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("padNo", padIdArr[i]);
//			this.pads
			List<PadSupply> list = this.padSupplyService.selectListByParam(paramMap);
			if(list !=null && list.size() == 1){
				//更新
				PadSupply padSupply = list.get(0);
				shopName = padSupply.getShopName();
				padSupply.setTargetShopId(Integer.valueOf(targetShopId));
				padSupply.setTargetShopName(targetShopName);
				padSupply.setSupplyId(null);
				padSupply.setSupplyName(null);
				result = this.padSupplyMapper.updateNoSelective(padSupply);
			}else if(list !=null && list.size() >1){
				//删除所有绑定关系。
				PadSupply padSupply = list.get(0);
				Integer shopId = padSupply.getShopId();
				shopName = padSupply.getShopName();
				this.padSupplyMapper.deleteByPadNo(padNo);
				//新建supply
				PadSupply padSupplyInfo = new PadSupply();
				padSupplyInfo.setPadNo(padNo);
				padSupplyInfo.setTargetShopId(Integer.parseInt(targetShopId));
				padSupplyInfo.setTargetShopName(targetShopName);
				padSupplyInfo.setShopId(shopId == null?1000:shopId);
				padSupplyInfo.setShopName(shopName);
				padSupplyInfo.setCreatetime(new Date());
				result = this.padSupplyMapper.insert(padSupplyInfo);
			}else if(list == null || (list !=null && list.size()==0)){
				//新建supply
				PadSupply padSupplyInfo = new PadSupply();
				padSupplyInfo.setPadNo(padNo);
				padSupplyInfo.setTargetShopId(Integer.parseInt(targetShopId));
				padSupplyInfo.setTargetShopName(targetShopName);
				padSupplyInfo.setShopId(1000);
				padSupplyInfo.setShopName("公司总部");
				padSupplyInfo.setCreatetime(new Date());
				result = this.padSupplyMapper.insert(padSupplyInfo);
			}
			if(result == 1){
				// log
				PadLog padLog = new PadLog();
				padLog.setPadNo(padNo);
				padLog.setOperatTime(new Date());
				padLog.setOperatorSid(Integer.valueOf(userSid));
				padLog.setOperator(userName);
				shopName = (shopName == null || shopName == "" )?"公司总部":shopName;
				String description = "操作：调拨。系统时间:"+df.format(new Date())+"  系统用户:"+userName +"  将PAD编号为："+padNo+"  申请从门店： "+shopName+"   调拨至目的门店: "+targetShopName+"。   -------PAD详情如下：";
				String jsonData = LogUtil.createPadLogDesc(padBaseinfo, description);
				padLog.setDescription(jsonData);
				logger.info("操作：调拨。系统时间:"+df.format(new Date())+"  系统用户:"+userName +"  将PAD编号为："+padNo+"  申请从门店： "+shopName+"   调拨至目的门店: "+targetShopName);
				this.padLogMapper.insert(padLog);
			}
		}
	}

	@Override
	public void savePadReceiveInfo(String[] padIdArr, Map<String, String> info) throws Exception{
		String userName = info.get("userName");
		String userSid = info.get("userSid");
		for (int i = 0; i < padIdArr.length; i++) {
			// 1.状态改为在库
			String padNo = padIdArr[i];
			PadBaseinfo padBaseinfo = this.padBaseinfoMapper.selectPadByPadNo(padNo);
			padBaseinfo.setPadStatus(0);// pad状态--['0',"在库"],['1',"卖场"],['2',"送修"],['3',"停用"],['4',"丢失"],['5',"在途"]
			this.padBaseinfoMapper.update(padBaseinfo);
			// 2.修改门店和目的门店
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("padNo", padNo);
			PadSupply padSupplyInfo = this.padSupplyMapper.selectPadSupplyByPadNo(map);
			Integer tshopId = padSupplyInfo.getTargetShopId();
			String tshopName = padSupplyInfo.getTargetShopName();
			String shopName = padSupplyInfo.getShopName();
			padSupplyInfo.setShopId(tshopId);
			padSupplyInfo.setShopName(tshopName);
			padSupplyInfo.setTargetShopId(null);
			padSupplyInfo.setTargetShopName(null);
			padSupplyInfo.setCreatetime(new Date());
			Integer result = this.padSupplyMapper.updateNoSelective(padSupplyInfo);
			// log
			if(result == 1){
				// log
				PadLog padLog = new PadLog();
				padLog.setPadNo(padNo);
				padLog.setOperatTime(new Date());
				padLog.setOperatorSid(Integer.valueOf(userSid));
				padLog.setOperator(userName);
				String description = "操作：收货。系统时间:"+df.format(new Date())+"  系统用户:"+userName +"  将PAD编号为："+padNo+"  从门店： "+shopName+"   收货至目的门店: "+tshopName+"。   -------PAD详情如下：";
				String jsonData = LogUtil.createPadLogDesc(padBaseinfo, description);
				padLog.setDescription(jsonData);
				Integer result1 = this.padLogMapper.insert(padLog);
				if(result1 !=1){
					//pad Log保存失败
					throw new ShopinException(ErrorCode.SAVE_PADLOG_ERROR.getMemo()); 
				}
			}else{
				//pad收货失败
				throw new ShopinException(ErrorCode.PAD_RECEIVE_RRROR.getMemo());  
			}
		}
	}

	@Override
	public Map<String, Integer> selectPadInfoReport(String shopId) {
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		Map<String, Integer> result = new HashMap<String, Integer>();
		Integer totalProperty = 0; // 每个门店总计
		// result.put("stopName", );
		for (int padStatus = 0; padStatus < 6; padStatus++) {
			criteriaMap.put("shopId", shopId);
			criteriaMap.put("padStatus", padStatus);// ['0',"在库"],['1',"卖场"],['2',"送修"],['3',"停用"],['4',"丢失"],['5',"在途"]
			Integer count = this.padBaseinfoMapper.selectPadInfoReport(criteriaMap);
			switch (padStatus) {
			case 0:
				result.put("InStore", count == null ? 0 : count);
				break;
			case 1:
				result.put("InShop", count == null ? 0 : count);
				break;
			case 2:
				result.put("InRepair", count == null ? 0 : count);
				break;
			case 3:
				result.put("InStop", count == null ? 0 : count);
				break;
			case 4:
				result.put("InLost", count == null ? 0 : count);
				break;
			case 5:
				result.put("InPassage", count == null ? 0 : count);
				break;
			}
			totalProperty += (count == null ? 0 : count);
		}
		result.put("totalProperty", totalProperty);
		return result;
	}


	public Map<String, String> selectPadInfoReportByPadStatus(HashMap<String, Object> map) {
		Map<String, String> result = new HashMap<String, String>();
		Integer totalProperty = 0; // 每个门店总计
		List<PadBaseinfoVO> list = padBaseinfoMapper.selectPadStatusNumByShopId(map);
		String shopId = (String) map.get("shopId");
		if(list !=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				PadBaseinfoVO vo = list.get(i);
				Integer padStatus = vo.getPadStatus();
				String count = vo.getPadStatusTotal();
				switch (padStatus) {// ['0',"在库"],['1',"卖场"],['2',"送修"],['3',"停用"],['4',"丢失"],['5',"在途"]
				case 0:
					result.put("InStore", count == null ? "0" : count);
					break;
				case 1:
					result.put("InShop", count == null ? "0" : count);
					break;
				case 2:
					result.put("InRepair", count == null ? "0" : count);
					break;
				case 3:
					result.put("InStop", count == null ? "0" : count);
					break;
				case 4:
					result.put("InLost", count == null ? "0" : count);
					break;
				case 5:
					result.put("InPassage", count == null ? "0" : count);
					break;
				default:
					break;
				}
				totalProperty += (count == null ? 0 : Integer.valueOf(count));
			}
			result.put("totalProperty", totalProperty+"");
		}
		return result;
	}

	@Override
	public String savePadInfoOfExcel(HashMap<String, Object> resultList, Map<String, String> info) throws Exception {

		String json = "";
		String padPurchaseBatchNo = info.get("padPurchaseBatchNo");
		String userSid = info.get("userSid");
		String userName = info.get("userName");
		String shopId = info.get("shopId");
		// 上限判断
		if (this.enteredNumIsEnough(padPurchaseBatchNo, resultList.size())) {
			throw new ShopinException(ErrorCode.ENTEREDNUM_IS_ENOUGH_ERROR.getMemo());
		}
		int startRow = 1;
		for (int i = startRow; i <= resultList.size(); i++) {
			
			Map<String, Object> map = (Map<String, Object>) resultList.get("row" + i);
			
			String padNo = (String) map.get("padNo");
			String macAddress = (String) map.get("macAddress");
			// 唯一性判断1,2
			if (!this.padNoIsUnique(padNo)) {
				throw new Exception("编号为：" + padNo + "的PAD已存在！");
			}
			if (!this.macAddressIsUnique(macAddress)) {
				throw new Exception("MAC地址为：" + macAddress + "的PAD已存在！");
			}
			PadBaseinfo baseinfo = new PadBaseinfo();
			baseinfo.setPadStatus(0);// 置为在库
			baseinfo.setPadNo(padNo);
			baseinfo.setUseType(-1);
			baseinfo.setMacAddress(macAddress);
			baseinfo.setCreateTime(new Date());
			baseinfo.setOperatorSid(Integer.parseInt(userSid));
			baseinfo.setOperator(userName);
			baseinfo.setPadPurchaseBatchNo(padPurchaseBatchNo);
			
			PadPurchaseInfo padPurchaseInfo = this.padPurchaseInfoService.getPadPurchseInfoByBatchNo(padPurchaseBatchNo);
			baseinfo.setBrand(padPurchaseInfo.getPadBrand());   //pad品牌  = padPurchas品牌
			
			// 插入
			this.padBaseinfoMapper.insert(baseinfo);

			// 门店：1000(公司总部)
			PadSupply padSupplyInfo = new PadSupply();
			padSupplyInfo.setPadNo(padNo);
			padSupplyInfo.setShopId(1000);
			padSupplyInfo.setShopName("公司总部");
			padSupplyInfo.setCreatetime(new Date());
			this.padSupplyMapper.insert(padSupplyInfo);
			
			//更新:录入PAD数量 加 1 。
			PadPurchaseInfo purchaseInfo = padPurchaseInfoService.getPadPurchseInfoByBatchNo(padPurchaseBatchNo);
			purchaseInfo.setPadEnteredNum(purchaseInfo.getPadEnteredNum()+1);
			this.padPurchaseInfoService.updateByPrimaryKeySelective(purchaseInfo);
			// log
			PadLog log = new PadLog();
			log.setPadNo(padNo);
			log.setOperatTime(new Date());
			log.setOperatorSid(Integer.valueOf(userSid));
			log.setOperator(userName);
			String description = "操作：添加。 系统时间:" + df.format(new Date()) + "系统用户:" + userName + " 添加编号为:"
					+ padNo + "的PAD基本信息。   -------PAD详情如下：";
			String jsonData = LogUtil.createPadLogDesc(baseinfo, description);
			log.setDescription(jsonData);
			Integer result = padLogMapper.insert(log);
			if (result != 1) {
				throw new ShopinException(
						ErrorCode.SAVE_PADLOG_ERROR.getErrorCode() + "_" + ErrorCode.SAVE_PADLOG_ERROR.getMemo());
			}
		}
		json = ResultUtil.createSuccessResult();
		return json;
	}

	@Override
	public boolean enteredNumIsEnough(String padPurchaseBatchNo, Integer addedNum) {

		PadPurchaseInfo padPurchaseInfo = this.padPurchaseInfoService.getPadPurchseInfoByBatchNo(padPurchaseBatchNo);
		// 采购数量
		Integer purchaseNum = padPurchaseInfo.getPadPurchaseNum();
		// 已录入PAD数量
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("padPurchaseBatchNo", padPurchaseBatchNo);
//		Integer count = this.getPadCountByBatchNo(map);
		Integer count = this.getCountByParam(map);
		//判断
		if ((count + addedNum) > purchaseNum) {
			return true;
		}
		return false;
	}
	@Override
	public boolean macAddressIsUnique(String macAddress){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("macAddress", macAddress);
		PadBaseinfo pad = this.selectPadByMacaddress(paramMap);
		if(pad==null){
			return true;
		}
		return false;
	}
	@Override
	public boolean padNoIsUnique(String padNo){
		boolean flag = false;
		PadBaseinfo pad = this.selectPadByPadNo(padNo);
		if(pad == null)
			flag = true;
		return flag;
	}

	@Override
	public List<PadLog> selectPadOperateLogByPadNo(String padNo) {
//		List<PadOperateLog> logs = this.padOperateLogMapper.selectByPrimaryKey(padNo);
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("padNo", padNo);
		List<PadLog> list = this.padLogMapper.selectListByParam(paramMap);
		return list;
	}

	@Override
	public List getPadInfoAndPurchaseInfo(Map paramMap) {
		List list = this.padBaseinfoMapper.getPadInfoAndPurchaseInfo(paramMap);
		return list;
	}

	@Override
	public Integer getCountByPadInfoAndPurchaseInfo(Map<String, Object> paramMap) {
		int count = this.padBaseinfoMapper.getCountByPadInfoAndPurchaseInfo(paramMap);
		return count;
	}

	@Override
	public PadBaseinfo selectPadbyPk(Long sid) {
		return this.padBaseinfoMapper.selectByPrimaryKey(sid);
	}

	@Override
	public List<PadBaseinfoVO> selectPadCanAllocateList(Map<String, Object> paramMap) throws Exception{
		return this.padBaseinfoMapper.getPadCanAllocateList(paramMap);
	}

	@Override
	public Integer getPadCanAllocateCount(Map<String, Object> map) {
		return this.padBaseinfoMapper.getPadCanAllocatCount(map);
	}


	@Override
	public Integer getCountByPadBaseAndSupplyInfo(Map paramMap) {
		return this.padBaseinfoMapper.getCountByPadBaseAndSupplyInfo(paramMap);
	}
	
	/*@Override
	public Integer getPadCountByBatchNo(Map<String, Object> paramMap) {
		
		return this.padBaseinfoMapper.getCountByParam(paramMap);
	}
*/
	
}
