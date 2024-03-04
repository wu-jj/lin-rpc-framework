package com.lin;

import com.lin.rpc.annotation.ServiceScan;
import com.lin.rpc.netty.server.NettyServer;
import com.lin.rpc.serializer.CommonSerializer;

@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        NettyServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.KRYO_SERIALIZER);
        server.start();
    }
}
