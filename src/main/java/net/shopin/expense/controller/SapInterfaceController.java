/**
 * SapInterfaceController.java
 * net.shopin.expense.controller
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月21日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

import net.shopin.expense.po.ExpenseSupplyArea;
import net.shopin.expense.service.ISupplyAreaService;
import net.shopin.expense.util.HttpUtil;
import net.shopin.expense.util.SAPConnUtil;

/**
 * <p>ClassName:SapInterfaceController</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月21日上午10:22:13
 */
@Controller
@RequestMapping("/sap")
public class SapInterfaceController {
	
	@Autowired
	private ISupplyAreaService supplyAreaService;
	@RequestMapping("/test")
	public void syncAreaInfo(){
		System.out.println("hello");
	    JCoFunction fun = null;
	    //连接sap
	    JCoDestination destination = SAPConnUtil.connect();
	    try {
	    	Calendar cal = Calendar.getInstance();
			String year = cal.get(Calendar.YEAR) + "";
			String month = cal.get(Calendar.MONTH) + 1 + "";
		    if(Integer.parseInt(month)<10){
		    	month = "0" + month;
		    }
		    fun = destination.getRepository().getFunction("ZBW_ITF_AREAS");
		    String days=cal.getActualMaximum(Calendar.DATE) + "";
	    	fun.getImportParameterList().setValue("ZSTR_DATE", year + month + "01");
			fun.getImportParameterList().setValue("ZEND_DATE", year + month + days);
//	    	fun.getImportParameterList().setValue("ZSTR_DATE", "20160701");
//			fun.getImportParameterList().setValue("ZEND_DATE", "20160731");
			fun.execute(destination);
			JCoTable tb = fun.getTableParameterList().getTable("ZOUT_TAB");//返回数据表
			JCoTable tb1 = fun.getTableParameterList().getTable("EX_RETURN");//返回状态表
			tb1.setRow(0);
			if(tb1.getString("ZSTATUS").equals("Success")){
				ExpenseSupplyArea supplyArea = new ExpenseSupplyArea();
				int rows = tb.getNumRows();
				for(int i=0; i<rows; i++){
					tb.setRow(i);
					supplyArea.setShopSid(tb.getString("PLANT"));
					supplyArea.setCategorySid(tb.getString("CM_CDT1"));
					supplyArea.setCategoryName(tb.getString("ZTXTSH"));
					String supplySid = tb.getString("VENDOR");
					for(int j=0; j<supplySid.length(); j++){
						Character c = supplySid.charAt(j);
						if(c != '0'){
							supplySid = supplySid.substring(j);
							break;
						}
					}
					supplyArea.setSupplySid(supplySid);
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					supplyArea.setCalDate(sdf1.parse(tb.getString("CALDAY")));
					supplyArea.setBrandSid(tb.getString("RF_BNDID"));
					supplyArea.setCalArea(Double.parseDouble(tb.getString("RP_STOARE")));
					supplyArea.setSupplyName(tb.getString("TXTMD"));
					supplyArea.setBrandName(tb.getString("TXTSH"));
					if(!supplyAreaService.isExits(supplyArea)){
						supplyAreaService.insert(supplyArea);
						System.out.println(supplyArea.getCalDate());
					}
				}
				System.out.println("final");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		String url = "http://172.16.200.68:8082/supply/";
//		String url = "http://192.168.200.118:8082/supply/";
//		String url = "http://localhost/supply_core";
		HttpUtil.HttpPostForlogistics(url, "sap/test.json", "");
		System.out.println("hello");
		
//		SapInterfaceController sap = new SapInterfaceController();
//		sap.syncAreaInfo();
	}
}

