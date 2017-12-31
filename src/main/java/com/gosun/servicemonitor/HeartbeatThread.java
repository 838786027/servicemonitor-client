package com.gosun.servicemonitor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gosun.servicemonitor.rpc.Info;
import com.gosun.servicemonitor.rpc.MonitorCentreInterface;
import com.gosun.servicemonitor.rpc.Response;
import com.gosun.util.InternetUtils;

/**
 * 心跳线程 单例，线程安全
 * 
 * @author caixiaopeng
 *
 */
public class HeartbeatThread extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatThread.class);

	private static HeartbeatThread instance;

	private MonitorContext context;

	/**
	 * 心跳间隔 单位ms 默认值1000
	 */
	private int interval = 1000;
	
	private boolean pause=false;

	/**
	 * 心跳信息类 默认为com.gosun.servicemonitor.Heartbeat
	 */
	private Class<? extends Heartbeat> heartbeatClass = Heartbeat.class;

	public synchronized static HeartbeatThread getInstance() {
		if (instance != null) {
			return instance;
		}
		instance = new HeartbeatThread();
		return instance;
	}

	private HeartbeatThread() {
		setName("监控中心——心跳线程");
	}

	public void run() {
		while (true) {
			LOGGER.debug("发送心跳");
			MonitorCentreInterface proxy=context.getMcProxy();
			Heartbeat heartbeat = null;
			try {
				heartbeat = heartbeatClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				LOGGER.error("心跳进程获取心跳信息实例时异常", e);
				return;
			}

			// rpc心跳请求
			try {
				com.gosun.servicemonitor.rpc.Heartbeat heartbeatTemp = new com.gosun.servicemonitor.rpc.Heartbeat();
				Info info=new Info();
				heartbeatTemp.nodeId = context.getNodeId();
				info.sendTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				// note：用于测试，待删除
				heartbeatTemp.extraMsg = heartbeat.getExtraInfo();

				Response response = proxy.sendHeartbeat(heartbeatTemp, info);

				LOGGER.debug("心跳请求返回");
				LOGGER.debug("\t status=" + response.status);
				LOGGER.debug("\t QTime=" + response.QTime);
				LOGGER.debug("\t msg=" + response.msg);
			} catch (AvroRemoteException e) {
				LOGGER.error("发送心跳请求失败");
				LOGGER.info("准备暂停心跳进程并重新连接监控中心");
				context.closeConnectionWithCentre();
				context.retryRegister();
				pause=true;
				try {
					synchronized (this) {
						wait();
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				LOGGER.error("心跳进程睡眠失败，导致心跳进程死亡", e);
				return;
			}
		}
	}

	public MonitorContext getContext() {
		return context;
	}

	public void setContext(MonitorContext context) {
		this.context = context;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public Class<? extends Heartbeat> getHeartbeatClass() {
		return heartbeatClass;
	}

	public void setHeartbeatClass(Class<? extends Heartbeat> heartbeatClass) {
		this.heartbeatClass = heartbeatClass;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
}
