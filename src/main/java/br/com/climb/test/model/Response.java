package br.com.climb.test.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Response {

    public static final String OK = "200";
    public static final String ERROR = "401";

    private String token;
    private String status;
    private String subject = "climbframework";
    private Date expiration;


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Response{" +
                "token='" + token + '\'' +
                ", status='" + status + '\'' +
                ", subject='" + subject + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}
