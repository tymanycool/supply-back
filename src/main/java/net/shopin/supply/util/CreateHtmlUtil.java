package net.shopin.supply.util;

/**
 * Title: oms_job
 * Description: 
 * Copyright @2014~2014
 * @author :songcs
 * @creatDate:2014-8-21上午2:31:44
 * @version: $Reversion:$
 */
public class CreateHtmlUtil {
	
	public  static  String  headhtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<head>")
				.append("<meta charset=\"UTF-8\">")
				.append("<meta http-equiv=\"Access-Control-Allow-Origin\" content=\"*\">")
				.append("<link href=\"http://www.shopin.net/css/reset.css\" rel=\"stylesheet\" type=\"text/css\">")
				.append("<link href=\"http://www.shopin.net/css/layout.css\" rel=\"stylesheet\" type=\"text/css\">")
				.append("<link href=\"http://www.shopin.net/css/cart.css\" rel=\"stylesheet\" type=\"text/css\">")
			.append("</head>");
		return sb.toString();
	}
	
	public  static  String  endhtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div>")
				.append("<div style=\"color:#000; font-size: 14px;font-family: arial;\">")
					.append("<div><br/></div>")
					.append("<div>")
						.append("<div>")
							.append("<p>")
								.append("有问题请联系：彭璞")
								.append("<br/>")
								.append("&nbsp;&nbsp;&nbsp;&nbsp;手机：15652345485")
								.append("<br/>")
								.append("&nbsp;&nbsp;&nbsp;&nbsp;邮箱：pengpu@shopin.cn")
							.append("</p>")
							.append("<p>")
								.append("<span>")
									.append("<font color=\"#c0c0c0\">北京市上品商业发展有限责任公司</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">信息系统:彭璞</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">手机：15652345485</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">公司地址：北京市朝阳区香宾路66-1号朝来天陆综合服务楼四层</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">邮政编码：100012</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">上品IT热线：010-59578626</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">------------------------------------------------------------------------------</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">SHOPIN IT，SMART IT</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">上品IT，精明的IT，智慧的IT</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">精明-专业-务实-协作</font>")
								.append("</span>")
						.append("</div>")
					.append("</div>")
				.append("</div>")
			.append("</div>");
		return sb.toString();
	}
	
	
	
	public  static  String  endhtml(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div>")
				.append("<div style=\"color:#000; font-size: 14px;font-family: arial;\">")
					.append("<div><br/></div>")
					.append("<div>")
						.append("<div>")
							.append("<p>");
								
								if ("songchaoshuai".equals(name)) {
									sb.append("有问题请联系：宋朝帅")
									.append("<br/>")
									.append("&nbsp;&nbsp;&nbsp;&nbsp;手机：13370130603")
									.append("<br/>")
									.append("&nbsp;&nbsp;&nbsp;&nbsp;邮箱：songchaohshuai@shopin.cn");
								} else if ("pengpu".equals(name)) {
									sb.append("有问题请联系：彭璞")
									.append("<br/>")
									.append("&nbsp;&nbsp;&nbsp;&nbsp;手机：15652345485")
									.append("<br/>")
									.append("&nbsp;&nbsp;&nbsp;&nbsp;邮箱：pengpu@shopin.cn");
								}
							sb.append("</p>")
							.append("<p>")
								.append("<span>")
									.append("<font color=\"#c0c0c0\">北京市上品商业发展有限责任公司</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">信息系统部：彭璞</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">手机：15652345485</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">公司地址：北京市朝阳区香宾路66-1号朝来天陆综合服务楼四层</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">邮政编码：100012</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">上品IT热线：010-59578626</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">------------------------------------------------------------------------------</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">SHOPIN IT，SMART IT</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">上品IT，精明的IT，智慧的IT</font>")
									.append("<br/>")
									.append("<font color=\"#c0c0c0\">精明-专业-务实-协作</font>")
								.append("</span>")
						.append("</div>")
					.append("</div>")
				.append("</div>")
			.append("</div>");
		return sb.toString();
	}
}
