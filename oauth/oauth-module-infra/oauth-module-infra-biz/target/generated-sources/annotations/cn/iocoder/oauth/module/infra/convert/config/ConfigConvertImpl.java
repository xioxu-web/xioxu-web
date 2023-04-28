package cn.iocoder.oauth.module.infra.convert.config;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.config.vo.ConfigCreateReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.config.vo.ConfigRespVO;
import cn.iocoder.oauth.module.infra.controller.admin.config.vo.ConfigUpdateReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.config.ConfigDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T14:54:50+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class ConfigConvertImpl implements ConfigConvert {

    @Override
    public PageResult<ConfigRespVO> convertPage(PageResult<ConfigDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<ConfigRespVO> pageResult = new PageResult<ConfigRespVO>();

        pageResult.setList( configDOListToConfigRespVOList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );

        return pageResult;
    }

    @Override
    public ConfigRespVO convert(ConfigDO bean) {
        if ( bean == null ) {
            return null;
        }

        ConfigRespVO configRespVO = new ConfigRespVO();

        configRespVO.setKey( bean.getConfigKey() );
        configRespVO.setCategory( bean.getCategory() );
        configRespVO.setName( bean.getName() );
        configRespVO.setValue( bean.getValue() );
        configRespVO.setVisible( bean.getVisible() );
        configRespVO.setRemark( bean.getRemark() );
        configRespVO.setId( bean.getId() );
        configRespVO.setType( bean.getType() );
        configRespVO.setCreateTime( bean.getCreateTime() );

        return configRespVO;
    }

    @Override
    public ConfigDO convert(ConfigCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        ConfigDO configDO = new ConfigDO();

        configDO.setConfigKey( bean.getKey() );
        configDO.setCategory( bean.getCategory() );
        configDO.setName( bean.getName() );
        configDO.setValue( bean.getValue() );
        configDO.setVisible( bean.getVisible() );
        configDO.setRemark( bean.getRemark() );

        return configDO;
    }

    @Override
    public ConfigDO convert(ConfigUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        ConfigDO configDO = new ConfigDO();

        configDO.setId( bean.getId() );
        configDO.setCategory( bean.getCategory() );
        configDO.setName( bean.getName() );
        configDO.setValue( bean.getValue() );
        configDO.setVisible( bean.getVisible() );
        configDO.setRemark( bean.getRemark() );

        return configDO;
    }

    protected List<ConfigRespVO> configDOListToConfigRespVOList(List<ConfigDO> list) {
        if ( list == null ) {
            return null;
        }

        List<ConfigRespVO> list1 = new ArrayList<ConfigRespVO>( list.size() );
        for ( ConfigDO configDO : list ) {
            list1.add( convert( configDO ) );
        }

        return list1;
    }
}
