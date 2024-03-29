package com.example.mybatis_plus_demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author xiaoxu123
 */
@TableName("User")
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {
    /**
     * @TableId 指定主键列名及主键策略方式
     */
    @TableId(value = "id",type = IdType.NONE)
    private Long id;
    /**
     * 姓名
     *@TableField 指定数据库列名
     */
    @TableField(condition = SqlCondition.LIKE,value = "name")
    private String name;

    /**
     * 年龄
     */
    @TableField(condition = "%s&lt;#{%s}",fill=FieldFill.UPDATE)
    private Integer age;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 直属上级id
     */
    private Long managerId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)//插入时填充
    private Date createdTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)//修改时填充
    private Date updateTime;

    /**
     * 版本
     */
    @Version
    private Integer version;
    /**
     * 0、未删除 1、已删除
     */
    @TableLogic//逻辑删除标识
    @TableField(select = false)//查询的时候不显示
    private Integer deleted;


    /** 备注  非数据库字段需要排除
     * 方法一加入transient 不让该变量序列化 不推荐
     *  方法二加入static 但是lombok不会生成get set方法需要自己手动添加 不推荐
     *  方法三  @TableField(exist = false) 默认是true 改为false表示不是数据库字段 推荐
     * */
    @TableField(exist = false)
    private /*static*/  /*transient*/ String remark;

}
