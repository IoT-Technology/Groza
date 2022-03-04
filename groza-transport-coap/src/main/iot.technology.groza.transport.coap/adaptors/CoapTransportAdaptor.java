package adaptors;

import com.google.protobuf.Descriptors;
import iot.technology.groza.server.gen.transport.TransportProtos.PostAttributeMsg;
import iot.technology.groza.server.gen.transport.TransportProtos.PostTelemetryMsg;
import iot.technology.groza.server.transport.api.AdaptorException;
import org.eclipse.californium.core.coap.Request;

/**
 * @author mushuwei
 */
public interface CoapTransportAdaptor {

    PostTelemetryMsg convertToPostTelemetry(String sessionId, Request inbound,
                                            Descriptors.Descriptor telemetryMsgDescriptor) throws AdaptorException;

    PostAttributeMsg convertToPostAttributes(String sessionId, Request inbound,
                                             Descriptors.Descriptor attributeMsgDescriptor) throws AdaptorException;
    

}
