package net.shopin.ledger.po;

import java.util.Date;

public class Approval {
	
	private Integer id;

	private String approvalStatus;

    private Date reviewDate;

    private String reviewName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReviewName() {
		return reviewName;
	}

	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}
    
}
