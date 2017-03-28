package org.bugu.mvc;

public class Action {

	private ControllerMapping controllerMapping;
	
	private MethodMapping methodMapping;
	
	
	public ControllerMapping getControllerMapping() {
		return controllerMapping;
	}
	public void setControllerMapping(ControllerMapping controllerMapping) {
		this.controllerMapping = controllerMapping;
	}
	public MethodMapping getMethodMapping() {
		return methodMapping;
	}
	public void setMethodMapping(MethodMapping methodMapping) {
		this.methodMapping = methodMapping;
	}
}
