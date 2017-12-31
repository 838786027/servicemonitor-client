package com.gosun.servicemonitor.rpc.impl;

import org.apache.avro.AvroRemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gosun.servicemonitor.MonitorContext;
import com.gosun.servicemonitor.rpc.Info;
import com.gosun.servicemonitor.rpc.MonitorClientInterface;
import com.gosun.servicemonitor.rpc.Response;
import com.gosun.servicemonitor.rpc.RpcEnv;

public class MonitorClientImpl implements MonitorClientInterface{

	private static final Logger LOGGER=LoggerFactory.getLogger(MonitorClientImpl.class);
	
	private MonitorContext context;
	
	public MonitorClientImpl(MonitorContext context) {
		super();
		this.context = context;
	}

	/**
	 * 激活本节点
	 * 
	 */
	@Override
	public Response activate(RpcEnv rpcEnv, Info info) throws AvroRemoteException {
		LOGGER.info("接收到来自监控中心 [ "+rpcEnv+" ] 的激活请求");
		if(!context.getMcRpcEnv().equals(rpcEnv)){
			// 监控中心地址改变
			LOGGER.info("监控中心地址已改变 [ "+context.getMcRpcEnv()+" ] -> [ "+rpcEnv+" ]");
			context.closeConnectionWithCentre();
			context.setRpcEnv(rpcEnv);
		}
		if (context.getMcClient()!=null && !context.getMcClient().isConnected()) {
			context.closeConnectionWithCentre();
			context.retryRegister();
		}else if(context.getMcClient()==null){
			context.retryRegister();
		}
		
		Response response=new Response();
		response.setMsg("已激活节点");
		response.setQTime(-1L);
		response.setStatus(0);
		return response;
	}

}
