package no.altran.gatewayclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import no.altran.gatewayclient.model.GatewayResponse;
import no.altran.gatewayclient.model.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* Created by catalin on 05/10/14.
*/
public class Parser {
    GatewayResponse parse(String jsonString){
//        String jsonString = "{\n" +
//                "    \"ts\": 1412462688079.1,\n" +
//                "    \"data\": {\n" +
//                "        \"001BC50C71000053\": {\n" +
//                "            \"ts\": 1412462688070.1,\n" +
//                "            \"sn\": 74,\n" +
//                "            \"lb\": 45,\n" +
//                "            \"uid\": \"001BC50C71000053\",\n" +
//                "            \"btn2\": 1412462631133.6,\n" +
//                "            \"btn1\": 1412462629024.2,\n" +
//                "            \"rt\": 0,\n" +
//                "            \"tmp\": 25,\n" +
//                "            \"z\": 53,\n" +
//                "            \"pre\": 1013,\n" +
//                "            \"lig\": 243,\n" +
//                "            \"x\": -6,\n" +
//                "            \"y\": 33\n" +
//                "        },\n" +
//                "        \"001BC50C71000052\": {\n" +
//                "            \"uid\": \"001BC50C71000052\",\n" +
//                "            \"sn\": 76,\n" +
//                "            \"ts\": 1412462686783.4,\n" +
//                "            \"pre\": 1013,\n" +
//                "            \"lig\": 306,\n" +
//                "            \"rt\": 0,\n" +
//                "            \"lb\": 44\n" +
//                "        }\n" +
//                "    },\n" +
//                "    \"now\": 1412462688835.7\n" +
//                "}";

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject)parser.parse(jsonString);

        final long timestamp = jsonObject.get("ts").getAsLong();
        final long nowTimestamp = jsonObject.get("now").getAsLong();

        JsonObject listOfSensors = jsonObject.get("data").getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> objects = listOfSensors.entrySet();

        final GatewayResponse gatewayResponse = new GatewayResponse();
        List<Sensor> sensors = new ArrayList<Sensor>();

        for (Map.Entry<String, JsonElement> entry : objects) {
            JsonElement jsonElement = entry.getValue();
            Set<Map.Entry<String, JsonElement>> sensorValues = jsonElement.getAsJsonObject().entrySet();
            Sensor sensor = new Sensor();
            sensor.setUid(entry.getKey());
            for (Map.Entry<String, JsonElement> sensorValue : sensorValues) {
                String sensorName = sensorValue.getKey();
                String sensorMeasuredValue = sensorValue.getValue().getAsString();
                sensor.addMeasuredValue(sensorName, sensorMeasuredValue);
            }
            sensors.add(sensor);
        }

        gatewayResponse.setSensors(sensors);
        gatewayResponse.setNowTimestamp(nowTimestamp);
        gatewayResponse.setTimestamp(timestamp);

        return gatewayResponse;
    }
}
