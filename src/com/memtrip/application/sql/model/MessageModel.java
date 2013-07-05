package com.memtrip.application.sql.model;

import java.util.HashMap;

import com.memtrip.application.sql.BaseSQLModel;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Message SQLite table model
 * NOTE: Ensure that the column names defined here match the SQLite table column names
 * @author	memtrip
 */
public class MessageModel extends BaseSQLModel {
	private int id;
	private String body;
	private String date;
	private int views;
	private float rating;
	private byte[] profilePicture;
	
	public static final String TABLE_NAME = "Message";
	private static final String COLUMN_BODY = "body";
	private static final String COLUMN_DATE = "date";
	private static final String COLUMN_VIEWS = "views";
	private static final String COLUMN_RATING = "rating";
	private static final String COLUMN_PROFILE_PICTURE = "profilePicture";
	
	public int getId() {
		return id;
	}

	public void setId(int newVal) {
		id = newVal;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String newVal) {
		body = newVal;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String newVal) {
		date = newVal;
	}
	
	public int getViews() {
		return views;
	}
	
	public void setViews(int newVal) {
		views = newVal;
	}
	
	public float getRating() {
		return rating;
	}
	
	public void setRating(float newVal) {
		rating = newVal;
	}
	
	public byte[] getProfilePicture() {
		return profilePicture;
	}
	
	public void setProfilePicture(byte[] newVal) {
		profilePicture = newVal;
	}
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}
	
	@Override
	public HashMap<String,Integer> getModelColumnTypeMap() {
		HashMap<String,Integer> modelColumns = new HashMap<String,Integer>();
		modelColumns.put(COLUMN_BODY, Cursor.FIELD_TYPE_STRING);
		modelColumns.put(COLUMN_DATE, Cursor.FIELD_TYPE_STRING);
		modelColumns.put(COLUMN_VIEWS, Cursor.FIELD_TYPE_INTEGER);
		modelColumns.put(COLUMN_RATING, Cursor.FIELD_TYPE_FLOAT);
		modelColumns.put(COLUMN_PROFILE_PICTURE, Cursor.FIELD_TYPE_BLOB);
	
		return modelColumns;
	}
	
	@Override
	public ContentValues toContentValues() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_BODY, getBody());
		contentValues.put(COLUMN_DATE, getDate());
		contentValues.put(COLUMN_VIEWS, getViews());
		contentValues.put(COLUMN_RATING, getRating());
		contentValues.put(COLUMN_PROFILE_PICTURE, getProfilePicture());
		return contentValues;
	}
	
	@Override
	public String toString() {
		return "";
	}

	@Override
	public String getNullColumnHack() {
		return null;
	}
}
