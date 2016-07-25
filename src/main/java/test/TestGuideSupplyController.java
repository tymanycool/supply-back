package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.GuideLog;
import net.shopin.supply.domain.entity.GuideSupply;
import net.shopin.supply.persistence.GuideSupplyMapper;
import net.shopin.supply.service.IGuideSupplyService;
import net.shopin.supply.util.HttpUtil;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.shopin.core.constants.ErrorCodeConstants.ErrorCode;
import com.shopin.core.framework.exception.ShopinException;

/**
 * ClassName: TestGuideSupplyController 
 * @Description: TODO
 * @author zhangqing
 * @date 2015-3-26
 */
@Controller
public class TestGuideSupplyController {
	
	@Autowired
	private IGuideSupplyService guideSupplyService;
	
	@Autowired
	private GuideSupplyMapper guideSupplyMapper;

	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 根据ssd的品牌id获取sap的品牌sid存入数据库
	 * @Title: saveBrandSSDSid 
	 * @Description: TODO
	 * @param    
	 * @return void  
	 * @throws
	 * @author zhangqing
	 * @date 2015-3-26
	 */
	@Ignore
	@Test
	public void saveBrandSSDSid(){
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		String url = "http://localhost/supply";
		String result = HttpUtil.HttpPost(url, "guideSupply/saveBrandSSDSid", map);
		logger.info(result);
	}
	
	/**
	 * 根据ssd的品牌id获取sap的品牌sid存入数据库(转场)
	 * @Title: saveChangeBrandSSDSid 
	 * @Description: TODO
	 * @param    
	 * @return void  
	 * @throws
	 * @author zhangqing
	 * @date 2015-3-26
	 */
	
	@Test
	public void saveChangeBrandSSDSid(){
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		String url = "http://localhost/supply";
		String result = HttpUtil.HttpPost(url, "guideSupply/saveChangeBrandSSDSid", map);
		logger.info(result);
	}
}
