package com.orc.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单实体类
 * @author xiaoxu123
 */
public class Order implements Serializable {

    private String orderNo;

    private String orderStatus;

    private String goods;

    private Date createTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
