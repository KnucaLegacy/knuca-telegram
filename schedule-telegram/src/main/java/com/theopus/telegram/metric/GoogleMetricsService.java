package com.theopus.telegram.metric;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;
import com.theopus.telegram.bot.handlers.TelegramHandler;

public class GoogleMetricsService implements MetricService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMetricsService.class);
    private final String appTag;
    private final ExecutorService executor;


    public GoogleMetricsService(String appTag) {
        this.appTag = appTag;
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void track(TelegramRequest request) {
        try {
            String id = String.valueOf(request.getChat().getId());

            HttpPost httpPost = new HttpPost("http://www.google-analytics.com/collect");
            List<NameValuePair> postParameters = new ArrayList<>();

            postParameters.add(new BasicNameValuePair("v", "1"));
            postParameters.add(new BasicNameValuePair("tid", appTag));
            postParameters.add(new BasicNameValuePair("cid", id));
            postParameters.add(new BasicNameValuePair("t", "event"));

            if (!request.isCallback()) {
                postParameters.add(new BasicNameValuePair("ec", "Direct"));
                postParameters.add(new BasicNameValuePair("ea", request.getCommand() != null ? request.getCommand() : "message"));
            } else {
                postParameters.add(new BasicNameValuePair("ec", "Callback"));
                postParameters.add(new BasicNameValuePair("ea", request.getCommand()));
            }

            postParameters.add(new BasicNameValuePair("el", request.getData()));

            httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            EntityUtils.consume(execute.getEntity());
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public void track(TelegramRequest request, TelegramResponse response) {

    }

    @Override
    public void trackAsync(TelegramRequest request) {
        executor.execute(() -> track(request));
    }

    @Override
    public void trackAsync(TelegramRequest request, TelegramResponse response) {

    }

    @Override
    public void trackException(TelegramRequest request, TelegramHandler handler, Exception exception) {
        try {
            String id = String.valueOf(request.getChat().getId());

            HttpPost httpPost = new HttpPost("http://www.google-analytics.com/collect");
            List<NameValuePair> postParameters = new ArrayList<>();

            postParameters.add(new BasicNameValuePair("v", "1"));
            postParameters.add(new BasicNameValuePair("tid", appTag));
            postParameters.add(new BasicNameValuePair("cid", id));
            postParameters.add(new BasicNameValuePair("t", "exception"));
            postParameters.add(new BasicNameValuePair("exd", handler.getClass().getSimpleName() + ":" + exception.toString()));

            httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            EntityUtils.consume(execute.getEntity());
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public void trackExceptionAsync(TelegramRequest request, TelegramHandler handler, Exception e) {
        executor.execute(() -> trackException(request, handler, e));
    }

    @Override
    public void close() throws InterruptedException {
        executor.shutdownNow();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
