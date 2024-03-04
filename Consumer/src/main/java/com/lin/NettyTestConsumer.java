package com.lin;

import com.lin.rpc.RpcClient;
import com.lin.rpc.RpcClientProxy;
import com.lin.rpc.netty.client.NettyClient;
import com.lin.rpc.serializer.KryoSerializer;
import com.lin.rpc.util.ThreadPoolFactory;

import java.util.concurrent.ExecutorService;

public class NettyTestConsumer {
    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        client.setSerializer(new KryoSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);

        ExecutorService threadPool = ThreadPoolFactory.createDefaultThreadPool("consumer:thread",false);
        for (int i = 0; i < 3000; i++) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    HelloObject object = new HelloObject(finalI, "hello world");
                    String res = helloService.sayHello(object);
                    System.out.println(res);
                }
            });
        }
    }
}
