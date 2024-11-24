package com.inditex.prices.rest;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class ControllerAbstractWebIT {
    public String constructUrl(int port, final Map<String, String> queryParams) {
        final StringBuilder sb = new StringBuilder("http://localhost:%s/price-tariffs/find?");
        sb.append(queryParams.keySet().stream()
                .filter(param -> queryParams.get(param) != null)
                .map(param -> "%s={%s}".formatted(param, param))
                .collect(Collectors.joining("&")));
        return sb.toString().formatted(port);
    }
}
