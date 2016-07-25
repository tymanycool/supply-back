/**   
* @Title: RoleResourcesController.java 
* @Package net.shopin.supply.controller 
* @Description: TODO(...) 
* @author zhangq   
* @date 2015-3-3 下午04:32:13 
* @version V1.0   
*/
package net.shopin.supply.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.Resources;
import net.shopin.supply.domain.entity.RoleResources;
import net.shopin.supply.service.IResourcesService;
import net.shopin.supply.service.IRoleResourceService;
import net.shopin.supply.util.ResultUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
* @ClassName: RoleResourcesController 
* @author zhangq
* @date 2015-3-3 下午04:32:27 
*
 */
@Controller
@RequestMapping("/roleResources")
public class RoleResourcesController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IResourcesService resourcesService;
	
	@Autowired
	private IRoleResourceService roleResourceService;
	
	/**
	 * 得到所有的根节点资源
	* @Title: getAllResources 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllResources", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String getAllResources(HttpServletRequest request, HttpServletResponse response){
		String parentSid = request.getParameter("node");
		//得到角色的id
		String orleSid = request.getParameter("roleSid");
		JSONObject obj = new JSONObject();
		JSONArray jsonarry = new JSONArray();
		List<Resources> result = new ArrayList();
		try {
			//得到所有资源
			if(!"0".equals(parentSid) && parentSid != null){
				Resources resource = new Resources();
				resource.setParentSid(Long.valueOf(parentSid));
				result = resourcesService.getResourcesByParam(resource);
			}else{
				List<Resources> list1 = resourcesService.getAllResources();
				
				//遍历得到所有跟节点
				for(int i = 0;i<list1.size();i++){
					Resources r = new Resources();
					r = list1.get(i);
					if(r.getParentSid()==null || r.getParentSid()==0){
						result.add(r);
					}
				}
			}
			//在此result 中的资源是树中所需要的资源我们在这根据角色用户表中的对应关系来进行操作
			//1根据角色id来从角色资源映射表中提取资源id
			List<RoleResources> roleResources = new ArrayList();
			RoleResources limitroleresource = new RoleResources();
			limitroleresource.setRoleSid(Long.valueOf(orleSid));
			roleResources = roleResourceService.getRoleResourcesByParam(limitroleresource);
			//2遍历角色资源映射
			for(RoleResources rore: roleResources){
				Long id = rore.getRsSid();
				for(Resources r:result){
					//3把角色资源映射表中存在的资源的checked置为true 否则为false
					if(id.equals(r.getSid())){
						r.setChecked(true);
					}
				}
			}
			
			//将跟节点list转换为树所需要的。
			for(Resources r : result){
				obj.put("id", r.getSid());
				obj.put("text", r.getRsName());
				obj.put("checked", r.isChecked());
				obj.put("expanded", true);
				obj.put("leaf", false);
				jsonarry.add(obj);
			}
			
		} catch (Exception e) {
	
			e.printStackTrace();
		}
		
		return jsonarry.toString();
	}
	
	/**
	 * 保存权限
	* @Title: savaRoleResource 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/savaRoleResources", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String savaRoleResources(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		String roleSid = request.getParameter("orleSid");
		String resourceSids = request.getParameter("resourceSid");
		String result = "";
		try {

			//1清空数据库中的与角色相关的资源权限
			RoleResources roleResource = new RoleResources();
			roleResource.setRoleSid(Long.valueOf(roleSid));
			result = roleResourceService.deleteRoleResources(roleResource).toString();
			//2将ResourceSids进行处理遍历。
			String []rsSids = resourceSids.trim().split(",");
			for(String s : rsSids){
				
				if(!s.equals("0")){
					roleResource.setRsSid(Long.valueOf(s));
					//3保存角色的权限
					result = roleResourceService.saveRoleResources(roleResource).toString();
				}
			}
			json = ResultUtil.createSuccessResult(result);
		} catch (Exception e) {
		
			e.printStackTrace();
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}

}
