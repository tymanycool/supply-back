/**   
* @Title: SystemUserController.java 
* @Package net.shopin.supply.controller 
* @author zhangq   
* @date 2015-1-23 上午11:57:09 
* @version V1.0   
*/
package net.shopin.supply.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.Guideinfo;
import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.SystemUser;
import net.shopin.supply.service.ISystemUserService;
import net.shopin.supply.util.MD5Util;
import net.shopin.supply.util.ResultUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * @ClassName: SystemUserController 
 * @Description: TODO(系统用户controller) 
 * @author zhangq
 * @date 2015-1-23 上午11:57:09 
 *  
 */

@Controller
@RequestMapping("/systemUser")
public class SystemUserController {
	
	@Autowired
	private ISystemUserService systemUserService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 获取用户信息列表
	* @Title: selectUserInforByParam 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSystemUserByParams", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String selectUserInforByParam(HttpServletRequest request,
			HttpServletResponse response) {
		String json = "";
		
		String userCode = request.getParameter("userCode");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(null != userCode && !userCode.equals("")){
			map.put("userCode", userCode);
		}
		if(null != start && !start.equals("")){
			map.put("start", Integer.parseInt(start));
		}
		if(null != limit && !limit.equals("")){
			map.put("limit", Integer.parseInt(limit));
		}
		
		List<SystemUser> list = this.systemUserService.getAllSystemUser(map);
		int count = this.systemUserService.getCountByParam(map);
		json = ResultUtil.createSuccessResult(list);
		json = json.substring(0, json.length()-1)+",'total':'"+count+"'}";
		return json;
	}
	
	/**
	 * 保存用户信息
	* @Title: insertMemberUser 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSystemUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String insertMemberUser(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		String json = "";
		
		try{
			String sid = request.getParameter("sid");
			String userCode = request.getParameter("userCode");
			String username = request.getParameter("username");
			String userPssword = request.getParameter("userPssword");
			String shopName = request.getParameter("shopName");
			String shopSid = request.getParameter("shopSid");
			String sex = request.getParameter("sex");
			String validBit = request.getParameter("validBit");
			//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 start
			String operator = (String)session.getAttribute("username");
			//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 end
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userCode", userCode);
			SystemUser systemUserObj = this.systemUserService.checkIsUnique(map);
			SystemUser systemUser = new SystemUser();
			if (sid != null && !"".equals(sid)) {
				systemUser.setSid(Integer.parseInt(sid));
				systemUser.setValidBit(Integer.parseInt(validBit));
				if(null != systemUserObj){
					if(systemUserObj.getSid() != Integer.parseInt(sid)){
						return ResultUtil.createFailureResult("10000", "此登录名已存在，请重新输入！");
					}
				}
			}else{
				systemUser.setValidBit(1);
				if(null != systemUserObj){
					return ResultUtil.createFailureResult("10000", "此登录名已存在，请重新输入！");
				}
			}
			
			systemUser.setUserCode(userCode);
			systemUser.setUsername(username);
			systemUser.setUserPssword(MD5Util.MD5(userPssword));
			systemUser.setShopSid(Integer.parseInt(shopSid));
			systemUser.setShopName(shopName);
			systemUser.setSex(sex);
			systemUser.setCreatetime(new Date());
			//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 start
			systemUser.setOperator(operator);
			//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 end

			int result = this.systemUserService.saveSystemUser(systemUser);
			if(result != 1){
				json = ResultUtil.createFailureResult("00000", "操作失败！");
			}else{
				json = ResultUtil.createSuccessResult("保存成功！");
			}
		}catch(Exception e){
			e.printStackTrace();
			json = ResultUtil.createFailureResult("00000", "操作失败！");
		}
		
		return json;
	}
	
	/**
	 * 删除系统用户信息
	* @Title: updateUserinfoValidStatus 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserinfoValidStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateUserinfoValidStatus(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		int result = 0;
		
		String sid = request.getParameter("sid");
		String username = request.getParameter("username");
		
		if(sid == null || sid.isEmpty()){
			return ResultUtil.createFailureResult("00000", "sid is null");
		}
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("sid", sid);
			SystemUser systemUser = this.systemUserService.getSystemUserByParam(map);
				
			systemUser.setValidBit(0);
			systemUser.setOperator(username);
			result = this.systemUserService.updateUserinfoValidStatus(systemUser);
			
			if(result == 0){
				json = ResultUtil.createFailureResult("00000" , "删除失败");
			}else{
				json = ResultUtil.createSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("删除失败！",e.getMessage());
		}
		return json;
	}
	
	/**
	 * 删除系统用户信息
	* @Title: deleteSystemUser 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSystemUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteSystemUser(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		String result = "";
		
		String sid = request.getParameter("sid");
		
		if(sid == null || sid.isEmpty()){
			return ResultUtil.createFailureResult("00000", "sid is null");
		}
		
		try {
			result = this.systemUserService.delGuidinfo(Integer.parseInt(sid)).toString();
			
			if(!result.equals("1")){
				json = ResultUtil.createFailureResult("00000" , "删除失败");
			}else{
				json = ResultUtil.createSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("删除失败！",e.getMessage());
		}
		return json;
	}
	
	/**
	 * 获取所有有效用户
	* @Title: getAllValidUser 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllValidUser", method = {RequestMethod.GET, RequestMethod.POST}
	,produces = "text/html;charset=UTF-8")
	public String getAllValidUser(HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		try {
			List<SystemUser> list = systemUserService.getAllValidUser();
			json = ResultUtil.createSuccessResult(list);
		} catch (Exception e) {
			json = ResultUtil.createFailureResult(e);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateUser", method = {RequestMethod.GET, RequestMethod.POST}
	,produces = "text/html;charset=UTF-8")
	public String updateUser(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		String json = "";
		
		SystemUser user = new SystemUser();
		
		String sid = request.getParameter("sid");
		String userCode = request.getParameter("userCode");
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		String validBit = request.getParameter("validBit");
		String shopSid = request.getParameter("shopSid");
		String shopName = request.getParameter("shopName");
		//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 start
		String operator = (String)session.getAttribute("username");
		//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 end
		if(sid != null && !sid.equals("")){
			user.setSid(Integer.parseInt((sid)));
		}
		if(userCode != null && !userCode.equals("")){
			user.setUserCode(userCode);
		}
		if(userName != null && !userName.equals("")){
			user.setUsername(userName);
		}
		if(userPassword != null && !userPassword.equals("userPassword")){
			user.setUserPssword(MD5Util.MD5(userPassword));
		}
		if(validBit != null && !validBit.equals("")){
			user.setValidBit(Integer.parseInt(validBit));
		}
		if(shopSid != null && !shopSid.equals("")){
			user.setShopSid(Integer.parseInt(shopSid));
		}
		if(shopName != null && !shopName.equals("")){
			user.setShopName(shopName);
		}
		//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 start
		user.setOperator(operator);
		//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 end
		try {
			
			Integer result = systemUserService.update(user);
			if(result != null){
				logger.info("会员用户信息修改成功");
				json = ResultUtil.createSuccessResult("保存成功");
			}else{
				logger.info("会员用户信息修改失败");
				json = ResultUtil.createSuccessResult("保存失败");
			}
		} catch (Exception e) {
			json = ResultUtil.createFailureResult(e);
		}
		return json;
	}

}
