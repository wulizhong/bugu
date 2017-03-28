package org.bugu.mvc;

/**
 * 根据结果 生成指定的View 视图
 * @author Administrator
 *
 */
public interface ViewResolver {

	public View resolver(Action action);
	
}
