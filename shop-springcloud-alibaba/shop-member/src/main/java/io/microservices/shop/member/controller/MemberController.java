package io.microservices.shop.member.controller;

import io.microservices.shop.bean.Member;
import io.microservices.shop.common.utils.Response;
import io.microservices.shop.common.utils.SecurityUtils;
import io.microservices.shop.common.utils.ServletUtils;
import io.microservices.shop.jwt.utils.ShopJwtTokenUtil;
import io.microservices.shop.member.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/member")
public class MemberController{

    @Resource
    private MemberService memberService;

    /**
     * 通过网关拿到 token 中的 userId，然后根据 userId 查询会员信息
     *
     * @return
     */
    @RequestMapping("/userinfo")
    public Response info(){
        String userId = SecurityUtils.getUserId();
        Member member = memberService.getMemberByUserId(userId);
        return Response.ok().put("member", member);
    }
}