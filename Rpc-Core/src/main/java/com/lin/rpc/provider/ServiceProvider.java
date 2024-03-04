package com.lin.rpc.provider;

/**
 * 保存和注册服务的提供对象
 */

public interface ServiceProvider {
    <T> void addServiceProvider(T service, String serviceName);

    Object getServiceProvider(String serviceName);
}
