package net.shopin.supply.domain.entity;

import java.util.Date;

public class Permission {
    private Integer sid;

    private Integer guideNo;

    private Integer type;

    private String typeDesc;

    private Date startTime;

    private Date endTime;

    private String operatorId;

    private String operatoeName;

    private Date operatTime;

    private Date creattime;
    
    private Integer flag;
    
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getGuideNo() {
		return guideNo;
	}

	public void setGuideNo(Integer guideNo) {
		this.guideNo = guideNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc == null ? null : typeDesc.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getOperatoeName() {
        return operatoeName;
    }

    public void setOperatoeName(String operatoeName) {
        this.operatoeName = operatoeName == null ? null : operatoeName.trim();
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

	public Date getOperatTime() {
		return operatTime;
	}

	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}
}