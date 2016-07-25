package net.shopin.supply.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.vo.GuideinfoVO;
import net.shopin.supply.service.IGuideinfoService;
import net.shopin.supply.service.ISendEmailService;
import net.shopin.supply.util.CommonProperties;
import net.shopin.supply.util.CreateExcelUtil;
import net.shopin.supply.util.DataUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorizeGuideJob {
	
	private final static Logger logger = LoggerFactory
	.getLogger(AuthorizeGuideJob.class);
	
	@Autowired
	private IGuideinfoService guideinfoService;
	
	@Autowired
	private ISendEmailService emailService;
	
//	private final static String url = CommonProperties
//	.get("logistics_path").toString();
	
	public void work(){
		logger.info("AuthorizeGuideJob发送邮件开始");
		String resultStr = "";
		//得到所有供应商信息
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("flag", "1");
		paramMap.put("guideStatusId", "1");
		paramMap.put("validBit", "1");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowdate = sdf.format(date);
		String dateStr = DataUtil.addDays(nowdate,-1);
		String fileName = dateStr+"_authorizeGuide.xls";
		paramMap.put("endTime", nowdate);
		
		//18:00发送给北京
		String sendFlag = "bj";
		String email = CommonProperties.get("email.recieveUsernameBj");
		boolean areaFlag = compareDate(date);
		if(!areaFlag){
			//19:00发送给杭州
			sendFlag = "hz";
			email = CommonProperties.get("email.recieveUsernameHz");
		}		
		paramMap.put("sendFlag", sendFlag);
		
		List<GuideinfoVO>  guideinfoVOList = this.guideinfoService.getAllGuideInfoListForAuthorizeGuideJob(paramMap);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("fileName", fileName);
		map.put("email",email);
		map.put("dateStr", dateStr);
		map.put("areaFlag", String.valueOf(areaFlag));
		
//		List<GuideinfoVO> guideinfoVOList = new ArrayList<GuideinfoVO>();
		
		
		
//		String result = HttpUtil.HttpPost(url, "supplyEmail/getAllEmailList.json", paramMap);
//		logger.info("得到供应商邮件信息为:"+result);
//		JSONObject resulObj = JSONObject.fromObject(result);
		if(guideinfoVOList!=null&&guideinfoVOList.size()>0){
				try {
					// 得到JSONArray
//					JSONArray arr = JSONArray.fromObject(list);
//					if(arr.size()>0){
//						for(int i = 0; i < arr.size(); i++){
//							JSONObject jOpt = arr.getJSONObject(i);
//							GuideinfoVO opt = (GuideinfoVO) JSONObject.toBean(jOpt, GuideinfoVO.class);
//							guideinfoVOList.add(opt);
//						}
//					}else{
//						continue;
//					}
					logger.info("导购变价在"+dateStr+"发送的邮件内容为大小为:"+guideinfoVOList.size());
					CreateExcelUtil.createExcelForAuthorizeGuide(guideinfoVOList, fileName);
					logger.info("导购变价在"+dateStr+"创建邮件参数"+map.toString());
//					SendEmailService emailService = new SendEmailServiceImpl();
					logger.info("emailService.hashCode:"+emailService.hashCode());
					resultStr += emailService.sendEmail(map);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("发送邮件错误信息："+e.toString());
				}
		}
		logger.info("发送邮件结束"+resultStr);
	}
	
	public boolean compareDate(Date d){
			d = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = sdf.format(d);
			String aStr = format+" "+CommonProperties.get("email.startTime");
			String bStr = format+" "+CommonProperties.get("email.endTime");
			long aDate = newSdf.parse(aStr).getTime();
			long bDate = newSdf.parse(bStr).getTime();
			long now = d.getTime();
			if(aDate<= now  && now < bDate){
				return true;
			}
//			return false;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
