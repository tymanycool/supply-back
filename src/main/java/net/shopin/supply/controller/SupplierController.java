package net.shopin.supply.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import net.shopin.supply.service.ISupplyService;
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


/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/supply")
public class SupplierController {
	private final static Logger logger = LogManager
			.getLogger(SupplierController.class);
	// private final static Logger logger =
	// LogManager.getLogger(Constants.class);

	private final static String url_supply = CommonProperties
			.get("supply_path").toString();
	private final static String url_brand = CommonProperties.get("brand_path")
			.toString();
	private final static String url_category = CommonProperties.get(
			"category_path").toString();
	private int maxPostSize = 100 * 1024 * 1024;

	@Autowired
	private ISupplyService supplyService;

	// 供应商查询接口
	@ResponseBody
	@RequestMapping(value = "/selectSupplyBySupplySid", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String selectSupplyBySupplySid(HttpServletRequest request,
			HttpServletResponse response) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		List lists = new ArrayList();
//		if(LocalCache.getValue("SupplyInfo") != null){// if the supplyInfo existed in Cache get it!
//			return gson.toJson(LocalCache.getValue("SupplyInfo"));
//		}else{
		try {
			String doMethod = "bw/querySupplyInfo.json";
			Map<String, String> paramMap = new HashMap<String, String>();
			String result = HttpUtil.HttpPost(url_supply, doMethod, paramMap);
			if (result == null || "".equals(result)) {
				map.put("success", "false");
				return gson.toJson(map);
			} else {
				Object obj = gson.fromJson(result, Object.class);
				Map remap = (Map) obj;
				Object objList = remap.get("result");
				JSONArray supplyList = JSONArray.fromObject(objList);

				for (int i = 0; i < supplyList.size(); i++) {
					JSONObject jsonObj = supplyList.getJSONObject(i);
					long sid = jsonObj.getLong("sid");
					String supplyName = jsonObj.get("supplyName").toString();
					Map jsonMap = new HashMap();
					jsonMap.put("sid", sid);
					jsonMap.put("supplyName", (sid+" "+supplyName));
					lists.add(jsonMap);
				}
//				LocalCache.setValue("SupplyInfo", lists);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		}
		return gson.toJson(lists);
	
	}

	// 品牌列表查询接口
	@ResponseBody
	@RequestMapping(value = "/queryBrandListByParam", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String queryBrandListByParam(HttpServletRequest request,
			HttpServletResponse response) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		List lists = new ArrayList();
//		if(LocalCache.getValue("brandList") != null){// if the supplyInfo existed in Cache get it!
//			return gson.toJson(LocalCache.getValue("brandList"));
//		}else{
		try {
			String doMethod = "photo/brandList.json";
			Map<String, String> paramMap = new HashMap<String, String>();
			Object value = new Object();
			String result = HttpUtil.HttpPost(url_brand, doMethod, paramMap);
			if (result == null || "".equals(result)) {
				map.put("success", "false");
				return gson.toJson(map);
			} else {
				Object obj = gson.fromJson(result, Object.class);
				Map remap = (Map) obj;
				Object objList = remap.get("result");
				JSONArray brandList = JSONArray.fromObject(objList);

				for (int i = 0; i < brandList.size(); i++) {
					JSONObject jsonObj = brandList.getJSONObject(i);
					long sid = jsonObj.getLong("sid");
					String brandName = jsonObj.get("brandName").toString();
					String brandSid=jsonObj.get("brandSid").toString();
					
					Map jsonMap = new HashMap();
					jsonMap.put("brandSid", brandSid);
					jsonMap.put("brandNames", brandName);
					jsonMap.put("brandName", (brandSid+" "+brandName));
				
					lists.add(jsonMap);
				}
			//	LocalCache.setValue("brandList", lists);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		}
		return gson.toJson(lists);
	}

	// 查询一级品类
	@ResponseBody
	@RequestMapping(value = "/queryCategoryByParam", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String queryCategoryByParam(HttpServletRequest request,
			HttpServletResponse response) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		List lists = new ArrayList();
//		if(LocalCache.getValue("Categroys") != null){// if the supplyInfo existed in Cache get it!
//			return gson.toJson(LocalCache.getValue("Categroys"));
//		}else{
		String json = "";
		String doMethod = "bw/queryCategroys.json";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("node", "1");
		paramMap.put("channelSid", "1");
		json = HttpUtil.HttpPost(url_category, doMethod, paramMap);
		JSONArray js = JSONArray.fromObject(json);
		for (int i = 0; i < js.size(); i++) {
			JSONObject catObj = js.getJSONObject(i);
			long id = catObj.getLong("id");

			String text = catObj.get("text").toString();
			Map catMap = new HashMap();
			catMap.put("id", id);
			catMap.put("cName",(id+" "+ text));
			lists.add(catMap);
		}
//		LocalCache.setValue("Categroys", lists);
//		}
		return gson.toJson(lists);
		// http://localhost/supply/supply/queryBrandListByParam.json?node=1&channelSid=2
	}

	// 获取参数方法
	private Map<String, String> createParam(HttpServletRequest request) {
		request.removeAttribute("_method");
		Map<String, String> paramMap = new HashMap<String, String>();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramStr = (String) enumeration.nextElement();
			if ("node".equals(paramStr)) {
				paramMap.put("node", request.getParameter(paramStr));
			}
			if ("channelSid".equals(paramStr)) {
				paramMap.put("channelSid", request.getParameter(paramStr));
			}
		}
		return paramMap;
	}

	/**
	 * 按条件查询
	 * 
	 * @param sid
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSupplyList", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String findSupplyList(HttpServletRequest request,
			HttpServletResponse response,Model model, Integer start, Integer limit) {

		String supplySid = request.getParameter("supplySid");
		String brandSid = request.getParameter("brandSid");
		String categorySid = request.getParameter("categorySid");

		Supply supplys = new Supply();
		String resultJson = "";
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if (supplySid != null && !"".equals(supplySid)) {
				supplys.setSupplySid(Integer.valueOf(supplySid));
			}
			if (brandSid != null && !"".equals(brandSid)) {
				supplys.setBrandSid(brandSid);
			}
			if (categorySid != null && !"".equals(categorySid)) {
				supplys.setCategorySid(Integer.valueOf(categorySid));
			}
			
	
			supplys.setStart(start);
			supplys.setPageSize(limit);
			int total = this.supplyService.SupplyCount(supplys);
			List<Supply> list = this.supplyService.selectAllSupply(supplys);


			model.addAttribute("total", total);
			model.addAttribute("list", list);
			resultJson = ResultUtil.createSuccessResult(list);
			resultJson = resultJson.substring(0, resultJson.length()-1) + ",'total':'"+total+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			// resultJson = ResultUtil.createSuccessResult();
		}
		return resultJson;
	}

	/**
	 * 添加方法
	 * 
	 * @param request
	 * @param respons
	 */
	@ResponseBody
	@RequestMapping(value = "/insertSupplier", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String insertSupplier(HttpServletRequest request,
			HttpServletResponse respons) throws Exception {
		String resultJson = "";
		String name;
		String encoding = request.getCharacterEncoding();
		OutputStream out = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxPostSize);

		try {
			Supply supply = new Supply();
			List fileItems = upload.parseRequest(request);
			name = FtpUtil.getImagePath();
			for (int i = 0; i < fileItems.size(); i++) {

				FileItem item = (FileItem) fileItems.get(i);
				if (!item.isFormField() && item.getName() != null // FileItem.isFormField()判断是普通表单域还是文件上传域，为true的话是普通表单域
						&& !"".equals(item.getName())) {
					String filename = item.getName();
					String upName = "";
					upName = name + i + "." + filename.split("\\.")[1];
					FtpUtil.saveBrandLOGOPicToFtp(out, upName, item);//
					if (item.getFieldName() == "image1"
							|| "image1".equals(item.getFieldName())) {
						supply.setPicUrl("/" + SystemConfig.BRAND_LOGO_PATH
								+ "/" + upName);
					}
				} else {
					String key = item.getFieldName();
					String value = item.getString(encoding);
					if ("supplyName".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setSupplyName(value);
					}
					if ("brandName".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setBrandName(value);
					}
					if ("categoryName".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setCategoryName(value);
					}
					if ("supplySid".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setSupplySid(Integer.valueOf(value));
					}
					if ("brandSid".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setBrandSid(value);
					}
					if ("categorySid".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setCategorySid(Integer.valueOf(value));
					}

				}
			}
			this.supplyService.insert(supply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultJson = ResultUtil.createSuccessResult();
		logger.info(resultJson);
//		System.out.println(resultJson);
		return resultJson;
	}

	/**
	 * 修改方法
	 * 
	 * @param sid
	 * @param m
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws FileUploadException
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSupplyBillMsg", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String updateSupplyBillMsg(String sid, Model m,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException, IOException, FileUploadException {

		String json = "";
		String name;
		OutputStream out = null;
		String encoding = request.getCharacterEncoding();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxPostSize);
		try {
			Supply supply = new Supply();
			List fileItems = upload.parseRequest(request);
			name = FtpUtil.getImagePath();
			for (int i = 0; i < fileItems.size(); i++) {
				FileItem item = (FileItem) fileItems.get(i);
				if (item.getFieldName() == "sid"
						|| "sid".equals(item.getFieldName())) {
					supply = this.supplyService.findSid(Integer.valueOf(item
							.getString(encoding)));
					break;
				}
			}
			for (int i = 0; i < fileItems.size(); i++) {

				FileItem item = (FileItem) fileItems.get(i);
				if (!item.isFormField() && item.getName() != null // FileItem.isFormField()判断是普通表单域还是文件上传域，为true的话是普通表单域
						&& !"".equals(item.getName())) {
					String filename = item.getName();
					String upName = "";
					upName = name + i + "." + filename.split("\\.")[1];
					FtpUtil.saveBrandLOGOPicToFtp(out, upName, item);//
					if (item.getFieldName() == "image1"
							|| "image1".equals(item.getFieldName())) {
						supply.setPicUrl("/" + SystemConfig.BRAND_LOGO_PATH
								+ "/" + upName);
					}
				} else {
					String key = item.getFieldName();
					String value = item.getString(encoding);
					if ("supplyName".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setSupplyName(value);
					}
					if ("brandName".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setBrandName(value);
					}
					if ("categoryName".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setCategoryName(value);
					}
					if ("supplySid".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setSupplySid(Integer.valueOf(value));
					}
					if ("brandSid".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setBrandSid(value);
					}
					if ("categorySid".equals(key) && value != null
							&& !"".equals(value)) {
						supply.setCategorySid(Integer.valueOf(value));
					}
				}
			}

			this.supplyService.updateSupply(supply);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//json = ResultUtil.createFailureResult(e);
			e.printStackTrace();
		}
		json = ResultUtil.createSuccessResult();
		return json;
	}

	// 删除
	@ResponseBody
	@RequestMapping(value = "/deleteBrandBySid", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String deleteBrandBySid(HttpServletRequest request,
			HttpServletResponse response) {
		String json = "";
		try {
			String sids = request.getParameter("sid");
			String sidArr[] = sids.split(",");
			for (int i = 0; i < sidArr.length; i++) {
				this.supplyService.deleteByPrimaryKey(Integer
						.parseInt(sidArr[i]));
			}
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			//json = ResultUtil.createFailureResult(e);
			e.printStackTrace();
		}

		return json;
	}
//	@ResponseBody
	@RequestMapping(value = "/downloadSid", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String downloadSid(HttpServletRequest request,HttpServletResponse response){
		String picUrl=request.getParameter("url");
		String[] url=picUrl.split("/supply/");
		System.out.println();
		FileOutputStream fos = null;
		String json = "";
		try {
			FileTransferClient client = new FileTransferClient();
			 client.setRemoteHost("172.16.200.4");
			  client.setUserName("ftptest");
			  client.setPassword("$ftptest1001");
			  client.setRemotePort(21);
			  client.connect();
			  response.setCharacterEncoding("utf-8"); 
			    response.setContentType("multipart/form-data"); 
				OutputStream os = new BufferedOutputStream(response.getOutputStream());
			response.setHeader("Content-Disposition", "attachment;fileName="+url[1]);
			  os.write(client.downloadByteArray(picUrl));
			  os.close();
			  client.disconnect();
			  json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;

		
	}
}
