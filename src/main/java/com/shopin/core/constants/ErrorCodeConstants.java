package com.shopin.core.constants;
/**
 * 说明:
 * @author guansq
 * @date 2012-12-10 下午02:56:48
 * @modify 
 */
public class ErrorCodeConstants {
	public enum ErrorCode {
		/**
		 * 错误编码规范：
		 * 长度：7位 00 0 00 00
		 * 含义：
		 * 		12位：系统，订单系统是21
		 * 		3位：消息等级，默认为1
		 * 		45位：模块，oms_core(01) oms_sdc(02) oms_admin(03) oms_erp(04)
		 * 		67位：具体错误
		 * 		系统异常：2110001-2110099
		 */
		/*21100***/
		SYSTEM_ERROR("2110001", "系统运行异常！"), 
		CALL_SRC_ERROR("2120001", "接口调用方来源不明！"),
		PARAM_ERROR("2110002", "参数不正确！"), 
		PARAM_IS_NOT_NUMBER("2110003","非数字格式！"),
		PARAM_DATE_ERROR("2111004","日期格式不正确！"),
		JSON_ERROR("21100021", "JSON格式不正确！"), 
		RESULT_IS_NULL_ERROR("2110003", "查询结果为空！"),
		DUPLICATE_KEY_ERROR("2110004", "主键已存在！"),
		UPDATE_ERROR("2110115", "更新失败"),
		UPDATE_SUCCESS("2110116", "更新成功"),
		MODIFY_MEMO_ERROR("2110163","更新备注失败！"),
		SAVE_ERROR("2110165","保存失败！"),
		CHECK_FALSE("2110197","校验失败"),
		
		SAVE_PADLOG_ERROR("211010209","保存PAD LOG失败"),
		SAVE_GuideLOG_ERROR("211010210","保存导购 LOG失败"),
		ENTEREDNUM_IS_ENOUGH_ERROR("211010211","该批次PAD个数添加已达上限,请与管理员联系修改入库数量！"),
		PAD_RECEIVE_RRROR("211010212","PAD操作:收货失败");
		
		private String errorCode;
		private String memo;
		private ErrorCode() {};
		private ErrorCode(String errorCode, String memo) {
			this.errorCode = errorCode;
			this.memo = memo;
		}
		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
	}
}
