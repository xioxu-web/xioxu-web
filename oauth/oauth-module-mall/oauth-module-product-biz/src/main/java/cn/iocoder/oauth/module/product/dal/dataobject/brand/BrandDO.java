package cn.iocoder.oauth.module.product.dal.dataobject.brand;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

// TODO @JeromeSoar：Product 前缀

/**
 * 品牌 DO
 *
 * @author admin
 */
@TableName("product_brand")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDO extends BaseDO {

    /**
     * 品牌编号
     */
    @TableId
    private Long id;
    /**
     * 分类编号
     */
    private Long categoryId;
    /**
     * 品牌名称
     */
    private String name;
    /**
     * 品牌图片
     */
    private String bannerUrl;
    /**
     * 品牌排序
     */
    private Integer sort;
    /**
     * 品牌描述
     */
    private String description;
    /**
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
