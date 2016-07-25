package net.shopin.supply.domain.entity;

/**
 * 
 * ClassName: UploadResource 
 * @Description: TODO
 * @author zhangqing
 * @date 2015-4-21
 */
public class UploadResource {
	
	private Long sid;
	private String type;
	private String version;
	private String url;
	private String shopSid;
	private String clientVersion;
	private String upgradeType;
	private String shopName;
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShopSid() {
		return shopSid;
	}
	public void setShopSid(String shopSid) {
		this.shopSid = shopSid;
	}
	public String getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	public String getUpgradeType() {
		return upgradeType;
	}
	public void setUpgradeType(String upgradeType) {
		this.upgradeType = upgradeType;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
