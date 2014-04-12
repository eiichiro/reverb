/*
 * Copyright (C) 2011 Eiichiro Uchiumi. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eiichiro.reverb.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@code MethodInvocation} represents a method invocation.
 * 
 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
 */
public class MethodInvocation<R> implements Invocation<R> {

	private final Method method;
	
	private final Object target;
	
	private final Object[] args;
	
	/**
	 * Constructs a new {@code MethodInvocation} instance with the specified 
	 * method.
	 * 
	 * @param method The method to be invoked.
	 */
	public MethodInvocation(Method method) {
		this.method = method;
		target = null;
		args = new Object[0];
	}
	
	/**
	 * Constructs a new {@code MethodInvocation} instance with the specified 
	 * static method and arguments.
	 * 
	 * @param method The static method to be invoked.
	 * @param args The arguments to be passed to the specified method.
	 */
	public MethodInvocation(Method method, Object[] args) {
		this.method = method;
		target = null;
		this.args = args;
	}
	
	/**
	 * Constructs a new {@code MethodInvocation} instance with the specified 
	 * method and object.
	 * 
	 * @param method The method to be invoked.
	 * @param object The object on which the specified method is invoked.
	 */
	public MethodInvocation(Method method, Object object) {
		this.method = method;
		this.target = object;
		args = new Object[0];
	}
	
	/**
	 * Constructs a new {@code MethodInvocation} instance with the specified 
	 * method, object and arguments.
	 * 
	 * @param method The method to be invoked.
	 * @param object The object on which the specified method is invoked.
	 * @param args The arguments to be passed to the specified method.
	 */
	public MethodInvocation(Method method, Object object, Object[] args) {
		this.method = method;
		this.target = object;
		this.args = args;
	}
	
	/**
	 * Constructs a new {@code MethodInvocation} instance with the specified 
	 * class, static method name, parameter types and arguments.
	 * 
	 * @param clazz The class on which the specified method is declared.
	 * @param method The static method name to be invoked.
	 * @param parameterTypes The array of parameter type to specify the method.
	 * @param args The arguments to be passed to the specified method.
	 * @throws NoSuchMethodException If a method of the specified name is not 
	 * declared on the specified class.
	 */
	public MethodInvocation(Class<?> clazz, String method,
			Class<?>[] parameterTypes, Object[] args)
			throws NoSuchMethodException {
		this.method = clazz.getMethod(method, parameterTypes);
		target = null;
		this.args = args;
	}
	
	/**
	 * Constructs a new {@code MethodInvocation} instance with the specified 
	 * class, static method name and arguments.
	 * 
	 * @param clazz The class on which the specified method is declared.
	 * @param method The static method name to be invoked.
	 * @param args The arguments to be passed to the specified method.
	 * @throws NoSuchMethodException If a method of the specified name is not 
	 * declared on the specified class.
	 */
	public MethodInvocation(Class<?> clazz, String method, Object[] args)
			throws NoSuchMethodException {
		Class<?>[] parameterTypes = new Class<?>[args.length];
		
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = args[i].getClass();
		}
		
		this.method = clazz.getMethod(method, parameterTypes);
		target = null;
		this.args = args;
	}
	
	/**
	 * Constructs a new {@code MethodInvocation} instance with the specified 
	 * class, method name, object, parameter types and arguments.
	 * 
	 * @param clazz The class on which the specified method is declared.
	 * @param method The method name to be invoked.
	 * @param object The object on which the specified method is invoked.
	 * @param parameterTypes The array of parameter type to specify the method.
	 * @param args The arguments to be passed to the specified method.
	 * @throws NoSuchMethodException If a method of the specified name is not 
	 * declared on the specified class.
	 */
	public MethodInvocation(Class<?> clazz, String method, Object object,
			Class<?>[] parameterTypes, Object[] args)
			throws NoSuchMethodException {
		this.method = clazz.getMethod(method, parameterTypes);
		this.target = object;
		this.args = args;
	}
	
	/**
	 * Constructs a new {@code MethodInvocation} instance with the specified 
	 * class, method name, object and arguments.
	 * 
	 * @param clazz The class on which the specified method is declared.
	 * @param method The method name to be invoked.
	 * @param object The object on which the specified method is invoked.
	 * @param args The arguments to be passed to the specified method.
	 * @throws NoSuchMethodException If a method of the specified name is not 
	 * declared on the specified class.
	 */
	public MethodInvocation(Class<?> clazz, String method, Object object,
			Object[] args) throws NoSuchMethodException {
		Class<?>[] parameterTypes = new Class<?>[args.length];
		
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = args[i].getClass();
		}
		
		this.method = clazz.getMethod(method, parameterTypes);
		this.target = object;
		this.args = args;
	}
	
	
	/**
	 * Proceeds this method invocation.
	 * 
	 * @return The result of this invocation proceeding.
	 * @throws Throwable If any exceptions occur while this method invocation is 
	 * proceeding.
	 */
	@SuppressWarnings("unchecked")
	public R proceed() throws Throwable {
		try {
			return (R) method().invoke(target, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
	
	/**
	 * Returns the method to be invoked.
	 * 
	 * @return The method to be invoked.
	 */
	public Method method() {
		return method;
	}
	
	/**
	 * Returns the target object the method to be invoked on.
	 * 
	 * @return The target object the method to be invoked on.
	 */
	public Object target() {
		return target;
	}

	/**
	 * Returns the arguments the method to be invoked with.
	 * 
	 * @return The arguments the method to be invoked with.
	 */
	@Override
	public Object[] args() {
		return args;
	}
	
}
