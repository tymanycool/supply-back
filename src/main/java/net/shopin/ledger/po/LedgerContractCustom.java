package net.shopin.ledger.po;

import java.util.Date;
import java.util.List;


public class LedgerContractCustom {

	// 以下是合同台账的基本信息
	private Integer id;

	//用于标明是否有补充合同
	private Integer parentId;
	
	private String settlementStatus;
	
	private String cabinetStatus;
	
	private Date cabinetDate;

	private String signMark;

	private Date authorizedStartDate;

	private Date authorizedEndDate;

	private String category;

	private String brandName;

	private String storeEncoding;

	private String storeName;

	private Double deductionRate;
	
	private String clearType;
	
	private String clearThreshold;
	
	private String clearDeduction;

	private Boolean decorateRules;//商户装修守则
	
	private String assessmentIndicator;

	private Date signingDate;

	private Date startDate;

	private Date endDate;

	private Date contractDeadline;
	
	private String brandLevel;//品牌级别
	
	private String area;//面积
	
	private String cooperationWay;
	
	private String bizContractVersion;
	/**
     * @author lcl
     * 说明：补充协议字段
     */
    private String supplemental;
    
    public String getSupplemental() {
		return supplemental;
	}

	public void setSupplemental(String supplemental) {
		this.supplemental = supplemental;
	}

	private Double managementFees;

	private String cardFees;

	private String contractVersion;

	private String checkTag;

	private String checkName;

	private Date checkDate;

	private Boolean valid;

	private String mark;

	// 以下是供应商的基本信息

	private String supplierCode;

	private String supplierName;

	private String legalRepresentative;

	private String attorney;

	private String mobilePhone1;

	private String mobilePhone1Name;

	private String mobilePhone2;

	private String mobilePhone2Name;

	private String fixedTelephone;

	private String fixedTelephoneName;
	
	private Date basicSigningDate;

	private LedgerContractSupplyInfo ledgerContractSupplyInfo;

	// 以下是历程基本信息
	private String courseAllInfo; // 历程所有信息json字符串

	private List<LedgerContractCourse> ledgerContractCourseList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	
	public String getCabinetStatus() {
		return cabinetStatus;
	}

	public void setCabinetStatus(String cabinetStatus) {
		this.cabinetStatus = cabinetStatus;
	}

	public Date getCabinetDate() {
		return cabinetDate;
	}

	public void setCabinetDate(Date cabinetDate) {
		this.cabinetDate = cabinetDate;
	}

	public String getSignMark() {
		return signMark;
	}

	public void setSignMark(String signMark) {
		this.signMark = signMark;
	}

	public Date getAuthorizedStartDate() {
		return authorizedStartDate;
	}

	public void setAuthorizedStartDate(Date authorizedStartDate) {
		this.authorizedStartDate = authorizedStartDate;
	}

	public Date getAuthorizedEndDate() {
		return authorizedEndDate;
	}

	public void setAuthorizedEndDate(Date authorizedEndDate) {
		this.authorizedEndDate = authorizedEndDate;
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

	public String getStoreEncoding() {
		return storeEncoding;
	}

	public void setStoreEncoding(String storeEncoding) {
		this.storeEncoding = storeEncoding;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Double getDeductionRate() {
		return deductionRate;
	}

	public void setDeductionRate(Double deductionRate) {
		this.deductionRate = deductionRate;
	}

	public String getClearType() {
		return clearType;
	}

	public void setClearType(String clearType) {
		this.clearType = clearType;
	}

	public String getClearThreshold() {
		return clearThreshold;
	}

	public void setClearThreshold(String clearThreshold) {
		this.clearThreshold = clearThreshold;
	}

	public String getClearDeduction() {
		return clearDeduction;
	}

	public void setClearDeduction(String clearDeduction) {
		this.clearDeduction = clearDeduction;
	}

	public Boolean getDecorateRules() {
		return decorateRules;
	}

	public void setDecorateRules(Boolean decorateRules) {
		this.decorateRules = decorateRules;
	}

	public String getAssessmentIndicator() {
		return assessmentIndicator;
	}

	public void setAssessmentIndicator(String assessmentIndicator) {
		this.assessmentIndicator = assessmentIndicator;
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getContractDeadline() {
		return contractDeadline;
	}

	public void setContractDeadline(Date contractDeadline) {
		this.contractDeadline = contractDeadline;
	}

	public Double getManagementFees() {
		return managementFees;
	}

	public void setManagementFees(Double managementFees) {
		this.managementFees = managementFees;
	}

	public String getCardFees() {
		return cardFees;
	}

	public void setCardFees(String cardFees) {
		this.cardFees = cardFees;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getCheckTag() {
		return checkTag;
	}

	public void setCheckTag(String checkTag) {
		this.checkTag = checkTag;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

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

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getAttorney() {
		return attorney;
	}

	public void setAttorney(String attorney) {
		this.attorney = attorney;
	}

	public String getMobilePhone1() {
		return mobilePhone1;
	}

	public void setMobilePhone1(String mobilePhone1) {
		this.mobilePhone1 = mobilePhone1;
	}

	public String getMobilePhone1Name() {
		return mobilePhone1Name;
	}

	public void setMobilePhone1Name(String mobilePhone1Name) {
		this.mobilePhone1Name = mobilePhone1Name;
	}

	public String getMobilePhone2() {
		return mobilePhone2;
	}

	public void setMobilePhone2(String mobilePhone2) {
		this.mobilePhone2 = mobilePhone2;
	}

	public String getMobilePhone2Name() {
		return mobilePhone2Name;
	}

	public void setMobilePhone2Name(String mobilePhone2Name) {
		this.mobilePhone2Name = mobilePhone2Name;
	}

	public String getFixedTelephone() {
		return fixedTelephone;
	}

	public void setFixedTelephone(String fixedTelephone) {
		this.fixedTelephone = fixedTelephone;
	}

	public String getFixedTelephoneName() {
		return fixedTelephoneName;
	}

	public void setFixedTelephoneName(String fixedTelephoneName) {
		this.fixedTelephoneName = fixedTelephoneName;
	}

	public Date getBasicSigningDate() {
		return basicSigningDate;
	}

	public void setBasicSigningDate(Date basicSigningDate) {
		this.basicSigningDate = basicSigningDate;
	}

	public LedgerContractSupplyInfo getLedgerContractSupplyInfo() {
		return ledgerContractSupplyInfo;
	}

	public void setLedgerContractSupplyInfo(
			LedgerContractSupplyInfo ledgerContractSupplyInfo) {
		this.ledgerContractSupplyInfo = ledgerContractSupplyInfo;
	}

	public String getCourseAllInfo() {
		return courseAllInfo;
	}

	public void setCourseAllInfo(String courseAllInfo) {
		this.courseAllInfo = courseAllInfo;
	}

	public List<LedgerContractCourse> getLedgerContractCourseList() {
		return ledgerContractCourseList;
	}

	public void setLedgerContractCourseList(
			List<LedgerContractCourse> ledgerContractCourseList) {
		this.ledgerContractCourseList = ledgerContractCourseList;
	}

	public String getBrandLevel() {
		return brandLevel;
	}

	public void setBrandLevel(String brandLevel) {
		this.brandLevel = brandLevel;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCooperationWay() {
		return cooperationWay;
	}

	public void setCooperationWay(String cooperationWay) {
		this.cooperationWay = cooperationWay;
	}

	public String getBizContractVersion() {
		return bizContractVersion;
	}

	public void setBizContractVersion(String bizContractVersion) {
		this.bizContractVersion = bizContractVersion;
	}
}
