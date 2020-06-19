package br.com.climb.framework.configuration;

public class ClimbConfiguration implements ConfigFile {

    protected ClimbConfiguration(){}

    public static ConfigFile getInstance() {
        return new ClimbConfiguration();
    }

    @Override
    public String getPackage() {
        return null;
    }
}
