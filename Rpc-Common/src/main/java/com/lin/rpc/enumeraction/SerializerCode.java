package com.lin.rpc.enumeraction;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标识用哪种序列化
 */

@AllArgsConstructor
@Getter
public enum SerializerCode {

    KRYO(0),
    JSON(1),
    HESSIAN(2),
    PROTOBUF(3);

    private final int code;

}