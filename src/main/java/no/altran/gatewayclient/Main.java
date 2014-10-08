package no.altran.gatewayclient;

import no.altran.gatewayclient.communication.Client;
import no.altran.gatewayclient.communication.DataSender;
import no.altran.gatewayclient.model.GatewayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author catalin@besleaga.com
 * @author Stig@Lau.no
 */

public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    private final Client client = new Client("");
    private Parser parser = new Parser();

    public void runFullRoundtrip() {
        try {
            String gatewayClientResponse = client.getContent();
            GatewayResponse gatewayResponse = parser.parse(gatewayClientResponse);
            DataSender.send(gatewayResponse);
        } catch (Exception e) {
            logger.error("BooYaa", e);
        }
    }

    public static void main(String[] args) {
        new Main().runFullRoundtrip();
    }
}
