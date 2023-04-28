package cn.iocoder.oauth.framework.datapermission.core.rule.dept;

/**
 * {@link cn.iocoder.oauth.framework.datapermission.core.rule.dept.DeptDataPermissionRule} 的自定义配置接口
 *
 * @author 芋道源码
 */
@FunctionalInterface
public interface DeptDataPermissionRuleCustomizer {

    /**
     * 自定义该权限规则
     * 1. 调用 {@link cn.iocoder.oauth.framework.datapermission.core.rule.dept.DeptDataPermissionRule#addDeptColumn(Class, String)} 方法，配置基于 dept_id 的过滤规则
     * 2. 调用 {@link cn.iocoder.oauth.framework.datapermission.core.rule.dept.DeptDataPermissionRule#addUserColumn(Class, String)} 方法，配置基于 user_id 的过滤规则
     *
     * @param rule 权限规则
     */
    void customize(cn.iocoder.oauth.framework.datapermission.core.rule.dept.DeptDataPermissionRule rule);

}
