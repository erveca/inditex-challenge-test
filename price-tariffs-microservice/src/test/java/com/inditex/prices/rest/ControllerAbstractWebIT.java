package com.inditex.prices.rest;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

public abstract class ControllerAbstractWebIT {
    public String constructUrl(int port) {
        return "http://localhost:%s/price-tariffs/find".formatted(port);
    }

    public static final class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (HttpMethod.GET.equals(httpMethod)) {
                return new HttpEntityEnclosingGetRequestBase(uri);
            }
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

    private static final class HttpEntityEnclosingGetRequestBase extends HttpEntityEnclosingRequestBase {
        public HttpEntityEnclosingGetRequestBase(final URI uri) {
            super.setURI(uri);
        }
        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }
}
