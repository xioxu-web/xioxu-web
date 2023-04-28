package io.microservices.shop.goods.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.sql.Clob;

/**
 * @author xiaoxu123
 */
@Data
public class Spu implements Serializable {

    private String id;
    private String sn; //货号
    private String name; //SPU名
    private String caption; //副标题
    private Integer brand_id; //品牌ID
    private Integer category1_id; //一级分类
    private Integer category2_id; //二级分类
    private Integer category3_id; //三级分类
    private Integer template_id; //模板ID
    private Integer freight_id;	//运费模板id
    private String image; //图片
    private String images; //图片列表
    private String sale_service; //售后服务
    private Clob introduction; //介绍
    private String spec_items; //规格列表
    private String para_items; //参数列表	VARCHAR
    private Integer sale_num; //销量
    private Integer comment_num; //评论数
    private Boolean is_marketable; //是否上架
    private Boolean is_enable_spec;	//是否启用规格
    private Boolean is_delete;	//是否删除
    private Boolean status;	//审核状态

}
