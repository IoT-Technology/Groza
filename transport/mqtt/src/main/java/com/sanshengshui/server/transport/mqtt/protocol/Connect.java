package com.sanshengshui.server.transport.mqtt.protocol;

import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author james mu
 * @date 18-12-7 下午5:01
 */
@Slf4j
public class Connect {

    public void processConnect(Channel channel, MqttConnectMessage msg){
        // 消息解码器出现异常
        if(msg.decoderResult().isFailure()){
            Throwable cause = msg.decoderResult().cause();
            if (cause instanceof MqttUnacceptableProtocolVersionException){
                // 不支持的协议版本
                MqttConnAckMessage connAckMessage = (MqttConnAckMessage) MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK,false,MqttQoS.AT_MOST_ONCE,false,0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION,false),
                        null
                );
                channel.write(connAckMessage);
                channel.close();
                return;
            }else if (cause instanceof MqttIdentifierRejectedException){
                // 不合格的clientId
                MqttConnAckMessage connAckMessage = (MqttConnAckMessage) MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK,false,MqttQoS.AT_MOST_ONCE,false,0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED,false),
                        null
                );
                channel.write(connAckMessage);
                channel.close();
                return;
            }
        }


    }
}
