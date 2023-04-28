package io.microservices.shop.goods.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxu123
 */
@Data
public class Sku implements Serializable {

    private String id;
    private String sn; //商品条码
    private String name; //SKU名称
    private double price; //价格
    private Integer num; //库存数量
    private Integer alert_num; //库存预警数量
    private String image; //商品图片
    private String images; //商品图片
    private Integer weight; //重量
    private Date create_time; //创建时间
    private Date update_time; //创建时间
    private Integer spu_id; //SPUID
    private Integer category_id; //模板ID
    private String category_name; //类目名称
    private String brand_name; //品牌名称
    private String spec; //规格
    private Integer sale_num; //销量
    private Integer comment_num; //评论数
    private Boolean status;	//商品状态
}
