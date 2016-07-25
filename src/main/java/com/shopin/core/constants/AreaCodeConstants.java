package com.shopin.core.constants;


public class AreaCodeConstants {
	public enum AreaCode{
		王府井店(1001L,1000L,"北京"),
		亚运村店(1002L,1000L,"北京"),
		首体店(1003L,1000L,"北京"),
		五棵松店(1004L,1000L,"北京"),
		中关村店(1005L,1000L,"北京"),
		朝阳门店(1006L,1000L,"北京"),
		三里河店(1007L,1000L,"北京"),
		来广营店(1008L,1000L,"北京"),
		回龙观店(1010L,1000L,"北京"),
		草桥店(1011L,1000L,"北京"),
		DC分销中心(5000L,1000L,"北京"),
		虚拟代销DC(5001L,1000L,"北京"),
		
		下沙店(1301L,3000L,"杭州"),
		杭州DC(5300L,3000L,"杭州");
		private Long shopSid;
		private Long areaCode;
		private String area;
		
		private AreaCode(){}
		private AreaCode(Long shopSid,Long areaCode,String area){
			this.shopSid = shopSid;
			this.areaCode = areaCode;
			this.area = area;
		}
		
		private static  AreaCode getAreaCodeVoByShopSid(Long shopSid){
			for (AreaCode uc : AreaCode.values()) {
				if (uc.getShopSid().longValue()==shopSid.longValue()) {
					return uc;
				}
			}
			return null;
		}
		
		public static String getShopNameByShopSid(Long shopSid){
			AreaCode uc = getAreaCodeVoByShopSid(shopSid);
			if(uc!=null){
				return uc.name();
			}
			return null;
		}
		
		public static Long getAreaSidByShopSid(Long shopSid){
			AreaCode uc = getAreaCodeVoByShopSid(shopSid);
			if(uc!=null){
				return uc.getAreaCode(shopSid);
			}
			return null;
		}
		
		public static String getAreaNameByShopSid(Long shopSid){
			AreaCode uc = getAreaCodeVoByShopSid(shopSid);
			if(uc!=null){
				return uc.getArea(shopSid);
			}
			return null;
		}
		
		public Long getShopSid() {
			return shopSid;
		}
		public void setShopSid(Long shopSid) {
			this.shopSid = shopSid;
		}
		public Long getAreaCode() {
			return areaCode;
		}
		public void setAreaCode(Long areaCode) {
			this.areaCode = areaCode;
		}
		public Long getAreaCode(Long shopSid) {
			return this.areaCode;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getArea(Long shopSid) {
			return this.area;
		}

		
	}
	public static void main(String[] args) {
//		System.out.println(AreaCodeConstants.AreaCode.getAreaSidByShopSid(1301L));
//		System.out.println(AreaCodeConstants.AreaCode.getAreaNameByShopSid(1301L));
	}
}
