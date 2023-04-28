package com.orc.rpc.client.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.orc.rpc.client.network.handler.SendHandlerV2;
import com.orc.rpc.common.model.RpcRequest;
import com.orc.rpc.common.model.RpcResponse;
import com.orc.rpc.common.model.Service;
import com.orc.rpc.common.protocol.MessageProtocol;

import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author xiaoxu123
 */
public class NettyNetClient implements NetWorkClient {

    private static Logger logger=LoggerFactory.getLogger( NettyNetClient.class);

    private ExecutorService pool=new ThreadPoolExecutor(4,10,200, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200),new ThreadFactoryBuilder().setNameFormat("rpcClient-%d").build() );

    private NioEventLoopGroup group=new NioEventLoopGroup(4);

    /**
     * 已连接的服务缓存
     * key: 服务地址，格式：ip:port
     */
    public static Map<String, SendHandlerV2> connectedServerNodes = new ConcurrentHashMap<>();

    @Override
    public byte[] sendRequest(byte[] data, Service service) throws InterruptedException {
        return new byte[0];
    }
    @Override
    public RpcResponse sendRequest(RpcRequest rpcRequest, Service service, MessageProtocol messageProtocol) {
        return null;
    }


}
