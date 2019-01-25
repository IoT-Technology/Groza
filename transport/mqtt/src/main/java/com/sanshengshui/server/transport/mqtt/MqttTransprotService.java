package com.sanshengshui.server.transport.mqtt;


import com.sanshengshui.server.common.transport.SessionMsgProcessor;
import com.sanshengshui.server.common.transport.auth.DeviceAuthService;
import com.sanshengshui.server.common.transport.quota.host.HostRequestsQuotaService;
import com.sanshengshui.server.dao.device.DeviceService;
import com.sanshengshui.server.dao.relation.RelationService;
import com.sanshengshui.server.transport.mqtt.adaptors.MqttTransportAdaptor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author james
 * @date 2018年10月29日 19:33
 */
@Service("MqttTransportService")
@ConditionalOnProperty(prefix = "mqtt", value = "enabled", havingValue = "true", matchIfMissing = false)
@Slf4j
public class MqttTransprotService {

    private static final String V1 = "v1";
    private static final String DEVICE = "device";

    @Autowired(required = false)
    private ApplicationContext appContext;

    @Autowired(required = false)
    private SessionMsgProcessor processor;

    @Autowired(required = false)
    private DeviceService deviceService;

    @Autowired(required = false)

    private DeviceAuthService authService;

    @Autowired(required = false)

    private RelationService relationService;

    @Autowired(required = false)

    private MqttSslHandlerProvider sslHandlerProvider;

    @Autowired(required = false)
    private HostRequestsQuotaService quotaService;

    @Value("${mqtt.bind_address}")
    private String host;
    @Value("${mqtt.bind_port}")
    private Integer port;
    @Value("${mqtt.adaptor}")
    private String adaptorName;

    @Value("${mqtt.netty.leak_detector_level}")
    private String leakDetectorLevel;
    @Value("${mqtt.netty.boss_group_thread_count}")
    private Integer bossGroupThreadCount;
    @Value("${mqtt.netty.worker_group_thread_count}")
    private Integer workerGroupThreadCount;
    @Value("${mqtt.netty.max_payload_size}")
    private Integer maxPayloadSize;

    private MqttTransportAdaptor adaptor;

    private Channel serverChannel;
    // 创建两个 EventLoopGroup 对象
    private EventLoopGroup bossGroup;//创建boss线程组 用于服务端接受客户端的连接
    private EventLoopGroup workerGroup;//创建worker线程组 用于进行SocketChannel的数据读写

    @PostConstruct
    public void init() throws Exception {
        //设置服务端Netty内存读写泄露检测级别，缺省条件下为:DISABLED
        log.info("Setting resource leak detector level to {}", leakDetectorLevel);
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(leakDetectorLevel.toUpperCase()));

        log.info("Starting MQTT transport...");
        log.info("Lookup MQTT transport adaptor {}", adaptorName);
        //适配器名加载成Bean类
        this.adaptor = (MqttTransportAdaptor) appContext.getBean(adaptorName);

        log.info("Starting MQTT transport server");
        //设置boss线程组和work线程组的线程数量
        bossGroup = new NioEventLoopGroup(bossGroupThreadCount);
        workerGroup = new NioEventLoopGroup(workerGroupThreadCount);
        //创建ServerBootstrap对象
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)//设置使用的EventLoopGroup
                .channel(NioServerSocketChannel.class)//设置要被实例化的为NioServerSocketChannel类
                .childHandler(new MqttTransportServerInitializer(processor, deviceService, authService, relationService,
                        adaptor, sslHandlerProvider, quotaService, maxPayloadSize));//设置连入服务端的Client的SocketChannel的处理器
        /**
         * 绑定端口，并同步等待成功，即启动服务器
         */
        serverChannel = b.bind(host, port).sync().channel();
        log.info("Mqtt transport started!");
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        log.info("Stopping MQTT transport!");
        try {
            /**
             * 监听服务端关闭，并阻塞等待
             */
            serverChannel.close().sync();
        } finally {
            //优雅关闭俩个EventLoopGroup对象
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        log.info("MQTT transport stopped!");
    }
}
