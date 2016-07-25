package net.shopin.expense.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SAPConnUtil {
	
	private static Logger log = Logger.getLogger(SAPConnUtil.class); // 初始化日志对象
	

	private static final String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
	static{
		Properties connectProperties = new Properties();
		
		//测试地址
//		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "192.168.200.62");//服务器
//		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");        //系统编号
//		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "600");       //SAP集团
//		connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "ZBW_JCO");  //SAP用户名
//		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "abc123");     //密码
//		connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "zh");        //登录语言
//		connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");  //最大连接数  
//		connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");  //最大连接线程 
		
		//生产地址
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "192.168.200.75");//服务器
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");        //系统编号
		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "800");       //SAP集团
		connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "ZBW_JCO");  //SAP用户名
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "abc123");     //密码
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "zh");        //登录语言
		connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");  //最大连接数  
		connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");  //最大连接线程 
		
		
		
		
		createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);
	}
	
	/**
	 * 创建SAP接口属性文件。
	 * @param name	ABAP管道名称
	 * @param suffix	属性文件后缀
	 * @param properties	属性文件内容
	 */
	private static void createDataFile(String name, String suffix, Properties properties){
		File cfg = new File(name+"."+suffix);
		if(cfg.exists()){
			cfg.deleteOnExit();
		}
		try{
			FileOutputStream fos = new FileOutputStream(cfg, false);
			properties.store(fos, "for tests only !");
			fos.close();
		}catch (Exception e){
			log.error("Create Data file fault, error msg: " + e.toString());
			throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
		}
	}
	
	/**
	 * 获取SAP连接
	 * @return	SAP连接对象
	 */
	public static JCoDestination connect(){
		JCoDestination destination =null;
		try {
			destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
		} catch (JCoException e) {
			log.error("Connect SAP fault, error msg: " + e.toString());
		}
		return destination;
	}
	
	public static String singleMethod(String categorySid,String oldmatnr,
			String saiso,String saisj,String proColor,String brandSid,
			String productSku,String supplySid,String shopSid) throws Exception{
		JCoDestination destination= SAPConnUtil.connect();
		//采购信息记录
	    JCoFunction fun = destination.getRepository().getFunction("Z_RFC_INPUT_S_ARTICLE");
	    //System.out.println("single==="+fun);
		
	    JCoStructure structure = fun.getImportParameterList().getStructure("GS_BASIC_DATA");
	    //structure.setValue("ARTNR", "1000065");       //物料编号
	    structure.setValue("REFART", "1000047");      //参考商品
	    //structure.setValue("MTART", "Z001");        //物料类型
	    structure.setValue("MERCHCAT", categorySid);    //商品类目   ****
	    structure.setValue("BUOM", "EA");     //ISO 代码的计量基本单位 ****
	    structure.setValue("OLDMATNR", "Z1");      //旧商品编码  ****
	    structure.setValue("SAISO", saiso);           //季节类别 ****
	    structure.setValue("SAISJ", saisj);       //季度年 ****
	    structure.setValue("BWSCL", "A");           //货源  ****
	    structure.setValue("EXTMATLG", proColor);        //外部商品组   色码 ****
	    structure.setValue("BRAND", brandSid);      //商标  ****
	    structure.setValue("PGROUP", "P013");      //采购组
	    
	    fun.getImportParameterList().setValue("I_MODEL", productSku); //供应商物料号****
	    fun.getImportParameterList().setValue("I_VENDOR", supplySid); //供应商编号****
	    
	    JCoTable table1 = fun.getTableParameterList().getTable("TB_SALES_POS");
	    table1.appendRow();
	    table1.setValue("STORE", shopSid);       //零售地点
	    JCoTable table2 = fun.getTableParameterList().getTable("TB_LOGISTIC");
	    table2.appendRow();
	    table2.setValue("STORE", shopSid);        //地点
	    JCoTable table3 = fun.getTableParameterList().getTable("TB_DESCRIPTS");
	    table3.appendRow();
	    table3.setValue("TEXT1", brandSid+"-"+productSku+"-"+proColor);        //商品描述（短文本）
	    table3.setValue("LANGU1", "1");           //语言代码
	    
	    fun.execute(destination); 
	    
	    JCoTable jt = fun.getTableParameterList().getTable("LT_RETURN");
	    JCoParameterList jp = fun.getExportParameterList();
	    
	    //System.out.println("========="+fun.getExportParameterList().getValue("E_SUCCESS"));
	    //System.out.println("========="+fun.getExportParameterList().getValue("E_MATERIAL"));
	    //System.out.println(jt);
	    //System.out.println(jt.getString("MESSAGE"));
	    
	    String error = "";
	    while (jt.nextRow()) {
			if ("E".equals(jt.getString("TYPE"))) {
				error = jt.getString("MESSAGE");
				break;
			}
		}
	    
	    return jp.getValue("E_SUCCESS")+","+jp.getValue("E_MATERIAL")+","+error;
	}
	
	public static String multiMethod(String categorySid,String oldmatnr,
			String saiso,String saisj,String proColor,String brandSid,
			String productSku,String supplySid,String shopSid,String proStans) throws Exception{
		JCoDestination destination= SAPConnUtil.connect();
		//采购信息记录
	    JCoFunction fun = destination.getRepository().getFunction("Z_RFC_INPUT_G_ARTICLE");
	    System.out.println("productRelation==="+fun);
	    
	    fun.getImportParameterList().setValue("I_MODEL", productSku);
	    fun.getImportParameterList().setValue("I_VENDOR", supplySid);
	    
	    JCoStructure structure = fun.getImportParameterList().getStructure("GS_HEADER_GEN");
//	    structure.setValue("GENNO", "3008675446");
	    structure.setValue("REFART", "1000012");    
	    structure.setValue("MERCHCAT", categorySid);    //商品类目    *****
	    structure.setValue("MCPROF", "N1");
	    
	    String[] ss = proStans.split(",");
	    for(String proStan:ss){
	    
	    JCoTable table100 = fun.getTableParameterList().getTable("TB_VARIANT");
	    table100.appendRow();
	    
	    JCoTable table1 = fun.getTableParameterList().getTable("TB_VARIANT_DEF");
	    table1.appendRow();
	    //table1.setValue("VARNO", "3008675446");
	    table1.setValue("ACHARNAME1", brandSid);       //特征名称    ****
	    table1.setValue("ACHARVAL1", proStan);        //特性值  ****
	    
	    JCoTable table2 = fun.getTableParameterList().getTable("TB_BASIC_DATA");
	    table2.appendRow();
	    table2.setValue("OLDMATNR", oldmatnr);        //旧商品编码    色系 ****
	    table2.setValue("BUOM", "EA");           //ISO 代码的计量基本单位  ****
	    table2.setValue("SAISO", saiso);        //季节类别 ****
	    table2.setValue("SAISJ", saisj);       //季度年  ****
	    table2.setValue("EXTMATLGRP", proColor);          //外部商品组  色码****
	    table2.setValue("PBRAND", "B196");              //商标 ****"B196"
	    table2.setValue("ATTYP", "01"); 	 //****
	    table2.setValue("BWSCL", "A");       //****
	    table2.setValue("PGROUP", "P08");   //不用传
	    
	    
	    table2.appendRow();
	    table2.setValue("OLDMATNR", oldmatnr);        //旧商品编码    色系
	    table2.setValue("BUOM", "EA");           //ISO 代码的计量基本单位 
	    table2.setValue("SAISO", saiso);        //季节类别
	    table2.setValue("SAISJ", saisj);       //季度年
	    table2.setValue("EXTMATLGRP", proColor);          //外部商品组  色码
	    table2.setValue("PBRAND", "B196");              //商标 
	    table2.setValue("ATTYP", "02"); 
	    table2.setValue("BWSCL", "A"); 
	    table2.setValue("PGROUP", "P08"); 
	    
	    //商品类别
	    JCoTable table3 = fun.getTableParameterList().getTable("TB_SALES_POS");
	    table3.appendRow();
	    table3.setValue("STORE", shopSid);       //零售地点
	    JCoTable table4 = fun.getTableParameterList().getTable("TB_LOGISTIC");
	    table4.appendRow();
	    table4.setValue("STORE", shopSid);       //地点
	    JCoTable table5 = fun.getTableParameterList().getTable("TB_DESCRIPTS");
	    table5.appendRow();
	    table5.setValue("TEXT1", productSku+proColor+","+proStan);        //特性值
	    table5.setValue("LANGU1", "1");      //语言代码
	    }
	    fun.execute(destination); 
	    
	    JCoParameterList jp = fun.getExportParameterList();
	    System.out.println("========="+fun.getExportParameterList().getValue("E_SUCCESS"));
	    System.out.println("========="+fun.getExportParameterList().getValue("E_MATERIAL"));

	    
	    JCoTable jt = fun.getTableParameterList().getTable("LT_RETURN");
	    //System.out.println(jt);
	    System.out.println("row=="+jt.getNumRows());
	    int k = 1;
	    while (jt.nextRow()) {
			System.out.println(jt.getString("MESSAGE")+"==="+k++);
		}
	    System.out.println(jt.getString("MESSAGE"));
	    
	    return jp.getValue("E_SUCCESS")+","+jp.getValue("E_MATERIAL");
	}

	
	
	public static void toSap() throws Exception{
		JCoDestination destination= SAPConnUtil.connect();
		//单一商品
	    JCoFunction fun = destination.getRepository().getFunction("Z_RFC_INPUT_ARTICLE");
	    
	    System.out.println("PRODUCT==="+fun);
	    
	    fun.getImportParameterList().setValue("I_VENDOR", "100001");     //供应商或债权人的帐号
	    fun.getImportParameterList().setValue("I_BRAND_ID", "abcd");    //品牌号
	    
	    JCoStructure codes1 = fun.getImportParameterList().getStructure("GS_ART_MASTER");
	    
	    //codes1.setValue("GENNO", "1000795");     //一般物料号。用于已经存在的一般商品加尺码
	    codes1.setValue("OLD_ART", "Z1");  //旧商品编码
	    codes1.setValue("MODEL", "single4");   //供应商使用的商品编码
	    codes1.setValue("UNIT", "EA");  //基本计量单位
	    codes1.setValue("MCPROF", "N1");  //配置参数文件
	    codes1.setValue("COLOR", "C30");   //颜色
	    codes1.setValue("COL_GROUP", "Z5");   //颜色类型组
	    codes1.setValue("MAT_GROUP", "J020604");   //商品类目
	    codes1.setValue("MAT_CAT", "01");     //商品类别(00:单一商品       01:一般商品)
	    codes1.setValue("SEASON", "FP02");     //季节标识
	    codes1.setValue("YEAR", "2013");     //财年
	    codes1.setValue("DESCRIPTION", "BattutaGioiello-31103220");   //商品描述（短文本）
	    codes1.setValue("SALE_TYPE", "A");     //货源
	    
	    JCoTable table = fun.getTableParameterList().getTable("TB_ART_VARIANT");
	    table.appendRow();//增加一行  
	    
	    table.setValue("ACHARNAME", "N1-1");     //特征名称
	    table.setValue("ACHARVAL", "35(225)");      //特性值
	    table.setValue("DESCRIPTION", "BattutaGioiello-31103220");            //商品描述（短文本）---变式商品
	    
	    table.appendRow();//增加一行  
	    
	    table.setValue("ACHARNAME", "N1-1");     //特征名称
	    table.setValue("ACHARVAL", "37(235)");      //特性值
	    table.setValue("DESCRIPTION", "BattutaGioiello-31103220");            //商品描述（短文本）---变式商品
	    
	    fun.execute(destination); 
	    
	    Object obj1 = fun.getExportParameterList().getValue("E_SUCCESS");      //输出('X'表示全部成功)
	    Object obj2 = fun.getExportParameterList().getValue("E_MATERIAL");     //输出(单一物料号或者一般物料号)
	    System.out.println(obj1+"\t"+obj2);
	    if ("X".equals(obj1)) {
	    	JCoTable jt1 = fun.getTableParameterList().getTable("TB_MATERIAL");    //输出(变式物料号和描述)
	 	    System.out.println("000"+jt1);
//	 	    while (condition) {
//	 			
//	 		}
	 	    System.out.println(jt1.getString("MATNR"));              //物料编号
	 	    System.out.println(jt1.getString("SIZE"));
		}
	    
	   
	    
	    JCoTable jt = fun.getTableParameterList().getTable("RETURN");
	    System.out.println(jt);
	    
	}
	
	public static void main(String[] args) throws Exception{
		//String result = singleMethod("abcdedgh","Z1","FP00","2013","C33","B001","COMONI-005","100001","1001");
		//System.out.println("=="+result);
		//multiMethod("J020604","Z5","FP02","2013","C30","N1-1","multi-003","100001","1001","35(225),36(230),37(235)");
		toSap();	
		
	}
}
