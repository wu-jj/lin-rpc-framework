package com.lin.rpc.client;

import com.lin.rpc.enity.RpcRequest;
import com.lin.rpc.enity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理 使调用方无需关注网络传输等细节
 */
public class RpcConsumerClientProxy implements InvocationHandler {
    private String host;
    private int port;

    public RpcConsumerClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes())
                .build();

        RpcConsumerClient rpcClient = new RpcConsumerClient();
        return ((RpcResponse) rpcClient.SendRequest(rpcRequest, host, port)).getData();
    }
}
