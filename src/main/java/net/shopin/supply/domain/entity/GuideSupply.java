package net.shopin.supply.domain.entity;

/**
 * 导购与供应商中间表
 * @author zhangq
 *
 */
public class GuideSupply {
	
	 private Integer sid;
	 private Integer supplyId;//供应商ID
	 private Integer guideNo;//导购编号
	 private String supplyName;//供应商名称
	 private Integer shopId;//门店ID
	 private String shopName;//门店名称
	 private String brand;//品牌名称
	 private String brandId;//sap品牌Id
	 private String brandSsdSid;//ssd品牌Id
	 private String categroys;//品类
	 private Integer categorysId;//品类ID
	 
	 private Integer changeSupplyId;//转场后供应商id
	 private String changeSupplyName;//转场后供应商名称
	 private String changeBrandId;//转场后品牌sap的id
	 private String changeBrandSsdId;//转场后品牌ssd的id
	 private String changeBrand;//转场后品牌名称
	 private Integer changeCategorysId;//转场后品类id
	 private String changeCategroys;//转场后品类名称
	 private Integer changeSupplyBit;//是否转场 0:不是 1:是
	 
	 private Integer validBit;//是否有效 0:无效 1:有效
	 
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
	public Integer getGuideNo() {
		return guideNo;
	}
	public void setGuideNo(Integer guideNo) {
		this.guideNo = guideNo;
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
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategroys() {
		return categroys;
	}
	public void setCategroys(String categroys) {
		this.categroys = categroys;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public Integer getCategorysId() {
		return categorysId;
	}
	public void setCategorysId(Integer categorysId) {
		this.categorysId = categorysId;
	}
	public Integer getValidBit() {
		return validBit;
	}
	public void setValidBit(Integer validBit) {
		this.validBit = validBit;
	}
	public Integer getChangeSupplyBit() {
		return changeSupplyBit;
	}
	public void setChangeSupplyBit(Integer changeSupplyBit) {
		this.changeSupplyBit = changeSupplyBit;
	}
	public String getChangeSupplyName() {
		return changeSupplyName;
	}
	public void setChangeSupplyName(String changeSupplyName) {
		this.changeSupplyName = changeSupplyName;
	}
	public Integer getChangeSupplyId() {
		return changeSupplyId;
	}
	public void setChangeSupplyId(Integer changeSupplyId) {
		this.changeSupplyId = changeSupplyId;
	}
	public String getChangeBrandId() {
		return changeBrandId;
	}
	public void setChangeBrandId(String changeBrandId) {
		this.changeBrandId = changeBrandId;
	}
	public String getChangeBrand() {
		return changeBrand;
	}
	public void setChangeBrand(String changeBrand) {
		this.changeBrand = changeBrand;
	}
	public Integer getChangeCategorysId() {
		return changeCategorysId;
	}
	public void setChangeCategorysId(Integer changeCategorysId) {
		this.changeCategorysId = changeCategorysId;
	}
	public String getChangeCategroys() {
		return changeCategroys;
	}
	public void setChangeCategroys(String changeCategroys) {
		this.changeCategroys = changeCategroys;
	}
	public String getChangeBrandSsdId() {
		return changeBrandSsdId;
	}
	public void setChangeBrandSsdId(String changeBrandSsdId) {
		this.changeBrandSsdId = changeBrandSsdId;
	}
	public String getBrandSsdSid() {
		return brandSsdSid;
	}
	public void setBrandSsdSid(String brandSsdSid) {
		this.brandSsdSid = brandSsdSid;
	}
}
