package io.microservices.shop.member.service;

import io.microservices.shop.bean.Member;

/**
 * 会员业务接口
 * @author xiaoxu123
 */
public interface MemberService {

    Member getMemberByUserId(String userId);
}
