package net.shopin.supply.domain.vo;

public class ActingLevelVO {
	private String supplierCode;
	private String supplierName;
	private String category;
	private String brandName;
	private String actingLevel;
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getActingLevel() {
		return actingLevel;
	}
	public void setActingLevel(String actingLevel) {
		this.actingLevel = actingLevel;
	}
	@Override
	public String toString() {
		return "ActingLevelVO [supplierCode=" + supplierCode + ", supplierName=" + supplierName + ", category="
				+ category + ", brandName=" + brandName + ", actingLevel=" + actingLevel + "]";
	}
}
