/**
 * TestSapController.java
 * test
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月18日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package test;

import org.junit.Test;

import com.sap.conn.jco.*;

import net.shopin.expense.util.SAPConnUtil;

/**
 * <p>ClassName:TestSapController</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月18日下午3:38:43
 */
public class TestSapController {
	@Test
	public void testSapInterfaceCall(){
	    JCoFunction fun = null;  
	    //连接sap
	    JCoDestination destination = SAPConnUtil.connect(); 
	    
	    try {
	    	fun = destination.getRepository().getFunction("ZBW_ITF_AREAS");
			fun.getImportParameterList().setValue("ZSTR_DATE", "20140101");
			fun.getImportParameterList().setValue("ZEND_DATE", "20151222");
			fun.execute(destination);
			JCoTable tb = fun.getTableParameterList().getTable("ZOUT_TAB");
			int rows = tb.getNumRows();
			int columns = tb.getNumColumns();
			System.out.println("行数："+rows+", 列数"+columns);
			for(int i=0; i<rows; i++){
				tb.setRow(i);
				for(int j=0; j<columns; j++){
					String record = tb.getString(j);
					System.out.println(record);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

