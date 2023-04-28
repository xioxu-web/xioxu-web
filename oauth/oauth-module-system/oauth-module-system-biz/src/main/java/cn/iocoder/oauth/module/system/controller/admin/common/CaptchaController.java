package cn.iocoder.oauth.module.system.controller.admin.common;
import cn.hutool.json.JSONObject;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.module.system.controller.admin.common.vo.CaptchaImageRespVO;
import cn.iocoder.oauth.module.system.service.common.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;

/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 验证码")
@RestController
@RequestMapping("/system/captcha")
@Slf4j
public class CaptchaController {
    private Logger logger= LoggerFactory.getLogger(CaptchaController.class);

    @Resource
    private CaptchaService captchaService;

    @GetMapping("/get-image")
    @ApiOperation("生成图片验证码")
    public CommonResult<CaptchaImageRespVO> getCaptchaImage() {
        return success(captchaService.getCaptchaImage());
    }

}
