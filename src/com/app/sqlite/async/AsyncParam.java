package com.app.sqlite.async;

import java.util.ArrayList;
import java.util.Map;

import com.app.sqlite.async.base.IParam;
import com.app.sqlite.base.BaseModel;

/**
 * @author samkirton
 */
public class AsyncParam implements IParam {
	private BaseModel mBaseModelValue;
	private BaseModel[] mBaseModelArrayValue;
	private ArrayList<Map<String,Object>> mMapValue;
	private String mWhereClause;
	private String[] mWhereArgs;
	private String mOrder;
	private String mLimit;
	private Class<?> mClassDef;
	
	public BaseModel getBaseModelValue() {
		return mBaseModelValue;
	}
	
	public AsyncParam setBaseModelValue(BaseModel newVal) {
		mBaseModelValue = newVal;
		return this;
	}
	
	public BaseModel[] getBaseModelArrayValue() {
		return mBaseModelArrayValue;
	}
	
	public AsyncParam setBaseModelArrayValue(BaseModel[] newVal) {
		mBaseModelArrayValue = newVal;
		return this;
	}
	
	public ArrayList<Map<String,Object>> getMapValue() {
		return mMapValue;
	}
	
	public AsyncParam setMapalue(ArrayList<Map<String,Object>> newVal) {
		mMapValue = newVal;
		return this;
	}
	
	public String getWhereClause() {
		return mWhereClause;
	}
	
	public AsyncParam setWhereClause(String newVal) {
		mWhereClause = newVal;
		return this;
	}
	
	public String[] getWhereArgs() {
		return mWhereArgs;
	}
	
	public AsyncParam setWhereArgs(String[] newVal) {
		mWhereArgs = newVal;
		return this;
	}
	
	public String getOrder() {
		return mOrder;
	}
	
	public AsyncParam setOrder(String newVal) {
		mOrder = newVal;
		return this;
	}
	
	public String getLimit() {
		return mLimit;
	}
	
	public AsyncParam setLimit(String newVal) {
		mLimit = newVal;
		return this;
	}
	
	public Class<?> getClassDef() {
		return mClassDef;
	}
	
	public AsyncParam setClassDef(Class<?> newVal) {
		mClassDef = newVal;
		return this;
	}
}
