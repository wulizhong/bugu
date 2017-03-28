package org.bugu.mvc;

import java.util.HashMap;

public class ModelAndView {

	private String view;
	
	private HashMap<String,Object> dataMap = new HashMap<String,Object>();
	
	public void addModel(String key,Object model){
		dataMap.put(key, model);
	}
	
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public HashMap<String, Object> getModelMap() {
		return dataMap;
	}

}
