package net.shopin.supply.domain.vo;

public class QualiErrorInfoVO {
	private String supplierCode;
	private String supplierName;
	private String category;
	private String brandName;
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
	@Override
	public String toString() {
		return "QualiErrorInfoVO [supplierCode=" + supplierCode + ", supplierName=" + supplierName + ", category="
				+ category + ", brandName=" + brandName + "]";
	}
}
