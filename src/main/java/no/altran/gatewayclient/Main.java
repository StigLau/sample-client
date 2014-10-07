package no.altran.gatewayclient;

import no.altran.gatewayclient.communication.Client;
import no.altran.gatewayclient.communication.DataSender;
import no.altran.gatewayclient.model.GatewayResponse;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Created by catalin@besleaga.com on 07/10/14.
 */

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, JSONException {
        String response = Client.getContent();
//        System.out.printf(response);

        Parser parser = new Parser();
        GatewayResponse gatewayResponse = parser.parse(response);
//        System.out.println(gatewayResponse);

        DataSender.send(gatewayResponse);
//        Main.test();

    }

    public static void test() throws IOException, URISyntaxException {
        String s = "{\"TimestampReceived\":\"1412709506904\",\"RadioSensorDescription\":\"001BC50C71000052\",\"Measurements\":{\"uid\":\"001BC50C71000052\",\"sn\":\"17\",\"ts\":\"1412709505368.2\",\"mfs\":\"169175233157\",\"pre\":\"1013\",\"mv\":\"14061001\",\"rt\":\"0\",\"lb\":\"44\",\"lig\":\"103\"},\"RadioGatewayName\":\"192.168.1.8\",\"RadioSensorName\":\"001BC50C71000052\",\"RadioGatewayDescription\":\"192.168.1.8\",\"RadioSensorId\":\"001BC50C71000052\",\"RadioGatewayId\":\"192.168.1.8\",\"TimestampCreated\":\"1412709505372\"}\n";
        DataSender.clientSend(s);
    }
}
