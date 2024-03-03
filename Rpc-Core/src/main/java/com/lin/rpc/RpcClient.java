package com.lin.rpc;

import com.lin.rpc.enity.RpcRequest;
import com.lin.rpc.serializer.CommonSerializer;

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);
}
