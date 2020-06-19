package br.com.climb.framework.configuration;

public abstract class ConfigFileBean {

    private String packge;

    public String getPackage(){
        return packge;
    }

    public void setPackge(String packge) {
        this.packge = packge;
    }
}
