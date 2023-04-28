package com.example.business.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName(value = "t_order")
public class Order {

    @TableId(value = "id")
    Long id;

    String orderNumber;

    Double money;

    Integer status;


}
