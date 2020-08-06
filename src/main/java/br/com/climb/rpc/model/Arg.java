package br.com.climb.rpc.model;

public class Arg {

    private String name;
    private String type;
    private String Value;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return Value;
    }

    @Override
    public String toString() {
        return "Arg{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}
