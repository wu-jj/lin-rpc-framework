package com.lin.rpc.enumeraction;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author linz
 */
@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

}