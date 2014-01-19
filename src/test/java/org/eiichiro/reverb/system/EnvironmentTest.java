package org.eiichiro.reverb.system;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Test;

public class EnvironmentTest {

	@Test
	public void testGetenvString() {
		String string = null;
		assertThat(Environment.getenv("doesn't exist"), is(string));
		Map<String, String> getenv = Environment.getenv();
		
		if (!getenv.isEmpty()) {
			String name = getenv.keySet().toArray(new String[getenv.size()])[0];
			assertThat(Environment.getenv(name).equals(System.getenv(name)), is(true));
		}
	}

	@Test
	public void testGetenv() {
		System.out.println(Environment.getenv());
		
		for (Entry<String, String> entry : System.getenv().entrySet()) {
			assertThat(Environment.getenv().get(entry.getKey()).equals(entry.getValue()), is(true));
		}
	}

	@Test
	public void testSetenv() {
		String name = "name";
		String value = "value";
		Environment.setenv(name, value);
		assertThat(Environment.getenv(name).equals(value), is(true));
		value = "value2";
		Environment.setenv(name, value);
		assertThat(Environment.getenv(name).equals(value), is(true));
	}

	@Test
	public void testGetPropertyString() {
		String string = null;
		assertThat(Environment.getProperty("doesn't exist"), is(string));
		Properties properties = Environment.getProperties();
		
		if (!properties.isEmpty()) {
			String key = properties.keySet().toArray(new String[properties.size()])[0];
			assertThat(Environment.getProperty(key).equals(System.getProperty(key)), is(true));
		}
	}

	@Test
	public void testGetPropertyStringString() {
		assertThat(Environment.getProperty("doesn't exist", "Property1"), is("Property1"));	}

	@Test
	public void testGetProperties() {
		System.out.println(Environment.getProperties());
		assertThat(Environment.getProperties().equals(System.getProperties()), is(true));
	}
	
	@Test
	public void testAvailableProcessors() {
		assertThat(Environment.availableProcessors() == Runtime.getRuntime().availableProcessors(), is(true));
	}

	@Test
	public void testFreeMemory() {}

	@Test
	public void testTotalMemory() {
		assertThat(Environment.totalMemory() == Runtime.getRuntime().totalMemory(), is(true));
	}

	@Test
	public void testMaxMemory() {
		assertThat(Environment.maxMemory() == Runtime.getRuntime().maxMemory(), is(true));
	}

}
