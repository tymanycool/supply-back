package net.shopin.expense.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseSupplyAreaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExpenseSupplyAreaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSidIsNull() {
            addCriterion("sid is null");
            return (Criteria) this;
        }

        public Criteria andSidIsNotNull() {
            addCriterion("sid is not null");
            return (Criteria) this;
        }

        public Criteria andSidEqualTo(Long value) {
            addCriterion("sid =", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotEqualTo(Long value) {
            addCriterion("sid <>", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThan(Long value) {
            addCriterion("sid >", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThanOrEqualTo(Long value) {
            addCriterion("sid >=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThan(Long value) {
            addCriterion("sid <", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThanOrEqualTo(Long value) {
            addCriterion("sid <=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidIn(List<Long> values) {
            addCriterion("sid in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotIn(List<Long> values) {
            addCriterion("sid not in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidBetween(Long value1, Long value2) {
            addCriterion("sid between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotBetween(Long value1, Long value2) {
            addCriterion("sid not between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andShopSidIsNull() {
            addCriterion("shop_sid is null");
            return (Criteria) this;
        }

        public Criteria andShopSidIsNotNull() {
            addCriterion("shop_sid is not null");
            return (Criteria) this;
        }

        public Criteria andShopSidEqualTo(String value) {
            addCriterion("shop_sid =", value, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidNotEqualTo(String value) {
            addCriterion("shop_sid <>", value, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidGreaterThan(String value) {
            addCriterion("shop_sid >", value, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidGreaterThanOrEqualTo(String value) {
            addCriterion("shop_sid >=", value, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidLessThan(String value) {
            addCriterion("shop_sid <", value, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidLessThanOrEqualTo(String value) {
            addCriterion("shop_sid <=", value, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidLike(String value) {
            addCriterion("shop_sid like", value, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidNotLike(String value) {
            addCriterion("shop_sid not like", value, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidIn(List<String> values) {
            addCriterion("shop_sid in", values, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidNotIn(List<String> values) {
            addCriterion("shop_sid not in", values, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidBetween(String value1, String value2) {
            addCriterion("shop_sid between", value1, value2, "shopSid");
            return (Criteria) this;
        }

        public Criteria andShopSidNotBetween(String value1, String value2) {
            addCriterion("shop_sid not between", value1, value2, "shopSid");
            return (Criteria) this;
        }

        public Criteria andCategorySidIsNull() {
            addCriterion("category_sid is null");
            return (Criteria) this;
        }

        public Criteria andCategorySidIsNotNull() {
            addCriterion("category_sid is not null");
            return (Criteria) this;
        }

        public Criteria andCategorySidEqualTo(String value) {
            addCriterion("category_sid =", value, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidNotEqualTo(String value) {
            addCriterion("category_sid <>", value, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidGreaterThan(String value) {
            addCriterion("category_sid >", value, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidGreaterThanOrEqualTo(String value) {
            addCriterion("category_sid >=", value, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidLessThan(String value) {
            addCriterion("category_sid <", value, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidLessThanOrEqualTo(String value) {
            addCriterion("category_sid <=", value, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidLike(String value) {
            addCriterion("category_sid like", value, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidNotLike(String value) {
            addCriterion("category_sid not like", value, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidIn(List<String> values) {
            addCriterion("category_sid in", values, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidNotIn(List<String> values) {
            addCriterion("category_sid not in", values, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidBetween(String value1, String value2) {
            addCriterion("category_sid between", value1, value2, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategorySidNotBetween(String value1, String value2) {
            addCriterion("category_sid not between", value1, value2, "categorySid");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIsNull() {
            addCriterion("category_name is null");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIsNotNull() {
            addCriterion("category_name is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryNameEqualTo(String value) {
            addCriterion("category_name =", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotEqualTo(String value) {
            addCriterion("category_name <>", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameGreaterThan(String value) {
            addCriterion("category_name >", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("category_name >=", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLessThan(String value) {
            addCriterion("category_name <", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("category_name <=", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLike(String value) {
            addCriterion("category_name like", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotLike(String value) {
            addCriterion("category_name not like", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIn(List<String> values) {
            addCriterion("category_name in", values, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotIn(List<String> values) {
            addCriterion("category_name not in", values, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameBetween(String value1, String value2) {
            addCriterion("category_name between", value1, value2, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotBetween(String value1, String value2) {
            addCriterion("category_name not between", value1, value2, "categoryName");
            return (Criteria) this;
        }

        public Criteria andSupplySidIsNull() {
            addCriterion("supply_sid is null");
            return (Criteria) this;
        }

        public Criteria andSupplySidIsNotNull() {
            addCriterion("supply_sid is not null");
            return (Criteria) this;
        }

        public Criteria andSupplySidEqualTo(String value) {
            addCriterion("supply_sid =", value, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidNotEqualTo(String value) {
            addCriterion("supply_sid <>", value, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidGreaterThan(String value) {
            addCriterion("supply_sid >", value, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidGreaterThanOrEqualTo(String value) {
            addCriterion("supply_sid >=", value, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidLessThan(String value) {
            addCriterion("supply_sid <", value, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidLessThanOrEqualTo(String value) {
            addCriterion("supply_sid <=", value, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidLike(String value) {
            addCriterion("supply_sid like", value, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidNotLike(String value) {
            addCriterion("supply_sid not like", value, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidIn(List<String> values) {
            addCriterion("supply_sid in", values, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidNotIn(List<String> values) {
            addCriterion("supply_sid not in", values, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidBetween(String value1, String value2) {
            addCriterion("supply_sid between", value1, value2, "supplySid");
            return (Criteria) this;
        }

        public Criteria andSupplySidNotBetween(String value1, String value2) {
            addCriterion("supply_sid not between", value1, value2, "supplySid");
            return (Criteria) this;
        }

        public Criteria andCalDateIsNull() {
            addCriterion("cal_date is null");
            return (Criteria) this;
        }

        public Criteria andCalDateIsNotNull() {
            addCriterion("cal_date is not null");
            return (Criteria) this;
        }

        public Criteria andCalDateEqualTo(Date value) {
            addCriterion("cal_date =", value, "calDate");
            return (Criteria) this;
        }

        public Criteria andCalDateNotEqualTo(Date value) {
            addCriterion("cal_date <>", value, "calDate");
            return (Criteria) this;
        }

        public Criteria andCalDateGreaterThan(Date value) {
            addCriterion("cal_date >", value, "calDate");
            return (Criteria) this;
        }

        public Criteria andCalDateGreaterThanOrEqualTo(Date value) {
            addCriterion("cal_date >=", value, "calDate");
            return (Criteria) this;
        }

        public Criteria andCalDateLessThan(Date value) {
            addCriterion("cal_date <", value, "calDate");
            return (Criteria) this;
        }

        public Criteria andCalDateLessThanOrEqualTo(Date value) {
            addCriterion("cal_date <=", value, "calDate");
            return (Criteria) this;
        }

        public Criteria andCalDateIn(List<Date> values) {
            addCriterion("cal_date in", values, "calDate");
            return (Criteria) this;
        }

        public Criteria andCalDateNotIn(List<Date> values) {
            addCriterion("cal_date not in", values, "calDate");
            return (Criteria) this;
        }

        public Criteria andCalDateBetween(Date value1, Date value2) {
            addCriterion("cal_date between", value1, value2, "calDate");
            return (Criteria) this;
        }

        public Criteria andCalDateNotBetween(Date value1, Date value2) {
            addCriterion("cal_date not between", value1, value2, "calDate");
            return (Criteria) this;
        }

        public Criteria andBrandSidIsNull() {
            addCriterion("brand_sid is null");
            return (Criteria) this;
        }

        public Criteria andBrandSidIsNotNull() {
            addCriterion("brand_sid is not null");
            return (Criteria) this;
        }

        public Criteria andBrandSidEqualTo(String value) {
            addCriterion("brand_sid =", value, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidNotEqualTo(String value) {
            addCriterion("brand_sid <>", value, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidGreaterThan(String value) {
            addCriterion("brand_sid >", value, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidGreaterThanOrEqualTo(String value) {
            addCriterion("brand_sid >=", value, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidLessThan(String value) {
            addCriterion("brand_sid <", value, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidLessThanOrEqualTo(String value) {
            addCriterion("brand_sid <=", value, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidLike(String value) {
            addCriterion("brand_sid like", value, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidNotLike(String value) {
            addCriterion("brand_sid not like", value, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidIn(List<String> values) {
            addCriterion("brand_sid in", values, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidNotIn(List<String> values) {
            addCriterion("brand_sid not in", values, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidBetween(String value1, String value2) {
            addCriterion("brand_sid between", value1, value2, "brandSid");
            return (Criteria) this;
        }

        public Criteria andBrandSidNotBetween(String value1, String value2) {
            addCriterion("brand_sid not between", value1, value2, "brandSid");
            return (Criteria) this;
        }

        public Criteria andCalAreaIsNull() {
            addCriterion("cal_area is null");
            return (Criteria) this;
        }

        public Criteria andCalAreaIsNotNull() {
            addCriterion("cal_area is not null");
            return (Criteria) this;
        }

        public Criteria andCalAreaEqualTo(Double value) {
            addCriterion("cal_area =", value, "calArea");
            return (Criteria) this;
        }

        public Criteria andCalAreaNotEqualTo(Double value) {
            addCriterion("cal_area <>", value, "calArea");
            return (Criteria) this;
        }

        public Criteria andCalAreaGreaterThan(Double value) {
            addCriterion("cal_area >", value, "calArea");
            return (Criteria) this;
        }

        public Criteria andCalAreaGreaterThanOrEqualTo(Double value) {
            addCriterion("cal_area >=", value, "calArea");
            return (Criteria) this;
        }

        public Criteria andCalAreaLessThan(Double value) {
            addCriterion("cal_area <", value, "calArea");
            return (Criteria) this;
        }

        public Criteria andCalAreaLessThanOrEqualTo(Double value) {
            addCriterion("cal_area <=", value, "calArea");
            return (Criteria) this;
        }

        public Criteria andCalAreaIn(List<Double> values) {
            addCriterion("cal_area in", values, "calArea");
            return (Criteria) this;
        }

        public Criteria andCalAreaNotIn(List<Double> values) {
            addCriterion("cal_area not in", values, "calArea");
            return (Criteria) this;
        }

        public Criteria andCalAreaBetween(Double value1, Double value2) {
            addCriterion("cal_area between", value1, value2, "calArea");
            return (Criteria) this;
        }

        public Criteria andCalAreaNotBetween(Double value1, Double value2) {
            addCriterion("cal_area not between", value1, value2, "calArea");
            return (Criteria) this;
        }

        public Criteria andSupplyNameIsNull() {
            addCriterion("supply_name is null");
            return (Criteria) this;
        }

        public Criteria andSupplyNameIsNotNull() {
            addCriterion("supply_name is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyNameEqualTo(String value) {
            addCriterion("supply_name =", value, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameNotEqualTo(String value) {
            addCriterion("supply_name <>", value, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameGreaterThan(String value) {
            addCriterion("supply_name >", value, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameGreaterThanOrEqualTo(String value) {
            addCriterion("supply_name >=", value, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameLessThan(String value) {
            addCriterion("supply_name <", value, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameLessThanOrEqualTo(String value) {
            addCriterion("supply_name <=", value, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameLike(String value) {
            addCriterion("supply_name like", value, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameNotLike(String value) {
            addCriterion("supply_name not like", value, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameIn(List<String> values) {
            addCriterion("supply_name in", values, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameNotIn(List<String> values) {
            addCriterion("supply_name not in", values, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameBetween(String value1, String value2) {
            addCriterion("supply_name between", value1, value2, "supplyName");
            return (Criteria) this;
        }

        public Criteria andSupplyNameNotBetween(String value1, String value2) {
            addCriterion("supply_name not between", value1, value2, "supplyName");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNull() {
            addCriterion("brand_name is null");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNotNull() {
            addCriterion("brand_name is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNameEqualTo(String value) {
            addCriterion("brand_name =", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotEqualTo(String value) {
            addCriterion("brand_name <>", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThan(String value) {
            addCriterion("brand_name >", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThanOrEqualTo(String value) {
            addCriterion("brand_name >=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThan(String value) {
            addCriterion("brand_name <", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThanOrEqualTo(String value) {
            addCriterion("brand_name <=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLike(String value) {
            addCriterion("brand_name like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotLike(String value) {
            addCriterion("brand_name not like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameIn(List<String> values) {
            addCriterion("brand_name in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotIn(List<String> values) {
            addCriterion("brand_name not in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameBetween(String value1, String value2) {
            addCriterion("brand_name between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotBetween(String value1, String value2) {
            addCriterion("brand_name not between", value1, value2, "brandName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}