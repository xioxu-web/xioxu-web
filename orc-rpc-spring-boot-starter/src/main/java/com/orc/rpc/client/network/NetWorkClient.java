package com.orc.rpc.client.network;

import com.orc.rpc.common.model.RpcRequest;
import com.orc.rpc.common.model.RpcResponse;
import com.orc.rpc.common.model.Service;
import com.orc.rpc.common.protocol.MessageProtocol;

/**
 *rpc发起网络请求
 * @author xiaoxu123
 */
public interface NetWorkClient {
    byte[] sendRequest(byte[] data, Service service) throws InterruptedException;

    RpcResponse sendRequest(RpcRequest rpcRequest, Service service, MessageProtocol messageProtocol);
}
