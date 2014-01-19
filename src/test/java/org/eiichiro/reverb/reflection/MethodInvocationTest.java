package org.eiichiro.reverb.reflection;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.lang.reflect.Method;

import org.junit.Test;

public class MethodInvocationTest {

	@Test
	public void testMethodInvocationMethod() throws Throwable {
		Method method = Object1.class.getDeclaredMethod("method1");
		Invocation<String> invocation = new MethodInvocation<String>(method);
		String proceed = invocation.proceed();
		assertThat(proceed, is("method1()"));
	}

	@Test
	public void testMethodInvocationMethodObjectArray() throws Throwable {
		Method method = Object1.class.getDeclaredMethod("method2", String.class);
		Invocation<String> invocation = new MethodInvocation<String>(method, new Object[] {"method2()"});
		String proceed = invocation.proceed();
		assertThat(proceed, is("method2()"));
	}

	@Test
	public void testMethodInvocationMethodObject() throws Throwable {
		Method method = Object1.class.getDeclaredMethod("method3");
		Object1 object1 = new Object1();
		Invocation<String> invocation = new MethodInvocation<String>(method, object1);
		String proceed = invocation.proceed();
		assertThat(proceed, is("method3()"));
		
		method = Object1.class.getDeclaredMethod("method5");
		invocation = new MethodInvocation<String>(method, object1);
		
		try {
			invocation.proceed();
			fail();
		} catch (IllegalAccessException e) {
			// Passed.
			e.printStackTrace();
		}
	}

	@Test
	public void testMethodInvocationMethodObjectObjectArray() throws Throwable {
		Method method = Object1.class.getDeclaredMethod("method4", String.class, String.class);
		Object1 object1 = new Object1();
		Invocation<String> invocation = new MethodInvocation<String>(method, object1, new Object[] {"method4(", "method4)"});
		String proceed = invocation.proceed();
		assertThat(proceed, is("method4(method4)"));
	}

	@Test
	public void testMethodInvocationClassOfQStringClassOfQArrayObjectArray() throws Throwable {
		Invocation<String> invocation = new MethodInvocation<String>(Object1.class, "method2", new Class<?>[] {String.class}, new Object[] {"method2()"});
		String proceed = invocation.proceed();
		assertThat(proceed, is("method2()"));
	}

	@Test
	public void testMethodInvocationClassOfQStringObjectArray() throws Throwable {
		Invocation<String> invocation = new MethodInvocation<String>(Object1.class, "method2", new Object[] {"method2()"});
		String proceed = invocation.proceed();
		assertThat(proceed, is("method2()"));
	}

	@Test
	public void testMethodInvocationClassOfQStringObjectClassOfQArrayObjectArray() throws Throwable {
		Invocation<String> invocation = new MethodInvocation<String>(Object1.class, "method4", new Object1(), new Class<?>[] {String.class, String.class}, new Object[] {"method4(", "method4)"});
		String proceed = invocation.proceed();
		assertThat(proceed, is("method4(method4)"));
	}

	@Test
	public void testMethodInvocationClassOfQStringObjectObjectArray() throws Throwable {
		Invocation<String> invocation = new MethodInvocation<String>(Object1.class, "method4", new Object1(), new Object[] {"method4(", "method4)"});
		String proceed = invocation.proceed();
		assertThat(proceed, is("method4(method4)"));
	}

	@Test
	public void testProceed() throws Throwable {
		Object1 object1 = new Object1();
		String method4 = object1.method4("string", "string2");
		Invocation<String> invocation = new MethodInvocation<String>(Object1.class, "method4", new Object1(), new Object[] {"string", "string2"});
		String proceed = invocation.proceed();
		assertThat(proceed.equals(method4), is(true));
	}

	@Test
	public void testMethod() throws SecurityException, NoSuchMethodException {
		Method method = Object1.class.getDeclaredMethod("method1");
		MethodInvocation<String> invocation = new MethodInvocation<String>(method);
		assertThat(invocation.method() == method, is(true));
	}

	@Test
	public void testTarget() throws NoSuchMethodException {
		Method method = Object1.class.getDeclaredMethod("method1");
		MethodInvocation<String> invocation = new MethodInvocation<String>(method);
		assertNull(invocation.target());
		method = Object1.class.getDeclaredMethod("method3");
		Object1 object1 = new Object1();
		invocation = new MethodInvocation<String>(method, object1);
		assertSame(invocation.target(), object1);
	}
	
	@Test
	public void testArgs() throws NoSuchMethodException {
		Method method = Object1.class.getDeclaredMethod("method1");
		Invocation<String> invocation = new MethodInvocation<String>(method);
		assertThat(invocation.args().length, is(0));
		Object[] args = new Object[] {"method2()"};
		invocation = new MethodInvocation<String>(Object1.class, "method2", new Class<?>[] {String.class}, args);
		assertSame(invocation.args(), args);
	}
	
}
