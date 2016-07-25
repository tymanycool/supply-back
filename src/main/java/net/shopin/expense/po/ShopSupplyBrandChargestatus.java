package net.shopin.expense.po;

public class ShopSupplyBrandChargestatus {
    private Integer shopSid;

    private Integer supplySid;

    private String brandSid;

    private Double expenseStatus;

    public Integer getShopSid() {
        return shopSid;
    }

    public void setShopSid(Integer shopSid) {
        this.shopSid = shopSid;
    }

    public Integer getSupplySid() {
        return supplySid;
    }

    public void setSupplySid(Integer supplySid) {
        this.supplySid = supplySid;
    }

    public String getBrandSid() {
        return brandSid;
    }

    public void setBrandSid(String brandSid) {
        this.brandSid = brandSid == null ? null : brandSid.trim();
    }

    public Double getExpenseStatus() {
        return expenseStatus;
    }

    public void setExpenseStatus(Double expenseStatus) {
        this.expenseStatus = expenseStatus;
    }
}