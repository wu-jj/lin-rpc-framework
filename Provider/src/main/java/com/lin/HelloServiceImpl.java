package com.lin;

import com.lin.rpc.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class HelloServiceImpl implements HelloService{

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    public String sayHello(HelloObject helloObject) {
        logger.info("接收到：{}", helloObject.getMessage());
        return "这是掉用的返回值，id=" + helloObject.getId();
    }
}
