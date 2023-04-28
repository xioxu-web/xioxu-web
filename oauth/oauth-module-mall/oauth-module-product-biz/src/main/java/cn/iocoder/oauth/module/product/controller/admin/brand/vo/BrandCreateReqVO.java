package cn.iocoder.oauth.module.product.controller.admin.brand.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author xiaoxu123
 */
@ApiModel("管理后台 - 品牌创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BrandCreateReqVO extends BrandBaseVO {
}
