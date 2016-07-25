package net.shopin.supply.domain.entity;

import java.util.Date;
/**
 * 新采购的PAD基本信息
 * Title: PadPurchaseInfo
 * Description: 
 * @author SunYukun
 * @date 2016年3月10日 上午11:28:28
 */
public class PadPurchaseInfo {
	private Integer sid;
	private String padSupply;// PAD供货厂商
	private String padBrand;// PAD品牌
	private Date padPurchaseTime;// PAD采购时间
	private Integer padPurchaseNum;// PAD采购数量
	private String padPurchaseBatchNo;// PAD采购批次号
	private String padOperator;  //操作人Name
	private Date padOperatorTime;   //操作时间
	private Integer padEnteredNum; //已录入系统的数量
	
	

	public Integer getPadEnteredNum() {
		return padEnteredNum;
	}

	public void setPadEnteredNum(Integer padEnteredNum) {
		this.padEnteredNum = padEnteredNum;
	}

	public Date getPadOperatorTime() {
		return padOperatorTime;
	}

	public void setPadOperatorTime(Date padOperatorTime) {
		this.padOperatorTime = padOperatorTime;
	}

	public Integer getSid() {
		return sid;
	}

	

	public String getPadOperator() {
		return padOperator;
	}

	public void setPadOperator(String padOperator) {
		this.padOperator = padOperator;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getPadSupply() {
		return padSupply;
	}

	public void setPadSupply(String padSupply) {
		this.padSupply = padSupply;
	}

	public String getPadBrand() {
		return padBrand;
	}

	public void setPadBrand(String padBrand) {
		this.padBrand = padBrand;
	}

	public Date getPadPurchaseTime() {
		return padPurchaseTime;
	}

	public void setPadPurchaseTime(Date padPurchaseTime) {
		this.padPurchaseTime = padPurchaseTime;
	}

	public Integer getPadPurchaseNum() {
		return padPurchaseNum;
	}

	public void setPadPurchaseNum(Integer padPurchaseNum) {
		this.padPurchaseNum = padPurchaseNum;
	}

	public String getPadPurchaseBatchNo() {
		return padPurchaseBatchNo;
	}

	public void setPadPurchaseBatchNo(String padPurchaseBatchNo) {
		this.padPurchaseBatchNo = padPurchaseBatchNo;
	}


}
