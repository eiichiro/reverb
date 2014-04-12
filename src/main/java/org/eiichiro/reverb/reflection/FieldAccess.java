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

import java.lang.reflect.Field;

/**
 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
 */
public class FieldAccess<T> implements Access<T> {

	private final Field field;
	
	private final Object object;
	
	/**
	 * Constructs a new {@code FieldAccess} with the specified static field.
	 * 
	 * @param field The field to be accessed.
	 */
	public FieldAccess(Field field) {
		this(field, null);
	}
	
	/**
	 * Constructs a new {@code FieldAccess} with the specified field and object.
	 * 
	 * @param field The field to be accessed.
	 * @param object The object on which the specified field is accessed.
	 */
	public FieldAccess(Field field, Object object) {
		if (field == null) {
			throw new IllegalArgumentException("'field' must not be [" + field + "]");
		}
		
		this.field = field;
		this.object = object;
	}
	
	/**
	 * Constructs a new {@code FieldAccess} with the specified field name and 
	 * class name.
	 * 
	 * @param field The field name to be accessed.
	 * @param clazz The class name on which the specified field is declared.
	 * @throws NoSuchFieldException If the specified field name is not declared 
	 * on the specified class.
	 * @throws ClassNotFoundException If the class of the specified name is not 
	 * found.
	 */
	public FieldAccess(String field, String clazz) throws NoSuchFieldException,
			ClassNotFoundException {
		this(field, clazz, null);
	}
	
	/**
	 * Constructs a new {@code FieldAccess} with the specified field name, class 
	 * name and object.
	 * 
	 * @param field The field name to be accessed.
	 * @param clazz The class name on which the specified field is declared.
	 * @param object The object on which the field is accessed.
	 * @throws NoSuchFieldException If the specified field name is not declared 
	 * on the specified class.
	 * @throws ClassNotFoundException If the class of the specified name is not 
	 * found.
	 */
	public FieldAccess(String field, String clazz, Object object)
			throws NoSuchFieldException, ClassNotFoundException {
		if (clazz == null) {
			throw new IllegalArgumentException("'clazz' must not be [" + clazz
					+ "]");
		}
		
		if (field == null) {
			throw new IllegalArgumentException("'field' must not be [" + field
					+ "]");
		}
		
		this.field = Class.forName(clazz).getDeclaredField(field);
		this.object = object;
	}
	
	/**
	 * Constructs a new {@code FieldAccess} with the specified static field name 
	 * and class.
	 * 
	 * @param field The static field name to be accessed.
	 * @param clazz The class on which the specified field is declared.
	 * @throws NoSuchFieldException If the field of the specified name is not 
	 * declared on the specified class.
	 */
	public FieldAccess(String field, Class<?> clazz)
			throws NoSuchFieldException {
		this(field, clazz, null);
	}
	
	/**
	 * Constructs a new {@code FieldAccess} with the spefied field name, class 
	 * and object.
	 * 
	 * @param field The field name to be accessed.
	 * @param clazz The class on which the specified field is declared.
	 * @param object The object on which the specified field is accessed.
	 * @throws NoSuchFieldException If the field of the specified name is not 
	 * declared on the specified class.
	 */
	public FieldAccess(String field, Class<?> clazz, Object object)
			throws NoSuchFieldException {
		if (clazz == null) {
			throw new IllegalArgumentException("'clazz' must not be [" + clazz
					+ "]");
		}
		
		if (field == null) {
			throw new IllegalArgumentException("'field' must not be [" + field
					+ "]");
		}
		
		this.field = clazz.getDeclaredField(field);
		this.object = object;
	}
	
	/**
	 * Reads the target field.
	 * 
	 * @return The value to be read.
	 * @throws IllegalAccessException If this read access is denied.
	 */
	@SuppressWarnings("unchecked")
	public T read() throws IllegalAccessException {
		return (T) field.get(object);
	}
	
	/**
	 * Writes the target field.
	 * 
	 * @param value The value to write.
	 * @throws IllegalAccessException If the write access is denied.
	 */
	public void write(T value) throws IllegalAccessException {
		field.set(object, value);
	}
	
}
