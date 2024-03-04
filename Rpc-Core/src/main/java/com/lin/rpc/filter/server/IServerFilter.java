package com.lin.rpc.filter.server;

import com.lin.rpc.enity.RpcRequest;
import com.lin.rpc.filter.IFilter;

public interface IServerFilter extends IFilter {
    void doFilter(RpcRequest rpcRequest);

}
