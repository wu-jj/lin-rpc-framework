package com.lin.rpc.filter.server;

import com.lin.rpc.enity.RpcRequest;

import java.util.ArrayList;
import java.util.List;

public class ServerFilterChain {

    private static List<IServerFilter> iServerFilters = new ArrayList<>();

    public void addServerFilter(IServerFilter serverFilter){
        iServerFilters.add(serverFilter);
    }

    public void doFilter(RpcRequest rpcRequest){
        for (IServerFilter iServerFilter : iServerFilters) {
            iServerFilter.doFilter(rpcRequest);
        }
    }
}
