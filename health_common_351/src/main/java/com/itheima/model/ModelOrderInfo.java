package com.itheima.model;

import java.io.Serializable;

public class ModelOrderInfo implements Serializable{

    private String setmealId;
    private String name;
    private String sex;
    private String telephone;
    private String validateCode;
    private String idCard;
    private String orderDate;

    public ModelOrderInfo() {
    }

    public String getSetmealId() {
        return setmealId;
    }

    public void setSetmealId(String setmealId) {
        this.setmealId = setmealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "ModelOrderInfo{" +
                "setmealId='" + setmealId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", telephone='" + telephone + '\'' +
                ", validateCode='" + validateCode + '\'' +
                ", idCard='" + idCard + '\'' +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
