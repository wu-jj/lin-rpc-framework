package com.lin.rpc.filter.server;

import com.lin.rpc.enity.RpcRequest;

import java.util.UUID;

public class MessageFilterImpl implements IServerFilter {
    @Override
    public void doFilter(RpcRequest rpcRequest) {
        int requestIdLen = UUID.randomUUID().toString().length();
        if (requestIdLen != rpcRequest.getRequestId().length()||
            rpcRequest.getRequestId().isEmpty()
        ){
            throw new RuntimeException(" verify result is false!");
        }
    }
}
