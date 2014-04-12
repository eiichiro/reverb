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
package org.eiichiro.reverb.diagnostics;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Dumps the state of target object.
 * 
 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
 */
public class Dump {

	private static final String TREE = "+ ";
	
	private static final String INDENT = "  ";
	
	private final Object object;
	
	/**
	 * Constructs a new {@code Dump} instance with the specified object.
	 * 
	 * @param object The object to be dumped.
	 */
	public Dump(Object object) {
		if (object == null) {
			throw new IllegalArgumentException("'object' must not be [" + object + "]");
		}
		
		this.object = object;
	}
	
	/**
	 * Dumps the state of the target object to the specified {@code PrintStream}.
	 * 
	 * @param printStream The print stream the target object state is printed to.
	 */
	public void to(PrintStream printStream) {
		printStream.print(toString());
	}
	
	@Override
	public String toString() {
		return toString(object, object.getClass(), "", 0).toString();
	}
	
	private StringBuilder toString(Object object, Class<?> type, String name, int depth) {
		StringBuilder stringBuilder = new StringBuilder();
		String string;
		
		if (object == null) {
			string = null;
		} else if (object.getClass().isArray()) {
			string = Arrays.toString((Object[]) object);
		} else {
			string = object.toString();
		}
		
		stringBuilder.append(TREE + "[" + name + (name.equals("") ? "" : ": ") + type.getName() + "] " + string);
		
		if (object != null && string.equals(type.getName() + "@" + Integer.toHexString(object.hashCode()))) {
			int d = ++depth;
			
			for (Field field : object.getClass().getDeclaredFields()) {
				Object value = null;
				
				try {
					// :-)
					field.setAccessible(true);
					value = field.get(object);
				} catch (Exception e) {
					value = e;
				}
				
				stringBuilder.append("\n");
				
				for (int i = 0; i < depth; i++) {
					stringBuilder.append(INDENT);
				}
				
				stringBuilder.append(toString(value, field.getType(), field.getName(), d));
			}
		}
		
		return stringBuilder;
	}
	
}
