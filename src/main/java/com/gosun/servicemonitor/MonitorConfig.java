package com.gosun.servicemonitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 监控中心配置类
 * @author caixiaopeng
 *
 */
public class MonitorConfig {
	private Map<Object, Object> configMap=new HashMap<Object, Object>();
	
	public MonitorConfig set(String key,Object value){
		configMap.put(key, value);
		return this;
	}
	
	public MonitorConfig set(Map<Object, Object> configs){
		configMap.putAll(configs);
		return this;
	}
	
	public MonitorConfig set(Properties configs){
		configMap.putAll(configs);
		return this;
	}
	
	public Object get(Object key){
		return configMap.get(key);
	}
	
	public Object get(Object key,Object defaultValue){
		Object value=configMap.get(key);
		return value==null?defaultValue:value;
	}
}
