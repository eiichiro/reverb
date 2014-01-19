package org.eiichiro.reverb.lang;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class UncheckedExceptionTest {

	@Test
	public void testHashCode() {
		Exception1 exception1 = new Exception1();
		UncheckedException exception = new UncheckedException(exception1);
		assertThat(exception.hashCode() == exception1.hashCode(), is(true));
	}

	@Test
	public void testPrintStackTrace() {
		Exception1 exception1 = new Exception1();
		exception1.printStackTrace();
		new UncheckedException(exception1).printStackTrace();
	}

	@Test
	public void testUncheckedException() {
		try {
			throw new UncheckedException(new Exception1());
		} catch (UncheckedException e) {
			// Passed.
			e.printStackTrace();
		}
	}

	@Test
	public void testToString() {
		Exception1 exception1 = new Exception1();
		UncheckedException exception = new UncheckedException(exception1);
		assertThat(exception.toString().equals(exception1.toString()), is(true));
	}

	@Test
	public void testGetCause() {
		Exception cause = new Exception();
		Exception1 exception1 = new Exception1();
		exception1.initCause(cause);
		UncheckedException exception = new UncheckedException(exception1);
		assertThat(exception.getCause().equals(exception1.getCause()), is(true));
	}

	@Test
	public void testGetLocalizedMessage() {
		Exception1 exception1 = new Exception1("Message");
		UncheckedException exception = new UncheckedException(exception1);
		assertThat(exception.getLocalizedMessage().equals(exception1.getLocalizedMessage()), is(true));
	}

	@Test
	public void testGetMessage() {
		Exception1 exception1 = new Exception1("Message");
		UncheckedException exception = new UncheckedException(exception1);
		assertThat(exception.getMessage().equals(exception1.getMessage()), is(true));
	}

	@Test
	public void testGetStackTrace() {
		Exception1 exception1 = new Exception1();
		UncheckedException exception = new UncheckedException(exception1);
		StackTraceElement[] elements = exception.getStackTrace();
		StackTraceElement[] elements2 = exception1.getStackTrace();
		assertThat(elements.length == elements2.length, is(true));
		
		for (int i = 0; i < elements.length; i++) {
			assertThat(elements[i].equals(elements2[i]), is(true));
		}
	}

	@Test
	public void testInitCauseThrowable() {
		Exception cause = new Exception();
		Exception1 exception1 = new Exception1();
		UncheckedException exception = new UncheckedException(exception1);
		exception.initCause(cause);
		assertThat(exception.getCause().equals(exception1.getCause()), is(true));
	}

	@Test
	public void testEqualsObject() {
		Exception1 exception1 = new Exception1();
		UncheckedException exception = new UncheckedException(exception1);
		assertThat(exception.equals(exception1), is(true));
	}

}
