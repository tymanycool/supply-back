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

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
import net.shopin.expense.service.IAreaChangeDayService;
import net.shopin.expense.util.DataUtil;
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
@RequestMapping(value="/changeday")
public class ExpenseChangeDayController {
	@Autowired
	private IAreaChangeDayService areaChangeDayService;
	
	private static JSONArray coll;
	
	@RequestMapping("/queryExpenseInfo")
	@ResponseBody
	public String queryExpenseInfo(Model model, HttpServletRequest request, HttpServletResponse response){

		String month = request.getParameter("month");
		JSONArray result = new JSONArray();
		if(month!=null && !"".equals(month)){
			month = month.substring(0, 7);
		}
		List<ExpenseAreaChangeDay> areaChanDays = areaChangeDayService.getChangeDaysObjByMonth(month);
		for(int i=0; i<areaChanDays.size(); i++){
			JSONObject obj  = new JSONObject();
			obj.put("sid", areaChanDays.get(i).getSid());
			obj.put("month", areaChanDays.get(i).getMonth());
			obj.put("changeDay", areaChanDays.get(i).getChangeDay());
			obj.put("memo", areaChanDays.get(i).getMemo());
			result.add(obj);
		}
		
		return result.toString();
	}
	
	@RequestMapping("/removeChangeDay")
	public void removeChangeDay(HttpServletRequest request, HttpServletResponse response){
		String parameters = (String) DataUtil.readRequest(request);
		String sid = JSONObject.fromObject(parameters).getString("sid");
		areaChangeDayService.removeChangeDay(Integer.parseInt(sid));
	}
	
	@RequestMapping("/updateChangeDay")
	public void updateChangeDay(HttpServletRequest request, HttpServletResponse response){
		String sid = request.getParameter("sid");
		String changDay = request.getParameter("changeDay");
		String memo = request.getParameter("memo");
		ExpenseAreaChangeDay day = new ExpenseAreaChangeDay();
		day.setSid(Integer.parseInt(sid));
		day.setChangeDay(changDay);
		day.setMemo(memo);
		areaChangeDayService.updateChangeDay(day);
	}
	
	@RequestMapping("/createChangeDay")
	public void createChangeDay(HttpServletRequest request, HttpServletResponse response){
		String date = request.getParameter("changeDay");
		String memo = request.getParameter("memo");
		String month = date.substring(0, 7);
		String changeDay = date.substring(8, 10);
		ExpenseAreaChangeDay day = new ExpenseAreaChangeDay();
		day.setMonth(month);
		day.setChangeDay(changeDay);
		day.setMemo(memo);
		areaChangeDayService.createChangeDay(day);
	}
	
	/*@RequestMapping("/exportExcelTable")
	public void exportExcelTable(HttpServletRequest request, HttpServletResponse response){
		String[] tableHeader = {"柜组编码","供应商编码","供应商名称","品牌","经营方式",
				"费用项目","使用台数","计费标准","起始日期","截止日期","使用天数","应收费用","合计费用","备注"};
		JSONArray array = new JSONArray();
		for(int i=0; i<coll.size(); i++){
			JSONObject obj = coll.getJSONObject(i);
			obj.put("0", obj.get("categoryName"));
			obj.put("1", obj.get("supplySid"));
			obj.put("2", obj.get("supplyName"));
			obj.put("3", obj.get("brand"));
			obj.put("4", obj.get("busPractice"));
			obj.put("5", obj.get("expenseSource"));
			obj.put("6", obj.get("padNum"));
			obj.put("7", obj.get("expensStandard"));
			obj.put("8", obj.get("startDate"));
			obj.put("9", obj.get("stopDate"));
			obj.put("10", obj.get("daysOfUsed"));
			obj.put("11", obj.get("predictExpense"));
			obj.put("12", obj.get("totalExpens"));
			obj.put("13", obj.get("memo"));
			array.add(obj);
		}
		ExcelUtil.createExcelTable(response, tableHeader, array);
	}*/
}