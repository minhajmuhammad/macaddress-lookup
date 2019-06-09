package io.macaddress.utils;

import io.macaddress.constant.HttpConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Slf4j
public class HttpUtil {
    private static final String UTF8 ="UTF8";

    private static URLConnection setHttpHeader(boolean doPost, String httpUrl, String param1) {
        URLConnection conn = null;
        try {

            //https://api.macaddress.io/v1?search=44:38:39:ff:ef:57
            String query = String.format("?search=%s", URLEncoder.encode(param1, "UTF-8"));

            URL url = new URL(httpUrl);
            conn = new URL(url + query).openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");

            if (doPost) {
                conn.setRequestProperty("Charset", UTF8);
                conn.setDoOutput(true);
            }

            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-Authentication-Token", HttpConstant.API_KEY);
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return conn;
    }

    public static String sendRequest(String data) throws IOException {
        return sendRequest(data, HttpConstant.BASE_URL, false);
    }

    public static String sendRequest(String search, String url, boolean isPost) throws IOException {

        if (StringUtils.isBlank(search) || StringUtils.isBlank(url)) {
            return null;
        }

        URLConnection conn = setHttpHeader(isPost, url, search);
        byte[] byteData = search.getBytes(UTF8);
        conn.setRequestProperty("Content-length", String.valueOf(byteData));
        conn.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(byteData);
        out.flush();
        out.close();

        InputStream in = conn.getInputStream();
        String result = new String(IOUtils.toByteArray(in), UTF8);
        in.close();
        return result;
    }
}
