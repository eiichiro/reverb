package org.eiichiro.reverb.lang;

@SuppressWarnings("serial")
public class Enum2 extends Enum1 {

	public static final Enum2 VALUE1 = new Enum2("VALUE1", 1);
	public static final Enum2 VALUE2 = new Enum2("VALUE2", 2);
	public static final Enum2 VALUE3 = new Enum2("VALUE3", 3);
	
	protected Enum2(String name, int ordinal) {
		super(name, ordinal);
	}

}
