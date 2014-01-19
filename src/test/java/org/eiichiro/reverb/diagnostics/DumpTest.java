package org.eiichiro.reverb.diagnostics;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class DumpTest {

	@Test
	public void testDump() {
		new Dump(new Object());
		
		try {
			new Dump(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("'object' must not be [null]"));
		}
	}

	@Test
	public void testTo() {
		new Dump(new Object1()).to(System.out);
	}

	@Test
	public void testToString() {
		Object1 object1 = new Object1();
		assertThat(new Dump(object1).toString(), is(
				"+ [org.eiichiro.reverb.diagnostics.Object1] " + object1 + "\n"
				+ "  + [i: int] 1\n"
				+ "  + [string: java.lang.String] string2\n"
				+ "  + [object2: org.eiichiro.reverb.diagnostics.Object2] " + object1.object2 + "\n"
				+ "    + [map: java.util.Map] {key3=value3}\n"
				+ "    + [object: java.lang.Object] null\n"
				+ "  + [object3: org.eiichiro.reverb.diagnostics.Object3] Object3"));
	}

}
