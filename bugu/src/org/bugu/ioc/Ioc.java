package org.bugu.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.bugu.aop.InterceptorChain;
import org.bugu.aop.MethodInterceptor;
import org.bugu.aop.annotation.Interceptor;
import org.bugu.ioc.annotation.Bean;
import org.bugu.ioc.annotation.Res;
import org.bugu.util.Toolkit;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 单例模式
 * 
 * @author Administrator
 *
 */
public class Ioc {
	private static Ioc instance;

	private Ioc() {

		objectPool = new HashMap<Class<?>, Object>();

		methodInterceptorPool = new HashMap<Class<? extends MethodInterceptor>, MethodInterceptor>();

		methodInterceptorListPool = new HashMap<String, ArrayList<MethodInterceptor>>();

	}

	public synchronized static Ioc getInstance() {
		if (instance == null) {
			instance = new Ioc();
		}
		return instance;
	}

	private HashMap<Class<?>, Object> objectPool;

	private HashMap<Class<? extends MethodInterceptor>, MethodInterceptor> methodInterceptorPool;

	private HashMap<String, ArrayList<MethodInterceptor>> methodInterceptorListPool;

	public Object get(Class<?> clazz) {
		Object obj = objectPool.get(clazz);
		if (obj == null) {
			Interceptor classInterceptor = clazz.getDeclaredAnnotation(Interceptor.class);
			ArrayList<MethodInterceptor> classMethodInterceptorlist = null;
			if (classInterceptor != null) {
				classMethodInterceptorlist = getMethodInterceptorList(classInterceptor);
			}
			Method methods[] = clazz.getDeclaredMethods();
			boolean proxyed = false;
			for (Method m : methods) {
				Interceptor methodInterceptor = m.getDeclaredAnnotation(Interceptor.class);
				if (methodInterceptor != null) {
					ArrayList<MethodInterceptor> list = getMethodInterceptorList(methodInterceptor);
					methodInterceptorListPool.put(clazz.getName() + "." + m.getName(), list);
					proxyed = true;
				} else if (!Toolkit.isEmpty(classMethodInterceptorlist)) {
					methodInterceptorListPool.put(clazz.getName() + "." + m.getName(), classMethodInterceptorlist);
					proxyed = true;
				}
			}
			if (!proxyed) {
				try {
					obj = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				obj = getProxyObject(clazz);
			}
			
			injectField(obj,clazz);
			
			objectPool.put(clazz, obj);
		}
		return obj;
	}

	private void injectField(Object obj,Class<?> clazz){
		Field fields[] = clazz.getDeclaredFields();
		for (Field field : fields) {
			Res res = field.getAnnotation(Res.class);
			Bean bean = field.getType().getAnnotation(Bean.class);
			if (bean != null && res != null) {
				field.setAccessible(true);
				try {
					field.set(obj, get(field.getType()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				field.setAccessible(false);
			}
		}
	}
	
	private ArrayList<MethodInterceptor> getMethodInterceptorList(Interceptor interceptor) {
		Class<? extends MethodInterceptor> metodInterceptorClass[] = interceptor.value();
		ArrayList<MethodInterceptor> list = new ArrayList<MethodInterceptor>(3);
		for (Class<? extends MethodInterceptor> interceptorClass : metodInterceptorClass) {
			MethodInterceptor mi = methodInterceptorPool.get(interceptorClass);
			if (mi == null) {
				try {
					mi = interceptorClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				methodInterceptorPool.put(interceptorClass, mi);
			}
			list.add(mi);
		}
		return list;
	}

	private Object getProxyObject(Class<?> clazz) {
		Object obj = null;
		Enhancer en = new Enhancer();
		en.setSuperclass(clazz);
		en.setCallback(new CglibMethodInterceptor());
		obj = en.create();
		return obj;
	}

	class CglibMethodInterceptor implements net.sf.cglib.proxy.MethodInterceptor {

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			ArrayList<MethodInterceptor> methodInterceptors = methodInterceptorListPool.get(obj.getClass().getSuperclass().getName()+"."+method.getName());
			if(Toolkit.isEmpty(methodInterceptors)){
				return proxy.invokeSuper(obj, args);
			}
			InterceptorChainInvokeCallback callback = new InterceptorChainInvokeCallback(proxy, obj, args);
			InterceptorChain chain = new InterceptorChain(methodInterceptors, obj, method, args, callback);
			chain.doChain();
			return chain.getReturnValue();
		}

	}

	class InterceptorChainInvokeCallback implements InterceptorChain.Callback {

		private MethodProxy proxy;
		private Object obj;
		private Object[] args;

		public InterceptorChainInvokeCallback(MethodProxy proxy, Object obj, Object[] args) {
			this.proxy = proxy;
			this.obj = obj;
			this.args = args;
		}

		@Override
		public Object invoke() {
			// TODO Auto-generated method stub
			try {
				return proxy.invokeSuper(obj, args);
			} catch (Throwable e) {
				// TODO Auto-generated catch
				// block
				e.printStackTrace();
				return null;
			}
		}

	}
}
