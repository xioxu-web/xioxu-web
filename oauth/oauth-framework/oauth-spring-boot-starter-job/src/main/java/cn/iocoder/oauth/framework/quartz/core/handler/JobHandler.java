package cn.iocoder.oauth.framework.quartz.core.handler;

/**
 * 任务处理器
 * @author xiaoxu123
 */
public interface JobHandler {

    /**
     *执行任务
     * @param param 参数
     * @return 执行结果
     * @throws Exception
     */
    String execute(String param) throws Exception;
}
