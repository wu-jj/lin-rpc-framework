package com.lin;

import com.lin.rpc.RpcClient;
import com.lin.rpc.RpcClientProxy;
import com.lin.rpc.netty.client.NettyClient;
import com.lin.rpc.serializer.KryoSerializer;

public class NettyTestConsumer {
    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        client.setSerializer(new KryoSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(1, "hello world");
        String res = helloService.sayHello(object);
        System.out.println(res);
    }
}
