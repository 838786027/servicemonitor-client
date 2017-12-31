package com.gosun.servicemonitor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Base64;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 不支持重复构建
 * 
 * @author caixiaopeng
 *
 */
public class MonitorContextBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorContextBuilder.class);
	private MonitorConfig config;
	private MonitorContext context;

	public MonitorContextBuilder config(MonitorConfig config) {
		this.config = config;
		return this;
	}

	public MonitorContext buildOrGet() {
		if (context != null) {
			return context;
		}

		// 设置上下文
		context = MonitorContext.getInstance();
		context.setServiceName("" + config.get(CommonParams.SERVICE_NAME));
		context.setServiceNote("" + config.get(CommonParams.SERVICE_NOTE));
		context.setNodeRole("" + config.get(CommonParams.NODE_ROLE));
		context.setNodeNote("" + config.get(CommonParams.NODE_NOTE));
		String temp=context.getServiceName()+":"+context.getNodeIp();
		context.setNodeId(Base64.getEncoder().encodeToString(temp.getBytes()));

		// 设置心跳线程
		HeartbeatThread heartbeatThread = context.getHeartbeatThread();
		heartbeatThread.setContext(context);
		heartbeatThread.setInterval(Integer.valueOf("" + config.get(HeartBeatParams.INTERVAL, "1000")));
		heartbeatThread.setHeartbeatClass((Class) config.get(HeartBeatParams.HEARTBEAT_CLASS, Heartbeat.class));

		// 设置本节点rpc服务
		context.getRpcEnv().setPort(9081);
		
		// 设置监控中心的rpc客户端
		context.getMcRpcEnv().setIp("" + config.get(CommonParams.CENTRE_HOST));
		context.getMcRpcEnv().setPort( Integer.valueOf("" + config.get(CommonParams.CENTRE_PORT, 9080)));

		return context;
	}
}
