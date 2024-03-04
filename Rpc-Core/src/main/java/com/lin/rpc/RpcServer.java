package com.lin.rpc;

import com.lin.rpc.serializer.CommonSerializer;

public interface RpcServer {
    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

//    <T> void publishService(Object service, Class<T> serviceClass);

    <T> void publishService(T service, String serviceName);
}
