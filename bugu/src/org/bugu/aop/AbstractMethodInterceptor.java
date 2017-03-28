package org.bugu.aop;

import java.lang.reflect.Method;

public class AbstractMethodInterceptor implements MethodInterceptor{

	@Override
	public void intercept(InterceptorChain chain) {
		// TODO Auto-generated method stub
		if(beforeInvoke(chain.getCallingObj(),chain.getCallingMethod(),chain.getCallingMethodArgs())){
			chain.doChain();
			Object returnObj = chain.getReturnValue();
			Object newReturnObj = afterInvoke(chain.getCallingObj(),returnObj,chain.getCallingMethod(),chain.getCallingMethodArgs());
			chain.setReturnValue(newReturnObj);
		}
	}
    public boolean beforeInvoke(Object obj, Method method, Object... args) {
        return true;
    }
    
    public Object afterInvoke(Object obj, Object returnObj, Method method, Object... args) {
        return returnObj;
    }
}
