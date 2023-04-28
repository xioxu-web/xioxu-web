package io.microservices.shop.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.microservices.shop.bean.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaoxu123
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 查询会员详情
     * @param userId
     * @return Member
     */
    Member getMemberByUserId(@Param("userId") String userId);
}
