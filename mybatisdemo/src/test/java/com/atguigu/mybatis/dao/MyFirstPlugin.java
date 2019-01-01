package com.atguigu.mybatis.dao;

import java.util.Properties;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
/**
 * 完成插件签名：
 * 		告诉MyBatis当前插件用来拦截哪个对象的哪个方法
 * @author Administrator
 *
 */
@Intercepts({
	@Signature(type=StatementHandler.class,method="parameterize",args=java.sql.Statement.class)
	
})
public class MyFirstPlugin implements Interceptor{

	/**
	 * intercept:拦截：
	 * 		拦截目标对象的目标方法的执行
	 */
	public Object intercept(Invocation invocation) throws Throwable {//3
		System.out.println("MyFirstPlugin..intercept:"+invocation.getMethod());
		
		//执行目标方法
		Object proceed = invocation.proceed();
		return proceed;
	}
	/**
	 * plugin：
	 * 		包装目标对象的：包装：为目标对象创建一个代理对象
	 */
	public Object plugin(Object target) {//2
		System.out.println("MyFirstPlugin..plugin:mybatis将要包装的对象"+target);
		//我们可以借助plugin的wrap方法来使用当前Interceptor包装我们的目标对象
		Object wrap = Plugin.wrap(target, this);
		//返回为当前target创建的动态代理
		return wrap;
	}
	/**
	 * setProperties：
	 * 		将插件注册时的Property属性设置进来
	 */
	public void setProperties(Properties properties) {//1
		System.out.println("插件配置信息:"+properties);
	}

}
