package com.app.sqlite;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.sqlite.async.AsyncParam;
import com.app.sqlite.async.AsyncResponse;
import com.app.sqlite.async.base.BaseAsyncTask;
import com.app.sqlite.async.base.BaseAsyncTask.SQLAsyncCallback;
import com.app.sqlite.async.base.BaseResponse;
import com.app.sqlite.async.base.IParam;
import com.app.sqlite.base.BaseModel;

/**
 * A wrapper class that encapsulates all SQLite behavior into a set
 * of generic methods
 * @author	memtrip
 */
public class SQLProvider implements Closeable {
	private SQLOperation mSQLOperation;
	
	public SQLProvider(SQLiteDatabase database) {
		mSQLOperation = new SQLOperation(database);
	}
	
	/**
	 * Insert a baseSQLModel into the table that relates to the baseSQLModel type
	 * @param	baseModel	The object being inserted into the SQLite table
	 * @return	The unique id of the created row
	 */
	public long insert(BaseModel baseModel) { 
		return mSQLOperation.insert(baseModel);
	}
	
	/**
	 * [ASYNC] Insert a baseSQLModel into the table that relates to the baseSQLModel type
	 * @param	baseModel	The object being inserted into the SQLite table
	 * @param	asyncCallback	Where the asynctask should send its result to
	 * @param	context	The android application context
	 */
	public void insertAsync(BaseModel baseModel, SQLAsyncCallback asyncCallback, Context context) {
		InsertAsync insertAsync = new InsertAsync(context);
		insertAsync.setSQLAsyncCallback(asyncCallback);
		insertAsync.execute(new AsyncParam().setBaseModelValue(baseModel));
	}
	
	/**
	 * Insert an array of baseModel into the table that relates to the array database
	 * @param	baseModelArray	The array of objects being inserted into the SQLite table
	 * @return	The unique ids of the created rows
	 */
	public long[] insertArray(BaseModel[] baseModelArray) { 
		return mSQLOperation.insertArray(baseModelArray); 
	}
	
	/**
	 * [ASYNC] Insert an array of baseModel into the table that relates to the array database
	 * @param	baseModelArray	The array of objects being inserted into the SQLite table
	 * @param	asyncCallback	Where the asynctask should send its result to
	 * @param	context	The android application context
	 */
	public void insertArrayAsync(BaseModel[] baseModelArray, SQLAsyncCallback asyncCallback, Context context) {
		InsertArrayAsync insertArrayAsync = new InsertArrayAsync(context);
		insertArrayAsync.setSQLAsyncCallback(asyncCallback);
		insertArrayAsync.execute(new AsyncParam().setBaseModelArrayValue(baseModelArray));
	}
	
	/**
	 * Updates the row associated with the provided baseModeland the whereClause provided
	 * @param	baseModel	An instance of the baseSQLModel class that is being updated
	 * @param	whereClause	The condition of the update
	 * @param 	whereArgs	The condition arguments
	 * @return	The amount of rows that have been updated
	 */
	public int update(BaseModel baseModel, String whereClause, String[] whereArgs) { 
		return mSQLOperation.update(baseModel, whereClause, whereArgs);
	}
	
	
	/**
	 * [ASYNC] Updates the row associated with the provided baseModeland the whereClause provided
	 * @param	baseModel	An instance of the baseSQLModel class that is being updated
	 * @param	whereClause	The condition of the update
	 * @param 	whereArgs	The condition arguments
	 * @param	asyncCallback	Where the asynctask should send its result to
	 * @param	context	The android application context
	 * @return	The amount of rows that have been updated
	 */
	public void update(BaseModel[] baseModelArray, String whereClause, String[] whereArgs, SQLAsyncCallback asyncCallback, Context context) {
		UpdateAsync updateAsync = new UpdateAsync(context);
		updateAsync.setSQLAsyncCallback(asyncCallback);
		updateAsync.execute(
			new AsyncParam().setBaseModelArrayValue(baseModelArray).setWhereClause(whereClause).setWhereArgs(whereArgs)
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
		return mSQLOperation.selectAll(c, baseModel, order, limit);
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
		return mSQLOperation.selectByWhereClause(c, baseModel, whereClause, conditions, order, limit);
	}
	
	/**
	 * Selects a results map that matches the raw query provided. The map keys relate to 
	 * the column names that are being selected
	 * @param	query	The query to fetch the results for
	 * @param	args	The arguments for the query
	 * @param	modelsInQuery	The model objects used within the query
	 * @return	A map of the row data values returned
	 */
	public ArrayList<Map<String,Object>> rawSelectQuery(String query, String[] args, Class<?>[] modelsInQuery) {
		return mSQLOperation.rawSelectQuery(query, args, modelsInQuery);
	}
	
	/**
	 * Count the number of rows in the provided table
	 * @param	c	The baseModel class that should have its rows counted
	 * @return	The number of rows in the table
	 */
	public int count(Class<?> c) {
		return mSQLOperation.count(c);
	}

	/**
	 * Truncate all the rows of the provided baseModel class ref
	 * @param	c	The baseModel class that should be truncated
	 */
	public void truncate(Class<?> c) {
		mSQLOperation.truncate(c);
	}
	
	/**
	 * Delete rows from the baseModel class ref based on the column and value provided
	 * @param	c	The baseModel class that should perform the delete
	 * @param	column	The where clause column
	 * @param	value	The where clause value
	 * @return
	 */
	public int deleteBy(Class<?> c, String column, String value) {
		return mSQLOperation.deleteBy(c, column, value);
	}
	
	/**
	 * Asynchronous version of insert(BaseModel baseModel) 
	 */
	private class InsertAsync extends BaseAsyncTask {

		public InsertAsync(Context context) { super(context); }

		@Override
		protected BaseResponse run(IParam param) {
			AsyncParam asyncParam = (AsyncParam)param;
			AsyncResponse asyncResponse = new AsyncResponse();
			
			long rowId = mSQLOperation.insert(asyncParam.getBaseModelValue());
			
			asyncResponse.setIsValid(rowId != -1);
			asyncResponse.setLongValue(rowId);
			return asyncResponse;
		}
	}
	
	/**
	 * Asynchronous version of insertArray(BaseModel[] baseModelArray) 
	 */
	private class InsertArrayAsync extends BaseAsyncTask {

		public InsertArrayAsync(Context context) { super(context); }

		@Override
		protected BaseResponse run(IParam param) {
			AsyncParam asyncParam = (AsyncParam)param;
			AsyncResponse asyncResponse = new AsyncResponse();
			
			long[] rowIds = mSQLOperation.insertArray(asyncParam.getBaseModelArrayValue());
			
			asyncResponse.setIsValid(asyncParam.getBaseModelArrayValue().length == rowIds.length);
			asyncResponse.setLongArrayValue(rowIds);
			return asyncResponse;
		}
	}
	
	/**
	 * Asynchronous version of update(BaseModel baseModel, String whereClause, String[] whereArgs)
	 */
	private class UpdateAsync extends BaseAsyncTask {

		public UpdateAsync(Context context) { super(context); }

		@Override
		protected BaseResponse run(IParam param) {
			AsyncParam asyncParam = (AsyncParam)param;
			AsyncResponse asyncResponse = new AsyncResponse();
			
			mSQLOperation.update(
				asyncParam.getBaseModelValue(), 
				asyncParam.getWhereClause(), 
				asyncParam.getWhereArgs()
			);

			return asyncResponse;
		}
	}

	@Override
	public void close() throws IOException {
		mSQLOperation.getDatabase().close();
	}
}
