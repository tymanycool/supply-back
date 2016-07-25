package net.shopin.supply.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.GuideSupply;
import net.shopin.supply.domain.entity.SupplyInfo;
import net.shopin.supply.service.IGuideSupplyService;
import net.shopin.supply.util.CommonProperties;
import net.shopin.supply.util.HttpUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shopin.core.util.ResultUtil;

/**
 * 导购与供应商关系管理
* @ClassName: GuideSupplyController 
* @author zhangq
* @date 2014-12-23 下午06:29:38 
*
 */
@Controller
@RequestMapping("/guideSupply")
public class GuideSupplyController {
	
	
	@Autowired
	private IGuideSupplyService guideSupplyService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 根据导购编号查询导购绑定的供应商和门店
	* @Title: getGuideSupplyList 
	* @param @param model
	* @param @param request guideNo 导购编号
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/list")
	public String getGuideSupplyList(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String guideNo = request.getParameter("guideNo");
		String json = "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			if(null != guideNo && !guideNo.equals("")){
				map.put("guideNo", guideNo);
			}
			List<GuideSupply> guideSupplyList = this.guideSupplyService.selectListByParam(map);
			map.put("list",guideSupplyList);
			map.put("total", guideSupplyList.size());
			json = ResultUtil.createSuccessResult(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	 
	/**
	 * 导购绑定供应商和门店
	* @Title: saveGuideSupply 
	* @param @param request supplyId 供应商id，supplyName 供应商名称，guideNo 导购编号，shopId 门店id，
	* 						shopName 门店名称，brandName 品牌名称，brandId 品牌id
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/saveGuideSupply",method={RequestMethod.POST,RequestMethod.GET})
	public String saveGuideSupply(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
        
		String supplyId = request.getParameter("supplyId");
		String supplyName = request.getParameter("supplyName");
		String guideNo = request.getParameter("guideNo");
		String shopId = request.getParameter("shopId");
		String shopName = request.getParameter("shopName");
		String brandName = request.getParameter("brandName");
		String brandSid = request.getParameter("brandId");
		String brandSsdSid = request.getParameter("brandSsdSid");
		String username = request.getParameter("username");
		String change = request.getParameter("change");
		String sid = request.getParameter("sid");
		
		try {
			GuideSupply guideSupply = new GuideSupply();
			guideSupply.setGuideNo(Integer.parseInt(guideNo));
			guideSupply.setSupplyId(Integer.parseInt(supplyId));
			guideSupply.setSupplyName(supplyName);
			guideSupply.setShopId(Integer.parseInt(shopId));
			guideSupply.setShopName(shopName);
			guideSupply.setValidBit(1);//是否有效 0:无效 1:有效
			guideSupply.setChangeSupplyBit(0);//是否转场 0:不是 1:是
			
			String[] brandSids = brandSid.split(",");
			String[] brandSsdSids = brandSsdSid.split(",");
			
			CharSequence supplyStr = supplyId.subSequence(0, 1);
			if(supplyId.equals("-100000")){
				guideSupply.setCategroys("自营");
				guideSupply.setCategorysId(Integer.parseInt("-100000"));
			}else if(supplyStr.equals("5")){
				guideSupply.setCategroys("租赁");
				guideSupply.setCategorysId(Integer.parseInt("-500000"));
			}else{
				String url = CommonProperties.get("GET_CATEGORYS_BYBRAND_LIST");
				json = HttpUtil.GetUrlContent(url, "supplySid="+supplyId+"&brandSid="+brandSids[0]);
				JSONObject jsonOb = JSONObject.fromObject(json);
				if(jsonOb.getBoolean("success")){
					String categoryName = "";
					String categoryId = "";
					JSONArray jsonArr = jsonOb.getJSONArray("result"); 
					for(int i=0;i<jsonArr.size();i++){
						JSONObject jsonObj = jsonArr.getJSONObject(0);
						categoryName = jsonObj.get("name").toString();
						categoryId = jsonObj.get("categorySid").toString();
					}
					guideSupply.setCategroys(categoryName);
					guideSupply.setCategorysId(Integer.parseInt(categoryId));
				}
			}
			
			String[] brandNames = brandName.split(",");

			for(int i=0;i<brandNames.length;i++){
				guideSupply.setBrand(brandNames[i]);
				guideSupply.setBrandId(brandSids[i]);
				guideSupply.setBrandSsdSid(brandSsdSids[i]);
				this.guideSupplyService.saveGuideSupply(guideSupply,username);
			}
			
			if(null != change && !"".equals(change)){
				if(change.equals("0")){//转场
					GuideSupply guideSupplyChange = this.guideSupplyService.get(Long.parseLong(sid));
					guideSupplyChange.setValidBit(0);
					guideSupplyChange.setChangeSupplyBit(1);//是否转场 0:不是 1:是
					this.guideSupplyService.update(guideSupplyChange);
				}
			}
			
			json = ResultUtil.createSuccessResult();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 导购转场(此方法已失效)
	* @Title: changeGuideSupply 
	* @param @param request supplyId 供应商id ,supplyName 供应商名称 , guideNo 导购编号 ，shopId 门店id ，
	* 						brandName 品牌名称，brandId 品牌id ，username 系统用户名
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/changeGuideSupply",method={RequestMethod.POST,RequestMethod.GET})
	public String changeGuideSupply(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
        
		String supplyId = request.getParameter("supplyId");
		String supplyName = request.getParameter("supplyName");
		String brandName = request.getParameter("brandName");
		String brandSid = request.getParameter("brandId");
		String changeBrandSsdSid = request.getParameter("changeBrandSsdSid");
		String username = request.getParameter("username");
		String sid = request.getParameter("sid");
		
		try {
			
			GuideSupply guideSupply = this.guideSupplyService.get(Long.parseLong(sid));
			int changeSupply = guideSupply.getChangeSupplyBit();
			if(changeSupply == 1){//转场
				guideSupply.setSupplyId(guideSupply.getChangeSupplyId());
				guideSupply.setSupplyName(guideSupply.getChangeSupplyName());
				guideSupply.setBrandId(guideSupply.getChangeBrandId());
				guideSupply.setBrandSsdSid(guideSupply.getChangeBrandSsdId());
				guideSupply.setBrand(guideSupply.getChangeBrand());
				guideSupply.setCategorysId(guideSupply.getCategorysId());
				guideSupply.setCategroys(guideSupply.getChangeCategroys());
			}
			guideSupply.setChangeSupplyId(Integer.parseInt(supplyId));
			guideSupply.setChangeSupplyName(supplyName);
			guideSupply.setChangeSupplyBit(1);//是否转场 0:不是 1:是
			
			String[] brandSids = brandSid.split(",");
			String[] changeBrandSsdSids = changeBrandSsdSid.split(",");
			CharSequence supplyStr = supplyId.subSequence(0, 1);
			if(supplyId.equals("-100000")){
				guideSupply.setChangeCategroys("自营");
				guideSupply.setChangeCategorysId(Integer.parseInt("-100000"));
			}else if(supplyStr.equals("5")){
				guideSupply.setChangeCategroys("租赁");
				guideSupply.setChangeCategorysId(Integer.parseInt("-500000"));
			}else{
				String url = CommonProperties.get("GET_CATEGORYS_BYBRAND_LIST");
				json = HttpUtil.GetUrlContent(url, "supplySid="+supplyId+"&brandSid="+brandSids[0]);
				JSONObject jsonOb = JSONObject.fromObject(json);
				if(jsonOb.getBoolean("success")){
					String categoryName = "";
					String categoryId = "";
					JSONArray jsonArr = jsonOb.getJSONArray("result"); 
					for(int i=0;i<jsonArr.size();i++){
						JSONObject jsonObj = jsonArr.getJSONObject(0);
						categoryName = jsonObj.get("name").toString();
						categoryId = jsonObj.get("categorySid").toString();
					}
					guideSupply.setChangeCategroys(categoryName);
					guideSupply.setChangeCategorysId(Integer.parseInt(categoryId));
				}
			}
			
			String[] brandNames = brandName.split(",");
			
			for(int i=0;i<brandNames.length;i++){
				guideSupply.setChangeBrand(brandNames[i]);
				guideSupply.setChangeBrandId(brandSids[i]);
				guideSupply.setChangeBrandSsdId(changeBrandSsdSids[i]);
				int result = this.guideSupplyService.changeGuideSupply(guideSupply,username);
				
				if(result != 1){
					json = ResultUtil.createFailureResult("00000","转场失败！");
				}else{
					json = ResultUtil.createSuccessResult("转场成功！");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 解除导购与供应商关系(删除)
	* @Title: delGuideSupply 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delGuideSupply", method = {RequestMethod.GET, RequestMethod.POST})
	public String delGuideSupply(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		
		String sid = request.getParameter("sid");
		String username = request.getParameter("username");
		try {
			this.guideSupplyService.delGuideSupply(Integer.parseInt(sid),username);
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("删除失败！",e.getMessage());
		}
		return json;
	}
	
	/**
	 * 获取所有供应商信息
	* @Title: getSupplyInfoList 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/getSupplyInfoList")
	@ResponseBody
	public String getSupplyInfoList(Model model, HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			List<SupplyInfo> supplyInfoList = this.guideSupplyService.selectSupplyInfoListByParam(map);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			for(int i=0;i<supplyInfoList.size();i++){
				Map<String,Object> supplyMap = new HashMap<String,Object>();
				supplyMap.put("supplySid", supplyInfoList.get(i).getSupplyId());
				supplyMap.put("supplyName", supplyInfoList.get(i).getSupplyName());
				list.add(supplyMap);
			}
				
			json = ResultUtil.createSuccessResult(list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 获取门店信息
	* @Title: getShopList 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/getShopList"})
	public String getShopList(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		Gson gson = new Gson();
		JSONArray array = null;
		try {
			String url = CommonProperties.get("GET_SHOP_LIST");
			json = HttpUtil.GetUrlContent(url, null);
			
			JSONObject jsonO = JSONObject.fromObject(json);
			Map map = new HashMap();
			map.put("sid", "1000");
			map.put("shopName", "公司总部");
			if(jsonO.getBoolean("success")){
				array = jsonO.getJSONArray("result");
				array.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gson.toJson(array);
	}
	
	@ResponseBody
	@RequestMapping(value={"/getShopsList"})
	public String getShopsList(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		Gson gson = new Gson();
		JSONArray array = null;
		try {
			String url = CommonProperties.get("GET_SHOP_LIST");
			json = HttpUtil.GetUrlContent(url, null);
			
			JSONObject jsonO = JSONObject.fromObject(json);
			Map map = new HashMap();
			map.put("sid", "1000");
			map.put("shopName", "全部");
			if(jsonO.getBoolean("success")){
				array = jsonO.getJSONArray("result");
				array.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gson.toJson(array);
	}
	
	/**
	 * 获取门店接口，将1000标为公司总部
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/getShopsList2"})
	public String getShopsList2(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		Gson gson = new Gson();
		JSONArray array = null;
		try {
			String url = CommonProperties.get("GET_SHOP_LIST");
			json = HttpUtil.GetUrlContent(url, null);
			
			JSONObject jsonO = JSONObject.fromObject(json);
			Map map = new HashMap();
			map.put("sid", "1000");
			map.put("shopName", "公司总部");
			if(jsonO.getBoolean("success")){
				array = jsonO.getJSONArray("result");
				array.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(array);
	}
	
	
	/**
	 * 获取一级品类信息
	* @Title: getCategroys 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/getCategroys"})
	public String getCategroys(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			//查询一级品类接口
			String url = CommonProperties.get("GET_CATEGORYS_LIST");
			json = HttpUtil.GetUrlContent(url, "node=1&channelSid=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 根据供应商和品牌获取品类
	* @Title: getFlCategory 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/getFlCategory"})
	public String getFlCategory(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			//查询一级品类接口
			String supplySid = request.getParameter("supplySid");
			String brandSid = request.getParameter("brandSid");
			String url = CommonProperties.get("GET_CATEGORYS_BYBRAND_LIST");
			json = HttpUtil.GetUrlContent(url, "supplySid="+supplySid+"&brandSid="+brandSid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 根据门店获取供应商信息
	* @Title: getSupplyInfo 
	* @param @param model
	* @param @param request shopId 门店id
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/getSupplyInfo"})
	public String getSupplyInfo(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		Gson gson = new Gson();
		List list = new ArrayList();
		try {
			String shopId = request.getParameter("shopId");
			String url = CommonProperties.get("GET_SUPPLY_LIST_BY_SHOP");
			json = HttpUtil.GetUrlContent(url, "shopSid="+shopId);
			
			JSONObject strJson = JSONObject.fromObject(json);
			if(strJson.getBoolean("success")){
				JSONArray strArray = strJson.getJSONArray("list");
				for(int i = 0; i < strArray.size(); i++) {
					JSONObject jsonObj = strArray.getJSONObject(i);
					if(null == jsonObj.get("supplyinfoSid")){
						continue;
					}else{
						String sid = jsonObj.get("supplyinfoSid").toString();
						String supplyName = jsonObj.get("supplyinfoName").toString();
						Map<String,Object> jsonMap = new HashMap<String,Object>();
						jsonMap.put("supplyinfoSid", sid);
						jsonMap.put("supplyinfoName", (sid+" "+supplyName));
						list.add(jsonMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(list);
	}
	
	/**
	 * 根据门店和供应商获取品牌信息
	* @Title: getBrandInfo 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/getBrandInfo"})
	public String getBrandInfo(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			String shopId = request.getParameter("shopId");
			String supplySid = request.getParameter("supplySid");
			String url = CommonProperties.get("GET_BRANDS_LIST");
			json = HttpUtil.GetUrlContent(url, "shopSid="+shopId+"&supplySid="+supplySid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 获取所有供应商
	* @Title: querySupplyInfo 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/querySupplyInfo"})
	public String querySupplyInfo(Model model,HttpServletRequest request, HttpServletResponse response) {

		String json = "";
		Gson gson = new Gson();
		List list = new ArrayList();
		try {
			String url = CommonProperties.get("GET_SUPPLY_LIST");
			json = HttpUtil.GetUrlContent(url, null);
			JSONObject strJson = JSONObject.fromObject(json);
			if(strJson.getBoolean("success")){
				JSONArray strArray = strJson.getJSONArray("result");
				for(int i = 0; i < strArray.size(); i++) {
					JSONObject jsonObj = strArray.getJSONObject(i);
					String sid = jsonObj.get("sid").toString();
					String supplyName = jsonObj.get("supplyName").toString();
					Map<String,Object> jsonMap = new HashMap<String,Object>();
					jsonMap.put("sid", sid);
					jsonMap.put("supplyName", (sid+" "+supplyName));
					list.add(jsonMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gson.toJson(list);
	}
	
	/**
	 * 获取所有品牌
	* @Title: brandList 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/brandList"})
	public String brandList(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			String url = CommonProperties.get("GET_BRAND_LIST");
			json = HttpUtil.GetUrlContent(url, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 补充品牌在ssd中sid
	 * @Title: saveBrandSSDSid 
	 * @Description: TODO
	 * @param @param model
	 * @param @param request
	 * @param @param response
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author zhangqing
	 * @date 2015-3-26
	 */
	@ResponseBody
	@RequestMapping(value={"/saveBrandSSDSid"})
	public String saveBrandSSDSid(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		String sapbrandid = "";
		Map<String,Object> map=new HashMap<String,Object>();
		
		List<GuideSupply> guideSupplyList = this.guideSupplyService.selectListByParam(map);
		for(GuideSupply guidesupply : guideSupplyList){
			sapbrandid = guidesupply.getBrandId();
			map.put("erpBrand", sapbrandid);
			//http://172.16.100.108/ssdservice/photo/getBrandSid.html?erpBrand=B290
			//{"success":"true","result":[{"sid":1,"brandSid":"Z001","brandName":"租赁用品牌","start":0,"end":0,"pageSize":10,"result":0,"success":false}]}
			String url = "http://172.16.100.108/ssdservice";
			String result = HttpUtil.HttpPost(url, "photo/getBrandSid.html", map);
			JSONObject strJson = JSONObject.fromObject(result);
			JSONArray strArray = strJson.getJSONArray("result");
			if(strArray.size()>0){
				for(int i = 0; i < strArray.size(); i++) {
					JSONObject jsonObj = strArray.getJSONObject(i);
					String ssdSid = jsonObj.get("sid").toString();
					
					GuideSupply guideSupply = new GuideSupply();
					guideSupply.setBrandSsdSid(ssdSid);
					guideSupply.setBrandId(sapbrandid);
					int results = this.guideSupplyService.saveBrandSSDSid(guideSupply);
 					if(results == 1){
						json = ResultUtil.createSuccessResult();
					}else{
						json = ResultUtil.createFailureResult("000000", "修改失败");
					}
				}
			}
		}
		return json;
	}
	
	/**
	 * 补充转场后品牌在ssd中sid
	 * @Title: saveChangeBrandSSDSid 
	 * @Description: TODO
	 * @param @param model
	 * @param @param request
	 * @param @param response
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author zhangqing
	 * @date 2015-3-26
	 */
	@ResponseBody
	@RequestMapping(value={"/saveChangeBrandSSDSid"})
	public String saveChangeBrandSSDSid(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		String sapChangeBrandid = "";
		Map<String,Object> map=new HashMap<String,Object>();
		
		List<GuideSupply> guideSupplyList = this.guideSupplyService.selectListByParam(map);
		for(GuideSupply guidesupply : guideSupplyList){
			sapChangeBrandid = guidesupply.getChangeBrandId();
			if(null == sapChangeBrandid){
				continue;
			}
			map.put("erpBrand", sapChangeBrandid);
			//http://172.16.100.108/ssdservice/photo/getBrandSid.html?erpBrand=B290
			//{"success":"true","result":[{"sid":1,"brandSid":"Z001","brandName":"租赁用品牌","start":0,"end":0,"pageSize":10,"result":0,"success":false}]}
			String url = "http://172.16.100.108/ssdservice";
			String result = HttpUtil.HttpPost(url, "photo/getBrandSid.html", map);
			JSONObject strJson = JSONObject.fromObject(result);
			JSONArray strArray = strJson.getJSONArray("result");
			if(strArray.size()>0){
				for(int i = 0; i < strArray.size(); i++) {
					JSONObject jsonObj = strArray.getJSONObject(i);
					String ssdSid = jsonObj.get("sid").toString();
					
					GuideSupply guideSupply = new GuideSupply();
					guideSupply.setChangeBrandSsdId(ssdSid);
					guideSupply.setChangeBrandId(sapChangeBrandid);
					int results = this.guideSupplyService.saveChangeBrandSSDSid(guideSupply);
					if(results == 1){
						json = ResultUtil.createSuccessResult();
					}else{
						json = ResultUtil.createFailureResult("000000", "修改失败");
					}
				}
			}
		}
		return json;
	}
}
