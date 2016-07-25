package net.shopin.supply.domain.vo;


/**
 * Title: DifferDeductionVo
 * Description: 差异性扣率Vo
 * @author SunYukun
 * @date 2016年2月17日 下午2:51:43
 */
public class DifferDeductionVo {
	private String supplierCode;
	private String supplierName;
	private String category;
	private String brandName;
	private String storeName;
	private double deductionRate;
	private String cooperationWay;
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public double getDeductionRate() {
		return deductionRate;
	}
	public void setDeductionRate(double deductionRate) {
		this.deductionRate = deductionRate;
	}
	public String getCooperationWay() {
		return cooperationWay;
	}
	public void setCooperationWay(String cooperationWay) {
		this.cooperationWay = cooperationWay;
	}
	@Override
	public String toString() {
		return "DifferDeductionVo [supplierCode=" + supplierCode + ", supplierName=" + supplierName + ", category="
				+ category + ", brandName=" + brandName + ", storeName=" + storeName + ", deductionRate="
				+ deductionRate + ", cooperationWay=" + cooperationWay + "]";
	}
}
