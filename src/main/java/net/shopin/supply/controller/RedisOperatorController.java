package net.shopin.supply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopin.core.util.ResultUtil;
import com.shopin.redis3.utils.Redis3Utils;

import net.shopin.supply.domain.entity.Guideinfo;

/**
 * 对供应商缓存数据的操作
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/editRedis")
public class RedisOperatorController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 通过  Key  获取供应商缓存
	 */
	@ResponseBody
	@RequestMapping(value="/selectRedisByKey",method={RequestMethod.POST,RequestMethod.GET})
	public String selectRedisByKey(HttpServletRequest request,HttpServletResponse response){
		String json = "";
		try{
			
			String redis_key = request.getParameter("rediskey");
			
			if(StringUtils.isBlank(redis_key)|| StringUtils.isBlank(redis_key)){
				return "{'success':'false','memo':'redis查询Key为空，请传入正确参数'}";
			}
			String redisString = Redis3Utils.get(redis_key);
			json = ResultUtil.createSuccessResult(redisString);
			
		}catch(Exception e){
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
		
	}
	
	/**
	 * 通过  Key  清除供应商缓存
	 */
	@ResponseBody
	@RequestMapping(value="/delRedisByKey",method={RequestMethod.POST,RequestMethod.GET})
	public String delRedisByKey(HttpServletRequest request,HttpServletResponse response){
		String json = "";
		try{
			
			String redis_key = request.getParameter("rediskey");
			
			if(StringUtils.isBlank(redis_key)|| StringUtils.isBlank(redis_key)){
				return "{'success':'false','memo':'redis查询Key为空，请传入正确参数'}";
			}
			Redis3Utils.del(redis_key);
			String redisString = Redis3Utils.get(redis_key);
			if(redisString==null){
				json = ResultUtil.createSuccessResult();
			}
		}catch(Exception e){
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
		
	}
}
