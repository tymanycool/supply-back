/**
 * 
 */
package net.shopin.supply.domain.entity;

/**
 * @author Administrator
 * 
 */
public class Supply {

	private int sid;
	private String supplyName;
	private String brandName;
	private String categoryName;
	private String picUrl;
	private int supplySid;
	private String brandSid;
	private int categorySid;
	private int pageSize;
	private int start;
	private int totoPage;
	
	/**
	 * @return the totoPage
	 */
	
	
	
	public int getTotoPage() {
		return totoPage;
	}
	/**
	 * @param totoPage
	 *            the totoPage to set
	 */
	public void setTotoPage(int totoPage) {
		this.totoPage = totoPage;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
	/**
	 * @return the supplySid
	 */
	public int getSupplySid() {
		return supplySid;
	}
	/**
	 * @param supplySid
	 *            the supplySid to set
	 */
	public void setSupplySid(int supplySid) {
		this.supplySid = supplySid;
	}
	/**
	 * @return the brandSid
	 */
	public String getBrandSid() {
		return brandSid;
	}
	/**
	 * @param brandSid
	 *            the brandSid to set
	 */
	public void setBrandSid(String brandSid) {
		this.brandSid = brandSid;
	}
	/**
	 * @return the categorySid
	 */
	public int getCategorySid() {
		return categorySid;
	}
	/**
	 * @param sid
	 *            the categorySid to set
	 */
	public void setCategorySid(int categorySid) {
		this.categorySid = categorySid;
	}

	/**
	 * @return the sid
	 */
	public int getSid() {
		return sid;
	}

	/**
	 * @param sid
	 *            the sid to set
	 */
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	/**
	 * @return the supplyName
	 */
	public String getSupplyName() {
		return supplyName;
	}

	

	/**
	 * @param supplyName
	 *            the supplyName to set
	 */
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl
	 *            the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * @return the categoryName
	 */

	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	

}
