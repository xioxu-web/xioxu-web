package com.distributed.demo.handler.limit;

import com.distributed.demo.handler.utils.RedisUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.InputStream;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xiaoxu123
 * @deprecated  限流器抽象父类
 */
@Slf4j
public class AbstractLimiterStrategy implements LimiterStrategy {

    // 这里是避免重复读取文件与防止并发问题
    private final static ConcurrentMap<String,String> scriptMapping = Maps.newConcurrentMap();

    private static final String SCRIPT_FILE_NAME = "lua/bucketLimter.lua";

    //lua 脚本路径
    private String scriptPath;

    //lua 脚本所需参数
    private LimiterPolicy limiterPolicy;

    //lua 脚本内容
    private String script;


    /**
     * 抽象父类限流器的构造器
     * @param scriptPath
     * @param limiterPolicy 一个参数的封装类
     */
    public AbstractLimiterStrategy(String scriptPath, LimiterPolicy limiterPolicy) {

        Assert.notNull(scriptPath, "scriptPathv is null");
        this.scriptPath = scriptPath;
        Assert.notNull(limiterPolicy,"limiterPolicy Can't NULL");
        this.limiterPolicy = limiterPolicy;
        this.init();
    }
    public AbstractLimiterStrategy(LimiterPolicy limiterPolicy) {
        this.scriptPath = this.LimiterFilePath();
        Assert.notNull(limiterPolicy,"limiterPolicy Can't NULL");
        this.limiterPolicy = limiterPolicy;
        this.init();
    }

    // 这个抽象方法是获取文件路径的
    public String LimiterFilePath() {
        return SCRIPT_FILE_NAME;
    }

    /**
     * 初始化限流器脚本内容
     */
    private void init() {
        String mapScript = scriptMapping.get(this.scriptPath);
        if(StringUtils.isBlank(mapScript)){
            try {
                // 构建获取 lua 脚本的脚本
                //classpath: 扫描的是 resources 目录下的
                // 获取资源
                ResourceScriptSource resourceScriptSource = new ResourceScriptSource(new ClassPathResource(this.scriptPath));
                InputStream inputStream = resourceScriptSource.getResource().getInputStream();
                byte[] scriptBytes = ByteStreams.toByteArray(inputStream);
                scriptMapping.putIfAbsent(this.scriptPath,new String(scriptBytes));
            } catch (Exception e) {
                log.error("init limiter error: The file may not exist", e);
                throw new RuntimeException(e);
            }
        }
        this.script = scriptMapping.get(this.scriptPath);
    }


    @Override
    public boolean access(String key) {
        try(Jedis jedis=RedisUtil.getJedis()){
            // 调用 eval 方法，参数分别是：脚本内容、Keys 集合，传入参数集合
            Assert.notNull(jedis,"jedis can not null");
            Long remain = (Long)jedis.eval(this.script, Lists.asList(key, new String[]{}), limiterPolicy.toParams());
            log.info("限流器类别:{} | key :{} 限流器内许可数量为:{} ", limiterPolicy.getClass().getSimpleName(), key, remain);
            return remain > 0;
        }catch (Exception e){
            log.error("限流器调用错误",e);
            return false;
        }
    }
}

