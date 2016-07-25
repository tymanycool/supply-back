package net.shopin.supply.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopin.core.util.ResultUtil;

import net.shopin.supply.domain.entity.PadPurchaseInfo;
import net.shopin.supply.service.IPadPurchaseInfoService;

/**
 * 
 * Title: PadInfoController
 * Description: pad管理模块-新
 * @author SunYukun
 * @date 2016年3月10日 上午9:44:42
 */
@Controller
@RequestMapping("/padPurchaseInfo")
public class PadInfoController {
	private static Logger logger = Logger.getLogger(PadInfoController.class);
	@Autowired
	private IPadPurchaseInfoService padPurchaseInfoService;
	/**
	 * 查询新采购的pad的批次信息
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/getPadPurchaseInfo", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String getPadPurchaseInfo(HttpServletRequest request,Integer start,Integer limit) throws Exception{
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		List lists = this.padPurchaseInfoService.getPadPurchaseInfo(map);
		Integer count = this.padPurchaseInfoService.getCountOfPadPurchase();
		map.put("list",lists);
		json = ResultUtil.createSuccessResult(lists);
		json = json.substring(0, json.length()-1) +",total:"+count+"}";
		return json;
	}
	
	/**
	 * 添加pad批次信息
	 * @param padPurchaseInfo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/addPadPurchaseInfo", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String insertPadPurchaseInfo(PadPurchaseInfo padPurchaseInfo) throws Exception{
		String json = "";
		if (padPurchaseInfo != null) {
			if (padPurchaseInfo.getPadPurchaseNum() <= 0) {
				json = ResultUtil.createFailureResult("00000", "进货数量有误！");
				return json;
			}
			Integer flag = this.padPurchaseInfoService.insertSelective(padPurchaseInfo);
			if (flag == -1) {
				json = ResultUtil.createFailureResult("00000", "批次号需唯一!");
				return json;
			}
			logger.info("创建新的padPurchaseInfo成功:数据为："+padPurchaseInfo.toString());
			json = ResultUtil.createSuccessResult();
		}
		return json;
	}
	
	/**
	 * 修改pad批次信息
	 * @param padPurchaseInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePadPurchaseInfo", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String updatePadPurchaseInfo(PadPurchaseInfo padPurchaseInfo){
		String json = "";
		if (padPurchaseInfo != null) {
			if (padPurchaseInfo.getPadPurchaseNum() <= 0) {
				json = ResultUtil.createFailureResult("00000", "进货数量有误！");
				return json;
			}
			 this.padPurchaseInfoService.updateByPrimaryKeySelective(padPurchaseInfo);
		}
		json = ResultUtil.createSuccessResult();
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletePadPurchaseInfo", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String deletePadPurchaseInfo(Long sid){
		String json = "";
		this.padPurchaseInfoService.deleteByPrimaryKey(sid);
		json = ResultUtil.createSuccessResult();
		return json;
	}
	
	
    @InitBinder  
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
	}
}
;