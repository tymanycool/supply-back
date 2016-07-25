package com.shopin.core.constants;


public class PayTypeCodeConstants {
	public enum PayTypeCode{
		支付宝("3","0703"),
		手机支付双向确认("20","0731"),
		手机支付建设银行("21","0731"),
		手机支付中国银行("22","0731"),
		手机支付农业银行("23","0731"),
		手机支付交通银行("24","0731"),
		银联支付("14","0742"),
		财付通("10","0727"),
		易宝支付("15","0726"),
		支付宝快捷支付("17","0703"),
		中国工商银行("11","0733"),
		招商银行网银("13","0725"),
		货到付款("4","0729"),
		支付宝找人代付("18","0703"),
		借记卡快捷支付("19","0703"),
		会员账户("1","0737"),
		首信易("2","0728"),
		淘宝("5","0702"),
		拍拍("6","0732"),
		工行网银("7","0733"),
		联通精品购物("8","0734"),
		团购("9","0735"),
		百付宝("12","0730"),
		淘宝商城("16","0741"),
		当当商城("25","0736"),
		支付宝扫码支付("26","0703"),
		微信支付("27","0743"),
		全渠道微信支付("28","0744");
		
		private String omsType;
		private String sapType;
		private PayTypeCode(){
		}
		
		private static PayTypeCode getPayTypeCodeByomsType(String omsType){
			for (PayTypeCode pyc : PayTypeCode.values()) {
				if (pyc.getOmsType().equals(omsType)) {
					return pyc;
				}
			}
			return null;
		}
		
		/**
		 * 根据omsType获取名字
		 * @param omsType
		 * @return
		 */
		public static String getOmsNameByOmsType(String omsType){
			PayTypeCode pyc = getPayTypeCodeByomsType(omsType);
			if (pyc != null) {
				return pyc.name();
			}
			return null;
		}
		
		/**
		 * 根据omsType获取sapType
		 * @param omsType
		 * @return
		 */
		public static String getsapTypeByOmsType(String omsType){
			PayTypeCode pyc = getPayTypeCodeByomsType(omsType);
			if (pyc != null) {
				return pyc.getSapType();
			}
			return null;
		}
		
		private PayTypeCode(String omsType,String sapType){
			this.omsType = omsType;
			this.sapType = sapType;
		}
		public String getOmsType() {
			return omsType;
		}
		public void setOmsType(String omsType) {
			this.omsType = omsType;
		}
		public String getSapType() {
			return sapType;
		}
		public void setSapType(String sapType) {
			this.sapType = sapType;
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(PayTypeCodeConstants.PayTypeCode.getOmsNameByOmsType("4"));
	}
}
