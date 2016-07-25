/**
 * @Probject Name: supply_core
 * @Path: net.shopin.supply.serviceISendEmailService.java
 * @Create By Administrator
 * @Create In 2015年6月17日 下午5:39:29
 * TODO
 */
package net.shopin.supply.service;

import java.util.Map;


/**
 * @Class Name ISendEmailService
 * @Author Administrator
 * @Create In 2015年6月17日
 */

public interface ISendEmailService {
	
	public String sendEmail(Map<String, String> paramMap)throws Exception;

}
