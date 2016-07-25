package net.shopin.expense.po;

public class ExpenseAreaChangeDay {
    private Integer sid;

    private String month;

    private String changeDay;

    private String memo;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getChangeDay() {
        return changeDay;
    }

    public void setChangeDay(String changeDay) {
        this.changeDay = changeDay == null ? null : changeDay.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}