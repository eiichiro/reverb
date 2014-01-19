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
package org.eiichiro.reverb.lang;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@code TypesafeEnum} is the common base class of inheritable typesafe enum.
 * Unlike {@code java.lang.Enum}, this class can be inherited.
 * 
 * @author <a href="mailto:eiichiro@eiichiro.org">Eiichiro Uchiumi</a>
 */
public class TypesafeEnum<E extends TypesafeEnum<E>> implements Comparable<E>,
		Cloneable, Serializable {

	private static final long serialVersionUID = 7803060603632458145L;

	private static Map<Class<? extends TypesafeEnum<?>>, Set<? extends TypesafeEnum<?>>> constants 
			= new HashMap<Class<? extends TypesafeEnum<?>>, Set<? extends TypesafeEnum<?>>>();
	
	private final String name;
	
	private final int ordinal;
	
	/**
	 * Constructs a new {@code TypesafeEnum} instance with the specified name 
	 * and ordinal.
	 * 
	 * @param name The {@code TypesafeEnum} instance name.
	 * @param ordinal The {@code TypesafeEnum} instance ordinal.
	 */
	@SuppressWarnings("unchecked")
	protected TypesafeEnum(String name, int ordinal) {
		synchronized (constants) {
			Class<? extends TypesafeEnum<?>> clazz 
					= (Class<? extends TypesafeEnum<?>>) getClass();
			Set<TypesafeEnum<E>> set;
			
			if (constants.containsKey(clazz)) {
				set = (Set<TypesafeEnum<E>>) constants.get(clazz);
			} else {
				set = new HashSet<TypesafeEnum<E>>();
				constants.put(clazz, set);
			}
			
			set.add(this);
		}
		
		this.name = name;
		this.ordinal = ordinal;
	}
	
	/** Returns the name. */
	public final String name() {
		return name;
	}
	
	/** Returns the ordinal. */
	public final int ordinal() {
		return ordinal;
	}
	
	/**
	 * Returns the instance set of the specified {@code TypesafeEnum} class.
	 * 
	 * @param clazz {@code TypesafeEnum} class.
	 * @return The instance set of the specified {@code TypesafeEnum} class.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends TypesafeEnum<T>> Set<T> values(Class<T> clazz) {
		Set<T> set = new HashSet<T>();
		
		if (constants.containsKey(clazz)) {
			set.addAll((Set<T>) constants.get(clazz));
		}
		
		return set;
	}
	
	/** May not create a clone. */
	protected final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * Compares with the specified object by ordinal.
	 * 
	 * @param o The object to be compared.
	 * @return <code>0</code> If this object is the same ordinal to the 
	 * specified object.
	 */
	public int compareTo(E o) {
		if (o.getClass() != getClass()) {
			throw new ClassCastException();
		}
		
		return ordinal - TypesafeEnum.class.cast(o).ordinal;
	}

}
