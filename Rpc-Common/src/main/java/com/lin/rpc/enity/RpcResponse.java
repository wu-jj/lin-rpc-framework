package com.lin.rpc.enity;

import com.lin.rpc.enumeraction.ResponseCode;
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
     * 实际返回数据
     */
    private T Data;

    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> rpcResponse = new RpcResponse<T>();
        rpcResponse.setCode(ResponseCode.SUCCESS.getCode());
        rpcResponse.setData(data);
        return rpcResponse;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code){
        RpcResponse<T> rpcResponse = new RpcResponse<T>();
        rpcResponse.setCode(code.getCode());
        rpcResponse.setMessage(code.getMessage());
        return rpcResponse;
    }
}
