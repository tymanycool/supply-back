/**
 * ExpensePadStatisController.java
 * net.shopin.expense.controller
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月23日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.QueryPo;
import net.shopin.expense.po.ShopSupplyBrandPad;
import net.shopin.expense.service.IShopSupplyBrandPadService;
import net.shopin.expense.service.ISupplyAreaService;
import net.shopin.expense.util.ExcelUtil;

/**
 * <p>ClassName:ExpensePadStatisController</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月23日上午10:50:08
 */
@Controller
@RequestMapping(value="/padstatis")
public class ExpensePadStatisController {
	@Autowired
	private ISupplyAreaService areaService;
	@Autowired
	private IShopSupplyBrandPadService shopSupplyBrandPadService;
	
	private static JSONArray coll;
	
	@RequestMapping("/queryExpenseInfo")
	@ResponseBody
	public String queryExpenseInfo(HttpServletRequest request, HttpServletResponse response){

		QueryPo query = new QueryPo();
		query.setShopSid(request.getParameter("shopId"));
		query.setBrandSid(request.getParameter("brand"));
		query.setSupplySid(request.getParameter("supplyId"));
		if(request.getParameter("purchaseId")!=null && !"".equals(request.getParameter("purchaseId"))){
			query.setPurchaseSid(Integer.parseInt(request.getParameter("purchaseId")));
		}
		String month = request.getParameter("month");
		if(month!=null && !"".equals(month)){
			month = month.substring(0, 7);
			query.setMonth(month);
		}
		int limit = Integer.parseInt(request.getParameter("limit"));
		int start = Integer.parseInt(request.getParameter("start"));
		
		if(start == 0){
			JSONArray array = new JSONArray();
			//这里得到的是门店、供应商、品牌，因为所有的统计都是以这三个为基础的
			List<ExpenseSupplyArea> list = areaService.querySupplyAreaInfo(query);
			for(int i=0; i<list.size(); i++){
				ExpenseSupplyArea area = list.get(i);
				//得到同时属于该门店、供应商、品牌的所有记录
				List<ExpenseSupplyArea> detailList = areaService.querySupplyAreaDetail(area, month);
				ExpenseSupplyArea areaDetail = detailList.get(0);
				JSONObject obj = new JSONObject();
				obj.put("shopSid", areaDetail.getShopSid());
				obj.put("categoryName", areaDetail.getCategoryName());
				String supplySid = area.getSupplySid();
				obj.put("supplySid", supplySid);
				obj.put("supplyName", areaDetail.getSupplyName());
				obj.put("brand", areaDetail.getBrandName());
				obj.put("brandSid", areaDetail.getBrandSid());
				char c = supplySid.charAt(0);
				if(c == '1'){
					obj.put("busPractice", "联营");
				} else if(c == '8'){
					obj.put("busPractice", "寄售");
				} else if(c == '9'){
					obj.put("busPractice", "自营");
				} else if(c == '5'){
					obj.put("busPractice", "租赁");
				}
				obj.put("expenseSource", "PAD使用费");
				//PAD的使用数量可以调节，所以需要动态读取PAD的使用数量
				ShopSupplyBrandPad pad = shopSupplyBrandPadService.getPadNums(area);
				int padNum = 1;
				if(pad != null){
					padNum = pad.getPadNums();
				}
				obj.put("padNum", padNum);
				obj.put("expensStandard", 10);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				int startIndex = 0;
				int endIndex = 0;
				int flag = 0;
				for(int j=0; j<detailList.size()-1; j++){
					String startDay = sdf.format(detailList.get(j).getCalDate()).substring(8, 10);
					String stopDay = sdf.format(detailList.get(j+1).getCalDate()).substring(8, 10);
					
					if((Integer.parseInt(stopDay)-Integer.parseInt(startDay)) == 1){
						continue;
					} else {
						endIndex = j;
						int days = endIndex-startIndex+1;
						obj.put("daysOfUsed", days);
						obj.put("startDate", sdf.format(detailList.get(startIndex).getCalDate()));
						obj.put("stopDate", sdf.format(detailList.get(endIndex).getCalDate()));
						obj.put("predictExpense", padNum*10*days);
						obj.put("totalExpens", padNum*10*days);
						obj.put("memo", "");
						array.add(obj);
						startIndex = j+1;
						flag = 1;
					}
				}
				if(flag == 0){
					obj.put("daysOfUsed", detailList.size());
					obj.put("startDate", sdf.format(detailList.get(0).getCalDate()));
					obj.put("stopDate", sdf.format(detailList.get(detailList.size()-1).getCalDate()));
					obj.put("predictExpense", padNum*10*detailList.size());
					obj.put("totalExpens", padNum*10*detailList.size());
					obj.put("memo", "");
					array.add(obj);
				} else {
					int days = detailList.size()-startIndex;
					obj.put("daysOfUsed", days);
					obj.put("startDate", sdf.format(detailList.get(startIndex).getCalDate()));
					obj.put("stopDate", sdf.format(detailList.get(detailList.size()-1).getCalDate()));
					obj.put("predictExpense", padNum*10*days);
					obj.put("totalExpens", padNum*10*days);
					obj.put("memo", "");
					array.add(obj);
				}
			}
			coll = array;
		}
		JSONArray resultArr = new JSONArray();
		for(int i=start; i<(start+limit) && i<coll.size(); i++){
			resultArr.add(coll.get(i));
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("totalCount", coll.size());
		result.put("list", resultArr.toString());
		return result.toString();
	}
	
	@RequestMapping("/exportExcelTable")
	public void exportExcelTable(HttpServletRequest request, HttpServletResponse response){
		String[] tableHeader = {"门店ID", "柜组编码","供应商编码","供应商名称","品牌","经营方式",
				"费用项目","使用台数","计费标准","起始日期","截止日期","使用天数","应收费用","合计费用","备注"};
		JSONArray array = new JSONArray();
		for(int i=0; i<coll.size(); i++){
			JSONObject obj = coll.getJSONObject(i);
			JSONObject obj1 = new JSONObject();
			obj1.put("0", obj.get("shopSid"));
			obj1.put("1", obj.get("categoryName"));
			obj1.put("2", obj.get("supplySid"));
			obj1.put("3", obj.get("supplyName"));
			obj1.put("4", obj.get("brand"));
			obj1.put("5", obj.get("busPractice"));
			obj1.put("6", obj.get("expenseSource"));
			obj1.put("7", obj.get("padNum"));
			obj1.put("8", obj.get("expensStandard"));
			obj1.put("9", obj.get("startDate"));
			obj1.put("10", obj.get("stopDate"));
			obj1.put("11", obj.get("daysOfUsed"));
			obj1.put("12", obj.get("predictExpense"));
			obj1.put("13", obj.get("totalExpens"));
			obj1.put("14", obj.get("memo"));
			array.add(obj1);
		}
		ExcelUtil.createExcelTable(response, tableHeader, array, "PAD费用明细表");
	}
	
	@RequestMapping("/updateExpenseInfo")
	@ResponseBody
	public String updateExpenseInfo(HttpServletRequest request, HttpServletResponse response){
		
		ShopSupplyBrandPad pad = new ShopSupplyBrandPad();
		pad.setBrandSid(request.getParameter("brandSid"));
		pad.setShopSid(Integer.parseInt(request.getParameter("shopSid")));
		pad.setSupplySid(Integer.parseInt(request.getParameter("supplySid")));
		pad.setPadNums(Integer.parseInt(request.getParameter("padNum")));
		
		JSONObject result = new JSONObject();
		if(shopSupplyBrandPadService.has(pad)){
			shopSupplyBrandPadService.update(pad);
		} else {
			shopSupplyBrandPadService.insert(pad);
		}
		
		return result.toString();
	}
}

