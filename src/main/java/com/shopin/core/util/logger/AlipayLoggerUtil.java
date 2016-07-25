/**
 * 说 明     :
 * author: 陆湘星
 * data  : 2012-2-1
 * email : xiangxingchina@163.com
 **/
package com.shopin.core.util.logger;

import java.util.Date;

public class AlipayLoggerUtil {
	/**
	 * 打印阿里的异常信息 
	 * @param method 		方法
	 * @param condMessage	条件信息
	 * @param msg			异常描述
	 * @param e				异常对象
	 */
	public static void PrintAlipayException(String method,String condMessage,String msg,Exception e){
		LoggerUtil logger = LoggerUtil.getLogger(" method = " +method);
		logger.setCondMessage(condMessage);
		logger.printAlipayException(msg, e);
	}
	/**
	 * 打印阿里的交易信息 
	 * @param method 		方法
	 * @param condMessage	条件信息
	 * @param resultJson	返回结果	
	 * @param start			开始时间
	 */
	public static void PrintAlipayResultInfo(String method,String condMessage,String resultJson,Date start){
		LoggerUtil logger = LoggerUtil.getLogger(" method = " +method);
		logger.setCondMessage(condMessage);
		logger.printAlipayResultInfo(resultJson, start);
	}
	
	public static void main(String[] args) {
		String method = "testPrintAlipayException";
		String condMessage = "a=b&t=2";
		//--测试异常
		String msg = "测试异常";
		Exception e = new Exception("测试异常");
		AlipayLoggerUtil.PrintAlipayException(method, condMessage, msg, e);
		
		//--测试打印交易信息
		String resultJson="{\"code\":\"SUCCESS\",\"codeInfo\":\"操作成功!\",\"data\":{\"currentPage\":1,\"endRecords\":0,\"list\":[{\"brandName\":\"COTTON REPUBLIC\",\"commanyName\":\"优联捷（北京）贸易有限公司\",\"inStockSum\":2,\"netRefunds\":23,\"netSales\":160,\"netSalesRefunds\":137,\"offlineSales\":0,\"outStockSum\":4,\"proClass\":\"内衣区\",\"saleCode\":\"902565\",\"salesProNum\":24,\"salesSum\":907,\"salesTime\":\"2012-01-29 00:00:00.0\",\"shopRefunds\":0,\"shopSales\":770,\"shopSalesRefunds\":770,\"stockSum\":1943,\"transferStockSum\":0}],\"pageSize\":10,\"startRecords\":0,\"totalPages\":1,\"totalRecords\":1}}";
		Date start = new Date();
		AlipayLoggerUtil.PrintAlipayResultInfo(method, condMessage, resultJson, start);
	}
	
}
