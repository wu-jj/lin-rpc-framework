package com.lin.rpc.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.rpc.enity.RpcRequest;
import com.lin.rpc.enumeraction.SerializerCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * 基于 JSON 的序列化器有一个毛病，就是在某个类的属性反序列化时，
 * 如果属性声明为 Object 的，就会造成反序列化出错，通常会把 Object 属性直接反序列化成 String 类型，就需要其他参数辅助序列化。
 * 并且，JSON 序列化器是基于字符串（JSON 串）的，占用空间较大且速度较慢。
 */

public class JsonSerializer implements CommonSerializer{
    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            logger.error("序列化时有错误发生: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object obj = objectMapper.readValue(bytes, clazz);
            if(obj instanceof RpcRequest) {
                obj = handleRequest(obj);
            }
            return obj;
        } catch (IOException e) {
            logger.error("反序列化时有错误发生: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /*
        这里由于使用JSON序列化和反序列化Object数组，无法保证反序列化后仍然为原实例类型
        需要重新判断处理
     */
    private Object handleRequest(Object obj) throws IOException {
        RpcRequest rpcRequest = (RpcRequest) obj;
        for(int i = 0; i < rpcRequest.getParamsType().length; i ++) {
            Class<?> clazz = rpcRequest.getParamsType()[i];
            if(!clazz.isAssignableFrom(rpcRequest.getParams()[i].getClass())) {
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParams()[i]);
                rpcRequest.getParams()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }
}
