package com.orc.rpc.server.register;

import com.alibaba.fastjson.JSON;
import com.orc.rpc.common.model.Service;
import com.orc.rpc.common.serializer.ZookeeperSerializer;
import org.I0Itec.zkclient.ZkClient;
import sun.net.util.IPAddressUtil;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;

import static com.orc.rpc.common.constants.RpcConstant.*;

/**
 * zk服务注册器，提供服务注册、暴露服务的能力
 * @author xiaoxu123
 */
public class ZookeeperServerRegister extends DefaultServerRegister{
   private ZkClient zkClient;

   public ZookeeperServerRegister(String zkAddress, Integer port, String protocol, Integer weight){
       zkClient = new ZkClient( zkAddress );
       zkClient.setZkSerializer(new ZookeeperSerializer() );
       this.port=port;
       this.protocol=protocol;
       this.weight=weight;
   }

    /**
     * 服务注册
     */
    @Override
    public void register(ServiceObject so) throws Exception {
        super.register( so );
        Service service = new Service();
        String host = InetAddress.getLocalHost().getHostAddress();
        String address = host + ":" + port;
        service.setAddress( address );
        service.setName( so.getClazz().getName() );
        service.setProtocol( protocol );
        service.setWeight( this.weight );
        this.exportService( service );
    }

    /**
     * 服务暴露(其实就是把服务信息保存到Zookeeper上)
     *
     * @param serviceResource
     */
    private void exportService(Service serviceResource) {
        String serviceName = serviceResource.getName();
        String uri = JSON.toJSONString(serviceResource);
        try {
            uri = URLEncoder.encode(uri, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String servicePath = ZK_SERVICE_PATH + PATH_DELIMITER + serviceName + "/service";
        if (!zkClient.exists(servicePath)) {
            // 没有该节点就创建
            zkClient.createPersistent(servicePath, true);
        }
        String uriPath = servicePath + PATH_DELIMITER + uri;
        if (zkClient.exists(uriPath)) {
            // 删除之前的节点
            zkClient.delete(uriPath);
        }
        // 创建一个临时节点，会话失效即被清理
        zkClient.createEphemeral(uriPath);
    }
}
