package cn.iocoder.oauth.module.member.dal.dataobject.user;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员用户 DO
 *
 * uk_mobile 索引：基于 {@link #mobile} 字段
 *
 * @author admin
 */
@TableName(value = "member_user", autoResultMap = true)
@KeySequence("member_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode()
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUserDO implements Serializable {

    /**
     * 用户ID
     */
    @TableId
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 帐号状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

    /**
     * 手机
     */
    private String mobile;
    /**
     * 加密后的密码
     *
     * 因为目前使用 {@link BCryptPasswordEncoder} 加密器，所以无需自己处理 salt 盐
     */
    private String password;
    /**
     * 注册 IP
     */
    private String registerIp;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;

}
