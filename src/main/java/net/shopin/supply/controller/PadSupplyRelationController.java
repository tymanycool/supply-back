package net.shopin.supply.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.PadSupply;
import net.shopin.supply.service.IPadBaseinfoService;
import net.shopin.supply.service.IPadSupplyRelationService;
import net.shopin.supply.service.IPadSupplyService;
import net.shopin.supply.util.CommonProperties;
import net.shopin.supply.util.HttpUtil;

import org.apache.log4j.Logger;
//import org.codehaus.jackson.map.ser.BasicSerializerFactory.IntegerSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopin.core.util.ResultUtil;

/**
 * Pad与供应商关系管理——新系统
* @ClassName: PadSupplyRelationController 
* @author syk
*
 */
@Controller
@RequestMapping("/padSupplyRelation")
public class PadSupplyRelationController {
	
	@Autowired
	private IPadSupplyRelationService padSupplyRelationService;
	
	@Autowired
	private IPadBaseinfoService padBaseinfoService;
	
	@Autowired
	private IPadSupplyService padSupplyService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 根据pad编号查询pad绑定的供应商和门店
	* @Title: getGuideSupplyList 
	* @param @param model
	* @param @param request padNo pad编号
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/list")
	public String getPadSupplyList(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String padNo = request.getParameter("padNo");
		String json = "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			if(null != padNo && !padNo.equals("")){
				map.put("padNo", padNo);
			}
			List<PadSupply> padSupplyList = this.padSupplyRelationService.selectListByParam(map);
			map.put("list",padSupplyList);
			json = ResultUtil.createSuccessResult(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 检查此pad是否已绑定供应商（如果pad的使用类型是导购，一个pad只能绑定一个供应商；如果是其他，一个pad可以绑定多个供应商）
	* @Title: checkPadBoundSupply 
	* @param @param model
	* @param @param request padNo pad编号
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/checkPadBoundSupply")
	@ResponseBody
	public String checkPadBoundSupply(Model model, HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		
		String padNo = request.getParameter("padNo");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("padNo", padNo);
		
		PadBaseinfo padBaseinfo = this.padBaseinfoService.selectPadByPadNo(padNo);
		int useType = padBaseinfo.getUseType();
		if(useType == 0){//导购
			PadSupply padSupply = this.padSupplyRelationService.selectPadSupplyRelationByPadNo(map);
			if(null != padSupply){
				json = ResultUtil.createSuccessResult(padSupply);
			}else{
				json = ResultUtil.createSuccessResult();
			}
		}else if(useType == -1){//旧系统中-1说明supply_pad_supply表中已经不存在该pad的信息了，因为在解除与供应商的关系时，置成了-1;并且再绑定时需要先修改状态
			json = ResultUtil.createFailureResult("00000", "请先点击修改按钮为此PAD选择使用类型，然后才能绑定供应商！");
		}else{
			json = ResultUtil.createSuccessResult();
		}
		return json;
	}
	
	
	/**
	 * Pad绑定供应商和门店
	* @Title: savePadSupply 
	* @param @param request supplyId 供应商id， supplyName 供应商名称， padNo pad编号，shopId 门店id，
	* 						shopName 门店名称，username 系统登录用户姓名
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/savePadSupply",method={RequestMethod.POST,RequestMethod.GET})
	public String savePadSupply(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
        
		String supplyId = request.getParameter("supplyId");
		String supplyName = request.getParameter("supplyName");
		String padNo = request.getParameter("padNo");
		String shopId = request.getParameter("shopId");    //没有门店是没有办法绑定的，跳转至此：表明此项不为null
		String shopName = request.getParameter("shopName");
		String username = request.getParameter("username");
		String userSid = request.getParameter("userSid");//系统登录用户名sid
		String flag = request.getParameter("flag");
		String useType = request.getParameter("useType");  //PAD的使用类型
		String useTypeDesc = request.getParameter("useTypeDesc");
		try {
			
			boolean isValid = false;   
			boolean isCreate = false;    //是否创建padSupply
			Integer result = null;
			if(useType !=null && !useType.equals("")&& !useType.equals("-1")){
				if(useTypeDesc != null && !useTypeDesc.equals("")){
					
					//更新pad:使用类型，
					PadBaseinfo pad = padBaseinfoService.selectPadByPadNo(padNo);
					pad.setUseType(Integer.valueOf(useType));
					pad.setUseTypeDesc(useTypeDesc);
					padBaseinfoService.save(pad, username,userSid);
					isValid = true;
				}
			}
			//新建pad时：已经默认创建关联的padSupply.
			if(isValid){
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("padNo", padNo);
				if(useType.equals("1")){  //使用类型：主管（一个PAD可关联多个供应商）
					List<PadSupply> padSupplys = padSupplyService.selectListByParam(map);
					if(padSupplys != null && (padSupplys.size()) == 1){
						PadSupply padinfoSupply = padSupplys.get(0);
						if(padinfoSupply.getSupplyId() == null ){
							//第一次绑定 ：更新
							padinfoSupply.setSupplyId(Integer.valueOf(supplyId));
							padinfoSupply.setSupplyName(supplyName);
							 result = this.padSupplyRelationService.savePadSupplyNew(padinfoSupply,username,userSid);
						}else{
							//新建padSupply
							isCreate = true;
						}
					}else{
						//新建padSupply
						isCreate = true;
					}
				}else{
					//非主管：更新。（一个PAD仅关联一个供应商）
					PadSupply padSupply = padSupplyService.selectPadSupplyByPadNo(map);
					if(padSupply !=null){
						padSupply.setSupplyId(Integer.valueOf(supplyId));
						padSupply.setSupplyName(supplyName);
						 result = this.padSupplyRelationService.savePadSupplyNew(padSupply,username,userSid);
					}else{
						//新建
						isCreate = true;
					}
				}
			}
			
			//是否创建padSupply
			if(isCreate){
				PadSupply padSupply = new PadSupply();
				padSupply.setPadNo(padNo);
				padSupply.setSupplyId(Integer.parseInt(supplyId));
				padSupply.setSupplyName(supplyName);
				padSupply.setShopId(Integer.parseInt(shopId));    
				padSupply.setShopName(shopName);
				padSupply.setCreatetime(new Date());
				result = this.padSupplyRelationService.savePadSupplyNew(padSupply,username,userSid);
			}
			
			if(result != 1){
				json = ResultUtil.createFailureResult("00000","操作失败！");
			}else{
				json = ResultUtil.createSuccessResult("操作成功！");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 解除pad与供应商关系
	* @Title: delPadSupplyInfo 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delPadSupplyInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public String delPadSupplyInfo(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		String sid = request.getParameter("sid");
		String flag = request.getParameter("flag");
		try {
			String username = request.getParameter("username");
			String userSid = request.getParameter("userSid");
			this.padSupplyRelationService.delPadSupplyInfo(Long.parseLong(sid),username,userSid,flag);
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("删除失败！",e.getMessage());
		}
		return json;
	}
	
	
	/**
	 * 查看pad软件安装列表
	* @Title: WatchPadSoftWindow 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	* @author qutengfei
	* feature https://tower.im/s/9cCdh
	 */
	@ResponseBody
	@RequestMapping(value = "/watchPadSoft", method = {RequestMethod.GET, RequestMethod.POST})
	public String watchPadSoft(HttpServletRequest request, HttpServletResponse response) {
		
		String maxAddress = request.getParameter("macAddress");
		String shopId = request.getParameter("shopId");
		System.out.println(maxAddress+" "+shopId);
		//获取配置文件中调用接口信息、url地址
		String url = CommonProperties.get("watchPadSoft.url");
		String info = CommonProperties.get("watchPadSoft.info");
		
		Map<String,String> map=new HashMap<String,String>();
		JSONObject padSoftObj = JSONObject.fromObject(info);
		
		map.put("optUserSid", padSoftObj.getString("optUserSid"));
		map.put("userName", padSoftObj.getString("userName"));
		map.put("password", padSoftObj.getString("password"));
		map.put("ipAddress", padSoftObj.getString("ipAddress"));
		map.put("macAddress", maxAddress);
		map.put("shopSid", shopId);
//		map.put("macAddress", "8c-cb-24-ea-8a-06");
//		map.put("shopSid", "1001");
		map.put("appVersionName", padSoftObj.getString("appVersionName"));
		
		String result = HttpUtil.HttpPost(url, "app-info.json?_method=getAppDetailInfo", map);
		System.out.println(result);
		
		return result;
		
	}
}
