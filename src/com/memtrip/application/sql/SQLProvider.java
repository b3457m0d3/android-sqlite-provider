package com.memtrip.application.sql;


import java.io.Closeable;

import com.memtrip.application.utils.ReflectionHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * A wrapper class that encapsulates all SQLite behavior into a set
 * of generic methods
 * @author	memtrip
 */
public class SQLProvider implements Closeable {
	private SQLiteDatabase database;
	private SQLDatabaseHelper sqlDatabaseHelper;
	
	private static final String FIELD_TABLE_NAME = "TABLE_NAME";
	
	/**
	 * Constructor
	 * @param	context	baseContext()
	 */
	public SQLProvider(Context context) {
		sqlDatabaseHelper = new SQLDatabaseHelper(context);
	}
	
	/**
	 * Create a new instance of the sqlite databsae
	 */
	public void open() {
		database = sqlDatabaseHelper.getWritableDatabase();
	}
	
	/**
	 * Close the SQL provider
	 */
	public void close() {
		sqlDatabaseHelper.close();
	}
	
	/**
	 * Insert a baseSQLModel into the table that relates to the provided class interface
	 * @param	c	The baseSQLModel class interface that relates to a SQLite table
	 * @param	baseSQLModel	The object being inserted into the SQLite table
	 * @return	The unique id of the created row
	 */
	public long insert(Class<?> c, BaseSQLModel baseSQLModel) {
		String tableName = ReflectionHelper.getStaticStringField(c, FIELD_TABLE_NAME);
		return database.insertOrThrow(tableName, baseSQLModel.getNullColumnHack(), baseSQLModel.toContentValues());
	}
	
	/**
	 * Selects all baseSQLModel elements from the provided class, it requires a baseSQLModel
	 * instance to retrieve the columns that should be populated 
	 * TODO: This method should return a genric that can be cast to an BaseSQLModel class
	 * @param	c	The baseSQLModel class that is being selected from the database
	 * @param	baseSQLModel	An instance of the baseSQLModel class that is being selected from the database
	 * @param	order	The order results should be returned (ASC or DESC)
	 * @param	limit	How many results should be returned
	 * @return	An array of BaseSQLModel results
	 */
	public <T> T[] selectAll(Class<T> c, BaseSQLModel baseSQLModel, String order, String limit) {
		String[] columns = baseSQLModel.getModelColumns();
		String tableName = ReflectionHelper.getStaticStringField(c, FIELD_TABLE_NAME);
		
		Cursor cursor = database.query(tableName, 
			columns, 
			null, 
			null, 
			null, 
			null, 
			order, 
			limit
		);
		
		T[] result = SQLDatabaseHelper.retreiveSQLSelectResults(c, baseSQLModel, cursor);
		return (T[])result;
	}
	
	/**
	 * Selects baseSQLModel elements that match the provided whereClause and conditions
	 * @param	c	The baseSQLModel class that is being selected from the database
	 * @param	baseSQLModel	An instance of the baseSQLModel class that is being selected from the database
	 * @param	whereClause	The where clause that should be applied to the database query
	 * @param	conditions	The conditions of the where clause
	 * @param	order	The order results should be returned (ASC or DESC)
	 * @param	limit	How many results should be returned
	 * @return	An array of BaseSQLModel results
	 */
	public <T> T[] selectByWhereClause(Class<T> c, BaseSQLModel baseSQLModel, String whereClause, String[] conditions, String order, String limit) {
		String[] columns = baseSQLModel.getModelColumns();
		String tableName = ReflectionHelper.getStaticStringField(c, FIELD_TABLE_NAME);
		
		Cursor cursor = database.query(tableName, 
			columns, 
			whereClause, 
			conditions, 
			null, 
			null, 
			order, 
			limit
		);
		
		T[] result = SQLDatabaseHelper.retreiveSQLSelectResults(c, baseSQLModel, cursor);
		return (T[])result;
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
}
