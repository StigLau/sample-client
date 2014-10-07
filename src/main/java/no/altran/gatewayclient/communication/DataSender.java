package no.altran.gatewayclient.communication;

import no.altran.gatewayclient.model.GatewayResponse;
import no.altran.gatewayclient.model.Sensor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by catalin@besleaga.com on 07/10/14.
 */
public class DataSender {
    public static final String PROTOCOL = "http";
    public static final String HOST = "iot.altrancloud.com";
    public static final String PATH = "/observe/observedsensor";


    public static void send(GatewayResponse gatewayResponse) throws URISyntaxException, JSONException, IOException {
        for (Sensor sensor : gatewayResponse.getSensors()) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("RadioSensorId", sensor.getUid());
            jsonObject.put("RadioSensorName", sensor.getUid());
            jsonObject.put("RadioSensorDescription", sensor.getUid());
            jsonObject.put("TimestampCreated", "" + gatewayResponse.getTimestamp());
            jsonObject.put("TimestampReceived", "" + gatewayResponse.getNowTimestamp());
            jsonObject.put("RadioGatewayId", "192.168.1.8");
            jsonObject.put("RadioGatewayName", "192.168.1.8");
            jsonObject.put("RadioGatewayDescription", "192.168.1.8");
            jsonObject.put("Measurements", sensor.getSensorWithValues());
            System.out.println(jsonObject.toString());
            clientSend(jsonObject.toString());
        }
    }

    public static void clientSend(String request) throws URISyntaxException, IOException {
        HttpClient httpclient = HttpClientBuilder.create().build();


        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPath(PATH)
                .build();

        //Define a postRequest request
        HttpPost postRequest = new HttpPost(uri);


        HttpEntity entity = new StringEntity(request);
        postRequest.setEntity(entity);
        HttpResponse httpResponse = httpclient.execute(postRequest);
        System.out.println(httpResponse);

    }
}
