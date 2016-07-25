/**
 * QueryPo.java
 * net.shopin.expense.po
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年12月23日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.po;

import java.util.Date;

/**
 * <p>ClassName:QueryPo</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年12月23日上午10:52:08
 */
public class QueryPo {
	private String shopSid;
	private String supplySid;
    private String brandSid;
    private Integer purchaseSid;
    private String month;
    
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getShopSid() {
		return shopSid;
	}
	public void setShopSid(String shopSid) {
		this.shopSid = shopSid;
	}
	public String getSupplySid() {
		return supplySid;
	}
	public void setSupplySid(String supplySid) {
		this.supplySid = supplySid;
	}
	public String getBrandSid() {
		return brandSid;
	}
	public void setBrandSid(String brandSid) {
		this.brandSid = brandSid;
	}
	public Integer getPurchaseSid() {
		return purchaseSid;
	}
	public void setPurchaseSid(int purchaseSid) {
		this.purchaseSid = purchaseSid;
	}
}

