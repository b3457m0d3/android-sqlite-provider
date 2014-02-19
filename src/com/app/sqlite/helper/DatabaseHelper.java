package com.app.sqlite.helper;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.app.sqlite.base.BaseModel;

import android.database.Cursor;

/**
 * A SQLite database helper class that handles the creation and updating of 
 * database tables. SQL statements that create tables should be defined here,
 * they should then be executed from the onCreate method. Drop queries should also
 * be provided for the database update.
 * @author	memtrip
 */
public class DatabaseHelper {
	/**
	 * A generic method that takes any class interface that extends BaseModel, it takes the provided
	 * cursor and populates a new instance of the provided BaseModel interface with the results.
	 * @param	c	The class interface to return the cursor results in
	 * @param	cursor	The provided SQLite cursor that the results should be populated from
	 * @param	baseModel	An instance of a class that extends BaseSQLModel, 
	 * 							it is used to get the columnType map associated with the 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] retreiveSQLSelectResults(Class<T> c, Cursor cursor, BaseModel baseModel) {
		if (c.isAssignableFrom(BaseModel.class)) {
			throw new IllegalArgumentException("Only classes that extends BaseSQLModel can be populated with SQLite cursor results");
		}
		
		HashMap<String, Integer> columnTypeMap = baseModel.getModelColumnTypeMap();
		HashMap<String,Method> setMethods = ReflectionHelper.getSetterMethods(c);
		
		T[] result = (T[]) Array.newInstance(c, cursor.getCount());
		// Loop through all the rows in the cursor and execute the appropriate 
		// sqlModel setter method, this will populate the model object.
		cursor.moveToFirst();
		for (int i = 0; !cursor.isAfterLast(); i++) {
			T baseSQLModelObject = (T)ReflectionHelper.newInstance(c);
			
			for (int x = 0; x < cursor.getColumnCount(); x++) {
				Method executeMethod = setMethods.get(cursor.getColumnName(x));
				setColumnValue(baseSQLModelObject, columnTypeMap, executeMethod, cursor, x);
			}
			
			result[i] = baseSQLModelObject;
			cursor.moveToNext();
		}
		
		return (T[])result;
	}


	/**
	 * Use reflection to access the setter methods of the baseModel instance, then set
	 * the value of whatever type is associated with the current cursorColumn. The method
	 * cursor.getType(int) is only available in API 11+, so to determine the type, each table column
	 * has a key value pair comprising of columnName/dataType
	 * @param	baseSQLModel	The object to invoke the setter method on
	 * @param	columnTypeMap	columnName/dataType pairs
	 * @param	executeMethod	The setter method that will be invoked on the provided object
	 * @param	cursor	The database cursor to get the value from
	 * @param	index	The cursor index that the desired value is positioned at
	 */
	private static void setColumnValue(Object baseSQLModel, HashMap<String,Integer> columnTypeMap, Method executeMethod, Cursor cursor, int index) {
		int columnType = columnTypeMap.get(cursor.getColumnName(index));

		switch (columnType) {
			case BaseModel.FIELD_STRING:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getString(index));
			break;
			
			case BaseModel.FIELD_INTEGER:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getInt(index));
			break;
			
			case BaseModel.FIELD_LONG:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getLong(index));
			break;
			
			case BaseModel.FIELD_BLOB:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getBlob(index));
			break;
			
			case BaseModel.FIELD_FLOAT:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getFloat(index));
			break;
		}
	}
}
