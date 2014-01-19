package org.eiichiro.reverb.reflection;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.lang.reflect.Constructor;

import org.junit.Test;

public class ConstructorInvocationTest {

	@Test
	public void testConstructorInvocationConstructorOfT() throws Throwable {
		Constructor<Object2> constructor = Object2.class.getDeclaredConstructor();
		Invocation<Object2> invocation = new ConstructorInvocation<Object2>(constructor);
		Object2 proceed = invocation.proceed();
		assertThat(proceed.method1(), is("constructor1"));
	}

	@Test
	public void testConstructorInvocationClassOfTClassOfQArrayObjectArray() throws Throwable {
		Invocation<Object2> invocation = new ConstructorInvocation<Object2>(Object2.class, new Class<?>[] {String.class}, new Object[] {"constructor2"});
		Object2 proceed = invocation.proceed();
		assertThat(proceed.method1(), is("constructor2"));
	}

	@Test
	public void testConstructorInvocationClassOfTObjectArray() throws Throwable {
		Invocation<Object2> invocation = new ConstructorInvocation<Object2>(Object2.class, new Object[] {"constructor2"});
		Object2 proceed = invocation.proceed();
		assertThat(proceed.method1(), is("constructor2"));
	}

	@Test
	public void testProceed() throws Throwable {
		Constructor<Object2> constructor = Object2.class.getDeclaredConstructor();
		Invocation<Object2> invocation = new ConstructorInvocation<Object2>(constructor);
		assertThat(invocation.proceed().getClass().equals(Object2.class), is(true));
	}

	@Test
	public void testConstructor() throws SecurityException, NoSuchMethodException {
		Constructor<Object2> constructor = Object2.class.getDeclaredConstructor();
		ConstructorInvocation<Object2> invocation = new ConstructorInvocation<Object2>(constructor);
		assertThat(invocation.constructor() == constructor, is(true));
	}

	@Test
	public void testArgs() throws NoSuchMethodException {
		Constructor<Object2> constructor = Object2.class.getDeclaredConstructor();
		Invocation<Object2> invocation = new ConstructorInvocation<Object2>(constructor);
		assertThat(invocation.args().length, is(0));
		Object[] args = new Object[] {"constructor2"};
		invocation = new ConstructorInvocation<Object2>(Object2.class, new Class<?>[] {String.class}, args);
		assertSame(invocation.args(), args);
	}
}
