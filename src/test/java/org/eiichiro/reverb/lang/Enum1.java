package org.eiichiro.reverb.lang;

@SuppressWarnings("serial")
public class Enum1 extends TypesafeEnum<Enum1> {

	public static final Enum1 VALUE1 = new Enum1("VALUE1", 1);
	public static final Enum1 VALUE2 = new Enum1("VALUE2", 2);
	public static final Enum1 VALUE3 = new Enum1("VALUE3", 3);
	
	protected Enum1(String name, int ordinal) {
		super(name, ordinal);
	}

}
