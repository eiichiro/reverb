package org.eiichiro.reverb.reflection;

public class Object2 {

	private final String string;
	
	public Object2() {
		string = "constructor1";
	}
	
	public Object2(String string) {
		this.string = string;
	}
	
	public String method1() {
		return string;
	}
	
}
