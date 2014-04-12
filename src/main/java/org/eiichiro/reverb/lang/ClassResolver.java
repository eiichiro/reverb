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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;

/**
 * {@code ClassResolver} is a base class for the component that resolves classes 
 * matches to the specified condition from its own search path.
 * {@code ClassResolver} can resolve classes by the following conditions: 
 * <ul>
 * <li>By name</li>
 * <li>By superclass</li>
 * <li>By interface</li>
 * <li>By annotation</li>
 * <li>By {@code Matcher&lt;T&gt;}</li>
 * </ul>
 * Type parameter <code>T</code> is the type of class representation. So the sub 
 * class must override {@code #load(String, InputStream)} method to load a class 
 * from the specified {@code InputStream} as its own type of class 
 * representation and the methods described above according to the type of class 
 * representation. By default, {@link JLCClassResolver} is provided, which 
 * represents the loaded class as {@code java.lang.Class}.
 * 
 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
 */
public abstract class ClassResolver<T> {

	private Iterable<URL> paths = new ArrayList<URL>();
	
	/**
	 * {@code Matcher} indicates whether the specified class matches to some 
	 * condition or not.
	 * 
	 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
	 */
	public static interface Matcher<T> {
		
		/**
		 * Indicates whether the specified class matches to some condition or 
		 * not.
		 * 
		 * @param T The type of class representation.
		 * @param clazz The class to be tested.
		 * @return <code>true</code> If the specified class matches to some 
		 * condition.
		 */
		public boolean matches(T clazz);
		
	}
	
	/**
	 * Constructs a new {@code ClassResolver} instance with the specified search 
	 * paths.
	 * 
	 * @param paths The search path.
	 */
	public ClassResolver(Iterable<URL> paths) {
		this.paths = paths;
	}
	
	/**
	 * Loads the class of the specified name from the specified 
	 * {@code InputStream} and returns loaded class representation as the type 
	 * of <code>T</code>.
	 * 
	 * @param clazz The name of the class to be loaded.
	 * @param stream {@code InputStream} to load a class file.
	 * @return The loaded class representation as <code>T</code>.
	 */
	protected abstract T load(String clazz, InputStream stream);
	
	/**
	 * Resolves the {@code Class}es that contains the specified name.
	 * 
	 * @param name The part of the class name.
	 * @return {@code Class}es that contains the specified name.
	 * @throws IOException If any I/O access fails while traversing the search 
	 * path.
	 */
	public abstract Set<T> resolveByName(final String name) throws IOException;
	
	/**
	 * Resolves the {@code Class}es that inherits the specified superclass.
	 * 
	 * @param superclass The superclass being inherited.
	 * @return {@code Class}es that inherits the specified superclass.
	 * @throws IOException If any I/O access fails while traversing the search 
	 * path.
	 */
	public abstract Set<T> resolveBySuperclass(final Class<?> superclass) throws IOException;
	
	/**
	 * Resolves the {@code Class}es that implements the specified interface.
	 * 
	 * @param interfaceClass The interface being implemented.
	 * @return {@code Class}es that implements the specified interface.
	 * @throws IOException If any I/O access fails while traversing the search 
	 * path.
	 */
	public abstract Set<T> resolveByInterface(final Class<?> interfaceClass) throws IOException;
	
	/**
	 * Resolves the {@code Class}es that is annotated by the specified annotation.
	 * 
	 * @param annotation The annotation the class being annotated.
	 * @return {@code Class}es that is annotated by the specified annotation.
	 * @throws IOException If any I/O access fails while traversing the search 
	 * path.
	 */
	public abstract Set<T> resolveByAnnotation(final Class<? extends Annotation> annotation) throws IOException;
	
	/**
	 * Resolves the {@code Class}es that matches to the specified {@code Matcher}.
	 * 
	 * @param matcher {@code Matcher}.
	 * @return {@code Class}es that matches to the specified {@code Matcher}.
	 * @throws IOException If any I/O access fails while traversing the search 
	 * path.
	 */
	public Set<T> resolve(Matcher<T> matcher) throws IOException {
		Set<T> classes = new HashSet<T>();
		
		for (URL url : paths) {
			if (url.toString().endsWith(".jar")) {
//				System.out.println(url);
				JarFile jarFile = new JarFile(URLDecoder.decode(url.getPath(), "UTF-8"));
				Manifest manifest = jarFile.getManifest();
				
				if (manifest != null) {
//					System.out.println(manifest);
					Attributes mainAttributes = manifest.getMainAttributes();
					
					if (mainAttributes != null) {
//						System.out.println(mainAttributes);
						String classpath = mainAttributes.getValue(Name.CLASS_PATH);
						
						if (classpath != null) {
//							System.out.println(classpath);
							StringTokenizer stringTokenizer = new StringTokenizer(classpath);
							
							while (stringTokenizer.hasMoreTokens()) {
								String token = stringTokenizer.nextToken();
								URL entry = new URL(url, token);
								
								if (entry.toString().endsWith("/")) {
//									System.out.println(entry);
									classes.addAll(getMatchedClasses(matcher, new File(URLDecoder.decode(entry.getPath(), "UTF-8"))));
								} else {
//									System.out.println(entry);
									classes.addAll(getMatchedClasses(matcher, new JarFile(URLDecoder.decode(entry.getPath(), "UTF-8"))));
								}
							}
						}
					}
				}
				
				classes.addAll(getMatchedClasses(matcher, jarFile));
			} else {
				File base = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
				classes.addAll(getMatchedClasses(matcher, base));
			}
		}
		
		return classes;
	}
	
	private Set<T> getMatchedClasses(Matcher<T> matcher, File base) throws IOException {
		List<File> files = getClasses(base);
		Set<T> classes = new HashSet<T>();
		
		for (File file : files) {
			String path = file.getPath();
//			System.out.println(path);
			InputStream stream = null;
			
			try {
				stream = new FileInputStream(file);
				T clazz = load(path.substring(base.getPath().length() + 1, path.length() - 6).replace(File.separatorChar, '.'), stream);
				
				if (clazz != null && matcher.matches(clazz)) {
					classes.add(clazz);
				}
				
			} finally {
				if (stream != null) {
					stream.close();
				}
			}
		}
		
		return classes;
	}
	
	private List<File> getClasses(File base) {
		// System.out.println(directory);
		List<File> classes = new ArrayList<File>();
		
		if (base.isDirectory()) {
			File[] files = base.listFiles();
			
			for (File file : files) {
				if (file.isDirectory()) {
					classes.addAll(getClasses(file));
				} else {
					if (file.getPath().endsWith(".class")) {
						classes.add(file);
					}
				}
			}
			
		} else {
			if (base.getPath().endsWith(".class")) {
				classes.add(base);
			}
		}
		
		return classes;
	}
	
	private Set<T> getMatchedClasses(Matcher<T> matcher, JarFile jarFile) throws IOException {
		Enumeration<JarEntry> entries = jarFile.entries();
		Set<T> classes = new HashSet<T>();
		
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String name = entry.getName();
//			System.out.println(name);
			
			if (name.endsWith(".class")) {
				InputStream stream = null;
				
				try {
					stream = jarFile.getInputStream(entry);
					T clazz = load(name.substring(0, name.length() - 6).replace('/', '.'), stream);
					
					if (clazz != null && matcher.matches(clazz)) {
						classes.add(clazz);
					}
					
				} finally {
					if (stream != null) {
						stream.close();
					}
				}
			}
		}
		
		return classes;
	}
	
	/**
	 * Returns the search paths to be traversed.
	 * 
	 * @return The search paths to be traversed.
	 */
	public Iterable<URL> paths() {
		return paths;
	}
	
}
