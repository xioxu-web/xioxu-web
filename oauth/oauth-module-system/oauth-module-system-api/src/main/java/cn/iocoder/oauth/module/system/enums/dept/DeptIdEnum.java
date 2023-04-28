package cn.iocoder.oauth.module.system.enums.dept;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 部门编号枚举
 * @author xiaoxu123
 */
@Getter
@AllArgsConstructor
public enum DeptIdEnum {

    /**
     * 根节点
     */
    ROOT(0L);

    private final Long id;

}
