package halo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {

	private Object target;

	private String methodName;

	private Object[] args = new Object[0];

	private Method method;

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	public void setSetterMethodName(String field){
		
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = (args != null ? args : new Object[0]);
	}

	public void prepare() {
		Object[] arguments = this.args;
		Class<?>[] argTypes = new Class[this.args.length];
		for (int i = 0; i < arguments.length; ++i) {
			argTypes[i] = (arguments[i] != null ? arguments[i].getClass()
					: Object.class);
		}
		try {
			this.method = this.target.getClass().getMethod(this.methodName,
					argTypes);
		}
		catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isPrepared() {
		return (this.method != null);
	}

	public Object invoke() {
		if (!this.isPrepared()) {
			throw new RuntimeException("must invoke prepare() first");
		}
		try {
			return this.method.invoke(this.target, args);
		}
		catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}