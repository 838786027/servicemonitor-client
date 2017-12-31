package com.gosun;

import java.util.Base64;
import java.util.Random;

import com.gosun.servicemonitor.CommonParams;
import com.gosun.servicemonitor.HeartBeatParams;
import com.gosun.servicemonitor.MonitorConfig;
import com.gosun.servicemonitor.MonitorContext;
import com.gosun.servicemonitor.MonitorContextBuilder;

public class Test2 {
	public static void main(String[] args) throws InterruptedException {
		String[] serviceNames = { "solr上传程序", "数据集成程序", "监控程序" };
		String[] nodeRotes = { "master", "slave1", "slave2" };
		String[] ips = { "192.168.11.143", "127.0.0.1", "localhost" };
		int[] ports = { 9081, 9082, 9083 };
		int i=2;
	
		MonitorConfig config = new MonitorConfig();
		config.set(CommonParams.CENTRE_HOST, "127.0.0.1").set(CommonParams.CENTRE_PORT, "9080")
				.set(CommonParams.SERVICE_NAME, serviceNames[i]).set(CommonParams.SERVICE_NOTE, "")
				.set(CommonParams.NODE_ROLE, nodeRotes[i]).set(CommonParams.NODE_NOTE, "")
				.set(HeartBeatParams.INTERVAL, "5000");

		MonitorContextBuilder builder = new MonitorContextBuilder();
		MonitorContext context = builder.config(config).buildOrGet();
		context.setNodeIp(ips[i]);
		String temp = context.getServiceName() + ":" + context.getNodeIp();
		context.setNodeId(Base64.getEncoder().encodeToString(temp.getBytes()));
		context.getRpcEnv().setPort(ports[i]);
		context.start();
	}
}
