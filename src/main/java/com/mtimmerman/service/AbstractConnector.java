package com.mtimmerman.service;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mtimmerman.service.exceptions.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
        jacksonXmlModule.setDefaultUseWrapper(
                false
        );

        xmlMapper = new XmlMapper(
                jacksonXmlModule
        );
    }

    protected HttpResponse handleResponse(HttpPost httpPost) throws IOException {
        HttpResponse httpResponse = httpClient.execute(
                httpPost,
                httpClientContext
        );

        int code = httpResponse.getStatusLine().getStatusCode();

        if (code < 400) {
            return httpResponse;
        }

        throw new IOException(
                String.format(
                        "Code %s returned.",
                        code
                )
        );
    }

    protected BufferedReader ungzipIt(InputStream inputStream)
            throws IOException {

        String result = "";

        byte[] buffer = new byte[1024];
        File target = new File("/tmp/torrent.dump");
        try (
                GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
                OutputStream outputStream = new FileOutputStream(target)
        ) {
            int length;

            while ((length = gzipInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }

        return new BufferedReader(new FileReader(target));
    }

    protected String unzipIt(byte[] input)
            throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                input
        );


        ZipInputStream zipInputStream = new ZipInputStream(
                byteArrayInputStream
        );

        byte[] buffer = new byte[1024];

        try {
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String filename = zipEntry.getName();

                if (filename.equals("en.xml")) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    Integer length;

                    while ((length = zipInputStream.read(buffer)) > 0) {
                        byteArrayOutputStream.write(
                                buffer,
                                0,
                                length
                        );
                    }

                    return new String(
                            byteArrayOutputStream.toByteArray(),
                            "UTF-8"
                    );
                }

                zipEntry = zipInputStream.getNextEntry();
            }
        } finally {
            zipInputStream.closeEntry();
            zipInputStream.close();
        }

        return null;
    }

    protected HttpResponse handleResponse(HttpGet httpGet) throws IOException {
        HttpResponse httpResponse = httpClient.execute(
                httpGet,
                httpClientContext
        );

        int code = httpResponse.getStatusLine().getStatusCode();

        if (code < 400) {
            return httpResponse;
        }

        throw new HttpException(
                String.format(
                        "Code %s returned.",
                        code
                ),
                code
        );
    }

    protected String urlEncodeUTF8(String s) {
        if (s != null) {
            try {
                return URLEncoder.encode(
                        s,
                        "UTF-8"
                );
            } catch (UnsupportedEncodingException e) {
                throw new UnsupportedOperationException(
                        e
                );
            }
        }

        return "";
    }
}
