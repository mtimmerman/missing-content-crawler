package com.mtimmerman.service;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by maarten on 29.12.14.
 */
public abstract class AbstractConnector {
    protected HttpClient httpClient;
    protected HttpClientContext httpClientContext;
    protected XmlMapper xmlMapper;

    abstract HttpGet createGET(String uri);
    abstract HttpPost createPOST(String uri);

    protected AbstractConnector() {
        httpClient = HttpClientBuilder.create().build();

        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        jacksonXmlModule.setDefaultUseWrapper(false);

        xmlMapper = new XmlMapper(jacksonXmlModule);
    }

    protected HttpResponse handleResponse(HttpPost httpPost) throws IOException {
        HttpResponse httpResponse = httpClient.execute(httpPost, httpClientContext);

        int code = httpResponse.getStatusLine().getStatusCode();
        if (code < 400) {
            return httpResponse;
        }

        throw new IOException(String.format("Code %s returned.", code));
    }

    protected HttpResponse handleResponse(HttpGet httpGet) throws IOException {
        HttpResponse httpResponse = httpClient.execute(httpGet, httpClientContext);

        int code = httpResponse.getStatusLine().getStatusCode();
        if (code < 400) {
            return httpResponse;
        }

        throw new IOException(String.format("Code %s returned.", code));
    }
}
