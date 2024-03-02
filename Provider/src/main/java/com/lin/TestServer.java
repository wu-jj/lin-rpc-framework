package com.lin;


import com.lin.rpc.register.DefaultServiceRegistry;
import com.lin.rpc.register.ServiceRegister;
import com.lin.rpc.server.RpcProviderServer;

public class TestServer {

    public static void main(String[] args) {

        HelloService helloService = new HelloServiceImpl();
        ServiceRegister serviceRegister = new DefaultServiceRegistry();
        serviceRegister.register(helloService);
        RpcProviderServer server = new RpcProviderServer(serviceRegister);
        server.start(9000);
    }
}
