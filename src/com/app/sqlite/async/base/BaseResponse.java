package com.app.sqlite.async.base;

/**
 * @author samkirton
 */
public abstract class BaseResponse {
	private boolean mIsValid = true;
	
	public boolean isValid() {
		return mIsValid;
	}
	
	public void setIsValid(boolean newVal) {
		mIsValid = newVal;
	}
}
