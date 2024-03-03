package com.lin.rpc.register.nacos;


import java.net.InetSocketAddress;

/**
 * 服务注册中心通用接口
 */
public interface ServiceRegistry {
    /**
     * 将服务注册进一个注册表
     */
    void register(String service, InetSocketAddress inetSocketAddress);

    /**
     * 根据服务名称查找服务实体
     */
    InetSocketAddress findServiceByServiceName(String serviceName);

}
