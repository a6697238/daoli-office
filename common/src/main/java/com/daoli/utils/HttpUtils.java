package com.daoli.utils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * AUTO-GENERATED: houlu @ 2019/10/5 下午3:59
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class HttpUtils {



    //设置超时时间
    private static final int HTTP_SOCKET_TIMEOUT = 100000;
    private static final int HTTP_CONNECT_TIMEOUT = 100000;
    private static final int HTTP_CONNECTION_REQUEST_TIMEOUT = 100000;

    private static final String UPLOAD_FILE = "up_load_file";

    public static final String HTTP_RETURN_ERROR = "http_return_error";


    public static String doPostRequest(Map<String, String> paramsMap, String postUrl){
        log.info("doPostRequest url is : {} , param is :{}",postUrl, JSON.toJSONString(paramsMap));
        HttpPost httpPost = new HttpPost(postUrl);
        String result;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            List<NameValuePair> kvs = Lists.newArrayList();
            for (Entry<String, String> entry : paramsMap.entrySet()) {
                kvs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setEntity(new UrlEncodedFormEntity(kvs, StandardCharsets.UTF_8));
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HTTP_SOCKET_TIMEOUT).setConnectionRequestTimeout(HTTP_CONNECTION_REQUEST_TIMEOUT).setConnectTimeout(HTTP_CONNECT_TIMEOUT).build();
            httpPost.setConfig(requestConfig);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                result = EntityUtils.toString(response.getEntity());
            }
        }catch (Exception e){
            log.error("fail to doPostRequest",e);
            return "";
        }
        return result;
    }
}
