package org.bugu.mvc;

import javax.servlet.http.HttpServletRequest;

import org.bugu.Context;
import org.bugu.util.Toolkit;

public class UrlMapping {

	private Context context;
	public UrlMapping(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	public Action mapping(HttpServletRequest request){
		String path = request.getServletPath();
		Action action = null;
		
		for(ControllerMapping cm : context.getControllerMappings()){
			if(Toolkit.isEmpty(cm.getUrl())){
				MethodMapping mm = cm.getMethodMapping(path);
				if(mm!=null){
					action = new Action();
					action.setControllerMapping(cm);
					action.setMethodMapping(mm);
				}
				break;
			}else{
				if(path.startsWith(cm.getUrl())){
					MethodMapping mm = cm.getMethodMapping(path.substring(cm.getUrl().length()));
					if(mm!=null){
						action = new Action();
						action.setControllerMapping(cm);
						action.setMethodMapping(mm);
					}
					break;
				}
			}
		}
		return action;
	}
}
