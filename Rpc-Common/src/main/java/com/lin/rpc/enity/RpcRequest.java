package com.lin;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RpcRequest implements Serializable {
    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 待调用方法的名称
     */
    private String methodName;

    /**
     * 调用方法的参数
     */
    private Object[] params;

    /**
     * 调用方法的参数类型
     */
    private Class<?> paramsType;

}
