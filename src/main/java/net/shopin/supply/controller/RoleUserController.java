package net.shopin.supply.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.RoleUser;
import net.shopin.supply.domain.entity.SystemUser;
import net.shopin.supply.service.IRoleUserService;
import net.shopin.supply.service.ISystemUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopin.core.util.ResultUtil;

/** 
 * @ClassName: RoleUserController 
 * @author zhangq
 * @date 2015-3-2 下午02:13:53 
 *  
 */
@Controller
@RequestMapping("/roleUser")
public class RoleUserController {

	@Autowired
	private IRoleUserService roleUserService;
	
	@Autowired
	private ISystemUserService systemUserService;
	
	/**
	 * 得到角色下的所有用户
	* @Title: selecetCheckedUser 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/selecetCheckedUser", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String selecetCheckedUser(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		String roleSid = request.getParameter("roleSid");
		if(roleSid !=null &&!roleSid.isEmpty()){
			try {
				//根据roleSid得到所有角色和用户的关系
				RoleUser roleUsers = new RoleUser();
				roleUsers.setRoleSid(Long.valueOf(roleSid));
				List<RoleUser> listRoleUser = roleUserService.getRoleUserByParam(roleUsers);
				//根据得到的关系中的userSid得到用户
				List<SystemUser> listUser = new ArrayList<SystemUser>();
				for(RoleUser roleUser :listRoleUser){
					SystemUser user = new SystemUser();
					user.setSid(new Long(roleUser.getUserSid()).intValue());
					user = systemUserService.selectByUserCode(user);
					if(null != user){
						listUser.add(user);
					}
				}
				String cheackUser = "";
			
				if (listUser != null && listUser.size() > 0) {
					for (Object obj : listUser) {
						if(!obj.equals(null) ){
							JSONObject jobj = JSONObject.fromObject(obj);
							cheackUser += jobj.getString("sid") + ",";
						}
						
					}
					cheackUser = cheackUser.substring(0, cheackUser.length() - 1);
					
				}
				json = ResultUtil.createSuccessResult(cheackUser);
			} catch (Exception e) {
				json = ResultUtil.createFailureResult(e);
			}
		}else{
			json = ResultUtil.createFailureResult("500", "参数不能为空");
		}
		System.out.println("json===>"+json);
		return json;
	}
	
	/**
	 * 保存角色关联用户信息
	* @Title: savaRoleResource 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/savaRoleUser", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String savaRoleResource(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		String roleSid = request.getParameter("roleSid");
		String userSids = request.getParameter("userSids"); 
		try {
			//如果存在进行删除后插入操作
			//删除操作
			roleUserService.deleteRoleUser(Long.valueOf(roleSid));
			//保存操作
			String [] userSid = userSids.split(",");
			RoleUser roleUser = new RoleUser();
			roleUser.setRoleSid(Long.valueOf(roleSid));
			for(String sid:userSid){
				roleUser.setUserSid(Long.valueOf(sid));
				try {
					roleUserService.saveRoleUser(roleUser).toString();
					json = ResultUtil.createSuccessResult();
				} catch (Exception e) {
					json = e.getMessage();
				}
			}
		} catch (Exception e) {
			json = e.getMessage();
		}
		
		return json;
	}
}
