package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * 
* @ClassName: Resources 
* @Description: TODO(...) 
* @author zhangq
* @date 2015-3-2 上午11:21:58 
*
 */
public class Resources {
	
	private Long sid;
	private String rsName;// 资源名称
	private String rsCode;// 资源编码
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
	private Integer delFlag;// 删除表示符
	private Long parentSid;// 父类Sid
	private Integer isLeaf;// 是否是叶子节点
	private boolean checked;// 是否是复选框
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getRsName() {
		return rsName;
	}
	public void setRsName(String rsName) {
		this.rsName = rsName;
	}
	public String getRsCode() {
		return rsCode;
	}
	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public Long getParentSid() {
		return parentSid;
	}
	public void setParentSid(Long parentSid) {
		this.parentSid = parentSid;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
