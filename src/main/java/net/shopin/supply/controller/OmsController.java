package net.shopin.supply.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.Supply;
import net.shopin.supply.domain.vo.GuideinfoVO;
import net.shopin.supply.domain.vo.OmsInfoVo;
import net.shopin.supply.service.IOmsService;
import net.shopin.supply.service.ISupplyService;
import net.shopin.supply.util.CollectionsUtils;
import net.shopin.supply.util.CommonProperties;
import net.shopin.supply.util.FtpUtil;
import net.shopin.supply.util.HttpUtil;
import net.shopin.supply.util.ResultUtil;
import net.shopin.supply.util.SystemConfig;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enterprisedt.net.ftp.FileTransferClient;
import com.google.gson.Gson;
import com.shopin.core.util.DateUtils;


/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/oms")
public class OmsController {
	private final static Logger logger = LogManager
			.getLogger(OmsController.class);

	@Autowired
	private IOmsService omsService;

	/**
	 * 收银员流水查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCashierList", method = { RequestMethod.GET,
			RequestMethod.POST },produces = "text/html;charset=UTF-8")
	public String selectCashierList(HttpServletRequest request,
			HttpServletResponse response,Model model,  OmsInfoVo omsInfoVo, Integer start, Integer limit) {


		String resultJson = "";
		try {
			Map<String, Object> paramsMap = CollectionsUtils.BeanToMapFilterNull(omsInfoVo);
			paramsMap.put("start", start);
			paramsMap.put("limit", limit);
			
			String endTime = (String)paramsMap.get("endTime");
			if(endTime!=null){
				paramsMap.put("endTime", DateUtils.addDays2(endTime, 1));
			}
			
	
			int total = this.omsService.SupplyCashierListCount(paramsMap);
			List list = this.omsService.selectCashierList(paramsMap);


			model.addAttribute("total", total);
			model.addAttribute("list", list);
			resultJson = ResultUtil.createSuccessResult(list);
			resultJson = resultJson.substring(0, resultJson.length()-1) + ",'total':'"+total+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/exportCashierReportToExcel",method = {RequestMethod.GET, RequestMethod.POST})
	public String exportCashierReportToExcel(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
		String type = "1";
		
		Map map = new HashMap();
		String shopSid = request.getParameter("shopSid");
		String guideNo = request.getParameter("guideNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String supplySid = request.getParameter("supplySid");
		String paymentTypeSid = request.getParameter("paymentTypeSid");
		String terminalNo = request.getParameter("terminalNo");
		
		if(null != shopSid && !shopSid.equals("")){
			map.put("shopSid", shopSid);
		}
		if(null != guideNo && !guideNo.equals("")){
			map.put("guideNo", guideNo);
		}
		if(null != startTime && !startTime.equals("")){
			map.put("startTime", startTime);
		}
		if(null != endTime && !endTime.equals("")){
			map.put("endTime", DateUtils.addDays2(endTime, 1));
		}
		if(null != supplySid && !supplySid.equals("")){
			map.put("supplySid", supplySid);
		}
		if(null != paymentTypeSid && !paymentTypeSid.equals("")){
			map.put("paymentTypeSid", paymentTypeSid);
		}
		if(null != terminalNo && !terminalNo.equals("")){
			map.put("terminalNo", terminalNo);
		}

		
		
		List<OmsInfoVo> guideinfoList = this.omsService.selectCashierListForExcel(map);
		if(null != guideinfoList){
			String result = this.omsService.cashierSelectsToExcel(response, guideinfoList,type);
			json = ResultUtil.createSuccessResult(result);
		}else{
			json = ResultUtil.createFailureResult("00000", "收银员流水导出Excel数据为0！");
		}
		
		return json;
	}
	
	//收银员长短款报表查询
	@ResponseBody
	@RequestMapping(value = "/selectLongShortList", method = { RequestMethod.GET,
			RequestMethod.POST },produces = "text/html;charset=UTF-8")
	public String selectLongShortList(HttpServletRequest request,
			HttpServletResponse response,Model model,  OmsInfoVo omsInfoVo, Integer start, Integer limit) {


		String resultJson = "";
		try {
			Map<String, Object> paramsMap = CollectionsUtils.BeanToMapFilterNull(omsInfoVo);
			paramsMap.put("start", start);
			paramsMap.put("limit", limit);
			
			String endTime = (String)paramsMap.get("endTime");
			if(endTime!=null){
				paramsMap.put("endTime", DateUtils.addDays2(endTime, 1));
			}
			
	
			List list = this.omsService.selectLongShortList(paramsMap);
			int total = this.omsService.SupplyLongShortListCount(paramsMap);


			model.addAttribute("total", total);
			model.addAttribute("list", list);
			resultJson = ResultUtil.createSuccessResult(list);
			resultJson = resultJson.substring(0, resultJson.length()-1) + ",'total':'"+total+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson;
	}
	
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/exportLongShortReportToExcel",method = {RequestMethod.GET, RequestMethod.POST})
	public String exportLongShortReportToExcel(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
		String type = "1";
		
		Map map = new HashMap();
		String guideNo = request.getParameter("guideNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String terminalNo = request.getParameter("terminalNo");
		
		if(null != guideNo && !guideNo.equals("")){
			map.put("guideNo", guideNo);
		}
		if(null != startTime && !startTime.equals("")){
			map.put("startTime", startTime);
		}
		if(null != endTime && !endTime.equals("")){
			map.put("endTime", DateUtils.addDays2(endTime, 1));
		}
		if(null != terminalNo && !terminalNo.equals("")){
			map.put("terminalNo", terminalNo);
		}
		
		List<OmsInfoVo> guideinfoList = this.omsService.selectLongShortListForExcel(map);
		if(null != guideinfoList){
			String result = this.omsService.longShortSelectsToExcel(response, guideinfoList,type);
			json = ResultUtil.createSuccessResult(result);
		}else{
			json = ResultUtil.createFailureResult("00000", "收银员流水导出Excel数据为0！");
		}
		
		return json;
	}
	
	

}
