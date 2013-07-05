package com.memtrip.application.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A helper class for all common reflection code
 * @author memtrip
 */
public class ReflectionHelper {
	private static final String METHOD_SET = "set";
	private static final String EMPTY = "";
	
	/**
	 * Create a new object instance based on the class provided
	 * @param	c	Create a new object instance based on this class
	 * @return	A new instance of the class
	 */
	public static Object newInstance(Class<?> c) {
		Object object = null;
		
		try {
			object = c.newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		
		return object;
	}
	
	/**
	 * Get a static string field from a class by reflection, null is returned
	 * if there are any problem with retreiving the field value
	 * @param	c	The class to get the fieldName 
	 * @param	fieldName	The field name to get the value for
	 * @return	The value 
	 */
	public static String getStaticStringField(Class<?> c, String fieldName) {
		String fieldValue = null;
		
		try {
			Field tableNameField = c.getDeclaredField(fieldName);
			fieldValue = (String)tableNameField.get(null);
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		
		return fieldValue;
	}
	
	/**
	 * Loop through all declared methods and find the method that matches
	 * the methodName (if it exists)
	 * @param	c	The class to get the declared methods from
	 * @param	methodName	The methodName that the declared method should be returned for
	 * @return	The method declaration that matches the methodName
	 */
	public static Method getMethod(Class<?> c, String methodName) {
		Method[] declaredMethods = c.getDeclaredMethods();
		Method returnMethod = null;
		
		for (Method method : declaredMethods) {
			if (method.getName().equals(methodName)) {
				returnMethod = method;
				break;
			}
		}
		
		return returnMethod;
	}
	
	/**
	 * @para	c	The class to get the setter methods from
	 * @return	A HashMap of setter method name/method declaration pairs
	 */
	public static HashMap<String,Method> getSetterMethods(Class<?> c) {
		HashMap<String,Method> setterMethods = new HashMap<String,Method>();
		Method[] declaredMethods = c.getDeclaredMethods();
		
		for (int i = 0; i < declaredMethods.length; i++) {
			Method declaredMethod = declaredMethods[i];
			String methodName = declaredMethod.getName();
			
			if (methodName.startsWith(METHOD_SET)) {
				setterMethods.put(removePrefixFromSetter(methodName),declaredMethod);
			}
		}
		
		return setterMethods;
	}
	
	/**
	 * Removes the "set" from the setter method name and converts
	 * the result into lower case
	 * @param	setMethodName	The name of the setter method
	 * @return	The setter method name with the "set" prefix removed
	 */
	private static String removePrefixFromSetter(String setMethodName) {
		String methodName = setMethodName.replace(METHOD_SET, EMPTY);
		String firstChar = methodName.substring(0,1);
		methodName = methodName.substring(1, methodName.length());
		methodName = firstChar.toLowerCase() + methodName;
		return methodName;
	}
	
	/**
	 * invoke reflection method
	 * @param	object	The object to invoke the method on
	 * @param	method	The method to invoke
	 * @param	byteValue	The value to pass into the method
	 */
	public static void invokeMethod(Object object, Method method, byte[] byteValue) {
		try {
			method.invoke(object, byteValue);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
	
	/**
	 * invoke reflection method
	 * @param	object	The object to invoke the method on
	 * @param	method	The method to invoke
	 * @param	flaotValue	The value to pass into the method
	 */
	public static void invokeMethod(Object object, Method method, float floatValue) {
		try {
			method.invoke(object, floatValue);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
	
	/**
	 * invoke reflection method
	 * @param	object	The object to invoke the method on
	 * @param	method	The method to invoke
	 * @param	intValue	The value to pass into the method
	 */
	public static void invokeMethod(Object object, Method method, int intValue) {
		try {
			method.invoke(object, intValue);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
	
	/**
	 * invoke reflection method
	 * @param	object	The object to invoke the method on
	 * @param	method	The method to invoke
	 * @param	stringValue	The value to pass into the method
	 */
	public static void invokeMethod(Object object, Method method,String stringValue) {
		try {
			method.invoke(object, stringValue);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
}
