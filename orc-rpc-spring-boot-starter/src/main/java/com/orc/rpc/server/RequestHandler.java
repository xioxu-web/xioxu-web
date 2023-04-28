package com.orc.rpc.server;
import com.orc.rpc.common.constants.RpcStatusEnum;
import com.orc.rpc.common.model.RpcRequest;
import com.orc.rpc.common.model.RpcResponse;
import com.orc.rpc.common.protocol.MessageProtocol;
import com.orc.rpc.server.register.ServerRegister;
import com.orc.rpc.server.register.ServiceObject;
import java.lang.reflect.Method;


/**
 * @author xiaoxu123
 */
public class RequestHandler {

    private MessageProtocol protocol;


    private ServerRegister serverRegister;

    public RequestHandler(MessageProtocol protocol, ServerRegister serverRegister) {
        this.protocol = protocol;
        this.serverRegister = serverRegister;
    }


    public byte[] handleRequest(byte[] data) throws Exception {
        // 1.解组消息
        RpcRequest req = this.protocol.unmarshallingRequest(data);

        // 2.查找服务对应
        ServiceObject so = serverRegister.getServiceObject(req.getServiceName());

        RpcResponse response = null;

        if (so == null){
            response = new RpcResponse(RpcStatusEnum.NOT_FOUND);

        }else {
            try {
                // 3.反射调用对应的方法过程
                Method method = so.getClazz().getMethod(req.getMethod(), req.getParameterTypes());
                Object returnValue = method.invoke(so.getObj(), req.getParameters());
                response = new RpcResponse(RpcStatusEnum.SUCCESS);
                response.setData(data);
            }catch (Exception e){
                response = new RpcResponse(RpcStatusEnum.ERROR);
                response.setException(e);
            }
        }
        // 编组响应消息
        response.setRequestId(req.getRequestId());
        return this.protocol.marshallingResponse(response);
    }


    public MessageProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(MessageProtocol protocol) {
        this.protocol = protocol;
    }

    public ServerRegister getServerRegister() {
        return serverRegister;
    }

    public void setServerRegister(ServerRegister serverRegister) {
        this.serverRegister = serverRegister;
    }


}
