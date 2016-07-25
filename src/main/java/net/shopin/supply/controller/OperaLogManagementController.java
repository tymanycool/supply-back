/**
* 导购手动变价权限日志报表
* for demand
* feature https://tower.im/s/beCeH
* author qutengfei
*/
package net.shopin.supply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shopin.supply.domain.vo.OperaLogManagementVO;
import net.shopin.supply.service.IOperaLogManagementService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopin.core.util.ResultUtil;

/**
 * 操作日志管理
* @ClassName: OperationLogManagementController 
* @author qutengfei
* @date 2015-07-15 下午1:34
*
 */
@Controller
@RequestMapping("/operaLog")
public class OperaLogManagementController {
	
	@Autowired
	private IOperaLogManagementService operaLogManagementService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	

	/**
	 * 操作日志查询   	查询手动变价权限操作记录(带分页)
	* @Title: getLogForGuideOperate
	* @param @param request guideNoId导购编号，typeId操作类型，operatorIdId操作人ID,operatorId操作人姓名，operatTimeBId操作开始时间,operatTimeEId操作时间
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @author qutengfei
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getLogForGuideOperate",method = {RequestMethod.GET, RequestMethod.POST})
	public String getLogForGuideOperate(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
		
		Map<String,Object> map = new HashMap<String,Object>();
		String guideNoId = request.getParameter("guideNoId");
		String typeId = request.getParameter("typeId");
		String operatorIdId = request.getParameter("operatorIdId");
		String operatorId = request.getParameter("operatorId");
		String operatTimeBId = request.getParameter("operatTimeBId");
		String operatTimeEId = request.getParameter("operatTimeEId");

		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		
		if(null != guideNoId && !guideNoId.equals("") && !"null".equals(guideNoId)){
			map.put("guideNoId", guideNoId);
		}
		if(null != typeId && !typeId.equals("") && !typeId.equals("null") && !typeId.equals("-1")){
			map.put("typeId", typeId);
		}
		if(null != operatorIdId && !operatorIdId.equals("") && !operatorIdId.equals("null")){
			map.put("operatorIdId", operatorIdId);
		}
		if(null != operatorId && !operatorId.equals("")){
			map.put("operatorId", operatorId);
		}
		if(null != operatTimeBId && !operatTimeBId.equals("")){
			map.put("operatTimeBId", operatTimeBId);
		}
		if(null != operatTimeEId && !operatTimeEId.equals("")){
			map.put("operatTimeEId", operatTimeEId);
		}
		if(null != start && !start.equals("")){
			map.put("start", Integer.parseInt(start));
		}
		if(null != limit && !limit.equals("")){
			map.put("limit", Integer.parseInt(limit));
		}
		
		List<OperaLogManagementVO> guideinfo = this.operaLogManagementService.getLogForGuideOperate(map);
		int count = this.operaLogManagementService.getCountLogForGuideOperate(map);
		//获取description 解析时间
		for(OperaLogManagementVO oLmv:guideinfo){
			
			//操作类型为1(修改导购变价权限)时 截取时间字符串
			if(oLmv.getType()!=null&&oLmv.getType()==1){
				String description = oLmv.getDescription();
				try{
				JSONObject obj = JSONObject.fromObject(description);
				if(obj.containsKey("startTime")){
					oLmv.setStartTime(obj.getString("startTime"));
				}
				if(obj.containsKey("endTime")){
					oLmv.setEndTime(obj.getString("endTime"));
				}
				if(obj.containsKey("ifopen")){
					oLmv.setIfopen(obj.getString("ifopen"));
				}
				}catch(Exception e){
					continue;
				}
				
			}
			
		}
		map.put("list",guideinfo);
		json = ResultUtil.createSuccessResult(map);
		json = json.substring(0, json.length()-1)+",'total':'"+count+"'}";
		return json;
	}
}
