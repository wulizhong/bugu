package org.bugu.mvc;

import java.util.HashMap;
import java.util.Map;
/**
 * 业务处理器映射信息
 * @author Administrator
 *
 */
public class ControllerMapping {
	
	private Object host;
	
	private String url;
	
	private Map<String,MethodMapping> methodMapping;
	
	public ControllerMapping(){
		methodMapping = new HashMap<String,MethodMapping>();
	}
	public void addMethodMapping(MethodMapping mp){
		methodMapping.put(mp.getUrl(), mp);
	}
	public MethodMapping getMethodMapping(String url){
		return methodMapping.get(url);
	}
	
	public Object getHost() {
		return host;
	}
	
	public void setHost(Object host) {
		this.host = host;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
