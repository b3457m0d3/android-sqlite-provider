package com.app.sqlite.helper;

public class StringHelper {
	private static final String METHOD_SET = "set";
	private static final String EMPTY = "";
	
	/**
	 * Removes the "set" from the setter method name and converts
	 * the result into lower case
	 * @param	setMethodName	The name of the setter method
	 * @return	The setter method name with the "set" prefix removed
	 */
	public static String removePrefixFromSetter(String setMethodName) {
		String methodName = setMethodName.replace(METHOD_SET, EMPTY);
		methodName = firstCharacterToCase(methodName,false);
		return methodName;
	}
	
	/**
	 * Converts the first letter of variableName to uppercase and appends 
	 * "get" to it
	 * @param	variableName	The variableName to create the getter for
	 * @return	A getter method name based on the variable name provided
	 */
	public static String buildGetterMethodNameFromVariableName(String variableName) {
		String methodName = "get";
		methodName += firstCharacterToCase(variableName,true);
		return methodName;
	}
	
	/**
	 * Coverts the first character in a string to either upper or lower case
	 * @param	string	The string to change the first letter case for
	 * @param	upper	Change to upper or lower?
	 * @return	The string with its first character to the desired case
	 */
	private static String firstCharacterToCase(String string, boolean upper) {
		String firstChar = string.substring(0,1);
		string = string.substring(1, string.length());
		
		if (upper) {
			string = firstChar.toUpperCase() + string;
		} else {
			string = firstChar.toLowerCase() + string;
		}
		
		return string;
	}
}
