/*
 * Copyright (C) 2012 Eiichiro Uchiumi. All Rights Reserved.
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
package org.eiichiro.reverb.lang;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Set;

/**
 * {@code JLCClassResolver} is a {@code java.lang.Class} based extension of 
 * {@link ClassResolver} ('JLC' means {@code java.lang.Class}).
 * This class loads the class as {@code java.lang.Class} directly from the 
 * specified search path.
 * 
 * @author <a href="mailto:eiichiro@eiichiro.org">Eiichiro Uchiumi</a>
 */
public class JLCClassResolver extends ClassResolver<Class<?>> {

	private ClassLoader classLoader;
	
	/**
	 * Constructs a new {@code JLCClassResolver} instance with the specified 
	 * search paths.
	 * 
	 * @param paths The search path.
	 */
	public JLCClassResolver(Iterable<URL> paths) {
		this(Thread.currentThread().getContextClassLoader(), paths);
	}
	
	/**
	 * Constructs a new {@code JLCClassResolver} instance with the specified 
	 * {@code ClassLoader} and search paths.
	 * 
	 * @param classLoader The {@code ClassLoader} to load classes.
	 * @param paths The search path.
	 */
	public JLCClassResolver(ClassLoader classLoader, Iterable<URL> paths) {
		super(paths);
		this.classLoader = classLoader;
	}
	
	/**
	 * Loads the class of the specified name from the specified 
	 * {@code InputStream} and returns loaded class representation as 
	 * {@code java.lang.Class}.
	 * 
	 * @param clazz The name of the class to be loaded.
	 * @param stream {@code InputStream} to load a class file.
	 * @return The loaded class representation as {@code java.lang.Class}.
	 */
	@Override
	protected Class<?> load(String clazz, InputStream stream) {
//		System.out.println(clazz);
		
		try {
			return Class.forName(clazz, true, classLoader);
		} catch (Exception e) {
//			System.out.println(e);
			return null;
		} catch (NoClassDefFoundError e) {
//			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Resolves the classes that contains the specified name as 
	 * {@code java.lang.Class}.
	 * 
	 * @param name The part of the class name.
	 * @return Classes that contains the specified name as 
	 * {@code java.lang.Class}.
	 * @throws IOException If any I/O access fails while traversing the search 
	 * path.
	 */
	@Override
	public Set<Class<?>> resolveByName(final String name) throws IOException {
		Matcher<Class<?>> matcher = new Matcher<Class<?>>() {
			
			public boolean matches(Class<?> clazz) {
				return clazz.getName().contains(name);
			}
			
		};
		return resolve(matcher);
	}
	
	/**
	 * Resolves the classes that inherits the specified superclass as 
	 * {@code java.lang.Class}.
	 * 
	 * @param superclass The superclass being inherited.
	 * @return Classes that inherits the specified superclass as 
	 * {@code java.lang.Class}.
	 * @throws IOException If any I/O access fails while traversing the search 
	 * path.
	 */
	@Override
	public Set<Class<?>> resolveBySuperclass(final Class<?> superclass)
			throws IOException {
		Matcher<Class<?>> matcher = new Matcher<Class<?>>() {
			
			public boolean matches(Class<?> clazz) {
				if (clazz.getSuperclass() != null) {
					if (clazz.getSuperclass().equals(superclass)) {
						return true;
					} else {
						return matches(clazz.getSuperclass());
					}
					
				} else {
					return false;
				}
			}
			
		};
		return resolve(matcher);
	}
	
	/**
	 * Resolves the classes that implements the specified interface as 
	 * {@code java.lang.Class}.
	 * 
	 * @param interfaceClass The interface being implemented.
	 * @return Classes that implements the specified interface as 
	 * {@code java.lang.Class}.
	 * @throws IOException If any I/O access fails while traversing the search 
	 * path.
	 */
	@Override
	public Set<Class<?>> resolveByInterface(final Class<?> interfaceClass)
			throws IOException {
		Matcher<Class<?>> matcher = new Matcher<Class<?>>() {
			
			public boolean matches(Class<?> clazz) {
				Class<?>[] interfaces = clazz.getInterfaces();
				
				for (Class<?> c : interfaces) {
					if (c.equals(interfaceClass)) {
						return true;
					}
				}
				
				if (clazz.getSuperclass() != null) {
					return matches(clazz.getSuperclass());
				} else {
					return false;
				}
			}
			
		};
		return resolve(matcher);
	}
	
	/**
	 * Resolves the classes that is annotated by the specified annotation as 
	 * {@code java.lang.Class}.
	 * 
	 * @param annotation The annotation the class being annotated.
	 * @return Classes that is annotated by the specified annotation as 
	 * {@code java.lang.Class}.
	 * @throws IOException If any I/O access fails while traversing the search 
	 * path.
	 */
	@Override
	public Set<Class<?>> resolveByAnnotation(
			final Class<? extends Annotation> annotation) throws IOException {
		Matcher<Class<?>> matcher = new Matcher<Class<?>>() {
			
			public boolean matches(Class<?> clazz) {
				Annotation[] annotations = clazz.getAnnotations();
				
				for (Annotation a : annotations) {
					if (a.annotationType().equals(annotation)) {
						return true;
					}
				}
				
				return false;
			}
			
		};
		return resolve(matcher);
	}
	
	/**
	 * Returns the {@code ClassLoader} to load classes.
	 * 
	 * @return The {@code ClassLoader} to load classes.
	 */
	public ClassLoader classLoader() {
		return classLoader;
	}
	
}
