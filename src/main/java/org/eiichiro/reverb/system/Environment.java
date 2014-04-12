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
package org.eiichiro.reverb.system;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code Environment} represents information about {@link System}, 
 * {@link Process} and {@link Runtime}.
 * The environment variables in this class are "virtual". The environment 
 * variables are copied from the system at the static initialization and may be 
 * manipulated by applications after the initialization.
 * 
 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
 */
public class Environment {

	private Environment() {}
	
	private static Map<String, String> env = new ConcurrentHashMap<String, String>();
	
	static {
		for (Entry<String, String> entry : System.getenv().entrySet()) {
			env.put(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Returns the (virtual) environment variable corresponding to the specified 
	 * name.
	 * 
	 * @param name The environment variable name.
	 * @return The environment variable name related to the specified name. If 
	 * the environment variable doesn't exist, this method returns {@code null}.
	 */
	public static String getenv(final String name) {
		return env.get(name);
	}
	
	/**
	 * Returns the all visible (virtual) environment variables.
	 * 
	 * @return All the visible environment variables as {@code Map}.
	 */
	public static Map<String, String> getenv() {
		return env;
	}
	
	/**
	 * Sets the (virtual) environment variable.
	 * If the environment variable has already existed, the value is overwritten.
	 * 
	 * @param name The environment variable name.
	 * @param value The environment variable value.
	 */
	public static void setenv(final String name, final String value) {
		env.put(name, value);
	}
	
	/**
	 * Returns the Java system property corresponding to the specified key.
	 * 
	 * @param key The Java system property key.
	 * @return The Java system property related to the specified key as 
	 * {@code String}. If the Java system property doesn't exist, this method 
	 * returns {@code null}.
	 * @see System#getProperty(String)
	 */
	public static String getProperty(final String key) {
		return System.getProperty(key);
	}
	
	/**
	 * Returns the Java system property corresponding to the specified key (If 
	 * the system property doesn't exist, this method returns the specified 
	 * default value).
	 * 
	 * @param key The key of Java system property.
	 * @param def The default value that is returned if the system property 
	 * doesn't exist. 
	 * @return The Java system property related to the specified key as 
	 * {@code String}. If the Java system property doesn't exist, this method 
	 * returns the specified default value.
	 * @see System#getProperty(String, String)
	 */
	public static String getProperty(String key, String def) {
		return System.getProperty(key, def);
	}
	
	/**
	 * Returns the Java system properties.
	 * 
	 * @return The current Java system properties.
	 */
	public static Properties getProperties() {
		return System.getProperties();
	}
	
	/**
	 * Returns the number of processors available to current JVM.
	 * 
	 * @return The number of processors available to current JVM.
	 * @see Runtime#availableProcessors()
	 */
	public static int availableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}
	
	/**
	 * Returns the size of free memory in the current JVM.
	 * 
	 * @return The size of free memory in the current JVM.
	 */
	public static long freeMemory() {
		return Runtime.getRuntime().freeMemory();
	}
	
	/**
	 * Returns the total ammount of used memory in the current JVM.
	 * 
	 * @return The total ammount of used memory in the current JVM.
	 */
	public static long totalMemory() {
		return Runtime.getRuntime().totalMemory();
	}
	
	/**
	 * Returns the maximum ammount of memory in the current JVM.
	 * 
	 * @return The maximum ammount of memory in the current JVM.
	 */
	public static long maxMemory() {
		return Runtime.getRuntime().maxMemory();
	}
	
}
