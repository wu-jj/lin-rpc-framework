package com.lin.rpc.register.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lin.rpc.enumeraction.RpcError;
import com.lin.rpc.exception.RpcException;
import com.lin.rpc.loadbalancer.LoadBalancer;
import com.lin.rpc.loadbalancer.RoundRobinLoadBalancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

public class NacosServiceRegistry implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private final LoadBalancer loadBalancer;
    private static final String SERVER_ADDR = "127.0.0.1:8848";
    private static final NamingService namingService;

    public NacosServiceRegistry(LoadBalancer loadBalancer){
        if (loadBalancer == null) this.loadBalancer = new RoundRobinLoadBalancer();
        else {
            this.loadBalancer = loadBalancer;
        }
    }

    static {
        try {
            namingService = NamingFactory.createNamingService(SERVER_ADDR);

        } catch (NacosException e) {
            logger.error("连接到Nacos时有错误发生: ", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    @Override
    public void register(String service, InetSocketAddress inetSocketAddress) {
        try {
            namingService.registerInstance(service,inetSocketAddress.getHostName(),inetSocketAddress.getPort());
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

    @Override
    public InetSocketAddress findServiceByServiceName(String serviceName) {
        try {
            List<Instance> Instances = namingService.getAllInstances(serviceName);
            Instance instance = loadBalancer.select(Instances);
            return new InetSocketAddress(instance.getIp(),instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
