package com.orc.rpc.common.protocol;


import com.orc.rpc.annotation.MessageProtocolAno;
import com.orc.rpc.common.constants.RpcConstant;
import com.orc.rpc.common.model.RpcRequest;
import com.orc.rpc.common.model.RpcResponse;
import com.orc.rpc.util.SerializingUtil;

@MessageProtocolAno(RpcConstant.PROTOCOL_PROTOBUF)
public class ProtoBufMessageProtocol implements MessageProtocol {

    @Override
    public byte[] marshallingRequest(RpcRequest request) throws Exception {
        return SerializingUtil.serialize(request);
    }

    @Override
    public RpcRequest unmarshallingRequest(byte[] data) throws Exception {
        return SerializingUtil.deserialize(data,RpcRequest.class);
    }

    @Override
    public byte[] marshallingResponse(RpcResponse response) throws Exception {
        return SerializingUtil.serialize(response);
    }

    @Override
    public RpcResponse unmarshallingResponse(byte[] data) throws Exception {
        return SerializingUtil.deserialize(data,RpcResponse.class);
    }
}
