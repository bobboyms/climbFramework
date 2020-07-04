package br.com.climb.restclient.aspect;

import br.com.climb.cdi.annotations.Interceptor;
import br.com.climb.cdi.interceptor.Context;
import br.com.climb.cdi.interceptor.MethodIntercept;
import br.com.climb.commons.annotations.param.PathVariable;
import br.com.climb.restclient.annotation.RestClient;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.BindException;
import java.util.Arrays;
import java.util.stream.IntStream;

@RestClient
@Interceptor
public class ImplementRestIntercept implements MethodIntercept {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object interceptorMethod(Context ctx) throws Exception {

        System.out.println("****** Intercept Rest ******");

        try {

            System.out.println(ctx.getArgs());
            System.out.println(ctx.getMethod());

            RestClient restClient = ctx.getMethod().getDeclaredAnnotation(RestClient.class);

            Object object = null;

            String url = "http://127.0.0.1:8080/get/nome/thiago/peso/78/altura/177.6998/idade/33/casado/true/";

            System.out.println("url: " + url);

            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get(url)
                    .header("accept", "application/json")
                    .asJson();
            return jsonResponse.getBody().toString();

        } catch (Exception e) {
            logger.error("ImplementRestIntercept ERROR: {}", e);
        }

        return null;
    }


    public static void main(String[] args) throws UnirestException {
        String url = "http://127.0.0.1:8080/get/nome/thiago/peso/78/altura/177.6998/idade/33/casado/true/";

        HttpResponse<JsonNode> jsonResponse
                = Unirest.get(url)
                .header("accept", "application/json")
                .asJson();

        System.out.println(jsonResponse.getBody());
    }


}
