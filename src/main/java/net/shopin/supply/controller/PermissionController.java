package net.shopin.supply.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.supply.domain.entity.Permission;
import net.shopin.supply.service.IPermissionService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopin.core.util.DateUtils;
import com.shopin.core.util.ResultUtil;

/**
 * 权限管理
 * ClassName: PermissionController 
 * @Description: TODO
 * @author zhangqing
 * @date 2015-6-9
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private IPermissionService permissionService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@ResponseBody
	@RequestMapping(value="/savePermission",method={RequestMethod.POST,RequestMethod.GET})
	public String savePermission(HttpServletRequest request,HttpServletResponse response){
		String json="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		String guideNos = request.getParameter("guideNos");
		String type = request.getParameter("type");//权限类型
//		String startTime = request.getParameter("startTime");
		String endtime = request.getParameter("endtime");
		String username = request.getParameter("username");
		String userSid = request.getParameter("userSid");
		String flag = request.getParameter("flag");
//		if(startTime.indexOf("T") != -1){
//			startTime = startTime.replaceAll("T"," ");
//		}
		if(endtime!=null&&!"".equals(endtime)){
			if(endtime.indexOf("T") != -1){
				endtime = endtime.replaceAll("T"," ");
			}
		}
		if(guideNos == null || guideNos.isEmpty()){
			return ResultUtil.createFailureResult("00000", "guideNo is null");
		}
		boolean ifSuccess = true; 
		String saveFatureGuidNo = "";
		
		try {
			String[] _guideNos = guideNos.split(",");
			
			for(String guideNo:_guideNos){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("guideNo", guideNo);
				Permission permission = this.permissionService.selectByParam(map);
				if(null == permission){
					permission = new Permission();
					permission.setCreattime(new Date());
				}
				permission.setGuideNo(Integer.parseInt(guideNo));
				permission.setType(Integer.parseInt(type));
				permission.setStartTime(new Date());
				permission.setEndTime((endtime==null || endtime.equals(""))?null:df.parse(DateUtils.addDays(endtime, 1)));
				permission.setOperatorId(userSid);
				permission.setOperatoeName(username);
				permission.setOperatTime(new Date());
				
				if("2".equals(type)){
					permission.setTypeDesc("客服退货支付权限");
					permission.setCustomerFlag(Integer.parseInt(flag));
				}else{
					permission.setFlag(Integer.parseInt(flag));
					permission.setTypeDesc("手动变价权限");
				}
				
				
				int result = this.permissionService.save(permission);
				if(result != 1){
					saveFatureGuidNo = saveFatureGuidNo+permission.getGuideNo()+",";
					ifSuccess = false;
				}
			}
			if(ifSuccess){
				json = ResultUtil.createSuccessResult("保存成功！");
			}else{
				json = ResultUtil.createFailureResult("00000", "导购编号为"+saveFatureGuidNo+"的导购修改失败" );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("授权失败！",e.getMessage());
		}
		return json;
	}
}
