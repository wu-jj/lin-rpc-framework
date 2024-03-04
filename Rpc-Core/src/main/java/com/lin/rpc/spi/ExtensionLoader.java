package com.lin.rpc.spi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ExtensionLoader{
    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);


    public static String EXTENSION_LOADER_DIR_PREFIX = "META-INF/linz/";

    /**
     * key：interface name  value：{key:configName value:ImplClass}
     */
    public static Map<String, LinkedHashMap<String, Class>> EXTENSION_LOADER_CLASS_CACHE = new ConcurrentHashMap<>();

    public void loadExtension(Class clazz) throws IOException, ClassNotFoundException {
        if (clazz == null) {
            throw new IllegalArgumentException("class can not null");
        }

        String spiFilePath = EXTENSION_LOADER_DIR_PREFIX + clazz.getName();
        ClassLoader classLoader = this.getClass().getClassLoader();
        Enumeration<URL> enumeration = classLoader.getResources(spiFilePath);
        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            InputStreamReader inputStreamReader = null;
            inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            LinkedHashMap<String, Class> classMap = new LinkedHashMap<>();
            while ((line = bufferedReader.readLine()) != null) {
                //如果配置中加入了#开头，则表示忽略该类，无需加载
                if (line.startsWith("#")){
                    continue;
                }
                String[] lineArr = line.split("=");
                String implClassName = lineArr[0];
                String interfaceName = lineArr[1];
                //保存的同时初始化类
                classMap.put(implClassName,Class.forName(interfaceName));
            }

            //放入缓存中
            if (EXTENSION_LOADER_CLASS_CACHE.containsKey(clazz.getName())){
                EXTENSION_LOADER_CLASS_CACHE.get(clazz.getName()).putAll(classMap);
            }else {
                EXTENSION_LOADER_CLASS_CACHE.put(clazz.getName(),classMap);
            }
        }
    }

}
