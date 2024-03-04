package com.lin.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

public class RoundRobinLoadBalancer implements LoadBalancer {

    private int idx = 0;

    @Override
    public Instance select(List<Instance> instances) {
        if (idx > instances.size()){
            idx %= instances.size();
        }
        return instances.get(idx++);
    }
}
