package net.shopin.supply.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.shopin.message.api.DefaultMessageClient;
import net.shopin.message.api.IMessageClient;
import net.shopin.message.request.SendMessageRequest;

import com.shopin.core.framework.exception.ShopinException;
import com.shopin.core.util.HttpClientUtil;

public class SendMessageUtil {
	public static void sendMessage(String tel,String content){
		try {
			String url = AppUtil.getPropertity("remote_ticket_message_url")+"&message="+URLEncoder.encode(content, "GBK")+"&phone="+tel+"&epid=108927&linkid=&subcode=";
			String json1=HttpClientUtil.HttpPost(url, null, null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String sendMessage(String sendUrl,String backUrl,String data){
		IMessageClient client = new DefaultMessageClient(sendUrl); // http://172.16.200.120/MMS_ProducerAgent
		SendMessageRequest smReq = new SendMessageRequest();
		smReq.setQueueType("queue");
		smReq.setQueueGroup("member"); // 队列名
		smReq.setQueueBackURL(backUrl); // 处理消息的接口地址
		smReq.setCallMethod("POST");
		smReq.setMessageSource("MEMBER"); // 消息发送者
		smReq.setQueueContent(data); // 消息内容 ***JSON***
		String result = "0";
		try {
			result = client.execute(smReq);
		} catch (ShopinException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static void main(String [] args){
		String str =SendMessageUtil.sendMessage("http://192.168.200.175/MMS_ProducerAgent",
				"http://172.16.102.64:8989/member418/memberInformation/addMemberInformation.json",
				"{'memberSid':'123456','name':'aaa','phone':'16343698','mobile':'10000000000','nikeName':'SA','gender':'1','chlId':'2000','weixinNo':'1234567890'}");
		System.out.println(str);
	}
}
