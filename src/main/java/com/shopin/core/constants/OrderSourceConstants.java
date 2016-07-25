package com.shopin.core.constants;

public class OrderSourceConstants {
	public enum OrderSource{
		本网(0l,"2000"),
		天猫(7l,"2001"),
		淘宝(1l,"2003"),
		当当(11l,"2002"),
		杭州本网(12l,"2300"),
		杭州天猫(13l,"2301"),
		杭州淘宝(15l,"2303"),
		杭州当当(14l,"2302"),
		北京微商城(16l,"2004"),
		杭州微商城(17l,"2304");
		private Long omsOrderSource;
		private String sapOrderSource;
		private OrderSource(){
			
		}
		
		private OrderSource(Long omsOrderSource,String sapOrderSource){
			this.omsOrderSource=omsOrderSource;
			this.sapOrderSource=sapOrderSource;
		}
		
		
		public Long getOmsOrderSource() {
			return omsOrderSource;
		}

		public void setOmsOrderSource(Long omsOrderSource) {
			this.omsOrderSource = omsOrderSource;
		}

		public String getSapOrderSource() {
			return sapOrderSource;
		}

		public void setSapOrderSource(String sapOrderSource) {
			this.sapOrderSource = sapOrderSource;
		}

		public static String getSapOrderSourceByOms(Long omsOrderSource){
			for(OrderSource os:OrderSource.values()){
				if(os.getOmsOrderSource()==omsOrderSource){
					return os.getSapOrderSource();
				}
			}
			return "";
		}
		
	}
	public static void main(String[] args) {
		System.out.println(OrderSource.getSapOrderSourceByOms(012l));
	}
}
