package com.shopin.core.constants;

public class StatusCodeConstants {
	public enum StatusCode{
		//共用状态****
		CANCEL_STATUS(-1,"作废"),
		ROUGH_DRAFT(0,"草稿"),
		NO_REFUND(0,"没有退货"),
		PART_REFUND(1,"部分退货,有退货"),
		TOTAL_REFUND(2,"整单退货"),
		
		//订单状态*******
		//主状态
		ORDER_SPLITED(-3,"订单已拆分"),
		ORDER_UNCHECKED(-2,"货到付款未审核通过"),
		ORDER_CANCLE(-1,"已作废"),
		ORDER_OVER_TIME(0,"超时"),
		ORDER_WEB_CREATE(1,"等待支付"),
		ORDER_NO_DELIVERY_CREATE(1,"货到付款审核中"),
		ORDER_PAYMENT_BY_EBANK(2,"已支付"),
		ORDER_NO_DELIVERY_PAYED(2,"货到付款审核通过"),
		ORDER_SENDED_GOODS(3,"已发货"),
		ORDER_CONFIRM_TAKE_DELIVERY(4,"确认收货"),
		ORDER_CHILD_REFUSE_GOODS(5,"拒收"),
		CASH_ON_DELIVERY_FINANCE_CONFRM(6,"货到付款  财务确认收款"),
		
		//订单副状态-物流状态
		ORDER_LOGISTICS_WAITING(2,"等待调拨"),
		ORDER_LOGISTICS_ALLOTING(3,"调拨中"),
		ORDER_LOGISTICS_ALLOTED(4,"调入物流中心"),
		ORDER_LOGISTICS_PRINTED(5,"打印快递单"),
		ORDER_LOGISTICS_RECHECKED(6,"发货复检"),
		ORDER_lOGISTICS_DELIVERY(7,"已发货"),
		

		//销售单状态*******
		WAIT_FOR_HAVE_GOODS(1,"等待导购确认是否有货"),
		CONFIRM_HAVE_GOODS(2,"导购确认有货"),
		PRINTED_NO_PAYMENT(3,"已打印未付款"),
		PAYMENT_NO_DELIVERY(4,"已付款未提货"),
		PAYMENT_AND_DELIVERY(5,"已付款已提货"),
		NO_PRODUCT(6,"导购确认无货"),
		//退货单状态*******
		PRINTED_REFUND(1,"打印退货单"),
		REFUND_CASH(3,"退货入收银"),
		CONFIRM_TAKE_DELIVERY_BY_GUIDE(4,"导购确认收货"),
		//退货申请****
		REFUNDAPPLY_CHECKING(-4,"客服审核中"),
		REFUNDAPPLY_REFUSE(-3,"物流拒签"),
		REFUNDAPPLY_CHECK_ERROR(-2,"审核未通过"),
		REFUNDAPPLY_CANCLE(-1,"作废"),
		REFUNDAPPLY_BUILD(1,"草稿"),
		REFUNDAPPLY_CHECK_SUCCESS(2,"审核通过"),
		REFUNDAPPLY_TAKE_GOODS(3,"物流确认收货"),
		REFUNDAPPLY_REPAY(4,"财务退款"),
		REFUNDAPPLY_DELAY_REPAY(5,"客服延迟退款"),
		REFUNDAPPLY_REMOTE_REFUND(6,"异地退货"),
		
		//退货申请单副状态-物流状态
		REFUND_LOGISTICS_WAITING(1,"等待顾客发货"),
		REFUND_LOGISTICS_DELIVERY(2,"顾客已发货"),
		REFUND_LOGISTICS_TAKE_GOODS(3,"调入分拣中心"),
		REFUND_LOGISTICS_ALLOTING(4,"调拨中"),
		REFUND_LOGISTICS_ALLOTED(5,"调入物流室"),
		REFUND_LOGISTICS_GUIDE_TAKE_GOODS(6,"导购确认收货"),
		REFUND_LOGISTICS_NO_PRODUCT(7,"导购确认无货"),
		REFUND_LOGISTICS_WAIT_ALLOT(8,"等待调拨"),
		REFUND_LOGISTICS_WAIT_TAKE_GOODS(9,"等待物流确认收货"),
		REFUND_LOGISTICS_ZHUAN_CHU(10,"转出中"),
		//退货申请明细*******
		APPLY_DETAIL_CONFIRM_STATUS(1,"导购确认收货"),
		//货到监控配送状态****
		CONTROL_SEND_PASS_STATUS(1,"配送在途"),
		CONTROL_SEND_SUCCESS_STATUS(2,"配送成功"),
		CONTROL_SEND_FAIL_STATUS(3,"配送失败"),
		CONTROL_SEND_REFUND_STATUS(4,"正常退货");
		
		
		
		
		
		private int status;
		private String comment;
		private StatusCode(){}
		private StatusCode(int status,String comment){
			this.status = status;
			this.comment = comment;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		
		
	}
}
