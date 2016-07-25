package net.shopin.supply.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.Resources;
import net.shopin.supply.service.IResourcesService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopin.core.util.ResultUtil;

/**
 * 资源管理
* @ClassName: Resources 
* @author zhangq
* @date 2015-3-2 上午10:40:27 
*
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IResourcesService resourcesService;
	
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
					if(r.getParentSid()==null ||r.getParentSid()==0){
						result.add(r);
					}
				}
			}
			
			//将跟节点list转换为树所需要的。
			for(Resources r : result){
				obj.put("id", r.getSid());
				obj.put("text", r.getRsName());
				//obj.put("icons", "");
				obj.put("linkBrand", null);
				obj.put("leaf", r.getIsLeaf());
				jsonarry.add(obj);
			}
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		return jsonarry.toString();
	}
	
	/**
	 * 根据条件进行查找资源
	* @Title: getLimitResourceByParam 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getResourceByParam", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String getResourceByParam(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		Resources resource = toResource(request);
		try {
			List result = resourcesService.getResourcesByParam(resource);
			json = ResultUtil.createSuccessResult(result);
		} catch (Exception e) {
		
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	/**
	 * 添加资源
	* @Title: saveResources 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResources", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String saveResources(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		Resources resource = toResource(request);
		resource.setDelFlag(0);
		resource.setCreateTime(new Date());
		try {
			String result = resourcesService.saveResource(resource).toString();
			json = ResultUtil.createSuccessResult(result);
		} catch (Exception e) {
		
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	/**
	 * 禁用资源
	* @Title: disableResources 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/disableResources", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String disableResources(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		Resources resource = toResource(request);
		resource.setDelFlag(1);
		try {
			String result = resourcesService.updateResource(resource).toString();
			json = ResultUtil.createSuccessResult(result);
		} catch (Exception e) {
		
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	/**
	 * 删除资源
	* @Title: deleteResources 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResources", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String deleteResources(HttpServletRequest request, HttpServletResponse response){
		String json = "";
//		Resources resource = toResource(request);
		String sid = request.getParameter("sid");
	
		try {
				String result = resourcesService.deleteResource(Long.parseLong(sid)).toString();
				json = ResultUtil.createSuccessResult(result);
		} catch (Exception e) {
	
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	/**
	 * 修改资源
	* @Title: updateLimitResources 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/updateResources", method = {RequestMethod.GET, RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String updateResources(HttpServletRequest request, HttpServletResponse response){
		String json = "";
		Resources resource = toResource(request);
		resource.setUpdateTime(new Date());
		try {
			String result = resourcesService.updateResource(resource).toString();
			json = ResultUtil.createSuccessResult(result);
		} catch (Exception e) {
		
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	/**
	 * 有请求参数转换为对象
	* @Title: toResource 
	* @param @param request
	* @param @return    设定文件 
	* @return Resources    返回类型 
	* @throws
	 */
	public Resources toResource(HttpServletRequest request){
		
		String sid = request.getParameter("sid");
		String rsName = request.getParameter("rsName");
		String rsCode = request.getParameter("rsCode");
		String delFlag = request.getParameter("delFlag");
		String parentSid = request.getParameter("parentSid");
		String isLeaf = request.getParameter("isLeaf");
		
		Resources resource = new Resources();
		
		if(sid != null && sid.length()>0){
			resource.setSid(Long.valueOf(sid));
		}
		if(rsName != null && rsName.length()>0){
			resource.setRsName(rsName);
		}
		if(rsCode != null && rsCode.length()>0){
			resource.setRsCode(rsCode);
		}
		if(delFlag != null && delFlag.length()>0){
			resource.setDelFlag(Integer.valueOf(delFlag));
		}
		if(parentSid != null && parentSid.length()>0){
			resource.setParentSid(Long.valueOf(parentSid));
		}
		if(isLeaf != null && isLeaf.length()>0){
			resource.setIsLeaf(Integer.valueOf(isLeaf));
		}
		return resource;
	}

}
