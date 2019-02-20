package com.theopus.search;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

public class RestRepository<T> {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Class<?> entityClass;
    private final String endpoint;

    public RestRepository(Class<T> entityClass, String endpoint) {
        this.entityClass = Array.newInstance(entityClass, 0).getClass();
        this.endpoint = endpoint;
    }

    public List<T> all() {
        T[] result = (T[]) restTemplate.getForObject(endpoint, entityClass);
        return (Arrays.asList(result));
    }
}
