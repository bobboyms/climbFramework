package br.com.climb.commons.configuration;

public abstract class ConfigFileBean {

    private String packge;
    private String securityUrl;

    public String getPackage(){
        return packge;
    }

    public void setPackge(String packge) {
        this.packge = packge;
    }

    public String getSecurityUrl() {
        return securityUrl;
    }

    public void setSecurityUrl(String securityUrl) {
        this.securityUrl = securityUrl;
    }
}
