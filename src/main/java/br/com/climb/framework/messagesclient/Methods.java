package br.com.climb.framework.messagesclient;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Methods {

    //TopicName / Topic Controller
    public static final Map<String, Class<?>> MESSAGE_CONTROLLERS = new ConcurrentHashMap<>();

    //Name method / Method
    public static final Map<String, Method> RPC_CONTROLLERS = new ConcurrentHashMap<>();

}
