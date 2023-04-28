package cn.iocoder.oauth.module.pay.service.notify;

import cn.iocoder.oauth.module.pay.service.notify.dto.PayNotifyTaskCreateReqDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 *支付通知接口
 * @author xiaoxu123
 */
public interface PayNotifyService {

    /**
     * 创建支付通知任务
     *
     * @param reqDTO 任务信息
     */
    void createPayNotifyTask(@Valid PayNotifyTaskCreateReqDTO reqDTO);

    /**
     * 执行支付通知
     *
     * 注意，该方法提供给定时任务调用。目前是 yudao-server 进行调用
     * @return 通知数量
     */
    int executeNotify() throws InterruptedException;
}
