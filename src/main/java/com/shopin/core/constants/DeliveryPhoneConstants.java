package com.shopin.core.constants;

/**
 * @author songcs
 * @date 2014-8-14
 * XXX new created type: DeliveryPhoneConstants
 */

public class DeliveryPhoneConstants {
	public enum DeliveryPhone {
		ZJS("宅急送","400-678-9000"),
		ST("申通","400-889-5543"),
		YT("圆通","021-69777888"),
		EMS("EMS","11183"),
		ZGYZ("中国邮政","11185"),
		YS("优速","400-1111-119"),
		CS100("城市100","400-820-0088"), 
		TT("天天","400-820-8198"),
		TJDY("天津大洋","400-820-0088"),
		KJ("快捷","400-830-4888"),
		YD("韵达","400-821-6789"),
		SF("顺丰","400-811-1111"),
		RFD("如风达","400-010-6660"),
		HT("汇通","400-956-5656"),
		ZT("中通","400-827-0270");
		
		private String comName;
		private String phone;
		
		private DeliveryPhone(){}
		private DeliveryPhone(String comName, String phone){
			this.comName = comName;
			this.phone = phone;
		}
		
		private static  DeliveryPhone getDeliveryPhoneByComName(String comName){
			for (DeliveryPhone dp : DeliveryPhone.values()) {
				if (comName.indexOf(dp.getComName()) != -1) {
					return dp;
				}
			}
			return null;
		}
		
		public static String getPhoneByShopSid(String comName){
			DeliveryPhone dp = getDeliveryPhoneByComName(comName);
			if(dp!=null){
				return dp.phone;
			}
			return null;
		}
		
		public String getComName() {
			return comName;
		}
		public void setComName(String comName) {
			this.comName = comName;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(DeliveryPhoneConstants.DeliveryPhone.getPhoneByShopSid("顺丰快递"));
	}

}
