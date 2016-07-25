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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.expense.po.ExpenseAreaChangeDay;
import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.po.QueryPo;
import net.shopin.expense.po.ShopSupplyBrandPad;
import net.shopin.expense.service.IAreaChangeDayService;
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
@RequestMapping(value="/propstatis")
public class ExpensePropStatisController {
	@Autowired
	private ISupplyAreaService areaService;
	@Autowired
	private IShopSupplyBrandPadService shopSupplyBrandPadService;
	@Autowired
	private IAreaChangeDayService areaChangeDayService;
	
	private static JSONArray coll;
	private static JSONArray collSupply;
	
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Double sarea = 0.0;
				int expenseStatus = 0;
				int days = 0;
				for(int k=0; k<areaChanDays.size(); k++){
					String changeDay = areaChanDays.get(k);//面积改变日
					
					for(int j=0; j<detailList.size(); j++){
						String day = sdf.format(detailList.get(j).getCalDate()).substring(8, 10);
					
						if(day.equals(changeDay)){
							sarea = detailList.get(j).getCalArea();
							expenseStatus = this.getExpenseStatus(sarea);
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
					obj.put("expStatus"+no, expenseStatus);
				}
				double totalExpense = 0.0;
				for(int j=1; j<areaChanDays.size()+1; j++){
					if(obj.has("area"+j) && obj.has("days"+j) && obj.has("expStatus"+j)){
						totalExpense += obj.getInt("days"+j) * obj.getInt("expStatus"+j);
					}
				}
				obj.put("totalExpense", totalExpense);
				array.add(obj);
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
	
	@RequestMapping("/querySupplyExpenseInfo")
	@ResponseBody
	public String querySupplyExpenseInfo(HttpServletRequest request, HttpServletResponse response){

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
			JSONArray array = new JSONArray();
			//得到门店和供应商
			List<ExpenseSupplyArea> list = areaService.querySupplyLayerAreaInfo(query);
			//得到面积修改日
			List<String> areaChanDays = areaChangeDayService.getChangeDaysByMonth(month);
			for(int i=0; i<list.size(); i++){
				ExpenseSupplyArea area = list.get(i);
				//根据门店、供应商和月份得到对应修改日的面积总和
				List<ExpenseSupplyArea> supplyAreaList = this.getSupplyArea(area, month);
				//areaDetail只是为了设置shopSid、supplySid和supplyName
				ExpenseSupplyArea areaDetail = supplyAreaList.get(0);
				JSONObject obj = new JSONObject();
				obj.put("shopSid", areaDetail.getShopSid());
				obj.put("supplySid", area.getSupplySid());
				obj.put("supplyName", areaDetail.getSupplyName());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				for(int k=0; k<areaChanDays.size(); k++){
					int changeDay = Integer.parseInt(areaChanDays.get(k));//面积改变日
					int nowDay = Integer.parseInt(sdf.format(new Date()).substring(8, 10));
					
					String nowDate = sdf.format(new Date());
					String changeDate = month + "-" + areaChanDays.get(k);
					int no = k+1;
					if(nowDate.compareTo(changeDate) >= 0){
						int days = 0;
						
						obj.put("area"+no, supplyAreaList.get(k).getCalArea());
						
						if(month.equals(sdf.format(new Date()).substring(0, 7))){
							if((k < areaChanDays.size()-1) && (nowDay >= Integer.parseInt(areaChanDays.get(k+1)))){
								days = Integer.parseInt(areaChanDays.get(k+1))-changeDay;
							} else {
								days = nowDay - changeDay + 1;
							}
						} else {
							if(k < areaChanDays.size()-1){
								days = Integer.parseInt(areaChanDays.get(k+1))-changeDay;
							} else {
								Calendar time=Calendar.getInstance();
								time.clear();
								time.set(Calendar.YEAR,Integer.parseInt(month.substring(0, 4)));
								//year年
								time.set(Calendar.MONTH,Integer.parseInt(month.substring(5, 7))-1);
								//Calendar对象默认一月为0,month月
								int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数 
								days = day - changeDay + 1;
							}
						}
						obj.put("days"+no, days);
						obj.put("expStatus"+no, this.getExpenseStatus(supplyAreaList.get(k).getCalArea()));
					} else {
						obj.put("area"+no, 0);
						obj.put("days"+no, 0);
						obj.put("expStatus"+no, 0);
					}
				}
				double totalExpense = 0.0;
				for(int j=1; j<areaChanDays.size()+1; j++){
					totalExpense += obj.getInt("days"+j) * obj.getInt("expStatus"+j);
				}
				obj.put("totalExpense", totalExpense);
				if(totalExpense > 0.0){
					array.add(obj);
				}
			}
			collSupply = array;
		}
		JSONArray resultArr = new JSONArray();
		for(int i=start; i<(start+limit) && i<collSupply.size(); i++){
			resultArr.add(collSupply.get(i));
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("totalCount", collSupply.size());
		result.put("list", resultArr.toString());
		return result.toString();
	}
	/**
	 * <p>Title:getSupplyArea</p>
	 * <p>Description:	得到某门店和供应商在某月的所有面积修改日所有品牌的面积总和</p>
	 * @param  @param area 门店和供应商id
	 * @param  @param month 月份
	 * @param  @return
	 * @return List<ExpenseSupplyArea> 返回各个修改日的面积总和
	 */
	private List<ExpenseSupplyArea> getSupplyArea(ExpenseSupplyArea area, String month) {
		
		List<ExpenseSupplyArea> returnList = new ArrayList<ExpenseSupplyArea>();
		//得到面积修改日
		List<String> areaChanDays = areaChangeDayService.getChangeDaysByMonth(month);
		for(int i=0; i<areaChanDays.size(); i++){
			String changeDay = areaChanDays.get(i);//面积改变日
			String day = month + "-" + changeDay;
			List<ExpenseSupplyArea> list = areaService.getSupplyAreaForDay(area, day);
			
			Double totalArea = 0.0;
			for(int j=0; j<list.size(); j++){
				totalArea += list.get(j).getCalArea();
			}
			ExpenseSupplyArea retureArea = area;
			if(list.size() > 0){
				retureArea.setCalArea(totalArea);
			} else {
				retureArea.setCalArea(0.0);
			}
			returnList.add(retureArea);
		}
		
		return returnList;
		
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
			list.add("天数");
			list.add("新收费标准");
		}
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
				obj1.put((no++)+"", obj.get("days"+j));
				obj1.put((no++)+"", obj.get("expStatus"+j));
			}
			no = 4+areaChanDays.size()*3+1;
			obj1.put(no+"", obj.get("totalExpense"));
			array.add(obj1);
		}
		ExcelUtil.createExcelTableByList(response, list, array, "道具费用明细表");
	}
	
	@RequestMapping("/exportSupplyExcelTable")
	public void exportSupplyExcelTable(HttpServletRequest request, HttpServletResponse response){
		String month = request.getParameter("month");
		if(month!=null && !"".equals(month)){
			month = month.substring(0, 7);
		}
		List<String> areaChanDays = areaChangeDayService.getChangeDaysByMonth(month);
		
		List<String> list = new ArrayList<String>();
		list.add("门店ID");
		list.add("供应商编码");
		list.add("供应商名称");
		for(int i=0; i<areaChanDays.size(); i++){
			list.add("经营面积");
			list.add("天数");
			list.add("新收费标准");
		}
		list.add("新面积新收费标准");
		
		JSONArray array = new JSONArray();
		for(int i=0; i<collSupply.size(); i++){
			JSONObject obj = collSupply.getJSONObject(i);
			JSONObject obj1 = new JSONObject();
			obj1.put("0", obj.get("shopSid"));
			obj1.put("1", obj.get("supplySid"));
			obj1.put("2", obj.get("supplyName"));
			int no = 3;
			for(int j=1; j<areaChanDays.size()+1; j++){
				obj1.put((no++)+"", obj.get("area"+j));
				obj1.put((no++)+"", obj.get("days"+j));
				obj1.put((no++)+"", obj.get("expStatus"+j));
			}
			no = 2+areaChanDays.size()*3+1;
			obj1.put(no+"", obj.get("totalExpense"));
			array.add(obj1);
		}
		ExcelUtil.createExcelTableByList(response, list, array, "道具费用明细(供应商)");
	}
	
	private int getExpenseStatus(Double area){
		int result = 0;
		
		if((0.0 < area) && (area <= 9.0)) result = 10;
		else if((9.0 < area) && (area <= 19.0)) result = 15;
		else if((19.0 < area) && (area <= 29.0)) result = 20;
		else if((29.0 < area) && (area <= 49.0)) result = 25;
		else if((49.0 < area) && (area <= 89.0)) result = 30;
		else if(area > 89.0) result = 40;
		
		return result;
	}
}