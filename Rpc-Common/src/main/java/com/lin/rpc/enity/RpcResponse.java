package com.lin;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse<T> implements Serializable {
    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应状态补充信息
     */
    private String message;

    /**
     * 实际返回书记
     */
    private T Data;

    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> rpcResponse = new RpcResponse<T>();
    }
}
