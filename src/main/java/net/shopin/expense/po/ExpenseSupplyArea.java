package net.shopin.expense.po;

import java.util.Date;

public class ExpenseSupplyArea {
    private Long sid;

    private String shopSid;

    private String categorySid;

    private String categoryName;

    private String supplySid;

    private Date calDate;

    private String brandSid;

    private Double calArea;

    private String supplyName;

    private String brandName;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getShopSid() {
        return shopSid;
    }

    public void setShopSid(String shopSid) {
        this.shopSid = shopSid == null ? null : shopSid.trim();
    }

    public String getCategorySid() {
        return categorySid;
    }

    public void setCategorySid(String categorySid) {
        this.categorySid = categorySid == null ? null : categorySid.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getSupplySid() {
        return supplySid;
    }

    public void setSupplySid(String supplySid) {
        this.supplySid = supplySid == null ? null : supplySid.trim();
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getBrandSid() {
        return brandSid;
    }

    public void setBrandSid(String brandSid) {
        this.brandSid = brandSid == null ? null : brandSid.trim();
    }

    public Double getCalArea() {
        return calArea;
    }

    public void setCalArea(Double calArea) {
        this.calArea = calArea;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName == null ? null : supplyName.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }
}