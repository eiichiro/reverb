package org.eiichiro.reverb.lang;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Set;

import org.junit.Test;

public class TypesafeEnumTest {

	@Test
	public void testTypesafeEnum() {
		Enum1.class.getClass();
		Enum2.class.getClass();
	}

	@Test
	public void testName() {
		assertThat(Enum1.VALUE1.name(), is("VALUE1"));
	}

	@Test
	public void testOrdinal() {
		assertThat(Enum1.VALUE1.ordinal(), is(1));
	}

	@Test
	public void testValues() {
		Set<Enum1> values = TypesafeEnum.values(Enum1.class);
		assertThat(values.size(), is(3));
		assertThat(values.contains(Enum1.VALUE1), is(true));
		assertThat(values.contains(Enum1.VALUE2), is(true));
		assertThat(values.contains(Enum1.VALUE3), is(true));
	}

	@Test
	public void testClone() {
		try {
			Enum1.VALUE1.clone();
			fail();
		} catch (CloneNotSupportedException e) {
			// Passed.
			e.printStackTrace();
		}
	}

	@Test
	public void testCompareTo() {
		assertThat(Enum1.VALUE1.compareTo(Enum1.VALUE2) < 0, is(true));
		
		try {
			Enum1.VALUE1.compareTo(Enum2.VALUE1);
			fail();
		} catch (ClassCastException e) {
			// Passed.
			e.printStackTrace();
		}
	}

}
