package com.gosun.servicemonitor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gosun.servicemonitor.rpc.Info;
import com.gosun.servicemonitor.rpc.MonitorCentreInterface;
import com.gosun.servicemonitor.rpc.MonitorClientInterface;
import com.gosun.servicemonitor.rpc.Node;
import com.gosun.servicemonitor.rpc.Response;
import com.gosun.servicemonitor.rpc.RpcEnv;
import com.gosun.servicemonitor.rpc.Servicer;
import com.gosun.servicemonitor.rpc.impl.MonitorClientImpl;
import com.gosun.util.InternetUtils;

/**
 * 监控中心上下文 单例，线程安全
 * 
 * @author caixiaopeng
 *
 */
public class MonitorContext {
	private String serviceName;
	private String serviceNote;
	private String nodeRole;
	private String nodeNote;
	private String nodeId;
	private String nodeIp = InternetUtils.getLocalHostLANAddress().getHostAddress();

	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorContext.class);
	private HeartbeatThread heartbeatThread = HeartbeatThread.getInstance();

	/**
	 * 本地rpc信息
	 */
	private RpcEnv rpcEnv = new RpcEnv();

	/**
	 * 监控中心rpc信息
	 */
	private RpcEnv mcRpcEnv = new RpcEnv();

	/**
	 * 监控中心的rpc客户端
	 */
	private NettyTransceiver mcClient;

	/**
	 * 监控中心的rpc代理
	 */
	private MonitorCentreInterface mcProxy;

	private static MonitorContext instance;

	private MonitorContext() {
		rpcEnv.setIp(nodeIp);
	}

	public synchronized static MonitorContext getInstance() {
		if (instance != null) {
			return instance;
		}
		instance = new MonitorContext();
		return instance;
	}

	/**
	 * 无阻塞启动
	 */
	public void start() {
		LOGGER.info("监控服务开启");
		// 开启本地rpc服务
		Server server = new NettyServer(
				new SpecificResponder(MonitorClientInterface.class, new MonitorClientImpl(MonitorContext.this)),
				new InetSocketAddress(rpcEnv.getPort()));
		server.start();
		// 尝试连接服务中心
		retryRegister();
	}

	/**
	 * 尝试重新注册
	 */
	public void retryRegister() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				LOGGER.info("准备注册监听服务...");
				while (mcClient == null || mcProxy == null) {
					try {
						String centreHost = "" + mcRpcEnv.getIp();
						int centrePort = mcRpcEnv.getPort();
						NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(centreHost, centrePort));
						MonitorCentreInterface proxy = (MonitorCentreInterface) SpecificRequestor
								.getClient(MonitorCentreInterface.class, client);
						mcClient = client;
						mcProxy = proxy;

						// 注册服务
						Node node = new Node();
						node.setId(nodeId);
						node.setIp(nodeIp);
						node.setRole(nodeRole);
						node.setNote(nodeNote);
						Servicer servicer = new Servicer();
						servicer.setName(serviceName);
						servicer.setNote(serviceNote);
						Info info = new Info();
						info.setSendTime(
								LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
						try {
							Response response = mcProxy.registerNode(rpcEnv, node, servicer, info);

							LOGGER.debug("注册请求返回");
							LOGGER.debug("\t status=" + response.status);
							LOGGER.debug("\t QTime=" + response.QTime);
							LOGGER.debug("\t msg=" + response.msg);
						} catch (AvroRemoteException e) {
							LOGGER.error("注册服务失败，关闭监控", e);
							return;
						}

						LOGGER.info("注册成功");
					} catch (IOException e) {
						LOGGER.warn("连接服务中心[" + mcRpcEnv + "]失败，30s后重试...");
						try {
							Thread.sleep(30 * 1000);
						} catch (InterruptedException e1) {
							LOGGER.error("监控上下文线程睡眠失败", e1);
							return;
						}
					}
				}

				// 开启心跳程序
				if (heartbeatThread.isPause()) {
					LOGGER.info("唤醒心跳进程");
					heartbeatThread.setPause(false);
					synchronized (heartbeatThread) {
						heartbeatThread.notifyAll();
					}
				} else {
					LOGGER.info("开启心跳进程");
					heartbeatThread.start();
				}
			}
		}).start();
	}

	/**
	 * 关闭和监控中心的连接
	 */
	public void closeConnectionWithCentre() {
		LOGGER.info("关闭监控中心 [ " + mcRpcEnv + " ] 连接");
		if (mcClient != null) {
			mcClient.close();
		}
		mcClient = null;
		mcProxy = null;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceNote() {
		return serviceNote;
	}

	public void setServiceNote(String serviceNote) {
		this.serviceNote = serviceNote;
	}

	public String getNodeRole() {
		return nodeRole;
	}

	public void setNodeRole(String nodeRole) {
		this.nodeRole = nodeRole;
	}

	public String getNodeNote() {
		return nodeNote;
	}

	public void setNodeNote(String nodeNote) {
		this.nodeNote = nodeNote;
	}

	public HeartbeatThread getHeartbeatThread() {
		return heartbeatThread;
	}

	public NettyTransceiver getMcClient() {
		return mcClient;
	}

	public void setMcClient(NettyTransceiver mcClient) {
		this.mcClient = mcClient;
	}

	public MonitorCentreInterface getMcProxy() {
		return mcProxy;
	}

	public void setMcProxy(MonitorCentreInterface mcProxy) {
		this.mcProxy = mcProxy;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	public RpcEnv getRpcEnv() {
		return rpcEnv;
	}

	public void setRpcEnv(RpcEnv rpcEnv) {
		this.rpcEnv = rpcEnv;
	}

	public RpcEnv getMcRpcEnv() {
		return mcRpcEnv;
	}

	public void setMcRpcEnv(RpcEnv mcRpcEnv) {
		this.mcRpcEnv = mcRpcEnv;
	}
}
