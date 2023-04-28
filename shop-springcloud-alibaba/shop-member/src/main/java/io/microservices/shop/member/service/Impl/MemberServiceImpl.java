package io.microservices.shop.member.service.Impl;

import io.microservices.shop.bean.Member;
import io.microservices.shop.member.mapper.MemberMapper;
import io.microservices.shop.member.service.MemberService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author xiaoxu123
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    @Transactional(rollbackFor =Exception.class)
    public Member getMemberByUserId(String userId) {
        return memberMapper.getMemberByUserId(userId);
    }
}
