package com.distributed.demo.handler.limit;

import java.util.List;

/**
 * @author xiaoxu123
 */
public interface LimiterPolicy {

    /**
     * 转成字符串数组，数组顺序与脚本取参顺序有关
     * @return
     */
    List<String> toParams();

}
