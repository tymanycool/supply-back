package net.shopin.ledger.po;

import java.util.Date;
import java.util.List;

public class LedgerQualificationCustom {

	/*
	 * 以下保存的是资质台账基本信息表和供应商基本信息表的数据
	 */
	private Integer id;

	private Date expirationDate;

	private String status;

	private String mobilePhone1;

	private String mobilePhone1Name;

	private String mobilePhone2;

	private String mobilePhone2Name;

	private String fixedTelephone;

	private String fixedTelephoneName;

	private String brandLevel;

	/*
	 * 供应商表存储的数据
	 */
	private String supplierCode;				//供应商编码

	private String supplierName;				//供应商名称

	private String businessLicense;				//营业执照

	private Boolean nationalTaxRegistration;	//国税登记证

	private Boolean landTaxRegistration;		//地税登记证

	private String generalTaxpayer;				//一般纳税人

	private Boolean isblacklist;				//黑名单
	
	private LedgerQualificationSupplyInfo ledgerQualificationSupplyInfo;//供应商基本信息

	private String category;

	private String brandName;

	private String registrationNumber;

	private String approvedCategory;

	private Date endDate;

	private Date applicationDate;

	private String trademarkHolderName;

	private String certificateType;

	private String certificateNumber;

	private String singleStoreAuthorization;//大文本

	private String actingLevel;

	private Date firstProxyDate;

	private String firstProxyAnnotation;

	private Date secondProxyDate;

	private String secondProxyAnnotation;

	private Date thirdProxyDate;

	private String thirdProxyAnnotation;

	private Date forthProxyDate;

	private String forthProxyAnnotation;

	private Date fifthProxyDate;

	private String fifthProxyAnnotation;
	
	private Date sixthProxyDate;
	
	private String sixthProxyAnnotation;
	
	private String declaration;

	private String descriptionProblem;//大文本

	private Date informationMaintenanceDate;

	private String informationMaintenanceName;

	private String approvalStatus;

	private Date reviewDate;

	private String reviewName;

	private String mark;//大文本

	private Boolean valid;
	
	/*
	 * 以下保存的是资质台账质检表存储的数据
	 */
	private String inspectionAllInfo;//所有质检信息json字符串
	
	private List<LedgerQualificationInspection> ledgerQualificationInspectionList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getBrandLevel() {
		return brandLevel;
	}

	public void setBrandLevel(String brandLevel) {
		this.brandLevel = brandLevel;
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

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public Boolean getNationalTaxRegistration() {
		return nationalTaxRegistration;
	}

	public void setNationalTaxRegistration(Boolean nationalTaxRegistration) {
		this.nationalTaxRegistration = nationalTaxRegistration;
	}

	public Boolean getLandTaxRegistration() {
		return landTaxRegistration;
	}

	public void setLandTaxRegistration(Boolean landTaxRegistration) {
		this.landTaxRegistration = landTaxRegistration;
	}

	public String getGeneralTaxpayer() {
		return generalTaxpayer;
	}

	public void setGeneralTaxpayer(String generalTaxpayer) {
		this.generalTaxpayer = generalTaxpayer;
	}

	public Boolean getIsblacklist() {
		return isblacklist;
	}

	public void setIsblacklist(Boolean isblacklist) {
		this.isblacklist = isblacklist;
	}

	public LedgerQualificationSupplyInfo getLedgerQualificationSupplyInfo() {
		return ledgerQualificationSupplyInfo;
	}

	public void setLedgerQualificationSupplyInfo(
			LedgerQualificationSupplyInfo ledgerQualificationSupplyInfo) {
		this.ledgerQualificationSupplyInfo = ledgerQualificationSupplyInfo;
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

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getApprovedCategory() {
		return approvedCategory;
	}

	public void setApprovedCategory(String approvedCategory) {
		this.approvedCategory = approvedCategory;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getTrademarkHolderName() {
		return trademarkHolderName;
	}

	public void setTrademarkHolderName(String trademarkHolderName) {
		this.trademarkHolderName = trademarkHolderName;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getSingleStoreAuthorization() {
		return singleStoreAuthorization;
	}

	public void setSingleStoreAuthorization(String singleStoreAuthorization) {
		this.singleStoreAuthorization = singleStoreAuthorization;
	}

	public String getActingLevel() {
		return actingLevel;
	}

	public void setActingLevel(String actingLevel) {
		this.actingLevel = actingLevel;
	}

	public Date getFirstProxyDate() {
		return firstProxyDate;
	}

	public void setFirstProxyDate(Date firstProxyDate) {
		this.firstProxyDate = firstProxyDate;
	}

	public String getFirstProxyAnnotation() {
		return firstProxyAnnotation;
	}

	public void setFirstProxyAnnotation(String firstProxyAnnotation) {
		this.firstProxyAnnotation = firstProxyAnnotation;
	}

	public Date getSecondProxyDate() {
		return secondProxyDate;
	}

	public void setSecondProxyDate(Date secondProxyDate) {
		this.secondProxyDate = secondProxyDate;
	}

	public String getSecondProxyAnnotation() {
		return secondProxyAnnotation;
	}

	public void setSecondProxyAnnotation(String secondProxyAnnotation) {
		this.secondProxyAnnotation = secondProxyAnnotation;
	}

	public Date getThirdProxyDate() {
		return thirdProxyDate;
	}

	public void setThirdProxyDate(Date thirdProxyDate) {
		this.thirdProxyDate = thirdProxyDate;
	}

	public String getThirdProxyAnnotation() {
		return thirdProxyAnnotation;
	}

	public void setThirdProxyAnnotation(String thirdProxyAnnotation) {
		this.thirdProxyAnnotation = thirdProxyAnnotation;
	}

	public Date getForthProxyDate() {
		return forthProxyDate;
	}

	public void setForthProxyDate(Date forthProxyDate) {
		this.forthProxyDate = forthProxyDate;
	}

	public String getForthProxyAnnotation() {
		return forthProxyAnnotation;
	}

	public void setForthProxyAnnotation(String forthProxyAnnotation) {
		this.forthProxyAnnotation = forthProxyAnnotation;
	}

	public Date getFifthProxyDate() {
		return fifthProxyDate;
	}

	public void setFifthProxyDate(Date fifthProxyDate) {
		this.fifthProxyDate = fifthProxyDate;
	}

	public String getFifthProxyAnnotation() {
		return fifthProxyAnnotation;
	}

	public void setFifthProxyAnnotation(String fifthProxyAnnotation) {
		this.fifthProxyAnnotation = fifthProxyAnnotation;
	}

	

	public Date getSixthProxyDate() {
		return sixthProxyDate;
	}

	public void setSixthProxyDate(Date sixthProxyDate) {
		this.sixthProxyDate = sixthProxyDate;
	}

	public String getSixthProxyAnnotation() {
		return sixthProxyAnnotation;
	}

	public void setSixthProxyAnnotation(String sixthProxyAnnotation) {
		this.sixthProxyAnnotation = sixthProxyAnnotation;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public String getDescriptionProblem() {
		return descriptionProblem;
	}

	public void setDescriptionProblem(String descriptionProblem) {
		this.descriptionProblem = descriptionProblem;
	}

	public Date getInformationMaintenanceDate() {
		return informationMaintenanceDate;
	}

	public void setInformationMaintenanceDate(Date informationMaintenanceDate) {
		this.informationMaintenanceDate = informationMaintenanceDate;
	}

	public String getInformationMaintenanceName() {
		return informationMaintenanceName;
	}

	public void setInformationMaintenanceName(String informationMaintenanceName) {
		this.informationMaintenanceName = informationMaintenanceName;
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

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getInspectionAllInfo() {
		return inspectionAllInfo;
	}

	public void setInspectionAllInfo(String inspectionAllInfo) {
		this.inspectionAllInfo = inspectionAllInfo;
	}

	public List<LedgerQualificationInspection> getLedgerQualificationInspectionList() {
		return ledgerQualificationInspectionList;
	}

	public void setLedgerQualificationInspectionList(
			List<LedgerQualificationInspection> ledgerQualificationInspectionList) {
		this.ledgerQualificationInspectionList = ledgerQualificationInspectionList;
	}

	@Override
	public String toString() {
		return "LedgerQualificationCustom [id=" + id + ", expirationDate=" + expirationDate + ", status=" + status
				+ ", mobilePhone1=" + mobilePhone1 + ", mobilePhone1Name=" + mobilePhone1Name + ", mobilePhone2="
				+ mobilePhone2 + ", mobilePhone2Name=" + mobilePhone2Name + ", fixedTelephone=" + fixedTelephone
				+ ", fixedTelephoneName=" + fixedTelephoneName + ", brandLevel=" + brandLevel + ", supplierCode="
				+ supplierCode + ", supplierName=" + supplierName + ", businessLicense=" + businessLicense
				+ ", nationalTaxRegistration=" + nationalTaxRegistration + ", landTaxRegistration="
				+ landTaxRegistration + ", generalTaxpayer=" + generalTaxpayer + ", isblacklist=" + isblacklist
				+ ", ledgerQualificationSupplyInfo=" + ledgerQualificationSupplyInfo + ", category=" + category
				+ ", brandName=" + brandName + ", registrationNumber=" + registrationNumber + ", approvedCategory="
				+ approvedCategory + ", endDate=" + endDate + ", applicationDate=" + applicationDate
				+ ", trademarkHolderName=" + trademarkHolderName + ", certificateType=" + certificateType
				+ ", certificateNumber=" + certificateNumber + ", singleStoreAuthorization=" + singleStoreAuthorization
				+ ", actingLevel=" + actingLevel + ", firstProxyDate=" + firstProxyDate + ", firstProxyAnnotation="
				+ firstProxyAnnotation + ", secondProxyDate=" + secondProxyDate + ", secondProxyAnnotation="
				+ secondProxyAnnotation + ", thirdProxyDate=" + thirdProxyDate + ", thirdProxyAnnotation="
				+ thirdProxyAnnotation + ", forthProxyDate=" + forthProxyDate + ", forthProxyAnnotation="
				+ forthProxyAnnotation + ", fifthProxyDate=" + fifthProxyDate + ", fifthProxyAnnotation="
				+ fifthProxyAnnotation + ", declaration=" + declaration + ", descriptionProblem=" + descriptionProblem
				+ ", informationMaintenanceDate=" + informationMaintenanceDate + ", informationMaintenanceName="
				+ informationMaintenanceName + ", approvalStatus=" + approvalStatus + ", reviewDate=" + reviewDate
				+ ", reviewName=" + reviewName + ", mark=" + mark + ", valid=" + valid + ", inspectionAllInfo="
				+ inspectionAllInfo + ", ledgerQualificationInspectionList=" + ledgerQualificationInspectionList + "]";
	}

}
