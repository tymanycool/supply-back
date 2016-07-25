package net.shopin.supply.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.supply.domain.entity.Role;
import net.shopin.supply.domain.entity.RoleResources;
import net.shopin.supply.service.IRoleResourceService;
import net.shopin.supply.service.IRoleService;
import net.shopin.supply.service.IRoleUserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shopin.core.util.ResultUtil;

/**
 * 
* @ClassName: RoleController 
* @author zhangq
* @date 2015-3-2 下午01:20:08 
*
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IRoleUserService roleUserService;
	
	@Autowired
	private IRoleResourceService roleResourceService;
	
	@ResponseBody
	@RequestMapping(value = "/selectRoleByParam", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String selectRoleByParam(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		String name = request.getParameter("roleCodeName");
		try {
			List result;
			if(name !=null && name.length()>0){
				Role role = new Role();
				role.setRoleName(name);
				result = roleService.getRoleByParam(role);
				json = ResultUtil.createSuccessResult(result);
			}else{
				result = roleService.getAllRole();
				json = ResultUtil.createSuccessResult(result);
			}
			
		} catch (Exception e) {
		
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	/**
	 * 禁用
	* @Title: disabledUse 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/disabledUse", method = {
	RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public String disabledUse(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		Role role = toRole(request);
		role.setUpdateTime(new Date());
		role.setDelFlag(1);
		
//		String sid = request.getParameter("roleSid");
//		role.setSid(sid);
		try {
			//改变角色信息
			int result = roleService.updateRole(role);
			//将角色用户表中的角色信息删除
			roleUserService.deleteRoleUser(role.getSid());
			//将角色资源表中的角色信息删除
			RoleResources roleresources = new RoleResources();
			roleresources.setRoleSid(role.getSid());
			roleResourceService.deleteRoleResources(roleresources);
			json = ResultUtil.createSuccessResult(result);
		} catch (Exception e) {
	
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	/**
	 * 保存角色
	* @Title: saveRole 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRole", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String saveRole(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		Role role = toRole(request);
		role.setDelFlag(0);
		role.setCreatedTime(new Date());
		try {
			String result = roleService.saveRole(role).toString();
			json = ResultUtil.createSuccessResult(result);
		} catch (Exception e) {
			logger.error("保存角色信息错误"+e.getMessage());
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	/**
	 * 修改角色
	* @Title: updateRole 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/updateRole", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String updateRole(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		Role role = toRole(request);
		role.setUpdateTime(new Date());
		try {
			int result = roleService.updateRole(role);
			json = ResultUtil.createSuccessResult(result);
		} catch (Exception e) {
			
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	public Role toRole(HttpServletRequest request){
		
		String sid = request.getParameter("sid");
		String roleName = request.getParameter("roleName");
		String roleCode = request.getParameter("roleCode");
		String delFlag = request.getParameter("delFlag");
		
		Role limitRole = new Role();
		
		if(sid != null && sid.length()>0){
			limitRole.setSid(Long.valueOf(sid));
		}
		if(roleName != null && roleName.length()>0){
			limitRole.setRoleName(roleName);
		}
		if(roleCode != null && roleCode.length() >0){
			limitRole.setRoleCode(roleCode);
		}
		if(delFlag != null && delFlag.length()>0){
			limitRole.setDelFlag(Integer.valueOf(delFlag));
		}
		
		return limitRole;
	}
}
