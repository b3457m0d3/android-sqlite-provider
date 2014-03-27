package com.app.sqlite;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;

import com.app.sqlite.base.BaseModel;
import com.app.sqlite.helper.ReflectionHelper;
import com.app.sqlite.helper.DatabaseHelper;
import com.app.sqlite.helper.StringHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * A wrapper class that encapsulates all SQLite behavior into a set
 * of generic methods
 * @author	memtrip
 */
public class SQLProvider implements Closeable {
	private SQLiteDatabase mDatabase;
	
	private static final String FIELD_TABLE_NAME = "TABLE_NAME";
	
	public SQLProvider(SQLiteDatabase database) {
		mDatabase = database;
	}
	
	/**
	 * Insert a baseSQLModel into the table that relates to the baseSQLModel type
	 * @param	baseModel	The object being inserted into the SQLite table
	 * @return	The unique id of the created row
	 */
	public long insert(BaseModel baseModel) {
		String tableName = ReflectionHelper.getStaticStringField(baseModel.getClass(), FIELD_TABLE_NAME);
		return mDatabase.insertOrThrow(tableName, null, baseModel.toContentValues());
	}
	
	/**
	 * Insert an array of baseModel into the table that relates to the array database
	 * @param	baseModelArray	The array of objects being inserted into the SQLite table
	 * @return	The unique ids of the created rows
	 */
	public long[] insertArray(BaseModel[] baseModelArray) {
		if (baseModelArray != null && baseModelArray.length > 0) {
			long[] respnseIds = new long[baseModelArray.length];
			
			for (int i = 0; i < baseModelArray.length; i++) {
				respnseIds[i] = this.insert(baseModelArray[i]);
			}
			
			return respnseIds;
		} else {
			return null;
		}
	}
	
	/**
	 * Updates the row associated with the provided baseModeland the whereClause provided
	 * @param	baseModel	An instance of the baseSQLModel class that is being updated
	 * @param	whereClause	The condition of the update
	 * @param 	whereArgs	The condition arguments
	 * @return	The amount of rows that have been updated
	 */
	public int update(BaseModel baseModel, String whereClause, String[] whereArgs) {
		String tableName = ReflectionHelper.getStaticStringField(baseModel.getClass(), FIELD_TABLE_NAME);
		
		ContentValues contentValues = new ContentValues();
		String[] modelColumns = baseModel.getModelColumns();
		for (String column : modelColumns) {
			Method executeMethod = ReflectionHelper.getMethod(baseModel.getClass(), StringHelper.buildGetterMethodNameFromVariableName(column));
			Object object = ReflectionHelper.invokeMethod(baseModel, executeMethod);
			
			if (object instanceof String) {
				String value = (String)object;
				contentValues.put(column, value);
			} else if (object instanceof byte[]) {
				byte[] value = (byte[])object;
				contentValues.put(column, value);
			}
		}
		
		return mDatabase.update(
			tableName, 
			contentValues, 
			whereClause, 
			whereArgs
		);
	}
	
	/**
	 * Selects all baseModel elements from the provided class, it requires a baseSQLModel
	 * instance to retrieve the columns that should be populated 
	 * @param	c	The baseSQLModel class that is being selected from the database
	 * @param	baseModel	An instance of the baseSQLModel class that is being selected from the database
	 * @param	order	The order results should be returned (ASC or DESC)
	 * @param	limit	How many results should be returned
	 * @return	An array of BaseSQLModel results
	 */
	public <T> T[] selectAll(Class<T> c, BaseModel baseModel, String order, String limit) {
		String[] columns = baseModel.getModelColumns();
		String tableName = ReflectionHelper.getStaticStringField(c, FIELD_TABLE_NAME);
		
		Cursor cursor = mDatabase.query(tableName, 
			columns, 
			null, 
			null, 
			null, 
			null, 
			order, 
			limit
		);
		
		T[] result = DatabaseHelper.retreiveSQLSelectResults(c, cursor, baseModel);
		return (T[])result;
	}
	
	/**
	 * Selects baseModel elements that match the provided whereClause and conditions
	 * @param	c	The baseSQLModel class that is being selected from the database
	 * @param	baseModel	An instance of the baseSQLModel class that is being selected from the database
	 * @param	whereClause	The where clause that should be applied to the database query
	 * @param	conditions	The conditions of the where clause
	 * @param	order	The order results should be returned (ASC or DESC)
	 * @param	limit	How many results should be returned
	 * @return	An array of BaseSQLModel results
	 */
	public <T> T[] selectByWhereClause(Class<T> c, BaseModel baseModel, String whereClause, String[] conditions, String order, String limit) {
		String[] columns = baseModel.getModelColumns();
		String tableName = ReflectionHelper.getStaticStringField(c, FIELD_TABLE_NAME);
		
		Cursor cursor = mDatabase.query(tableName, 
			columns, 
			whereClause, 
			conditions, 
			null, 
			null, 
			order, 
			limit
		);
		
		T[] result = DatabaseHelper.retreiveSQLSelectResults(c, cursor, baseModel);
		return (T[])result;
	}
	
	/**
	 * Count the number of rows in the provided table
	 * @param	c	The baseModel class that should have its rows counted
	 * @return	The number of rows in the table
	 */
	public int count(Class<?> c) {
		String tableName = ReflectionHelper.getStaticStringField(c, FIELD_TABLE_NAME);
		Cursor cursor = mDatabase.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
		
		// ensure there is at least one row and one column
		cursor.moveToFirst();
		if (cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
			return cursor.getInt(0);
		} else {
			return 0;
		}
	}

	/**
	 * Truncate all the rows of the provided baseModel class ref
	 * @param	c	The baseModel class that should be truncated
	 */
	public void truncate(Class<?> c) {
		String tableName = ReflectionHelper.getStaticStringField(c, FIELD_TABLE_NAME);
		mDatabase.rawQuery("DELETE FROM " + tableName, null);
	}
	
	/**
	 * Delete rows from the baseModel class ref based on the column and value provided
	 * @param	c	The baseModel class that should perform the delete
	 * @param	column	The where clause column
	 * @param	value	The where clause value
	 * @return
	 */
	public int deleteBy(Class<?> c, String column, String value) {
		String tableName = ReflectionHelper.getStaticStringField(c, FIELD_TABLE_NAME);
		return mDatabase.delete(
			tableName, 
			column + " = ?", 
			new String[] {value}
		);
	}
	
	@Override
	public void close() throws IOException {
		mDatabase.close();
	}
}
