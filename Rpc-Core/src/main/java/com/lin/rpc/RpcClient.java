package com.lin.rpc;

import com.lin.rpc.enity.RpcRequest;

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
