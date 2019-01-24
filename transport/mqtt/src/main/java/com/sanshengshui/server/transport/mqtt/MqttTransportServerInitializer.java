package com.sanshengshui.server.transport.mqtt;

import com.sanshengshui.server.common.transport.SessionMsgProcessor;
import com.sanshengshui.server.common.transport.auth.DeviceAuthService;
import com.sanshengshui.server.common.transport.quota.QuotaService;
import com.sanshengshui.server.dao.device.DeviceService;
import com.sanshengshui.server.dao.relation.RelationService;
import com.sanshengshui.server.transport.mqtt.adaptors.MqttTransportAdaptor;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.ssl.SslHandler;

/**
 * @author james mu
 * @date 19-1-2 下午2:24
 */
public class MqttTransportServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SessionMsgProcessor processor;
    private final DeviceService deviceService;
    private final DeviceAuthService authService;
    private final RelationService relationService;
    private final MqttTransportAdaptor adaptor;
    private final MqttSslHandlerProvider sslHandlerProvider;
    private final QuotaService quotaService;
    private final int maxPayloadSize;

    public MqttTransportServerInitializer(SessionMsgProcessor processor, DeviceService deviceService, DeviceAuthService authService, RelationService relationService,
                                          MqttTransportAdaptor adaptor, MqttSslHandlerProvider sslHandlerProvider,
                                           QuotaService quotaService, int maxPayloadSize) {
        this.processor = processor;
        this.deviceService = deviceService;
        this.authService = authService;
        this.relationService = relationService;
        this.adaptor = adaptor;
        this.sslHandlerProvider = sslHandlerProvider;
        this.quotaService = quotaService;
        this.maxPayloadSize = maxPayloadSize;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();//设置ChannelPipeLine
        SslHandler sslHandler = null;
        //判断SSL处理器处理类是否为空，如果不为空，将SSL处理器加入到ChannelPipeLine
        if (sslHandlerProvider != null) {
            sslHandler = sslHandlerProvider.getSslHandler();
            pipeline.addLast(sslHandler);
        }
        //添加负载内容的解编码器
        pipeline.addLast("decoder", new MqttDecoder(maxPayloadSize));
        pipeline.addLast("encoder", MqttEncoder.INSTANCE);

        MqttTransportHandler handler = new MqttTransportHandler(processor, deviceService, authService, relationService,
                adaptor, sslHandler, quotaService);

        //添加Mqtt协议处理器
        pipeline.addLast(handler);
        //异步操作完成时回调
        ch.closeFuture().addListener(handler);
    }
}
