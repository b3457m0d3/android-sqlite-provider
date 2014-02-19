package com.app.sqlite;

import java.io.Closeable;
import java.io.IOException;

import com.app.sqlite.base.BaseModel;
import com.app.sqlite.helper.ReflectionHelper;
import com.app.sqlite.helper.DatabaseHelper;

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
		return mDatabase.insertOrThrow(tableName, baseModel.getNullColumnHack(), baseModel.toContentValues());
	}
	
	/**
	 * Insert an array of baseSQLModel into the table that relates to the array database
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
	 * Selects all baseSQLModel elements from the provided class, it requires a baseSQLModel
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
	 * Selects baseSQLModel elements that match the provided whereClause and conditions
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
	 * @param	c	The baseSQLModel class that should have its rows counted
	 * @return	The number of rows in the table
	 */
	public <T> int count(Class<T> c) {
		String tableName = ReflectionHelper.getStaticStringField(c, FIELD_TABLE_NAME);
		Cursor cursor = mDatabase.rawQuery("select count(*) from " + tableName, null);
		
		// ensure there is at least one row and one column
		cursor.moveToFirst();
		if (cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
			return cursor.getInt(0);
		} else {
			return 0;
		}
	}
	
	/**
	 * 
	 */
	public void truncate() {
		
	}
	
	/**
	 * 
	 */
	public void deleteByColumnKey() {
		
	}
	
	@Override
	public void close() throws IOException {
		mDatabase.close();
	}
}
