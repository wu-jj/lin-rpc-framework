package com.lin.rpc.netty.client;

import com.lin.rpc.RpcClient;
import com.lin.rpc.enity.RpcRequest;
import com.lin.rpc.enity.RpcResponse;
import com.lin.rpc.enumeraction.RpcError;
import com.lin.rpc.exception.RpcException;
import com.lin.rpc.register.nacos.NacosServiceRegistry;
import com.lin.rpc.register.nacos.ServiceRegistry;
import com.lin.rpc.serializer.CommonSerializer;
import com.lin.rpc.spi.ExtensionLoader;
import com.lin.rpc.util.RpcMessageChecker;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private static final Bootstrap bootstrap;
    private  ServiceRegistry serviceRegistry;

    private CommonSerializer serializer;


    public NettyClient() {
        try {
            new ExtensionLoader().loadExtension(ServiceRegistry.class);
            LinkedHashMap<String, Class> registerMap = ExtensionLoader.EXTENSION_LOADER_CLASS_CACHE.get(ServiceRegistry.class.getName());

        } catch (Exception e) {
            logger.error("SPI注册服务实例化发生异常:",e);
        }

        this.serviceRegistry = new NacosServiceRegistry(null);
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        AtomicReference<Object> result = new AtomicReference<>(null);
        try {
            InetSocketAddress inetSocketAddress = serviceRegistry.findServiceByServiceName(rpcRequest.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if(channel.isActive()) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        logger.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
                RpcResponse rpcResponse = channel.attr(key).get();
                RpcMessageChecker.check(rpcRequest, rpcResponse);
                result.set(rpcResponse.getData());
            } else {
                System.exit(0);
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生: ", e);
        }
        return result.get();
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
