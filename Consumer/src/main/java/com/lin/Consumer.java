package com.lin;


import com.lin.rpc.socket.socketClient.RpcConsumerClientProxy;

public class Consumer{
    public static void main(String[] args) {
        RpcConsumerClientProxy proxy = new RpcConsumerClientProxy("localhost",9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(1,"hello world");
        String res = helloService.sayHello(object);
        System.out.println(res);
    }
}
