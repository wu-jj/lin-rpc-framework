package com.lin.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.lin.rpc.enity.RpcRequest;
import com.lin.rpc.enity.RpcResponse;
import com.lin.rpc.enumeraction.SerializerCode;
import com.lin.rpc.exception.SerializeException;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Kryo 是一个快速高效的 Java 对象序列化框架，主要特点是高性能、高效和易用。最重要的两个特点，
 * 一是基于字节的序列化，对空间利用率较高，在网络传输时可以减小体积；
 * 二是序列化时记录属性对象的类型信息，这样在反序列化时就不会出现之前的问题了。
 */

public class KryoSerializer implements CommonSerializer{

    private static final Logger logger = LoggerFactory.getLogger(KryoSerializer.class);

    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcRequest.class);
        kryo.register(RpcResponse.class);
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try(ByteOutputStream byteOutputStream = new ByteOutputStream();
            Output output = new Output(byteOutputStream)
        ){
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output,obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            logger.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try(ByteArrayInputStream byteOutputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteOutputStream)
        ){
            Kryo kryo = kryoThreadLocal.get();
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return o;
        } catch (Exception e) {
            logger.error("反序列化时有错误发生:", e);
            throw new SerializeException("反序列化时有错误发生");
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("KRYO").getCode();
    }
}
