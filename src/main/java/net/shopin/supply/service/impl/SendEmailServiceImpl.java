package net.shopin.supply.service.impl;

import java.io.File;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import net.shopin.supply.service.ISendEmailService;
import net.shopin.supply.util.CommonProperties;
import net.shopin.supply.util.CreateHtmlUtil;


/**
 * @Class Name SendEmailServiceImpl
 * @Author Administrator
 * @Create In 2015年6月17日
 */
@Service
public class SendEmailServiceImpl implements ISendEmailService {
	
	private final static Logger logger = LoggerFactory
			.getLogger(SendEmailServiceImpl.class);
	
	private static final String CHARSET = "utf-8";
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public String sendEmail(Map<String, String> paramMap) throws Exception {
		logger.info("开始创建邮件");
		String fileName = paramMap.get("fileName");
		String dateStr = paramMap.get("dateStr");
		boolean areaFlag = Boolean.parseBoolean(paramMap.get("areaFlag"));
		
		logger.info("得到MimeMessage"+mailSender.hashCode());
		MimeMessage mime = mailSender.createMimeMessage();
		try {
		MimeMessageHelper helper;
		
		
			helper = new MimeMessageHelper(mime,true,CHARSET);
			logger.info("得到MimeMessageHelper"+helper.hashCode());
			logger.info("发信人"+CommonProperties.get("email.username"));
			//发信人
			helper.setFrom(CommonProperties.get("email.username"));
			//收信人
			String toList = paramMap.get("email");
			logger.info("收件人地址"+toList);
			InternetAddress[] iaToList = new InternetAddress().parse(toList);  
			mime.setRecipients(Message.RecipientType.TO,iaToList);
			
			//抄送人
			String ccList = CommonProperties.get("email.mcf.bj");//北京的抄送人
			if(!areaFlag){
				 ccList = CommonProperties.get("email.mcf.hz");//杭州的抄送人
			}
			InternetAddress[] ccToList = new InternetAddress().parse(ccList); 
			helper.setCc(ccToList);
			
			helper.setSubject(dateStr+"的已开启导购变价权限明细");
			logger.info("邮件正文"+helper.toString());
			if (fileName != null) {
				helper.setText("<html>" + 
						"<body><div style=\"color:#000; font-size: 14px;font-family: arial;\">您好！<br>"+
							"&nbsp;&nbsp;&nbsp;&nbsp;附件是"+dateStr+"的已开启导购变价权限明细，详情见附件。请核实！<br></div>" +
								 CreateHtmlUtil.endhtml()+"</body>"+
						"</html>",true);
				String path = new File("").getAbsolutePath() +File.separator+"authorizeGuideExcel/"+File.separator+fileName;
				System.out.println("path---------------"+path);
				File file=new File(path);
				helper.addAttachment(fileName, file);
			}
			logger.info("添加附件成功");
		} catch (Exception e) {
			e.printStackTrace();
			return fileName + "false";
		}
		mailSender.send(mime);
		logger.info(fileName+"发送成功");
		return fileName + "--true";
	}

}
