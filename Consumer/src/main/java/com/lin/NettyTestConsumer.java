package com.lin;

import com.lin.rpc.RpcClient;
import com.lin.rpc.RpcClientProxy;
import com.lin.rpc.netty.client.NettyClient;
import com.lin.rpc.socket.socketClient.RpcConsumerClientProxy;

public class NettyTestConsumer {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.sayHello(object);
        System.out.println(res);
    }
}
