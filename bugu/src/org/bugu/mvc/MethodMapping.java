package org.bugu.mvc;

import java.lang.reflect.Method;
/**
 * 方法映射信息
 * @author Administrator
 *
 */
public class MethodMapping{
	private Method method;
	private String url;
	private ActionFilter[] actionFilters;
	private String result;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ActionFilter[] getActionFilters() {
		return actionFilters;
	}
	public void setActionFilters(ActionFilter[] actionFilters) {
		this.actionFilters = actionFilters;
	}
	
}
