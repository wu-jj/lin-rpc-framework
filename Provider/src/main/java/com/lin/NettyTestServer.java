package com.lin;

import com.lin.rpc.netty.server.NettyServer;
import com.lin.rpc.register.DefaultServiceRegistry;
import com.lin.rpc.register.ServiceRegister;

public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegister registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
