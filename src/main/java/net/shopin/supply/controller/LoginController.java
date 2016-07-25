package net.shopin.supply.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.Resources;
import net.shopin.supply.domain.entity.RoleResources;
import net.shopin.supply.domain.entity.RoleUser;
import net.shopin.supply.domain.entity.SystemUser;
import net.shopin.supply.service.IResourcesService;
import net.shopin.supply.service.IRoleResourceService;
import net.shopin.supply.service.IRoleUserService;
import net.shopin.supply.service.ISystemUserService;
import net.shopin.supply.util.MD5Util;
import net.shopin.supply.util.ResultUtil;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopin.core.framework.base.vo.Page;

@Controller
public class LoginController {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private ISystemUserService systemUserService;
	
	@Autowired
	private IRoleUserService roleUserService;
	
	@Autowired
	private IRoleResourceService roleResourceService;
	
	@Autowired
	private IResourcesService resourcesService;
	
	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request,
			HttpServletResponse response) throws HttpException, IOException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpSession session = servletRequest.getSession();
		String username = (String) request.getParameter("username");
		String password = MD5Util.MD5((String) request.getParameter("password"));
		if(username == null){
			return "redirect:" + "/";
		}
		String result = null;
		
		try {
//			SystemUser systemUser = this.systemUserService.getSystemUserByParam(paramMap);
			
//			得到用户信息
			result = this.selectUserTypeByParam(username,1,20);
			JSONObject userStr = JSONObject.fromObject(result);
			JSONObject userPage = JSONObject.fromObject(userStr.get("obj"));
			JSONArray userList = JSONArray.fromObject(userPage.get("list"));
			String userInfor = userList.getString(0);
			
			//得到用户资源	
			result = this.loginCheck(username, password);
			JSONObject resultObject = JSONObject.fromObject(result);
			if(resultObject != null && resultObject.getBoolean("success")){
				if(log.isDebugEnabled()) {
					log.debug("登录成功,user:" + username);
				}
				
				JSONObject json = new JSONObject(); 
				if(userInfor.indexOf("shopName") == -1){
					session.setAttribute("shopname", "上品中心");
				}else{
					String shopname = json.fromObject(userInfor).get("shopName").toString();
					String shopid = json.fromObject(userInfor).get("shopSid").toString();
					session.setAttribute("shopname", shopname);
					session.setAttribute("shopid", shopid);
				}
				session.setAttribute("username", username);
				session.setAttribute("sid", json.fromObject(userInfor).get("sid").toString());
				
				String roleUser = this.selectRoleUser(username, password);
				JSONObject roleUserObject = JSONObject.fromObject(roleUser);
				
				session.setAttribute("password", password);
				session.setAttribute("userInfor", userInfor);
				session.setAttribute("resources", resultObject.getString("obj"));//资源rsCode集合
				session.setAttribute("roleUser", roleUserObject.getString("obj"));//角色ids
				return "redirect:" + "/";
			}
			else if(resultObject != null){
				model.addAttribute("error",resultObject.getString("memo"));
				return "login";
			}else{
				model.addAttribute("error", "登录验证失败!");
				return "login";
			}
		} catch (Exception e) {
			model.addAttribute("error", "连接后台服务器失败!");
			return "login";
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "/selectUserTypeByParam", method = {RequestMethod.GET, RequestMethod.POST}
	,produces = "text/html;charset=UTF-8")
	public String selectUserTypeByParam(String username,int pageNumber,int fetchSize) {
		
		String json = "";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(username != null && !username.equals("")){
			paramMap.put("userCode", username);
		}
		if(username != null && !username.equals("")){
			paramMap.put("userName", username);
		}
		paramMap.put("pageNumber", pageNumber);
		paramMap.put("fetchSize", fetchSize);
		paramMap.put("start", (Integer.valueOf(pageNumber) - 1) * Integer.valueOf(fetchSize));
		paramMap.put("limit", Integer.valueOf(fetchSize));
		try {
			Page<SystemUser> page =systemUserService.selectAllbyParam(paramMap);
			json = ResultUtil.createSuccessResult(page);
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("error", e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	public String loginCheck(String username,String password){
		String json = "";
		String resources = "";
		if(username == null){
			return ResultUtil.createFailureResult("500", "用户名不能为空");
		}
		//根据username查找用户
		SystemUser user = new SystemUser();
		user.setUserCode(username);
		try {
			user = systemUserService.selectByUserCode(user);
			if(user!=null && password.equals(user.getUserPssword())){
				//根据userSid去找所有角色 关系
				RoleUser roleUsers = new RoleUser();
				roleUsers.setUserSid(new Long((long)user.getSid()));
				List<RoleUser> roleUserList = roleUserService.getRoleUserByParam(roleUsers);
				
				if(roleUserList !=null && roleUserList.size()>0){
					//根据多个roleSid 得到角色和资源关系
					List<RoleResources> roleResourcesListAll = new ArrayList<RoleResources>();
					for(RoleUser roleUser: roleUserList){
						RoleResources roleresource = new RoleResources();
						roleresource.setRoleSid(roleUser.getRoleSid());
						List<RoleResources> roleResourcesList = roleResourceService.getRoleResourcesByParam(roleresource);
						roleResourcesListAll.addAll(roleResourcesList);
					}
					
					if(roleResourcesListAll !=null && roleResourcesListAll.size()>0){
						//根据多个resourceSid 得到多个资源
						List<Resources> resourceListAll = new ArrayList<Resources>();
						for(RoleResources roleResour : roleResourcesListAll){
							Resources resource = new Resources();
							resource.setSid(roleResour.getRsSid());
							List<Resources> resourceList = resourcesService.getResourcesByParam(resource);
							resourceListAll.addAll(resourceList);
						}
						if(resourceListAll != null && resourceListAll.size()>0){
							//的到每个资源的编码并返回给前台
							for(Resources res:resourceListAll ){
								resources += res.getRsCode() + ",";
							}
							json = ResultUtil.createSuccessResult(resources);
						}
					}else{
						json = ResultUtil.createFailureResult("500", "此用户没有资源权限");
					}
					
				}else{
					json = ResultUtil.createFailureResult("500", "没有给用户设置角色");
				}
			}else{
				json = ResultUtil.createFailureResult("500", "用户名密码不正确");
			}
		} catch (Exception e) {
			json = ResultUtil.createFailureResult("500","服务器维护中！");
		}
		return json;
	}
	
	@ResponseBody
	public String selectRoleUser(String username,String password){
		String json = "";
		String roleUserIds = "";
		if(username == null){
			return ResultUtil.createFailureResult("500", "用户名不能为空");
		}
		//根据username查找用户
		SystemUser user = new SystemUser();
		user.setUserCode(username);
		try {
			user = systemUserService.selectByUserCode(user);
			if(user!=null && password.equals(user.getUserPssword())){
				//根据userSid去找所有角色 关系
				RoleUser roleUsers = new RoleUser();
				roleUsers.setUserSid(new Long((long)user.getSid()));
				List<RoleUser> roleUserList = roleUserService.getRoleUserByParam(roleUsers);
				
				if(roleUserList !=null && roleUserList.size()>0){
					//根据多个roleSid 得到角色和资源关系
					List<RoleResources> roleResourcesListAll = new ArrayList<RoleResources>();
					for(RoleUser roleUser: roleUserList){
						RoleResources roleresource = new RoleResources();
						roleresource.setRoleSid(roleUser.getRoleSid());
						roleUserIds += roleUser.getRoleSid() + ",";
					}
					json = ResultUtil.createSuccessResult(roleUserIds);
					
				}else{
					json = ResultUtil.createFailureResult("500", "没有给用户设置角色");
				}
			}else{
				json = ResultUtil.createFailureResult("500", "用户名密码不正确");
			}
		} catch (Exception e) {
			json = ResultUtil.createFailureResult("500","服务器维护中！");
		}
		return json;
	}

}
