package org.bugu.mvc.impl;

import org.bugu.mvc.Action;
import org.bugu.mvc.View;
import org.bugu.mvc.ViewResolver;
import org.bugu.util.Toolkit;

public class DefaultViewResolver implements ViewResolver{

	@Override
	public View resolver(Action action) {
		// TODO Auto-generated method stub
		String str = action.getMethodMapping().getResult();
		if(Toolkit.isEmpty(str))
			return new SimpleView();
		if(str.startsWith("json")){
			return new JsonView();
		}else if(str.startsWith("jsp")){
			return new JspView();
		}
		return new SimpleView();
	}

}
