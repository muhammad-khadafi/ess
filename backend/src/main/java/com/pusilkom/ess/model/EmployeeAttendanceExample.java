package com.pusilkom.ess.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EmployeeAttendanceExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public EmployeeAttendanceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterionForJDBCDate("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterionForJDBCDate("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterionForJDBCDate("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterionForJDBCDate("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andClockInIsNull() {
            addCriterion("clock_in is null");
            return (Criteria) this;
        }

        public Criteria andClockInIsNotNull() {
            addCriterion("clock_in is not null");
            return (Criteria) this;
        }

        public Criteria andClockInEqualTo(Date value) {
            addCriterion("clock_in =", value, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInNotEqualTo(Date value) {
            addCriterion("clock_in <>", value, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInGreaterThan(Date value) {
            addCriterion("clock_in >", value, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInGreaterThanOrEqualTo(Date value) {
            addCriterion("clock_in >=", value, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInLessThan(Date value) {
            addCriterion("clock_in <", value, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInLessThanOrEqualTo(Date value) {
            addCriterion("clock_in <=", value, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInIn(List<Date> values) {
            addCriterion("clock_in in", values, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInNotIn(List<Date> values) {
            addCriterion("clock_in not in", values, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInBetween(Date value1, Date value2) {
            addCriterion("clock_in between", value1, value2, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInNotBetween(Date value1, Date value2) {
            addCriterion("clock_in not between", value1, value2, "clockIn");
            return (Criteria) this;
        }

        public Criteria andClockInLocationIsNull() {
            addCriterion("clock_in_location is null");
            return (Criteria) this;
        }

        public Criteria andClockInLocationIsNotNull() {
            addCriterion("clock_in_location is not null");
            return (Criteria) this;
        }

        public Criteria andClockInLocationEqualTo(String value) {
            addCriterion("clock_in_location =", value, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationNotEqualTo(String value) {
            addCriterion("clock_in_location <>", value, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationGreaterThan(String value) {
            addCriterion("clock_in_location >", value, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationGreaterThanOrEqualTo(String value) {
            addCriterion("clock_in_location >=", value, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationLessThan(String value) {
            addCriterion("clock_in_location <", value, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationLessThanOrEqualTo(String value) {
            addCriterion("clock_in_location <=", value, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationLike(String value) {
            addCriterion("clock_in_location like", value, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationNotLike(String value) {
            addCriterion("clock_in_location not like", value, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationIn(List<String> values) {
            addCriterion("clock_in_location in", values, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationNotIn(List<String> values) {
            addCriterion("clock_in_location not in", values, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationBetween(String value1, String value2) {
            addCriterion("clock_in_location between", value1, value2, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockInLocationNotBetween(String value1, String value2) {
            addCriterion("clock_in_location not between", value1, value2, "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutIsNull() {
            addCriterion("clock_out is null");
            return (Criteria) this;
        }

        public Criteria andClockOutIsNotNull() {
            addCriterion("clock_out is not null");
            return (Criteria) this;
        }

        public Criteria andClockOutEqualTo(Date value) {
            addCriterion("clock_out =", value, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutNotEqualTo(Date value) {
            addCriterion("clock_out <>", value, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutGreaterThan(Date value) {
            addCriterion("clock_out >", value, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutGreaterThanOrEqualTo(Date value) {
            addCriterion("clock_out >=", value, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutLessThan(Date value) {
            addCriterion("clock_out <", value, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutLessThanOrEqualTo(Date value) {
            addCriterion("clock_out <=", value, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutIn(List<Date> values) {
            addCriterion("clock_out in", values, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutNotIn(List<Date> values) {
            addCriterion("clock_out not in", values, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutBetween(Date value1, Date value2) {
            addCriterion("clock_out between", value1, value2, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutNotBetween(Date value1, Date value2) {
            addCriterion("clock_out not between", value1, value2, "clockOut");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationIsNull() {
            addCriterion("clock_out_location is null");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationIsNotNull() {
            addCriterion("clock_out_location is not null");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationEqualTo(String value) {
            addCriterion("clock_out_location =", value, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationNotEqualTo(String value) {
            addCriterion("clock_out_location <>", value, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationGreaterThan(String value) {
            addCriterion("clock_out_location >", value, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationGreaterThanOrEqualTo(String value) {
            addCriterion("clock_out_location >=", value, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationLessThan(String value) {
            addCriterion("clock_out_location <", value, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationLessThanOrEqualTo(String value) {
            addCriterion("clock_out_location <=", value, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationLike(String value) {
            addCriterion("clock_out_location like", value, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationNotLike(String value) {
            addCriterion("clock_out_location not like", value, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationIn(List<String> values) {
            addCriterion("clock_out_location in", values, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationNotIn(List<String> values) {
            addCriterion("clock_out_location not in", values, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationBetween(String value1, String value2) {
            addCriterion("clock_out_location between", value1, value2, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationNotBetween(String value1, String value2) {
            addCriterion("clock_out_location not between", value1, value2, "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andActivityDetailIsNull() {
            addCriterion("activity_detail is null");
            return (Criteria) this;
        }

        public Criteria andActivityDetailIsNotNull() {
            addCriterion("activity_detail is not null");
            return (Criteria) this;
        }

        public Criteria andActivityDetailEqualTo(String value) {
            addCriterion("activity_detail =", value, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailNotEqualTo(String value) {
            addCriterion("activity_detail <>", value, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailGreaterThan(String value) {
            addCriterion("activity_detail >", value, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailGreaterThanOrEqualTo(String value) {
            addCriterion("activity_detail >=", value, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailLessThan(String value) {
            addCriterion("activity_detail <", value, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailLessThanOrEqualTo(String value) {
            addCriterion("activity_detail <=", value, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailLike(String value) {
            addCriterion("activity_detail like", value, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailNotLike(String value) {
            addCriterion("activity_detail not like", value, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailIn(List<String> values) {
            addCriterion("activity_detail in", values, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailNotIn(List<String> values) {
            addCriterion("activity_detail not in", values, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailBetween(String value1, String value2) {
            addCriterion("activity_detail between", value1, value2, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andActivityDetailNotBetween(String value1, String value2) {
            addCriterion("activity_detail not between", value1, value2, "activityDetail");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIsNull() {
            addCriterion("employee_id is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIsNotNull() {
            addCriterion("employee_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdEqualTo(Integer value) {
            addCriterion("employee_id =", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotEqualTo(Integer value) {
            addCriterion("employee_id <>", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThan(Integer value) {
            addCriterion("employee_id >", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("employee_id >=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThan(Integer value) {
            addCriterion("employee_id <", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThanOrEqualTo(Integer value) {
            addCriterion("employee_id <=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIn(List<Integer> values) {
            addCriterion("employee_id in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotIn(List<Integer> values) {
            addCriterion("employee_id not in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdBetween(Integer value1, Integer value2) {
            addCriterion("employee_id between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("employee_id not between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {
            addCriterion("duration is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {
            addCriterion("duration is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(Float value) {
            addCriterion("duration =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(Float value) {
            addCriterion("duration <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(Float value) {
            addCriterion("duration >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(Float value) {
            addCriterion("duration >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(Float value) {
            addCriterion("duration <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(Float value) {
            addCriterion("duration <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<Float> values) {
            addCriterion("duration in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<Float> values) {
            addCriterion("duration not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(Float value1, Float value2) {
            addCriterion("duration between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(Float value1, Float value2) {
            addCriterion("duration not between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNull() {
            addCriterion("is_valid is null");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNotNull() {
            addCriterion("is_valid is not null");
            return (Criteria) this;
        }

        public Criteria andIsValidEqualTo(Boolean value) {
            addCriterion("is_valid =", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotEqualTo(Boolean value) {
            addCriterion("is_valid <>", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThan(Boolean value) {
            addCriterion("is_valid >", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_valid >=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThan(Boolean value) {
            addCriterion("is_valid <", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThanOrEqualTo(Boolean value) {
            addCriterion("is_valid <=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidIn(List<Boolean> values) {
            addCriterion("is_valid in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotIn(List<Boolean> values) {
            addCriterion("is_valid not in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidBetween(Boolean value1, Boolean value2) {
            addCriterion("is_valid between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_valid not between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andClockInLocationLikeInsensitive(String value) {
            addCriterion("upper(clock_in_location) like", value.toUpperCase(), "clockInLocation");
            return (Criteria) this;
        }

        public Criteria andClockOutLocationLikeInsensitive(String value) {
            addCriterion("upper(clock_out_location) like", value.toUpperCase(), "clockOutLocation");
            return (Criteria) this;
        }

        public Criteria andActivityDetailLikeInsensitive(String value) {
            addCriterion("upper(activity_detail) like", value.toUpperCase(), "activityDetail");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table employee_attendance
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table employee_attendance
     *
     * @mbg.generated
     */
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