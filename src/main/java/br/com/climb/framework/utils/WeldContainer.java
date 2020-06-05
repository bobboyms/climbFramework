package br.com.climb.framework.utils;

import javax.enterprise.inject.se.SeContainerInitializer;

public class WeldContainer {

    public static final SeContainerInitializer initializer = SeContainerInitializer.newInstance();
//    public static final SeContainer WELD_CONTAINER = initializer.initialize();

    private WeldContainer(){}

}
