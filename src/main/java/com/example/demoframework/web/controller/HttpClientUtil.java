package com.example.demoframework.web.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * copy from paipai-user
 *
 * @author zhangxueli6
 * @date 2019年5月28日
 */
public class HttpClientUtil {
    /**
     * logger日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * httpClient pool
     */
    private static PoolingHttpClientConnectionManager connManager = null;
    /**
     * httpclient
     */
    private static CloseableHttpClient httpclient = null;
    /**
     * objectMapper
     */
    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * CONNECT_TIMEOUT
     */
    public final static int CONNECT_TIMEOUT = 6000;
    /**
     * charSet
     */
    public final static String UTF_8 = "UTF-8";
    /**
     * http 请求成功
     */
    public final static int SUCESS = 200;

    /**
     * 初始化SSL
     */
    static {
        try {
            SSLContext sslContext = SSLContexts.custom().build();
            sslContext.init(null,
                    new TrustManager[]{new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(
                                X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(
                                X509Certificate[] certs, String authType) {
                        }
                    }}, null);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();

            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            httpclient = HttpClients.custom().setConnectionManager(connManager).build();
            // Create socket configuration
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            connManager.setDefaultSocketConfig(socketConfig);
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200)
                    .setMaxLineLength(2000)
                    .build();
            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints)
                    .build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);
        } catch (KeyManagementException e) {
            logger.error("KeyManagementException", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
        }
    }

    /**
     * post
     *
     * @param url
     * @param body
     * @param timeout
     * @param headers
     * @param encoding
     * @return
     */
    public static String post(String url, String body, int timeout, Map<String, String> headers, String encoding) {
        HttpPost post = new HttpPost(url);
        try {
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setExpectContinueEnabled(false).build();
            post.setConfig(requestConfig);
            if (body != null) {
                post.setEntity(new StringEntity(body, encoding));
                logger.info("[HttpUtils Post] begin invoke url:" + url + " , params:" + body);
            }
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if (entity != null) {
                        String str = EntityUtils.toString(entity, encoding);
                        logger.info("[HttpUtils Post]Debug response, url :" + url + " , StatusCode:" + response.getStatusLine().getStatusCode() + " , response string :" + str);
                        return str;
                    }
                } finally {
                    if (entity != null) {
                        entity.getContent().close();
                    }
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            post.releaseConnection();
        }
        return "";
    }

    /**
     * postJsonBody
     * @param url
     * @param token
     * @param timeout
     * @param map
     * @param encoding
     * @return
     */
    public static String postJsonBody(String url, String token, int timeout, Map<String, Object> map, String encoding) {
        HttpPost post = new HttpPost(url);
        try {
            post.setHeader("Content-type", "application/json;");
            if (StringUtils.isNotBlank(token)) {
                post.setHeader("token", token);
            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setExpectContinueEnabled(false).build();
            post.setConfig(requestConfig);
            if (map != null && !map.isEmpty()) {

                String str1 = objectMapper.writeValueAsString(map);
                str1 = StringUtils.replace(str1, "\\", "");
                post.setEntity(new StringEntity(str1, encoding));
                logger.info("[HttpUtils Post] begin invoke url:" + url + " , params:" + str1);
            }
            CloseableHttpResponse response = httpclient.execute(post);
            if (SUCESS != response.getStatusLine().getStatusCode()){
                logger.info("[HttpUtils Post]Debug response, url :" + url + " , StatusCode:" + response.getStatusLine().getStatusCode() + " , ReasonPhrase:" + response.getStatusLine().getReasonPhrase());
            }

        logger.debug("[HttpUtils Post]Debug response :{}", JSON.toJSONString(response));
        try {
            HttpEntity entity = response.getEntity();
            try {
                if (entity != null) {
                    String str = EntityUtils.toString(entity, encoding);
                    logger.info("[HttpUtils Post]Debug response, url :" + url + " , StatusCode:" + response.getStatusLine().getStatusCode() + " , response string :" + str);
                    return str;
                }
            } finally {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    } catch (UnsupportedEncodingException e) {
        logger.error("UnsupportedEncodingException", e);
    } catch (Exception e) {
        logger.error("Exception", e);
    } finally {
        post.releaseConnection();
    }
        return "";
}

    /**
     * postJsonBody
     * @param url
     * @param token
     * @param obj
     * @param timeout
     * @param encoding
     * @return
     */
    public static String postJsonBody(String url, String token, Object obj, int timeout, String encoding) {
        HttpPost post = new HttpPost(url);
        try {
            post.setHeader("Content-type", "application/json;");
            if (StringUtils.isNotBlank(token)) {
                post.setHeader("token", token);
            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setExpectContinueEnabled(false).build();
            post.setConfig(requestConfig);
            if (obj != null) {
                String str1 = objectMapper.writeValueAsString(obj);
                str1 = StringUtils.replace(str1, "\\", "");
                post.setEntity(new StringEntity(str1, encoding));
                logger.info("[HttpUtils Post] begin invoke url:" + url + " , params:" + str1 + " , token:" + token);
            }
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if (entity != null) {
                        String str = EntityUtils.toString(entity, encoding);
                        logger.info("[HttpUtils Post]Debug response, url :" + url + " , StatusCode:" + response.getStatusLine().getStatusCode() + "  ,  response string :" + str);
                        return str;
                    }
                } finally {
                    if (entity != null) {
                        entity.getContent().close();
                    }
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            post.releaseConnection();
        }
        return "";
    }

    /**
     * postJsonBody
     * @param url
     * @param token
     * @param timeout
     * @param obj
     * @param encoding
     * @return
     */
    public static String postJsonBody(String url, String token, int timeout, Object obj, String encoding) {
        HttpPost post = new HttpPost(url);
        try {
            post.setHeader("Content-type", "application/json;");
            if (StringUtils.isNotBlank(token)) {
                post.setHeader("token", token);
            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setExpectContinueEnabled(false).build();
            post.setConfig(requestConfig);
            if (obj != null) {
                String str1 = objectMapper.writeValueAsString(obj);
                str1 = StringUtils.replace(str1, "\\", "");
                post.setEntity(new StringEntity(str1, encoding));
                logger.info("[HttpUtils Post] begin invoke url:" + url + " , params:" + str1 + " , token:" + token);
            }
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if (entity != null) {
                        String str = EntityUtils.toString(entity, encoding);
                        logger.info("[HttpUtils Post]Debug response, url :" + url + " , StatusCode:" + response.getStatusLine().getStatusCode() + "  ,  response string :" + str);
                        return str;
                    }
                } finally {
                    if (entity != null) {
                        entity.getContent().close();
                    }
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            post.releaseConnection();
        }
        return "";
    }

    /**
     * invokeGet
     * @param url
     * @param params
     * @param encode
     * @param connectTimeout
     * @param soTimeout
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String invokeGet(String url, Map<String, Object> params, String encode, int connectTimeout,
                                   int soTimeout) {
        String responseString = null;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(soTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout).build();

        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (params != null && !params.isEmpty()) {
            int i = 0;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (i == 0 && !url.contains("?")) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                //FIXME 20160815 尝试解决entry.getValue().toString()空指针问题
                if (entry.getKey() != null && entry.getValue() != null) {
                    sb.append(entry.getKey());
                    sb.append("=");
                    String value = entry.getValue().toString();
                    try {
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        logger.warn("encode http get params error, value is {}", value, e);
                        sb.append(URLEncoder.encode(value));
                    }
                } else {
                    logger.warn("params is empty:{}={}", entry.getKey(), entry.getValue());
                }
                i++;
            }
        }
        logger.info("[HttpUtils Get] begin invoke:{}", sb.toString());
        HttpGet get = new HttpGet(sb.toString());
        get.setConfig(requestConfig);

        try {
            CloseableHttpResponse response = httpclient.execute(get);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if (entity != null) {
                        responseString = EntityUtils.toString(entity, encode);
                        logger.info("[HttpUtils Get]Debug response, url :" + url + " , StatusCode:" + response.getStatusLine().getStatusCode() + "  ,  response string :" + responseString);
                    }
                } finally {
                    if (entity != null) {
                        entity.getContent().close();
                    }
                }
            } catch (Exception e) {
                logger.error(String.format("[HttpUtils Get]get response error, url:%s", sb.toString()), e);
                return responseString;
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            logger.info(String.format("[HttpUtils Get]Debug url:%s , response string %s:", sb.toString(), responseString));
        } catch (SocketTimeoutException e) {
            logger.error(String.format("[HttpUtils Get]invoke get timout error, url:%s", sb.toString()), e);
            return responseString;
        } catch (Exception e) {
            logger.error(String.format("[HttpUtils Get]invoke get error, url:%s", sb.toString()), e);
        } finally {
            get.releaseConnection();
        }
        return responseString;
    }

    /**
     * HTTPS请求
     *
     * @param reqURL
     * @param params
     * @return
     */
    public static String connectPostHttps(String reqURL, Map<String, String> params, String encode, int connectTimeout,
                                          int soTimeout) {

        String responseContent = null;

        HttpPost httpPost = new HttpPost(reqURL);
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(soTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(connectTimeout).build();

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            // 绑定到请求 Entry
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encode));
            httpPost.setConfig(requestConfig);

            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // 执行POST请求
                HttpEntity entity = response.getEntity(); // 获取响应实体
                try {
                    if (null != entity) {
                        responseContent = EntityUtils.toString(entity, encode);
                        logger.info("[HttpUtils Post]Debug response, url :" + reqURL + " , StatusCode:" + response.getStatusLine().getStatusCode() + "  ,  response string :" + responseContent);
                    }
                } finally {
                    if (entity != null) {
                        entity.getContent().close();
                    }
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            logger.info("requestURI : " + httpPost.getURI() + ", responseContent: " + responseContent);
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseContent;

    }

    /**
     * HTTPS请求，默认超时为2S
     *
     * @param reqURL
     * @param params
     * @return
     */
    public static String connectPostHttps(String reqURL, Map<String, String> params, String encode) {
        return connectPostHttps(reqURL, params, encode, CONNECT_TIMEOUT, CONNECT_TIMEOUT);

    }

    /**
     * HTTPS请求，默认超时为2S 编码 UTF-8
     *
     * @param reqURL
     * @param params
     * @return
     */
    public static String connectPostHttps(String reqURL, Map<String, String> params) {
        return connectPostHttps(reqURL, params, UTF_8, CONNECT_TIMEOUT, CONNECT_TIMEOUT);

    }

    /**
     * get请求，默认超时为2S 编码 UTF-8
     *
     * @param url
     * @param params
     * @return
     */
    public static String invokeGet(String url, Map<String, Object> params) {
        return invokeGet(url, params, UTF_8, CONNECT_TIMEOUT, CONNECT_TIMEOUT);
    }

    /**
     * get请求，默认超时为2S 编码
     *
     * @param url
     * @param params
     * @param encode
     * @return
     */
    public static String invokeGet(String url, Map<String, Object> params, String encode) {
        return invokeGet(url, params, encode, CONNECT_TIMEOUT, CONNECT_TIMEOUT);
    }

    /**
     * get请求，默认超时为2S 编码
     *
     * @param url
     * @param params
     * @param connectTimeout
     * @return
     */
    public static String invokeGet(String url, Map<String, Object> params, int connectTimeout) {
        return invokeGet(url, params, UTF_8, connectTimeout, connectTimeout);
    }

    /**
     * get请求，默认超时为2S 编码
     *
     * @param url
     * @param params
     * @param connectTimeout
     * @return
     */
    public static String invokeGet(String url, Map<String, Object> params, String encode, int connectTimeout) {
        return invokeGet(url, params, encode, connectTimeout, connectTimeout);
    }

    /**
     * POST JSON 请求 默认 2s 编码 UTF-8
     *
     * @param url
     * @param map
     * @return
     */
    public static String postJsonBody(String url, Map<String, Object> map) {
        return postJsonBody(url, null, CONNECT_TIMEOUT, map, UTF_8);
    }

    /**
     * POST JSON 请求 默认 2s 编码 UTF-8
     *
     * @param url
     * @param map
     * @return
     */
    public static String postJsonBody(String url, Map<String, Object> map, int connectTimeout) {
        return postJsonBody(url, null, connectTimeout, map, UTF_8);
    }


    /**
     * POST JSON 请求 默认 2s
     *
     * @param url
     * @param map
     * @return
     */
    public static String postJsonBody(String url, Map<String, Object> map, String encode) {
        return postJsonBody(url, null, CONNECT_TIMEOUT, map, encode);
    }

}