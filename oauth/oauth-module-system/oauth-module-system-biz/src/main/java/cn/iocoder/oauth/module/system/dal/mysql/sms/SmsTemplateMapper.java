package cn.iocoder.oauth.module.system.dal.mysql.sms;

import cn.iocoder.oauth.framework.common.pojo.PageParam;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.template.SmsTemplatePageReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.sms.SmsTemplateDO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @author xiaoxu123
 */
@Mapper
public interface SmsTemplateMapper extends BaseMapperX<SmsTemplateDO> {

    @Select("select count(*) from system_sms_template WHERE update_time>#{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);


    default SmsTemplateDO selectByCode(String code){
      return selectOne(SmsTemplateDO::getCode,code);
    }


    default PageResult<SmsTemplateDO> selectPage(SmsTemplatePageReqVO reqVO) {
        return selectPage(reqVO,new LambdaQueryWrapperX<SmsTemplateDO>()
                 .eqIfPresent(SmsTemplateDO::getType,reqVO.getType())
                 .eqIfPresent(SmsTemplateDO::getStatus,reqVO.getStatus())
                 .likeIfPresent(SmsTemplateDO::getCode,reqVO.getCode())
                 .likeIfPresent(SmsTemplateDO::getContent,reqVO.getContent())
                 .likeIfPresent(SmsTemplateDO::getApiTemplateId,reqVO.getApiTemplateId())
                 .eqIfPresent(SmsTemplateDO::getChannelId,reqVO.getChannelId())
                 .betweenIfPresent(SmsTemplateDO::getCreateTime,reqVO.getCreateTime())
                 .orderByDesc(SmsTemplateDO::getId));
    }

    default Long selectCountByChannelId(Long channelId) {
        return selectCount(SmsTemplateDO::getChannelId, channelId);
    }


}
