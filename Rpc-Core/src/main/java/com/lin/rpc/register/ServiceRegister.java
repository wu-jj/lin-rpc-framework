package com.lin.rpc.register;

public interface ServiceRegister {
    /**
     *用来注册服务
     */
    <T> void register(T service);
    /**
     *用来获取服务服务信息
     */
    Object getService(String serviceName);
}
