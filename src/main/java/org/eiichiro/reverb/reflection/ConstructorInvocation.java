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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * {@code ConstructorInvocation} represents a constructor invocation.
 * 
 * @author <a href="mailto:eiichiro@eiichiro.org">Eiichiro Uchiumi</a>
 */
public class ConstructorInvocation<T> implements Invocation<T> {

	private final Constructor<T> constructor;
	
	private final Object[] args;
	
	/**
	 * Constructs a new {@code ConstructorInvocation} with the specified 
	 * constructor.
	 * 
	 * @param constructor The constructor to be invoked.
	 */
	public ConstructorInvocation(Constructor<T> constructor) {
		this.constructor = constructor;
		args = new Object[0];
	}
	
	/**
	 * Constructs a new {@code ConstructorInvocation} with the specified 
	 * class, parameter types and arguments.
	 * 
	 * @param clazz The class that the constructor is invoked.
	 * @param parameterTypes The array of parameter type to specify the 
	 * constructor.
	 * @param args The arguments to be passed to the constructor.
	 * @throws NoSuchMethodException If the constructor is not declared on the 
	 * specified class.
	 */
	public ConstructorInvocation(Class<T> clazz, Class<?>[] parameterTypes,
			Object[] args) throws NoSuchMethodException {
		constructor = clazz.getConstructor(parameterTypes);
		this.args = args;
	}
	
	/**
	 * Constructs a new {@code ConstructorInvocation} with the specified 
	 * class and arguments.
	 * 
	 * @param clazz The class that the constructor is invoked.
	 * @param args The arguments to be passed to the constructor.
	 * @throws NoSuchMethodException If the constructor is not declared on the 
	 * specified class.
	 */
	public ConstructorInvocation(Class<T> clazz, Object[] args)
			throws NoSuchMethodException {
		Class<?>[] parameterTypes = new Class<?>[args.length];
		
		for (int i = 0; i < args.length; i++) {
			parameterTypes[i] = args[i].getClass();
		}
		
		constructor = clazz.getConstructor(parameterTypes);
		this.args = args;
	}
	
	/**
	 * Proceeds this constructor invocation.
	 * 
	 * @return The instance of the specified class.
	 * @throws Throwable If any exceptions occur while this invocation is 
	 * proceeding.
	 */
	public T proceed() throws Throwable {
		try {
			return constructor().newInstance(args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
	
	/**
	 * Returns the constructor to be invoked.
	 * 
	 * @return The constructor to be invoked.
	 */
	public Constructor<T> constructor() {
		return constructor;
	}

	
	/**
	 * Returns the arguments the constructor to be invoked with.
	 * 
	 * @return The arguments the constructor to be invoked with.
	 */
	@Override
	public Object[] args() {
		return args;
	}
	
}
