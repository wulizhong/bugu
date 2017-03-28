package org.bugu.aop;

public interface MethodInterceptor {
	public void intercept(InterceptorChain chain);
}
