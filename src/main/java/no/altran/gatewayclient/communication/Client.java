package no.altran.gatewayclient.communication;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * Created by catalin@besleaga.com on 07/10/14.
 */
public class Client {

    public static final String PROTOCOL = "http";
    public static final String HOST = "192.168.1.8";
    public static final String PATH = "/cgi-bin/luci/wisense/info";

    public static final String CONTENT_TYPE = "content-type";
    public static final String APPLICATION_JSON = "application/json";

    public static String getContent() throws URISyntaxException, IOException {
        String response = "";
        HttpClient httpclient = HttpClientBuilder.create().build();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPath(PATH)
                .setParameter("ts", "" + new Date().getTime())
                .build();

        //Define a postRequest request
        HttpPost postRequest = new HttpPost(uri);

        //Set the content-type header
        postRequest.addHeader(CONTENT_TYPE, APPLICATION_JSON);

        BufferedReader br = null;
        try {
            HttpResponse httpResponse = httpclient.execute(postRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String readLine;
                while (((readLine = br.readLine()) != null)) {
                    response += readLine;
                }
            }

        } finally {
            postRequest.releaseConnection();
            if (br != null) try {
                br.close();
            } catch (Exception ignored) {
            }
        }

        return response;
    }
}
