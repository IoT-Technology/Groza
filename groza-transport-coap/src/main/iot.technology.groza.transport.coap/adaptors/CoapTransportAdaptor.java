package adaptors;

import com.google.protobuf.Descriptors;
import iot.technology.server.gen.transport.TransportProtos;
import org.eclipse.californium.core.coap.Request;

/**
 * @author mushuwei
 */
public interface CoapTransportAdaptor {

    TransportProtos.PostTelemetryMsg convertToPostTelemetry(String sessionId, Request inbound,
                                                            Descriptors.Descriptor telemetryMsgDescriptor);
}
