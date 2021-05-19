package adaptors;

import com.google.protobuf.Descriptors;
import iot.technology.groza.msg.queue.gen.TransportProtos;
import org.eclipse.californium.core.coap.Request;

/**
 * @author mushuwei
 */
public interface CoapTransportAdaptor {

    TransportProtos.PostTelemetryMsg convertToPostTelemetry(String sessionId, Request inbound,
                                                            Descriptors.Descriptor telemetryMsgDescriptor);
}
