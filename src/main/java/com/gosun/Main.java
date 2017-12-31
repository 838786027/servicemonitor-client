package com.gosun;

import com.gosun.servicemonitor.CommonParams;
import com.gosun.servicemonitor.HeartBeatParams;
import com.gosun.servicemonitor.MonitorConfig;
import com.gosun.servicemonitor.MonitorContext;
import com.gosun.servicemonitor.MonitorContextBuilder;

public class Main {
	public static void main(String[] args) {
		MonitorConfig config=new MonitorConfig();
		config.set(CommonParams.CENTRE_HOST,"127.0.0.1")
			.set(CommonParams.CENTRE_PORT,"9080")
			.set(CommonParams.SERVICE_NAME, "Solr上传程序1")
			.set(CommonParams.SERVICE_NOTE,"SERVICE_NOTE")
			.set(CommonParams.NODE_ROLE,"NODE_ROLE")
			.set(CommonParams.NODE_NOTE,"NODE_NOTE")
			.set(HeartBeatParams.INTERVAL,"5000");
		
		MonitorContextBuilder builder=new MonitorContextBuilder();
		MonitorContext context=builder.config(config)
			.buildOrGet();
		context.start();
	}
}
