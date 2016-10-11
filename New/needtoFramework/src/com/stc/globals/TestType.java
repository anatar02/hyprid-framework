package com.stc.globals;

import static com.stc.globals.Constants.*;


public final class TestType {
	
	private static String testType=FUNCTIONAL_TEST;
	
	public static void setTestType(String  type){
		testType=type;
	}
	
	public static String getTestType(){
		return testType;
	}
}
