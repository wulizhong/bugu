package org.bugu;

import java.util.ArrayList;

import org.bugu.mvc.ControllerMapping;
/**
 * 上下文环境，项目启动后的一些映射注解等信息会存储在这里
 * @author Administrator
 *
 */
public class Context {
	
	private ArrayList<ControllerMapping> controllerMappings;

	public ArrayList<ControllerMapping> getControllerMappings() {
		return controllerMappings;
	}
	protected void setControllerMappings(ArrayList<ControllerMapping> controllerMappings) {
		this.controllerMappings = controllerMappings;
	}
	
}
