package cn.iocoder.oauth.module.product.controller.admin.brand.vo;

import cn.iocoder.oauth.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.oauth.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @author xiaoxu123
 */
@ApiModel("管理后台 - 品牌分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BrandPageReqVO extends PageParam {

    @ApiModelProperty(value = "分类编号", example = "1")
    private Long categoryId;

    @ApiModelProperty(value = "品牌名称", example = "芋道")
    private String name;

    @ApiModelProperty(value = "状态", example = "0")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "创建时间")
    private Date[] createTime;

}
