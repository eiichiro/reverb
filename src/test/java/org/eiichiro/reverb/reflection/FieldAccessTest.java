package org.eiichiro.reverb.reflection;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class FieldAccessTest {

	@Test
	public void testFieldAccessField() throws IllegalAccessException, SecurityException, NoSuchFieldException {
		Field field = Object3.class.getDeclaredField("field3");
		Access<String> access = new FieldAccess<String>(field);
		access.write("string3");
		assertThat(access.read(), is("string3"));
		
		field = Object3.class.getDeclaredField("field4");
		access = new FieldAccess<String>(field);
		
		try {
			access.write("string3");
			fail();
		} catch (IllegalAccessException e) {
			// Passed.
			e.printStackTrace();
		}
		
		field.setAccessible(true);
		access.write("string3");
	}

	@Test
	public void testFieldAccessFieldObject() throws IllegalAccessException, SecurityException, NoSuchFieldException {
		Field field = Object3.class.getDeclaredField("field1");
		Object3 object3 = new Object3();
		Access<String> access = new FieldAccess<String>(field, object3);
		access.write("string1");
		assertThat(access.read(), is("string1"));
		
		field = Object3.class.getDeclaredField("field2");
		access = new FieldAccess<String>(field, object3);
		
		try {
			access.write("string2");
			fail();
		} catch (IllegalAccessException e) {
			// Passed.
			e.printStackTrace();
		}
		
		field.setAccessible(true);
		access.write("string2");
	}

	@Test
	public void testFieldAccessStringString() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
		Access<String> access = new FieldAccess<String>("field3", Object3.class.getName());
		access.write("string3");
		assertThat(access.read(), is("string3"));
	}

	@Test
	public void testFieldAccessStringStringObject() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
		Object3 object3 = new Object3();
		Access<String> access = new FieldAccess<String>("field1", Object3.class.getName(), object3);
		access.write("string1");
		assertThat(access.read(), is("string1"));
	}

	@Test
	public void testFieldAccessStringClassOfQ() throws IllegalAccessException, NoSuchFieldException {
		Access<String> access = new FieldAccess<String>("field3", Object3.class);
		access.write("string3");
		assertThat(access.read(), is("string3"));
	}

	@Test
	public void testFieldAccessStringClassOfQObject() throws NoSuchFieldException, IllegalAccessException {
		Object3 object3 = new Object3();
		Access<String> access = new FieldAccess<String>("field1", Object3.class, object3);
		access.write("string1");
		assertThat(access.read(), is("string1"));
	}

	@Test
	public void testRead() throws IllegalAccessException, SecurityException, NoSuchFieldException {
		Field field = Object3.class.getDeclaredField("field1");
		Object3 object3 = new Object3();
		object3.field1 = "string1";
		Access<String> access = new FieldAccess<String>(field, object3);
		assertThat(access.read(), is(object3.field1));
	}

	@Test
	public void testWrite() throws IllegalAccessException, SecurityException, NoSuchFieldException {
		Field field = Object3.class.getDeclaredField("field1");
		Object3 object3 = new Object3();
		Access<String> access = new FieldAccess<String>(field, object3);
		access.write("string1");
		assertThat(access.read(), is(object3.field1));
	}

}
