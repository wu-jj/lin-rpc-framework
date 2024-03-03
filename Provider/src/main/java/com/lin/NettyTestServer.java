package com.lin;

import com.lin.rpc.netty.server.NettyServer;
import com.lin.rpc.register.DefaultServiceRegistry;
import com.lin.rpc.register.ServiceRegister;
import com.lin.rpc.serializer.KryoSerializer;

public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1",9999);
        server.setSerializer(new KryoSerializer());
        server.publishService(helloService,HelloService.class);
    }
}
