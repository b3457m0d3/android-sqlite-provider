package com.app.sqlite.async.base;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.AsyncTask;

/**
 * @author samkirton
 */
public abstract class BaseAsyncTask extends AsyncTask<IParam, Void, BaseResponse>  {
	private Context mContext;
	private SQLAsyncCallback mSQLAsyncCallback;
	private BaseResponse mResult;
	private BaseAsyncTask mInstanceRef;
	
	public interface SQLAsyncCallback {
		public void onSQLTaskFinished(BaseResponse response, BaseAsyncTask baseAsyncTask);
	}
	
	/**
	 * Run the result of the AsyncTask on the GUI thread
	 */
	private Runnable doUpdateGUI = new Runnable() {
		public void run() { 
			mSQLAsyncCallback.onSQLTaskFinished(mResult, mInstanceRef);
		} 
	};
	
	public void setSQLAsyncCallback(SQLAsyncCallback newVal) {
		mSQLAsyncCallback = newVal;
	}
	
	public Context getContext() {
		return mContext;
	}
	
	/**
	 * Constructor
	 * @param	applicationContext	The application context
	 */
	public BaseAsyncTask(Context context) {
		if (!(context instanceof Activity) && !(context instanceof Service)) {
			throw new IllegalArgumentException("The AsyncTask context must be able to cast into Activity or Service");
		}
		
		mContext = context;
	}
	
	/**
	 * An override of the AsyncTask execute method that takes a single
	 * BaseContext param
	 * @param	baseContext	The BaseContext to use as params
	 */
	public void execute(IParam param) {
		IParam[] params = new IParam[1];
		params[0] = param;
		this.execute(params);
	}
	
	@Override
	protected BaseResponse doInBackground(IParam... params) {
		IParam param = null;
		if (params !=  null && params.length > 0) {
			param = params[0];
		}
		
		return run(param);
	}
	
	@Override
	protected void onPostExecute(BaseResponse result) {
		mResult = result;
		mInstanceRef = this;
		
		if (mContext instanceof Activity) {
			((Activity)mContext).runOnUiThread(doUpdateGUI);
		} else if (mContext instanceof Service) {
			mSQLAsyncCallback.onSQLTaskFinished(mResult, mInstanceRef);
		}
	}
	
	@Override
	protected void onPreExecute() { }
	
	@Override
	protected void onProgressUpdate(Void... values) { }
	
	/**
	 * Run the async logic based on the context provided
	 * @param	param	The param for the logic
	 * @return	Return a response
	 */
	protected abstract BaseResponse run(IParam param);
}
