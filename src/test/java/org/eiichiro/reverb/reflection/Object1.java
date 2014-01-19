package org.eiichiro.reverb.reflection;

public class Object1 {

	public static String method1() {
		return "method1()";
	}
	
	public static String method2(String string) {
		return string;
	}
	
	public String method3() {
		return "method3()";
	}
	
	public String method4(String string, String string2) {
		return string + string2;
	}
	
	@SuppressWarnings("unused")
	private String method5() {
		return "method5()";
	}
	
}
