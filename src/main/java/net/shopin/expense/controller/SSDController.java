/**
 * SSDController.java
 * net.shopin.pz.controller
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年10月21日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.util.HttpUtil;
import net.shopin.supply.util.SystemConfig;

/**
 * <p>ClassName:SSDController</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年10月21日下午2:55:38
 */
@Controller
@RequestMapping(value="/ssd")
public class SSDController {

	public static Logger logger = Logger.getLogger(SSDController.class);
	//缓存
	public static Map<String, String> coll = new HashMap<String, String>();
	public static Map<String, String> category = new HashMap<String, String>();
	public static Map<String, String> sku = new HashMap<String, String>();
	/**
	 * <p>Title:getAllBrands</p>
	 * <p>Description:	得到所有品牌</p>
	 */
	@ResponseBody
	@RequestMapping(value="/getAllBrands", method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllBrands(HttpServletRequest request, HttpServletResponse response){
		
		if(coll.get("allBrands") == null){
			Map<String, Object> map = new HashMap<String, Object>();
			String str = HttpUtil.HttpPostForMap(SystemConfig.SSD_SYSTEM_URL, "photo/brandList.html", map);
			JSONObject obj = JSONObject.fromObject(str);
			JSONArray array = obj.getJSONArray("result");
			JSONArray jArray = new JSONArray();
			
			for(int i=0; i<array.size(); i++){
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jObject = new JSONObject();
				
				if(!"预留".equals(jsonObject.getString("brandName"))){
					jObject.put("show", jsonObject.getString("brandName"));
					jObject.put("value", jsonObject.getInt("sid"));
				}
				jArray.add(jObject);
			}
			coll.put("allBrands", jArray.toString());
		}
		
		return coll.get("allBrands");
	}
	
	@ResponseBody
	@RequestMapping(value="/getAllBrandsErp", method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllBrandsErp(HttpServletRequest request, HttpServletResponse response){
		
		if(coll.get("allBrandsErp") == null){
			Map<String, Object> map = new HashMap<String, Object>();
			String str = HttpUtil.HttpPostForMap(SystemConfig.SSD_SYSTEM_URL, "photo/brandList.html", map);
			JSONObject obj = JSONObject.fromObject(str);
			JSONArray array = obj.getJSONArray("result");
			JSONArray jArray = new JSONArray();
			
			for(int i=0; i<array.size(); i++){
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jObject = new JSONObject();
				
				if(!"预留".equals(jsonObject.getString("brandName"))){
					jObject.put("show", jsonObject.getString("brandName"));
					jObject.put("value", jsonObject.getString("brandSid"));
				}
				jArray.add(jObject);
			}
			coll.put("allBrandsErp", jArray.toString());
		}
		
		return coll.get("allBrandsErp");
	}
	
	/**
	 * <p>Title:getAllShopInfo</p>
	 * <p>Description:	得到所有门店信息</p>
	 */
	@ResponseBody
	@RequestMapping(value="/getAllShopInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllShopInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		
		if(coll.get("allShopInfo") == null){
			Map<String, Object> map = new HashMap<String, Object>();
			String str = HttpUtil.HttpPostForMap(SystemConfig.SSD_SYSTEM_URL, "photo/queryShopInfo.html", map);
			JSONObject obj = JSONObject.fromObject(str);
			JSONArray array = obj.getJSONArray("result");
			JSONArray jArray = new JSONArray();
			
			JSONObject jo = new JSONObject();
			jo.put("show", "公司总部");
			jo.put("value", "1");
			jArray.add(jo);
			
			for(int i=0; i<array.size(); i++){
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jObject = new JSONObject();
				
				jObject.put("show", jsonObject.getString("shopName"));
				jObject.put("value", jsonObject.getInt("sid"));
				jArray.add(jObject);
			}
			
			coll.put("allShopInfo", jArray.toString());
		}
		
		
		return coll.get("allShopInfo");
	}
	/**
	 * <p>Title:getSkuByBrandSid</p>
	 * <p>Description:	通过brandSid得到所有款号</p>
	 * @param brand
	 */
	@ResponseBody
	@RequestMapping(value="/getSkuByBrandSid", method = { RequestMethod.GET, RequestMethod.POST })
	public String getSkuByBrandSid(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		
		String brand = request.getParameter("brand");
		
		if(sku.get(brand) == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("brandSid", Integer.parseInt(brand));
			String str = HttpUtil.HttpPostForMap(SystemConfig.SSD_SYSTEM_URL, "photo/getSku.html", map);
			JSONObject obj = JSONObject.fromObject(str);
			JSONArray array = obj.getJSONArray("result");
			JSONArray jArray = new JSONArray();
			
			for(int i=0; i<array.size(); i++){
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jObject = new JSONObject();
				
				jObject.put("show", jsonObject.getString("productSku"));
				jObject.put("value", jsonObject.getString("productSku"));
				jArray.add(jObject);
			}
			sku.put(brand, jArray.toString());
		}
		
		return sku.get(brand);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAllSupplies", method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllSupplies(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		
		if(coll.get("allSupplies") == null){
			Map<String, Object> map = new HashMap<String, Object>();
			String str = HttpUtil.HttpPostForMap(SystemConfig.SSD_SYSTEM_URL, "photo/querySupplyInfo.html", map);
			JSONObject obj = JSONObject.fromObject(str);
			JSONArray array = obj.getJSONArray("result");
			JSONArray jArray = new JSONArray();
			
			for(int i=0; i<array.size(); i++){
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jObject = new JSONObject();
				
				jObject.put("show", jsonObject.getString("supplyName"));
				jObject.put("value", jsonObject.getString("sid"));
				jArray.add(jObject);
			}
			coll.put("allSupplies", jArray.toString());
		}
		
		return coll.get("allSupplies");
	}
	/**
	 * <p>Title:getProductClass</p>
	 * <p>Description:	得到商品的所有品类，用于在编辑窗口分级显示</p>
	 */
	@ResponseBody
	@RequestMapping(value="/getProductClass", method = { RequestMethod.GET, RequestMethod.POST })
	public String getProductClass(HttpServletRequest request, HttpServletResponse response){
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		JSONArray json = new JSONArray();
		
		String node = request.getParameter("node");
		String checked = request.getParameter("checked");
		String source = request.getParameter("source");
		
		if("0".equals(node)){
			node = "1";
		}
		if("1".equals(source)){
			if(coll.get("erpCategory") == null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("channelSid", source);
				String jsonClass = HttpUtil.HttpPostForMap(SystemConfig.SSD_SYSTEM_URL, "photo/queryCategorys.html", map);
				obj = JSONObject.fromObject(jsonClass);
				coll.put("erpCategory", obj.getJSONArray("result").toString());
			}
			array = JSONArray.fromObject(coll.get("erpCategory"));
		} else if("2".equals(source)){
			if(coll.get("category") == null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("channelSid", source);
				String jsonClass = HttpUtil.HttpPostForMap(SystemConfig.SSD_SYSTEM_URL, "photo/queryCategorys.html", map);
				obj = JSONObject.fromObject(jsonClass);
				coll.put("category", obj.getJSONArray("result").toString());
			}
			array = JSONArray.fromObject(coll.get("category"));
		}
		
		for(int i=0; i<array.size(); i++){
			JSONObject jObject = (JSONObject) array.get(i);
			JSONObject jObj = new JSONObject();
			if(jObject.has("parentSid")){
				if(jObject.getInt("parentSid") == Integer.parseInt(node)){
					jObj.put("id", jObject.getInt("categorySid"));
					jObj.put("text", jObject.getString("name"));
					jObj.put("categorySid", jObject.getInt("categorySid"));
					if(jObject.getInt("isParent") == 0){
						jObj.put("leaf", true);
						if(checked != null && checked.equals("true")){
							jObj.put("checked", false);
						}
					}
					json.add(jObj);
				}
			}
		}
		
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/getFirstLevelClass", method = { RequestMethod.GET, RequestMethod.POST })
	public String getFirstLevelClass(HttpServletRequest request, HttpServletResponse response){
		
		String source = request.getParameter("source");
		JSONArray result = new JSONArray();
		
		if(coll.get("erpFirstCategory") == null){
			coll.put("erpFirstCategory", getFirstCategory(source).toString());
		}
		
		return coll.get("erpFirstCategory");
	}
	
	public static JSONArray getFirstCategory(String channelSid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelSid", channelSid);
		String jsonClass = HttpUtil.HttpPostForMap(SystemConfig.SSD_SYSTEM_URL, "photo/queryCategorys.html", map);
		JSONObject obj = JSONObject.fromObject(jsonClass);
		JSONArray array = JSONArray.fromObject(obj.getJSONArray("result").toString());
		JSONArray result = new JSONArray();
		for(int i=0; i<array.size(); i++){
			JSONObject jObject = (JSONObject) array.get(i);
			JSONObject jObj = new JSONObject();
			if(jObject.has("parentSid")){
				if(jObject.getInt("parentSid") == 1){
					jObj.put("value", jObject.getInt("categorySid"));
					jObj.put("show", jObject.getString("name"));
					result.add(jObj);
				}
			}
		}
		return result;
	}
}

