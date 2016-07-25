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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import net.shopin.expense.po.ShopSupplyBrandChargestatus;
import net.shopin.expense.po.ShopSupplyBrandPad;
import net.shopin.expense.service.IAreaChangeDayService;
import net.shopin.expense.service.IShopSupplyBrandStatusService;
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
@RequestMapping(value="/genaralstatis")
public class ExpenseGeneralStatisController {
	@Autowired
	private ISupplyAreaService areaService;
	@Autowired
	private IAreaChangeDayService areaChangeDayService;
	@Autowired
	private IShopSupplyBrandStatusService statusService;
	
	private static JSONArray coll;
	private static JSONArray supplyColl = new JSONArray();
	
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
			this.getBrandExpenseInfo(query);
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
	
	@RequestMapping("/queryExpenseSupplyInfo")
	@ResponseBody
	public String queryExpenseSupplyInfo(HttpServletRequest request, HttpServletResponse response){

		QueryPo query = new QueryPo();
		query.setShopSid(request.getParameter("shopId"));
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
			this.getBrandExpenseInfo(query);
		
			Integer shopSid = coll.getJSONObject(0).getInt("shopSid");
			Integer supplySid = coll.getJSONObject(0).getInt("supplySid");
			for(int i=1; i<coll.size(); i++){
				JSONObject obj = (JSONObject) coll.get(i);
				if(obj.getInt("shopSid")==shopSid && obj.getInt("supplySid")==supplySid){
					obj.put("totalExpense", obj.getDouble("totalExpense") + coll.getJSONObject(i-1).getDouble("totalExpense"));
					if((i == coll.size()-1) && (obj.getDouble("totalExpense")>0.0)){
						supplyColl.add(obj);
					}
				} else {
					if(coll.getJSONObject(i-1).getDouble("totalExpense") > 0.0){
						supplyColl.add(coll.getJSONObject(i-1));
					}
					if((i == coll.size()-1) && (coll.getJSONObject(i).getDouble("totalExpense")>0.0)){
						supplyColl.add(coll.getJSONObject(i));
					} else {
						shopSid = ((JSONObject)coll.get(i)).getInt("shopSid");
						supplySid = ((JSONObject)coll.get(i)).getInt("supplySid");
					}
				}
			}
		}
		
		JSONArray resultArr = new JSONArray();
		for(int i=start; i<(start+limit) && i<supplyColl.size(); i++){
			resultArr.add(supplyColl.get(i));
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("totalCount", supplyColl.size());
		result.put("list", resultArr.toString());
		return result.toString();
	}
	
	private void getBrandExpenseInfo(QueryPo query){
		//得到月份
		String month = query.getMonth();
		
		JSONArray array = new JSONArray();
		List<ExpenseSupplyArea> list = areaService.querySupplyAreaInfo(query);
		//得到面积修改日
		List<String> areaChanDays = areaChangeDayService.getChangeDaysByMonth(month);
		for(int i=0; i<list.size(); i++){
			ExpenseSupplyArea area = list.get(i);
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
			ShopSupplyBrandChargestatus status = statusService.getStatus(area);
			double expenseStatus = 1.5;
			if(status != null){
				expenseStatus = status.getExpenseStatus();
			}
			obj.put("newExpenseStatus", expenseStatus);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Double sarea = 0.0;
			Double expenseArea = 0.0;
			int days = 0;
			for(int k=0; k<areaChanDays.size(); k++){
				String changeDay = areaChanDays.get(k);//面积改变日
				
				for(int j=0; j<detailList.size(); j++){
					String day = sdf.format(detailList.get(j).getCalDate()).substring(8, 10);
				
					if(day.equals(changeDay)){
						sarea = detailList.get(j).getCalArea();
						expenseArea = sarea * 2.2;
						BigDecimal b = new BigDecimal(expenseArea);  
						expenseArea = b.setScale(2, RoundingMode.HALF_UP).doubleValue(); 
						if(k != areaChanDays.size()-1){
							days = Integer.parseInt(areaChanDays.get(k+1))-Integer.parseInt(changeDay);
						} else {
							days = detailList.size()-j;
						}
						break;
					}
				}
				int no = k+1;
				obj.put("area"+no, sarea);
				obj.put("days"+no, days);
				obj.put("expenseArea"+no, expenseArea);
			}
			double totalExpense = 0.0;
			for(int j=1; j<areaChanDays.size()+1; j++){
				if(obj.has("area"+j) && obj.has("days"+j) && obj.has("expenseArea"+j)){
					totalExpense += obj.getInt("days"+j) * obj.getDouble("expenseArea"+j);
				}
			}
			totalExpense = totalExpense * expenseStatus;
			BigDecimal b = new BigDecimal(totalExpense);  
			totalExpense = b.setScale(0, RoundingMode.HALF_UP).doubleValue(); 
			obj.put("totalExpense", totalExpense);
			array.add(obj);
		}
		coll = array;
	}
	
	@ResponseBody
	@RequestMapping("/changeDay")
	public String changeDay(HttpServletRequest request, HttpServletResponse response){
		String month = request.getParameter("month");
		if(month!=null && !"".equals(month)){
			month = month.substring(0, 7);
		}
		List<String> areaChanDays = areaChangeDayService.getChangeDaysByMonth(month);
		JSONArray array = new JSONArray();
		for(int i=0; i<areaChanDays.size(); i++){
			array.add(areaChanDays.get(i));
		}
		return array.toString();
	}
	
	@RequestMapping("/exportExcelTable")
	public void exportExcelTable(HttpServletRequest request, HttpServletResponse response){
		String month = request.getParameter("month");
		if(month!=null && !"".equals(month)){
			month = month.substring(0, 7);
		}
		List<String> areaChanDays = areaChangeDayService.getChangeDaysByMonth(month);
		
		List<String> list = new ArrayList<String>();
		list.add("门店ID");
		list.add("品类");
		list.add("供应商编码");
		list.add("供应商名称");
		list.add("品牌");
		for(int i=0; i<areaChanDays.size(); i++){
			list.add("经营面积");
			list.add("收费面积");
			list.add("天数");
		}
		list.add("新收费标准");
		list.add("新面积新收费标准");
		
		JSONArray array = new JSONArray();
		for(int i=0; i<coll.size(); i++){
			JSONObject obj = coll.getJSONObject(i);
			JSONObject obj1 = new JSONObject();
			obj1.put("0", obj.get("shopSid"));
			obj1.put("1", obj.get("categoryName"));
			obj1.put("2", obj.get("supplySid"));
			obj1.put("3", obj.get("supplyName"));
			obj1.put("4", obj.get("brand"));
			int no = 5;
			for(int j=1; j<areaChanDays.size()+1; j++){
				obj1.put((no++)+"", obj.get("area"+j));
				obj1.put((no++)+"", obj.get("expenseArea"+j));
				obj1.put((no++)+"", obj.get("days"+j));
			}
			no = 4+areaChanDays.size()*3+1;
			obj1.put(no+"", obj.get("newExpenseStatus"));
			obj1.put((++no)+"", obj.get("totalExpense"));
			array.add(obj1);
		}
		ExcelUtil.createExcelTableByList(response, list, array, "综合费用费用明细表");
	}
	
	@RequestMapping("/exportSupplyExcelTable")
	public void exportSupplyExcelTable(HttpServletRequest request, HttpServletResponse response){
		String month = request.getParameter("month");
		if(month!=null && !"".equals(month)){
			month = month.substring(0, 7);
		}
		
		List<String> list = new ArrayList<String>();
		list.add("门店ID");
		list.add("供应商编码");
		list.add("供应商名称");
		list.add("新面积新收费标准");
		
		JSONArray array = new JSONArray();
		for(int i=0; i<supplyColl.size(); i++){
			JSONObject obj = supplyColl.getJSONObject(i);
			JSONObject obj1 = new JSONObject();
			obj1.put("0", obj.get("shopSid"));
			obj1.put("1", obj.get("supplySid"));
			obj1.put("2", obj.get("supplyName"));
			obj1.put("3", obj.get("totalExpense"));
			array.add(obj1);
		}
		ExcelUtil.createExcelTableByList(response, list, array, "综合费用费用明细表（供应商）");
	}
	
	@RequestMapping("/updateExpenseStatus")
	@ResponseBody
	public String updateExpenseStatus(HttpServletRequest request, HttpServletResponse response){
		
		ShopSupplyBrandChargestatus status = new ShopSupplyBrandChargestatus();
		status.setBrandSid(request.getParameter("brandSid"));
		status.setShopSid(Integer.parseInt(request.getParameter("shopSid")));
		status.setSupplySid(Integer.parseInt(request.getParameter("supplySid")));
		status.setExpenseStatus(Double.parseDouble(request.getParameter("newExpenseStatus")));
		
		JSONObject result = new JSONObject();
		if(statusService.has(status)){
			statusService.update(status);
		} else {
			statusService.insert(status);
		}
		
		return result.toString();
	}
}