android-sqlite-wrapper
======================

This repository contains wrapper classes that encapsulate all Android SQLite behaviour in a set of generic methods. The provider allows database tables to be defined in models classes, the model objects can then be inserted or selected by helper methods. 

TODO:
- Add methods that allow groupBy and having clauses to the SQLProvider
- Validate SQLModel classes to ensure that the properties of the class match the values of the SQL table, this will help with debugging any SQL issues
- Implement unit tests for SQLProvider and SQLDatabaseHelper methods

Example usage:
- Copy the packages directly into your Android project

```java
// create a new instance of a model class and populate it. The class represents
// a row in the "Message" database table
MessageModel messageModel = new MessageModel();
messageModel.setBody("This is a message body");
messageModel.setDate("10/08/2012");
messageModel.setViews(31431232);
messageModel.setRating(3.5f);
messageModel.setProfilePicture(messageModel.getBody().getBytes());
	
// open SQLite database connection
SQLProvider sqlProvider = new SQLProvider(getBaseContext());
sqlProvider.open();
		
// insert model into Message table
long id = sqlProvider.insert(MessageModel.class, messageModel);
		
// select all from Message table as a MessageModel[] array
MessageModel[] messageArray = sqlProvider.selectAll(MessageModel.class, new MessageModel(), null, null);
		
// close the SQLite database connection
sqlProvider.close();
```