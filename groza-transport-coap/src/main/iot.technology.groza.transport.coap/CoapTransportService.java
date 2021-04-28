import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapServer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author jamesmsw
 * @date 2021/4/22 5:18 下午
 */
@Service("CoapTransportService")
@Slf4j
public class CoapTransportService {

    private static final String V1 = "v1";
    private static final String API = "api";
    private static final String EFENTO = "efento";
    private static final String MEASUREMENTS = "m";

    private CoapServer server;

    @PostConstruct
    public void init() {
        log.info("Starting CoAP transport...");
        log.info("Starting CoAP transport server");
        this.server = new CoapServer();

    }

    private void createResources() {

    }
}
