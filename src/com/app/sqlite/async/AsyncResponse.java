package com.app.sqlite.async;

import java.util.ArrayList;
import java.util.Map;

import com.app.sqlite.async.base.BaseResponse;
import com.app.sqlite.base.BaseModel;

/**
 * @author samkirton
 */
public class AsyncResponse extends BaseResponse {
	private long mLongValue;
	private long[] mLongArrayValue;
//	private int mIntValue;
//	private int[] mIntArrayValue;
	private BaseModel mBaseModelValue;
	private BaseModel[] mBaseModelArrayValue;
	private ArrayList<Map<String,Object>> mMapValue;
	
	public long getLongValue() {
		return mLongValue;
	}
	
	public void setLongValue(long newVal) {
		mLongValue = newVal;
	}
	
	public long[] getLongArrayValue() {
		return mLongArrayValue;
	}
	
	public void setLongArrayValue(long[] newVal) {
		mLongArrayValue = newVal;
	}
	
//	public int getIntValue() {
//		return mIntValue;
//	}
//	
//	public void setIntValue(int newVal) {
//		mIntValue = newVal;
//	}
//	
//	public int[] getIntArrayValue() {
//		return mIntArrayValue;
//	}
//	
//	public void setIntArrayValue(int[] newVal) {
//		mIntArrayValue = newVal;
//	}
	
	public BaseModel getBaseModelValue() {
		return mBaseModelValue;
	}
	
	public void setBaseModelValue(BaseModel newVal) {
		mBaseModelValue = newVal;
	}
	
	public BaseModel[] getBaseModelArrayValue() {
		return mBaseModelArrayValue;
	}
	
	public void setBaseModelArrayValue(BaseModel[] newVal) {
		mBaseModelArrayValue = newVal;
	}
	
	public ArrayList<Map<String,Object>> getMapValue() {
		return mMapValue;
	}
	
	public void setMapalue(ArrayList<Map<String,Object>> newVal) {
		mMapValue = newVal;
	}
}
