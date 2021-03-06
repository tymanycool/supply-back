package net.shopin.ledger.po;

import java.util.Date;

public class LedgerContractCourse {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ledger_contract_course.id
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ledger_contract_course.ledger_contract_id
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    private Integer ledgerContractId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ledger_contract_course.contract_return_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    private Date contractReturnDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ledger_contract_course.contract_type
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    private String contractType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ledger_contract_course.contract_begin_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    private Date contractBeginDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ledger_contract_course.contract_end_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    private Date contractEndDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ledger_contract_course.contract_review_status
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    private String contractReviewStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ledger_contract_course.contract_reviewer
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    private String contractReviewer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ledger_contract_course.contract_review_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    private Date contractReviewDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ledger_contract_course.id
     *
     * @return the value of ledger_contract_course.id
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ledger_contract_course.id
     *
     * @param id the value for ledger_contract_course.id
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ledger_contract_course.ledger_contract_id
     *
     * @return the value of ledger_contract_course.ledger_contract_id
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public Integer getLedgerContractId() {
        return ledgerContractId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ledger_contract_course.ledger_contract_id
     *
     * @param ledgerContractId the value for ledger_contract_course.ledger_contract_id
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public void setLedgerContractId(Integer ledgerContractId) {
        this.ledgerContractId = ledgerContractId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ledger_contract_course.contract_return_date
     *
     * @return the value of ledger_contract_course.contract_return_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public Date getContractReturnDate() {
        return contractReturnDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ledger_contract_course.contract_return_date
     *
     * @param contractReturnDate the value for ledger_contract_course.contract_return_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public void setContractReturnDate(Date contractReturnDate) {
        this.contractReturnDate = contractReturnDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ledger_contract_course.contract_type
     *
     * @return the value of ledger_contract_course.contract_type
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ledger_contract_course.contract_type
     *
     * @param contractType the value for ledger_contract_course.contract_type
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ledger_contract_course.contract_begin_date
     *
     * @return the value of ledger_contract_course.contract_begin_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public Date getContractBeginDate() {
        return contractBeginDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ledger_contract_course.contract_begin_date
     *
     * @param contractBeginDate the value for ledger_contract_course.contract_begin_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public void setContractBeginDate(Date contractBeginDate) {
        this.contractBeginDate = contractBeginDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ledger_contract_course.contract_end_date
     *
     * @return the value of ledger_contract_course.contract_end_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public Date getContractEndDate() {
        return contractEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ledger_contract_course.contract_end_date
     *
     * @param contractEndDate the value for ledger_contract_course.contract_end_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ledger_contract_course.contract_review_status
     *
     * @return the value of ledger_contract_course.contract_review_status
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public String getContractReviewStatus() {
        return contractReviewStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ledger_contract_course.contract_review_status
     *
     * @param contractReviewStatus the value for ledger_contract_course.contract_review_status
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public void setContractReviewStatus(String contractReviewStatus) {
        this.contractReviewStatus = contractReviewStatus == null ? null : contractReviewStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ledger_contract_course.contract_reviewer
     *
     * @return the value of ledger_contract_course.contract_reviewer
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public String getContractReviewer() {
        return contractReviewer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ledger_contract_course.contract_reviewer
     *
     * @param contractReviewer the value for ledger_contract_course.contract_reviewer
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public void setContractReviewer(String contractReviewer) {
        this.contractReviewer = contractReviewer == null ? null : contractReviewer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ledger_contract_course.contract_review_date
     *
     * @return the value of ledger_contract_course.contract_review_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public Date getContractReviewDate() {
        return contractReviewDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ledger_contract_course.contract_review_date
     *
     * @param contractReviewDate the value for ledger_contract_course.contract_review_date
     *
     * @mbggenerated Sat Oct 10 11:42:15 CST 2015
     */
    public void setContractReviewDate(Date contractReviewDate) {
        this.contractReviewDate = contractReviewDate;
    }
}