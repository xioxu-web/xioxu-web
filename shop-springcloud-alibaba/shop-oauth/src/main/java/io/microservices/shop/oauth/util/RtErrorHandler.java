package io.microservices.shop.oauth.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 捕获RestTemplate异常

 * @author xiaoxu123
 */
public class RtErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
        List<HttpStatus> donotDeal = new ArrayList<>();
        donotDeal.add(HttpStatus.UNAUTHORIZED);
        if (!donotDeal.contains(statusCode)) {
            super.handleError(response);
        }
    }

}
