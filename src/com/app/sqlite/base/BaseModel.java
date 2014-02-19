package com.app.sqlite.base;

import java.util.HashMap;

import android.content.ContentValues;

/**
 * The base class of all SQL models
 * @author	memtrip
 */
public abstract class BaseModel {
	public static final int FIELD_STRING = 0;
	public static final int FIELD_INTEGER = 1;
	public static final int FIELD_LONG = 2;
	public static final int FIELD_FLOAT = 3;
	public static final int FIELD_BLOB = 4;
	
	/**
	 * @return	the name of the SQL table this map will be saved to
	 */
	public abstract String getTableName();
	
	/**
	 * @return	All the columns/types associated with the model
	 */
	public abstract HashMap<String,Integer> getModelColumnTypeMap();
	
	/**
	 * @return	All the column names associated with the model
	 */
	public String[] getModelColumns() {
		Object[] modelColumnsObject = getModelColumnTypeMap().keySet().toArray();
		String[] modelColumnsString = new String[modelColumnsObject.length];
		
		for (int i = 0; i < modelColumnsObject.length; i++) {
			modelColumnsString[i] = modelColumnsObject[i].toString();
		}
		
		return modelColumnsString;
	}
	
	/**
	 * @return	the model converted into a ContentValues object ready to be inserted
	 * into the SQLite database. 
	 * 
	 * NOTE: ENSURE YOU DO NOT ATTEMPT TO INSERT VALUES INTO PRIMARY KEY FIELDS, YOU WILL
	 * RECEIVE A CONSTRAINT EXCEPTION.
	 */
	public abstract ContentValues toContentValues();
	
	/**
	 * @return	SQLite cannot insert empty rows, a column that is safe to insert NULL into
	 * must be provided. E.G:
	 * INVALID: insert into user;
	 * VALID: insert into user (username) VALUES (NULL);
	 */
	public abstract String getNullColumnHack();
}
