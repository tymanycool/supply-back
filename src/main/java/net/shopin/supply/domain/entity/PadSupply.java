package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * PAD与供应商中间表
 * @author zhangq
 *
 */
public class PadSupply {
	
	 private Integer sid;
	 private String padNo;//pad编号
	 private Integer supplyId;//供应商id
	 private String supplyName;//供应商名称
	 private Integer shopId;//门店id
	 private String shopName;//门店名称
	 private Date createtime;
	 private Integer targetShopId;
	 private String targetShopName;
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(Integer supplyId) {
		this.supplyId = supplyId;
	}
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getPadNo() {
		return padNo;
	}
	public void setPadNo(String padNo) {
		this.padNo = padNo;
	}
	public Integer getTargetShopId() {
		return targetShopId;
	}
	public void setTargetShopId(Integer targetShopId) {
		this.targetShopId = targetShopId;
	}
	public String getTargetShopName() {
		return targetShopName;
	}
	public void setTargetShopName(String targetShopName) {
		this.targetShopName = targetShopName;
	}
}
